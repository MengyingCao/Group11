import java.util.TreeMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TransactionDB {
	
	private String transactionFile;
	private TreeMap<Integer,LinkedList<Transaction>> memberTransactionDB;
	private TreeMap<Integer ,LinkedList<Transaction>> providerTransactionDB;
	
	
	/* default constructor */
	public TransactionDB(){
		memberTransactionDB = new TreeMap<Integer,LinkedList<Transaction>>();
		providerTransactionDB = new TreeMap<Integer,LinkedList<Transaction>>();
		transactionFile = null;
	}
	/* construct database from file - this constructor is preferred */
	public TransactionDB(String filenamePath) throws FileNotFoundException{
		memberTransactionDB = new TreeMap<Integer,LinkedList<Transaction>>();
		providerTransactionDB = new TreeMap<Integer,LinkedList<Transaction>>();
		transactionFile = filenamePath;
		readInDatabaseFromFile();
	}
	/* add a transaction to the database */
	public Boolean addTransaction(Transaction toAdd){
		if (isFileSpecified() == false)
			return false; //no file is specified
		
		this.addToMemberDatabase(toAdd);
		this.addToProviderDatabase(toAdd);
		
		return true;
	}
	/* retrieve a copy of all the transactions found for a specific member */
	public LinkedList<Transaction> retrieveMemberTransactions(Integer memberID){
		
		LinkedList<Transaction> memberTransactions;
		LinkedList<Transaction> memberTransactionsCopy;
		
		/* retrieve transactions from member */
		memberTransactions = memberTransactionDB.get(memberID);
		memberTransactionsCopy = null;
		
		/* make a copy of those transactions to return */
		if (memberTransactions != null)
			memberTransactionsCopy = new LinkedList<Transaction>(memberTransactions);
		
		return memberTransactionsCopy;
	}
	/* retrieve a copy of all the transaction found for a specific provider */
	public LinkedList<Transaction> retrieveProviderTransactions(Integer providerID){
		
		LinkedList<Transaction> providerTransactions;
		LinkedList<Transaction> providerTransactionsCopy;
		
		/* retrieve transactions from provider */
		providerTransactions = providerTransactionDB.get(providerID);
		providerTransactionsCopy = null;
		
		/* make a copy of those transactions to return */
		if (providerTransactions != null)
			providerTransactionsCopy = new LinkedList<Transaction>(providerTransactions);
		
		return providerTransactionsCopy;

	}
	/* display all transactions */
	public void displayTransactions(){
		for(Entry<Integer, LinkedList<Transaction>> entry : memberTransactionDB.entrySet())
		{
			 for (int i = 0; i < entry.getValue().size(); ++i)
			 {
				 Transaction toDisplay = entry.getValue().get(i);
				 toDisplay.displayInformation();
				 System.out.print('\n');
			 }
		}
	}
	/* write the current state of the TransactionDatabase to its file */
	public Boolean writeDatabaseToFile(){
		if ( this.isFileSpecified() == false )
			return false;
		
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(transactionFile);
		} 
		catch (FileNotFoundException e) {
			return false;
		}	
		
		for(Entry<Integer, LinkedList<Transaction>> entry : memberTransactionDB.entrySet())
		{
			 for (int i = 0; i < entry.getValue().size(); ++i)
			 {
				 Transaction singleTransaction = entry.getValue().get(i);
				 String stringToWrite = this.convertTransactionToString(singleTransaction);
				 writer.write(stringToWrite + '\n');
			 }
		}
		
		writer.close();
		return true;
	}
	/* load the database from a file */
	public Boolean readInDatabaseFromFile() throws FileNotFoundException
	{
		if (isFileSpecified() == false)
			return false;
		
		File tFile = new File(transactionFile);
		Scanner reader = new Scanner(tFile);
		reader.useDelimiter(";");
		
		while (reader.hasNext())
		{
			String tDateString = reader.next();
			String rDateTimeString = reader.next();
			int memID = reader.nextInt();
			int proID = reader.nextInt();
			int serviceCode = reader.nextInt();
			String comment = reader.next();
			reader.nextLine(); //eats the end line character '\n'
			LocalDate tDate;
			LocalDateTime dateTime;

			try{
			tDate = LocalDate.parse(tDateString);
			dateTime = LocalDateTime.parse(rDateTimeString);
			}
			catch(DateTimeParseException e){
				memberTransactionDB.clear();
				providerTransactionDB.clear();
				reader.close();
				return false;
			}
						
			Transaction toAdd = new Transaction();
			toAdd.setTransactionDate(tDate);
			toAdd.setDateTimeRecieved(dateTime);
			toAdd.setMemberNumber(memID);
			toAdd.setProviderNumber(proID);
			toAdd.setServiceCode(serviceCode);
			toAdd.setComment(comment);
			
			this.addTransaction(toAdd);
		}
		
		reader.close();
		
		return false;
	}
	/* sets the location of the transactionFile */
	public Boolean specifyTransactionFile(String filenamePath){
		// in order to avoid complications, you can only specify a file once
		if ( isFileSpecified() == false ){
			if ( new File(filenamePath).isFile() ){
				this.transactionFile = filenamePath;
				return true; // file is valid
			}
			else
				return false; //file is invalid
		}
		else
			return false; // file has already been set before
	}
	public Boolean isFileSpecified(){
		if ( transactionFile != null )
			return true;
		else
			return false;
	}
	/* helper functions */
	
	/* creates a single lined string encoding of a transaction record/object */
	private String convertTransactionToString(Transaction transactionObj){
		String date;
		String dateTime;
		int memberID;
		int providerID;
		int serviceCode;
		String comment;
		
		date = transactionObj.getTransactionDate().toString();
		dateTime = transactionObj.getDateTimeRecieved().toString();
		memberID = transactionObj.getMemberNumber();
		providerID = transactionObj.getProviderNumber();
		serviceCode = transactionObj.getServiceCode();
		comment = transactionObj.getComment();
		
		
		String encodedTransaction = date + ";" + dateTime + ";"
				+ memberID + ";" + providerID + ";"
				+ serviceCode + ";" + comment + ";";
		
		return encodedTransaction;
		
	}
	private void addToMemberDatabase(Transaction memberT){
		LinkedList<Transaction> database = memberTransactionDB.get(memberT.getMemberNumber());
		Transaction toStore = new Transaction(memberT);
		
		if (database == null)
			database = new LinkedList<Transaction>();
			
		database.add(toStore);
		
		memberTransactionDB.put(memberT.getMemberNumber(), database);
	}
	private void addToProviderDatabase(Transaction providerT){
		LinkedList<Transaction> database = providerTransactionDB.get(providerT.getProviderNumber());
		Transaction toStore = new Transaction(providerT);
		if (database == null)
			database = new LinkedList<Transaction>();
		
		database.add(toStore);
		
		providerTransactionDB.put(providerT.getProviderNumber(), database);
	}
	public static void main(String[] args) throws FileNotFoundException {
		//tests
		TransactionDB foo;
		foo = new TransactionDB();
		foo.specifyTransactionFile("transactions.txt");
		
		Boolean readSuccess;
		readSuccess = foo.readInDatabaseFromFile();
		/*
		Transaction a = new Transaction();
		a.setComment("some comment");
		foo.addTransaction(a);
		*/
		
		//test retrieves
		LinkedList<Transaction> memberTest;
		LinkedList<Transaction> providerTest;
		memberTest = foo.retrieveMemberTransactions(483625375);
		providerTest = foo.retrieveProviderTransactions(482536273);
		
		System.out.print("Displaying transaction for member: " + 483625375 + '\n');
		if (memberTest != null)
			for (int i=0; i < memberTest.size(); ++i)
				memberTest.get(i).displayInformation();
		
		System.out.print("\nDisplaying transactions for provider: " + 482536273 + '\n');
		if (providerTest != null)
			for (int i=0; i < providerTest.size(); ++i)
				providerTest.get(i).displayInformation();
		
		System.out.print("\nDisplaying all transactions: " + '\n');
		
		foo.displayTransactions();
		if (readSuccess == true)
			foo.writeDatabaseToFile();
	}
}

/* format for transaction encoding
 * date;date received;time received;member#;provider#;service code;comment;
 * ex.
 * 2016-05-15;2016-03-15T14:21:20;492647329;482536273;349274;"No Comment";
 * */
