import java.time.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ReportGeneratorAuto extends Thread {

	ChocAnonSystem context;
	LocalTime generateTime;
	DayOfWeek generateWeek;
	
	public ProviderDB providerDB;
    public MemberDB memberDB;
    public TransactionDB transDB;
    public ProviderDirectory providerDIR;
	
	
	public ReportGeneratorAuto(ChocAnonSystem con){
		context = con;
		//rGen = new ReportGenerator();
		generateTime = LocalTime.parse("15:46:00");
		generateWeek = DayOfWeek.THURSDAY;
		memberDB = new MemberDB();
        try{
			memberDB.ReadFromFile("members.txt");
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		LocalDateTime current;
		while(true){
			current = LocalDateTime.now();
			if(current.toLocalTime().equals(generateTime) && current.getDayOfWeek().equals(generateWeek)){
				context.rGen.generateMemberReports(context.memberDB, context.providerDB, context.transDB, context.providerDIR);
				context.rGen.generateProviderReports(context.memberDB, context.providerDB, context.transDB, context.providerDIR);
				//context.rGen.generateProviderReportsAndEFTS(context.memberDB, context.providerDB, context.transDB, context.providerDIR);
				context.rGen.generateSummaryReport(context.memberDB, context.providerDB, context.transDB, context.providerDIR);
				//System.out.println("Right time"+LocalDateTime.now());
				try {
					sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				//ReportGenerator rGen = new ReportGenerator();
				//rGen.generateSummaryReport(memberDB, providerDB, transDB, providerDIR);
			}
		}
	}
}
