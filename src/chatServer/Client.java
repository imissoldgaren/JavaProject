package chatServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
	public Socket clientSocket;
	public BufferedWriter bWriter;
	 public BufferedReader bReader;
	 public String username;


	
	public Client(Socket socket ,String username) {
		try {
			this.clientSocket = socket;
			this.username = username;
			this.bReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.bWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		} catch (IOException e) {
			close(clientSocket,bReader,bWriter);
		}
		
		
	}

	

	
	public void sendMessage() throws IOException {
		try {
			 bWriter.write(username);
			 bWriter.newLine();
			Scanner scanner = new Scanner(System.in);
			 while(clientSocket.isConnected()) {
				 String messageTosend = scanner.nextLine();
				 bWriter.write(username + " " + messageTosend);
				 bWriter.newLine();
				 bWriter.flush();
		}
		
		 }catch(IOException e) {
			 close(clientSocket,bReader,bWriter);
		 }
		 
	}
	public void listenToMessage() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
					String msgFromGroup;
				while(clientSocket.isConnected()) {
					try {
						msgFromGroup = bReader.readLine();
						System.out.println(msgFromGroup);
						
					} catch (Exception e) {
						close(clientSocket,bReader,bWriter);
					}
				
			}
			}}).start();
			
			
		
						
	}
	
	
	public void close(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter) {

			try {
				if(socket !=null)
				socket.close();
				if(bufferedReader != null)
					bufferedReader.close();
				if(bufferedWriter != null)
					bufferedWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
public static void main(String[] args) throws UnknownHostException, IOException {
	Scanner scanner = new Scanner(System.in);
	System.err.println("please eneter your username");
	String username = scanner.nextLine();
	Socket socket = new Socket("localhost" , 123);
	Client client = new Client(socket, username);
	client.listenToMessage();
	client.sendMessage();
	
	
}

}
