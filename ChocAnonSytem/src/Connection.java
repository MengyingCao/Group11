import java.io.*;
import java.time.*;
import java.net.*;
import javax.swing.*;

public class Connection extends Thread{
	Socket socket;
	int clientNum;
    int providerNum;
    int memberNum;
    ChocAnonSystem context;
	
	public Connection(Socket sk, int cnum, int pnum, ChocAnonSystem cont){
		socket = sk;
		clientNum = cnum;
                providerNum = pnum;
		context = cont;
                context.jTextArea1.append("Provider "+providerNum+" (client "+clientNum+" ) is connecting.\n");
	}
	
	public void run(){
            try{
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
                String temps = in.readLine();
                char tempc = temps.charAt(0);
                while(tempc == 'M' || tempc == 'E'){
                	if(tempc == 'E'){
                		context.jTextArea1.append('E'+"\n");
                		context.providerDIR.saveOutForEmail();
                		out.println("Email send succeed.");
                	}else{
                		temps = in.readLine();
                    	context.jTextArea1.append("Receive from "+providerNum+" (client "+clientNum+" ): "+temps+"(Member Number)\n");
                    	int num = Integer.parseInt(temps);
                    	if(!context.memberDB.isValid(num)){
                    		out.println("F");
                    		out.println("Invalid Number");
                    		context.jTextArea1.append("Invalid Number\n");
                    	}else if(context.memberDB.isSuspended(num)){
                    		out.println("F");
                    		out.println("Member Suspended");
                    		context.jTextArea1.append("Member suspended\n");
                    	}else{
                    		out.println("T");
                    		out.println("Validated");
                    		out.println("If you want to add a service record, Please input the date below in the format MM-DD-YYYY");
                    		memberNum = num;
                    	}
                	}
                	temps = in.readLine();
                	context.jTextArea1.append(temps+"\n");
                    tempc = temps.charAt(0);
                }
                try {
		    addService(temps, in, out);
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
                socket.close();
                
            }catch (IOException e){
                    System.out.println(e);
            }
	}
	
	public void addService(String m, BufferedReader in, PrintWriter out){
		try {
			String temp;
			Service tempser = new Service();
			boolean repeat = true;
			int num;
			Transaction temptran = new Transaction();
			LocalDate tDate = LocalDate.parse(m);
			temptran.setTransactionDate(tDate);
			
			//service
			while(repeat){
				out.println("Please choose options:");
				out.println("1. Search Service by Name");
				out.println("2. Type in Service through Code");
				out.println("~");
				switch(Integer.parseInt(in.readLine())){
				case 1: out.println("Please type in the Name:");
						out.println("~");
						temp = in.readLine();
						context.jTextArea1.append(temp+"\n");
						tempser = context.providerDIR.searchByNameAndCopy(temp, tempser);
						if(tempser == null){
							out.println("This service not exist.");
						}else{
							out.println("The service code is: "+tempser.getServiceCode());
						}
						context.jTextArea1.append(tempser.getServiceName());
						
						break;
				case 2: out.println("Please type in the Number:");
						out.println("~");
						num = Integer.parseInt(in.readLine());
						context.jTextArea1.append(num+"\n");
						tempser = context.providerDIR.searchByNumAndCopy(num);
						context.jTextArea1.append(tempser.getServiceName());
						out.println("The service name is: "+tempser.getServiceName());
						out.println("Sure to record this service? 1-Yes 2-No");
						out.println("~");
						num = Integer.parseInt(in.readLine());
						if(num==1){
							repeat = false;
						}
						break;
				default:out.println("Wrong input!");
						out.println("~");
						break;
				}
			}
			temptran.setServiceCode(tempser.getServiceCode());
			out.println("Please give the comments:");
			context.jTextArea1.append("Sent to Provider "+providerNum+"(client "+clientNum+"): Please give the comments:\n");
			out.println("~");
			temptran.setComment(in.readLine());
			temptran.setMemberNumber(memberNum);
			temptran.setProviderNumber(providerNum);
			temptran.setDateTimeRecieved(LocalDateTime.now());
			if(context.transDB.addTransaction(temptran)){
				out.println("Transaction add succeed.Thanks!\nDisconnect");
				out.println("!");
				context.jTextArea1.append("Provider "+providerNum+"(client "+clientNum+"): Add a new transaction for Member "+memberNum+"\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
