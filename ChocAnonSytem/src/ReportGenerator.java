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
	//writes to disk the consultation, EFT, and summary reports for each provider
	public void generateProviderReports(MemberDB m, ProviderDB p, TransactionDB t, ProviderDirectory pDir){
        Stack<Providers> pStack = null;
        LinkedList<Transaction> tList = null;
        LocalDate today = LocalDate.now();
        PrintWriter writer = null;
        PrintWriter writerSumm = null;
        String filename = null;
        String filenameEFT = null;
        String filenameSumm = null;
        pStack = p.stackProviders();
        int totalConsults;
        float totalFees;
        int totalAllProvs = 0;
        int totalAllConsults = 0;
        //int totalAllFees = 0.0;
        int totalAllFees = 0;

        if (pStack == null)
            return;

        filenameSumm = createSummName();
        try {
            writerSumm = new PrintWriter(filenameSumm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (!pStack.isEmpty()){
            totalConsults = 0;
            totalFees = 0.0f;

            Providers currentProvider = pStack.pop();
            today = LocalDate.now();
            //create a filename for this member report
            filename = createProvFilename(currentProvider.GetID());
            filenameEFT = createEFTFilename(currentProvider.GetID());

            try {
                writer = new PrintWriter(filename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            writeProviderToFile(writer, currentProvider);

            //retrieve a list of transaction for that member
            tList = t.retrieveProviderTransactions(currentProvider.GetID());

            //for each transaction
            for (int i = 0; i < tList.size(); ++i)
            {
                Transaction trans = tList.get(i);
                if (trans.isWithinPastWeek()){
                    int memberNumber = trans.getMemberNumber();
                    int serviceCode = trans.getServiceCode();

                    //get member name
                    Person memberPerson = null;
                    memberPerson = p.searchByNumAndCopy(memberNumber, memberPerson);
                    String memberName = memberPerson.GetName();

                    //get service name and fee
                    String serviceName = null;
                    Service foundService;
                    foundService = pDir.searchByNumAndCopy(trans.getServiceCode());
                    serviceName = foundService.getServiceName();
                    float serviceFee = foundService.getFee();

                    //get service dates
                    String transDate = trans.getTransactionDate().toString();
                    String dateReceived = trans.getDateTimeRecieved().toString();

                    writer.write("\nService " + (i+1) + "\n");
                    writer.write(transDate + "\n");
                    writer.write("Date entered:" + dateReceived + "\n");
                    writer.write("Member name: " + memberName + "\n");
                    writer.write("Member number: " + memberNumber + "\n");
                    writer.write("Code: " + serviceCode + "\n");
                    writer.write("Fee: " + serviceFee + "\n");

                    ++totalConsults;
                    totalFees+=serviceFee;
                }
            }
            writer.write("Total consultations: " + totalConsults + "\n");
            writer.write("Total fees: " + totalFees);

            try {
                writer = new PrintWriter(filenameEFT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            writer.write(currentProvider.GetName()+";"+currentProvider.GetID().toString()+";"+totalFees);

            writerSumm.write(currentProvider.GetName()+";"+currentProvider.GetID().toString()
                            +";"+totalConsults+";"+totalFees+"\n");
            ++totalAllProvs;
            ++totalAllConsults;
            totalAllFees+=totalFees;

            writerSumm.write("\nTotal providers: "+totalAllProvs
                            +"\nTotal consultations: "+totalAllConsults
                            +"\nTotal fees: "+totalAllFees);
        }
        writer.close();
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
			System.out.println(filename);
			
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
	
	private String createMembFilename(int id){
		return "MemberReports/" + "M_" + id + "_" + LocalDate.now().toString() + ".txt";
	}

    private String createProvFilename(int id){
        return "P_" + id + "_" + LocalDate.now().toString() + ".txt";
    }

    private String createEFTFilename(int id){
        String filename = "";
        filename.concat("EFT_" + id + "_" + LocalDate.now().toString() + ".txt");
        return filename;
    }

    private String createSummName() {
        String filename = "";
        filename.concat("SUMM_"+LocalDate.now().toString()+".txt");
        return filename;
    }

    private void writeProviderToFile(PrintWriter w, Providers p){
        w.write("Provider Information:\n");
        w.write(p.GetName());
        w.write("\nID: " + p.GetID().toString());
        w.write("\nAddress: " + p.GetAddress());
    }

	private void writeMemberToFile(PrintWriter w, Member m){
		w.write("Member Information:\n");
		w.write(m.GetName());
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
		rGen.generateMemberReports(mDB, pDB, tDB, pDir);
		
	}
}
