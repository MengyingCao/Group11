import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class ServerSystem extends Thread {
    private ServerSocket listener;
	private Socket socket;
	private int clientNo;
	private ChocAnonSystem context;
    private PrintWriter out;
    private BufferedReader input;
	
	public ServerSystem(ChocAnonSystem con){
		context = con;
        clientNo = 1;
	}
	
	public void run(){
            try {
                listener = new ServerSocket(1111);
                context.jTextArea1.append("Server starts...\n");
                while(true){
                    socket = listener.accept();
                    context.jTextArea1.append("Client " + clientNo + " try to connect...\n");
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream(), true);
                    int num = Integer.parseInt(input.readLine());
                    if(context.providerDB.searchByProNum(num)==1){
                    	out.println("T");
                    	Thread t = new Connection(socket, clientNo, num, context);
                    	clientNo++;
                    	t.start();
                    }else{
                    	out.println("F");
                    	context.jTextArea1.append("Invalid Provider Number\n");
                    }
                }
                //listener.close();

            } catch (IOException ex) {
                Logger.getLogger(ServerSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
        
        public void end() throws IOException{
            listener.close();
        }
}
