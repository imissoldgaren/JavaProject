package chatServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ClientHandler implements Runnable {
	
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String clientName;


	public void boradcastMessage(String message) {
		for (ClientHandler clientHandler : clientHandlers) {
			try {
				if(!clientHandler.clientName.equals(clientName)) {
					clientHandler.bufferedWriter.write(message);
					clientHandler.bufferedWriter.newLine();
					clientHandler.bufferedWriter.flush();
				}
			}catch(IOException e) {
				closeClientHnadler(this.socket,bufferedReader, bufferedWriter);
			}
		}
	}
	
	public ClientHandler(Socket socket) throws IOException{
		try {
			this.socket = socket;
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.clientName = bufferedReader.readLine();
			clientHandlers.add(this);
			boradcastMessage("Server" + clientName + " has enterd the chat!");
		} catch (IOException e) {
			closeClientHnadler(this.socket,bufferedReader, bufferedWriter);
			
		}
	}

	@Override
	public void run() {
		String listenToMessage;
		while(socket.isConnected()) {
			try {
				listenToMessage = bufferedReader.readLine();
				boradcastMessage(listenToMessage);
			}catch (IOException e) {
				closeClientHnadler(socket,bufferedReader, bufferedWriter);
				break;
			}
			
		}
		
	}
	

	
	public void removeClientHandler() {
		clientHandlers.remove(this);
		boradcastMessage("Server: " + clientName + "has left the chat");
		
	}
	
	public void closeClientHnadler(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter) {
		removeClientHandler();
	
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

}
