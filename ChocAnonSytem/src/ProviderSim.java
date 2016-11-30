import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.time.format.DateTimeFormatter;
@SuppressWarnings("unused")
public class ProviderSim {
	private ProviderDirectory pDirectory;
	private Transaction currentTransaction;
	private static Scanner in = new Scanner(System.in);
	
	public ProviderSim() throws FileNotFoundException 
	{
		pDirectory = new ProviderDirectory("Provider Directory.txt");
		currentTransaction = null;
	}
	public ProviderSim(String filenamePath) throws FileNotFoundException 
	{
		pDirectory = new ProviderDirectory(filenamePath);
		currentTransaction = null;

	}
	// steps the user through the process of entering in a provider ID
	// an integer is returned for external verification
	public int providerLogon(){
		String prompt = "Please enter your provider ID(9-digits): ";

		int providerID = promptForID(prompt);
		while (providerID == -1){
			System.out.println("Error: incorrect id format. Please try again.");
			providerID = promptForID(prompt);
		}
		return providerID; //return for verification purposes
	}
	// steps the user through the process of entering in a member ID
	public int memberLogon(){
		String prompt = "Please swipe the Members ID card or type in the member ID(9-digits): ";
		int memberID = promptForID(prompt);
		while (memberID == -1){
			System.out.println("Error: incorrect id format. Please try again.");
			memberID = promptForID(prompt);
		}
		return memberID; //return for verification purposes
	}
	// returns a valid transaction
	public Transaction send(){
		Transaction toSend;
		
		try {
		toSend = new Transaction(currentTransaction);
		}
		catch (NullPointerException e){
			return null;
		}
		return toSend;
	}
	// uses the provider directory to get a fee
	// for the current transactions service code
	private float getFee(int serviceNumber){
		Service ofCost = new Service();
		pDirectory.searchByNumAndCopy(serviceNumber, ofCost);
		return ofCost.getFee();
	}
	// prints n new lines to clear the screen
	public void clearScreen(){
		int n = 100;
		for (int i=0; i < n; ++i)
			System.out.println();
	}
	public void singleLineMessage(String message){
		clearScreen();
		System.out.println(message + " Press enter to continue.");
		
		//wait for enter input
		in.nextLine();
		
		clearScreen();
	}
	//prompts for id returns -1 if incorrect format or less than 9 digits
	private int promptForID(String prompt){
		Integer id;
		String idString;
		
		idString = getInput(prompt);
		
		try{
		id = Integer.parseInt(idString);
		}
		catch (NumberFormatException e){
			return -1;
		}
		if ( id.toString().length() > 9 )
			return -1;
		
		return id;
	}
	
	private LocalDate promptForDate(String prompt){
		LocalDate inputDate = null;
		String dateString = null;
		
		dateString = getInput(prompt);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		
		try{
			inputDate = inputDate.parse(dateString,format);
		}
		catch (DateTimeParseException e){
			System.out.println("Error: invalid format for date. Please try again: ");
			return null;
		}
		
		return inputDate;

	}
	private int promptForServiceCode(String prompt){
		Integer code;
		String codeString;
		
		codeString = getInput(prompt);
		
		try{
		code = Integer.parseInt(codeString);
		}
		catch (NumberFormatException e){
			return -1;
		}
		if ( code.toString().length() > 6 )
			return -1;
		
		return code;
	}

	private String getInput(String prompt){
		String input = null;
		System.out.println(prompt + "(or ~ for provider directory)");
		input = in.nextLine();
		while (input.contains("~") ){
			pDirectory.displayAllByName();
			System.out.println(prompt + "(or ~ for provider directory)");
			input = in.nextLine();
		}
		
		return input;
	}
	private void writeTransactionToFile(Transaction toWrite, String filenamePath){
	}
	// steps the user through processing of a member
	// precondition: member and provider must be verified
	// postcondition: a valid transaction is prepared
	public void processTransaction(MemberDB members, ProviderDB providers){
		int memberID = -1;
		int providerID = -1;
		int serviceCode = -1;
		Boolean memberValid = false;
		Boolean providerValid = false;
		currentTransaction = new Transaction();
		LocalDate inputDate = null;
		
		//member login(initial)
		while (memberValid != true){
		memberID = memberLogon();
			if (members.isValid(memberID)){
				if (members.isSuspended(memberID)){
					singleLineMessage("Member invalid: Member suspended.");
					memberValid = false;
				}
				else{				
					singleLineMessage("Member Valid: Validated.");
					memberValid = true;
				}
			}
			else{
				singleLineMessage("Member invalid: Invalid number.");
				memberValid = false;
				}
		}
		
		//provider login(initial)
		while (providerValid != true){
			providerID = providerLogon();
			if (providers.searchByProNum(providerID) == 1){
				singleLineMessage("Provider valid: Validated.");
				providerValid = true;
			}
			else{
				singleLineMessage("Provider invalid: Invalid Number.");
				providerValid = false;
			}
		}
		// member log-on again -> date prompt
		System.out.println("Please swipe the card again or enter the members ID: ");
		
		while (memberValid != true){
		memberID = memberLogon();
			if (members.isValid(memberID)){
				if (members.isSuspended(memberID)){
					singleLineMessage("Member invalid: Member suspended.");
					memberValid = false;
				}
				else{				
					singleLineMessage("Member Valid: Validated.");
					memberValid = true;
				}
			}
			else{
				singleLineMessage("Member invalid: Invalid number.");
				memberValid = false;
				}
		}
		
		// prompt for date
		do
		{
			inputDate = promptForDate("Please enter todays date in the format MM-DD-YYYY: ");
		} while (inputDate == null);
		
		// prompt for service code
		Boolean invalidSelection = true;
		Service found = new Service();
		while (invalidSelection == true)
		{
			int isValidServiceCode = 0;
			do
			{
				serviceCode = promptForServiceCode("Please enter the 6-digit service code: ");
				isValidServiceCode = pDirectory.searchByNum(serviceCode);
				if ( isValidServiceCode == 0 )
					System.out.println("Error: non-existant service code. Please try again.");
			}while (isValidServiceCode == 0);
			
			//display service name
			found = pDirectory.searchByNumAndCopy(serviceCode, found);
			System.out.println("Service name: " + found.getServiceName());
			
			String confirm = getInput("Is this correct(type y or n): ");
			while (!(confirm.compareToIgnoreCase("y") == 0 || confirm.compareToIgnoreCase("n") == 0))
			{
				System.out.println("Error: invalid response. Please try again.");
				confirm = getInput("Is this correct(type y or n): ");
			}
			
			if (confirm.compareToIgnoreCase("n") == 0){
				invalidSelection = true;
			}
			else
				invalidSelection = false;
		}
		
		// optional comment
		String comment = getInput("Enter a comment on the service provided(optional): ");
		
		// write a copy to the provider system
		currentTransaction = new Transaction();
		currentTransaction.setMemberNumber(memberID);
		currentTransaction.setProviderNumber(providerID);
		currentTransaction.setServiceCode(serviceCode);
		currentTransaction.setTransactionDate(inputDate);
		LocalDateTime recieved = LocalDateTime.now();
		currentTransaction.setDateTimeRecieved(recieved);
		currentTransaction.setComment(comment);
		
		String filename = "";
		filename.concat(providerID + "_" + recieved.toString() + ".txt");
		writeTransactionToFile(currentTransaction, filename);
		
		// fee lookup -> display 
		singleLineMessage("Service Fee: " + found.getFee());
		// log-off
	}
	
	public static void main(String[] args) {
		ProviderSim obj;
		MemberDB members;
		ProviderDB providers;
		members = new MemberDB();
		try{
			providers = new ProviderDB("ProviderDB.txt");
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return;
		}
		
		try{
		members.ReadFromFile("members.txt");
		}
		catch (Exception e){
			return;
		}
		
		//load ProviderSim
		try{
		obj = new ProviderSim();
		} catch (Exception e){
			return;
		}
		Transaction foo;
		obj.processTransaction(members, providers);
		foo = obj.send();
		
		foo.displayInformation();
		
	//	int i;
	//	i = obj.memberLogon();
	//	i = obj.providerLogon();
	//	System.out.print(i);
	//	obj.verifyMember(-1);
	}
}
