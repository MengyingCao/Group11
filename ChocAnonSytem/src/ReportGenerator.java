import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.*;
import java.util.LinkedList;
import java.util.Stack;

public class ReportGenerator {
	private MemberDB m;
	private ProviderDB p;
	private TransactionDB t;
	private ProviderDirectory pDir;
	
	public ReportGenerator(){
	}
	public ReportGenerator(MemberDB m, ProviderDB p, TransactionDB t, ProviderDirectory pDir){
		this.m = m;
		this.p = p;
		this.t = t;
		this.pDir = pDir;
	}

	
	public void generateProviderReports(MemberDB members, ProviderDB providers){
		//get a list of providers
		//Stack<Provider> pStack = providers;
				
				//need to implement provider stack
	}
	
	public void generateMemberReports(MemberDB m, ProviderDB p, TransactionDB t, ProviderDirectory pDir){
		Stack<Member> mStack = null;
		LinkedList<Transaction> tList = null;
		PrintWriter writer = null;
		LocalDate today = LocalDate.now();
		String filename = null;
		mStack = m.stackMembers();
		
		if (mStack == null)
			return;
		
		while (!mStack.isEmpty()){
			Member currentMember = mStack.pop();
			today = LocalDate.now();
			//create a filename for this member report
			filename = createFilename(currentMember.GetID());
		
			try {
				writer = new PrintWriter(filename);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			writeMemberToFile(writer, currentMember);
			
			//retrieve a list of transaction for that member
			tList = t.retrieveMemberTransactions(currentMember.GetID());
			
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
					 
					 writer.write(providerNumber + "\n");
					 writer.write(providerName + "\n");
					 writer.write(trans.getServiceCode() + "\n");
					 writer.write(serviceName + "\n");
					 writer.write(serviceDate + "\n");
			 }
			}	
		}
		writer.close();
	}
	
	private String createFilename(int id){
		String filename = "";
		filename.concat(id + "_" + LocalDate.now().toString() + ".txt");
		return filename;
	}
	
	private void writeMemberToFile(PrintWriter w, Member m){
		w.write("Member Information\n");
		w.write("Member Name: "+ m.GetName());
		w.write("\nMember ID: " + m.GetID().toString());
		w.write("\nMember Address: " + m.GetAddress());
		w.write("\nMember Status: " + m.GetSuspendedStatus().toString());
	}
	
	public static void main(String[] args) {
	}

}
