import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.*;
import java.util.LinkedList;
import java.util.Stack;

public class ReportGenerator {
	public ReportGenerator(){
	}
    public void generateProviderReportsAndEFTS(MemberDB m, ProviderDB p, TransactionDB t, ProviderDirectory pDir){
    Stack<Providers> pStack = null;
		LinkedList<Transaction> tList = null;
		PrintWriter writer = null;
		LocalDate today = LocalDate.now();
		String filename = null;
		pStack = p.stackProviders();
		
		if (pStack.isEmpty() == true)
			return;
		
		while (pStack.isEmpty() == false){
			Providers currentProvider = pStack.pop();
			today = LocalDate.now();
			
			filename = createProvFilename(currentProvider.GetID());
			
			//create new file from filename
			File pFile = new File(filename);
			try {
				pFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		
			try {
				writer = new PrintWriter(filename);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			writeProviderToFile(writer, currentProvider);
			
			tList = t.retrieveProviderTransactions(currentProvider.GetID());	
			
			float feeAccumulator = 0.0f;
			if (tList != null)
			{
				 writer.write("\nRecent Transactions(within the past week): \n");
				 int numTransactions = 0; //accumulator
				 feeAccumulator = 0.0f;
				//for each transaction
				 for (int i = 0; i < tList.size(); ++i)
				 {
					 Transaction trans = tList.get(i);
					 if (trans.isWithinPastWeek()){
						 
						 //get date of service
						 String transactionDate = trans.getTransactionDate().toString();
						 
						 //get recieved date
						 String recievedDate = trans.getDateTimeRecieved().toString();
						 
						 //get member name
						 Member found = m.retrieveMember(trans.getMemberNumber());
						 String memberName = found.GetName();
						 
						 //get member number
						 int memberNumber = found.GetID();
						 
						 //get service code
						 int serviceCode = trans.getServiceCode();
						 
						 //get fee
						 Service foundService = pDir.searchByNumAndCopy(serviceCode);
						 float fee = foundService.getFee();
						 
						 writer.write("Date of Transaction : " + transactionDate + "\n");
						 writer.write("Date transaction was recieved: " + recievedDate + "\n");
						 writer.write("Member Name: " + memberName + "\n");
						 writer.write("Member Number: " + memberNumber + "\n");
						 writer.write("Service Code: " + serviceCode + "\n");
						 writer.write("Fee to be paid: " + fee + "\n");
						 
						 ++numTransactions;
						 feeAccumulator += fee;
				 }
				 writer.write("Number of consultations this week: " + numTransactions + "\n");
				 writer.write("Total fee for the week: " + feeAccumulator + "\n");
			 }
			}	
			if (writer != null)
				writer.close();
			
			//before finishing for this provider - generate an EFT file
			String eftFilename = createEFTFilename(currentProvider.GetID());
			
			//create new eft file from filename
			File eFile = new File(eftFilename);
			try {
				eFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
			try {
				writer = new PrintWriter(eftFilename);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}	
			// write eft file - provider number - privder name - fee
			writer.write(currentProvider.GetID() + "\n");
			writer.write(currentProvider.GetName() + "\n");
			writer.write(String.valueOf(feeAccumulator));
			
			if (writer != null)
				writer.close();
		}
    }
	public void generateProviderReports(MemberDB m, ProviderDB p, TransactionDB t, ProviderDirectory pDir){
		Stack<Providers> pStack = null;
		LinkedList<Transaction> tList = null;
		PrintWriter writer = null;
		LocalDate today = LocalDate.now();
		String filename = null;
		pStack = p.stackProviders();
		
		if (pStack.isEmpty() == true)
			return;
		
		while (pStack.isEmpty() == false){
			Providers currentProvider = pStack.pop();
			today = LocalDate.now();
			
			filename = createProvFilename(currentProvider.GetID());
			
			//create new file from filename
			File pFile = new File(filename);
			try {
				pFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		
			try {
				writer = new PrintWriter(filename);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			writeProviderToFile(writer, currentProvider);
			
			tList = t.retrieveProviderTransactions(currentProvider.GetID());	
			
			if (tList != null)
			{
				 writer.write("\nRecent Transactions(within the past week): \n");
				 int numTransactions = 0; //accumulator
				 float feeAccumulator = 0.0f;
				//for each transaction
				 for (int i = 0; i < tList.size(); ++i)
				 {
					 Transaction trans = tList.get(i);
					 if (trans.isWithinPastWeek()){
						 
						 //get date of service
						 String transactionDate = trans.getTransactionDate().toString();
						 
						 //get recieved date
						 String recievedDate = trans.getDateTimeRecieved().toString();
						 
						 //get member name
						 Member found = m.retrieveMember(trans.getMemberNumber());
						 String memberName = found.GetName();
						 
						 //get member number
						 int memberNumber = found.GetID();
						 
						 //get service code
						 int serviceCode = trans.getServiceCode();
						 
						 //get fee
						 Service foundService = pDir.searchByNumAndCopy(serviceCode);
						 float fee = foundService.getFee();
						 
						 writer.write("Date of Transaction : " + transactionDate + "\n");
						 writer.write("Date transaction was recieved: " + recievedDate + "\n");
						 writer.write("Member Name: " + memberName + "\n");
						 writer.write("Member Number: " + memberNumber + "\n");
						 writer.write("Service Code: " + serviceCode + "\n");
						 writer.write("Fee to be paid: " + fee + "\n");
						 
						 ++numTransactions;
						 feeAccumulator += fee;
				 }
				 writer.write("Number of consultations this week: " + numTransactions + "\n");
				 writer.write("Total fee for the week: " + feeAccumulator + "\n");
			 }
			}	
		if (writer != null)
			writer.close();
		}
	}
    //writes to disk the service reports for each member
	public void generateMemberReports(MemberDB m, ProviderDB p, TransactionDB t, ProviderDirectory pDir){
		Stack<Member> mStack = null;
		LinkedList<Transaction> tList = null;
		PrintWriter writer = null;
		LocalDate today = LocalDate.now();
		String filename = null;
		mStack = m.stackMembers();
		
		if (mStack.isEmpty() == true){
			return;
		}
		
		
		while (mStack.isEmpty() == false){
			Member currentMember = mStack.pop();
			today = LocalDate.now();
			//create a filename for this member report
			filename = createMembFilename(currentMember.GetID());
			
			//create new file from filename
			File mFile = new File(filename);
			try {
				mFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		
			try {
				writer = new PrintWriter(filename);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			writeMemberToFile(writer, currentMember);
			
			//retrieve a list of transaction for that member
			tList = t.retrieveMemberTransactions(currentMember.GetID());
			
			if (tList != null){
				 writer.write("\nRecent Transactions(within the past week): \n");
				//for each transaction
				 for (int i = 0; i < tList.size(); ++i)
				 {
					 Transaction trans = tList.get(i);
					 if (trans.isWithinPastWeek()){
						 int providerNumber = trans.getProviderNumber();
						 
						 //get provider name
						 Person providerPerson = null;
						 providerPerson = p.searchByNumAndCopy(providerNumber, providerPerson);
						 String providerName = providerPerson.GetName();
						 
						 //get service name
						 String serviceName = null;
						 Service foundService;
						 foundService = pDir.searchByNumAndCopy(trans.getServiceCode());
						 serviceName = foundService.getServiceName();
						 
						 //get service date
						 String serviceDate = trans.getTransactionDate().toString();

						 writer.write("Service " + (i+1) + "\n");
						 writer.write(serviceDate + "\n");
						 //writer.write(providerNumber + "\n");
						 writer.write(providerName + "\n");
						 //writer.write(trans.getServiceCode() + "\n");
						 writer.write(serviceName + "\n");
				 }
			 }
			}	
		}
		if (writer != null)
			writer.close();
	}
	public void generateSummaryReport(MemberDB m, ProviderDB p, TransactionDB t, ProviderDirectory pDir){
		Stack<Providers> pStack = null;
		LinkedList<Transaction> tList = null;
		PrintWriter writer = null;
		LocalDate today = LocalDate.now();
		String filename = null;
		pStack = p.stackProviders();
		
		if (pStack.isEmpty() == true)
			return;
		// create a single summary report file
		filename = createSummName();
			
			//create new file from filename
			File sFile = new File(filename);
			try {
				sFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		
			try {
				writer = new PrintWriter(filename);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		while (pStack.isEmpty() == false){
			Providers currentProvider = pStack.pop();
			today = LocalDate.now();
			
			writeProviderToFile(writer, currentProvider);
		
			//calculate number of consultations and total fee for the week
			tList = t.retrieveProviderTransactions(currentProvider.GetID());	
			
			float feeAccumulator = 0.0f;
			int numTransactions = 0; //accumulator
			if (tList != null)
			{
				 numTransactions = 0; //accumulator
				 feeAccumulator = 0.0f;
				//for each transaction
				 for (int i = 0; i < tList.size(); ++i)
				 {
					 Transaction trans = tList.get(i);
					 if (trans.isWithinPastWeek()){
						 //get service code
						 int serviceCode = trans.getServiceCode();
						 
						 //get fee
						 Service foundService = pDir.searchByNumAndCopy(serviceCode);
						 float fee = foundService.getFee();
						 
						 ++numTransactions;
						 feeAccumulator += fee;
					 }
				 writer.write("Number of consultations this week: " + numTransactions + "\n");
				 writer.write("Total fee for the week: " + feeAccumulator + "\n");
				 }
			}
			else
			{
				 // no transactions were found for the current week
				 writer.write("Number of consultations this week: " + numTransactions + "\n");
				 writer.write("Total fee for the week: " + feeAccumulator + "\n");
			}
		}
		if (writer != null)
			writer.close();

	}
	
	private String createMembFilename(int id){
		return "MemberReports/" + "M_" + id + "_" + LocalDate.now().toString() + ".txt";
	}

    private String createProvFilename(int id){
        return "ProviderReports/" + "P_" + id + "_" + LocalDate.now().toString() + ".txt";
    }

    private String createEFTFilename(int id){
        return "EFT_" + id + "_" + LocalDate.now().toString() + ".txt";
    }
    
    
    private String createSummName() {
        return "SUMM_"+LocalDate.now().toString()+".txt";
    }

    private void writeProviderToFile(PrintWriter w, Providers p){
        w.write("Provider Information:\n");
		w.write("\nProvider Name: " + p.GetName());
        w.write("\nID: " + p.GetID().toString());
        w.write("\nAddress: " + p.GetAddress());
    }

	private void writeMemberToFile(PrintWriter w, Member m){
		w.write("Member Information:\n");
		w.write("\nMember Name: " + m.GetName());
		w.write("\nID: " + m.GetID().toString());
		w.write("\nAddress: " + m.GetAddress());
		w.write("\nStatus: " + m.GetSuspendedStatus().toString());
	}

	public static void main(String[] args) {
		MemberDB mDB = new MemberDB();
		try {
			mDB.ReadFromFile("members.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ProviderDB pDB = null;
		try {
			pDB = new ProviderDB("ProviderDB.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		TransactionDB tDB = null;
		try {
			tDB = new TransactionDB("transactions.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ProviderDirectory pDir = null;
		try {
			pDir = new ProviderDirectory("Provider Directory.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ReportGenerator rGen = new ReportGenerator();
		//rGen.generateMemberReports(mDB, pDB, tDB, pDir);
		//rGen.generateProviderReports(mDB, pDB, tDB, pDir);
		//rGen.generateProviderReportsAndEFT(mDB, pDB, tDB, pDir);
		//rGen.generateSummaryReport(mDB, pDB, tDB, pDir);
		
	}
}
