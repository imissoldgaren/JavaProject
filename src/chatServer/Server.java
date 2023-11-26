package chatServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
	
	public ServerSocket serverSocket;

	 
	
public Server(ServerSocket serverSocket) {
	this.serverSocket = serverSocket;
	
}


public void stop() throws IOException {
	try {
		if(serverSocket != null)
			serverSocket.close();
		
	}catch(IOException e){
		e.printStackTrace();
	}
		
}
public void startServer() throws IOException {
	try{

		while(!serverSocket.isClosed()) {

			Socket clientSocket = serverSocket.accept();
			System.out.println("client has connected");
			ClientHandler clientHandler = new ClientHandler(clientSocket);
			Thread thread = new Thread(clientHandler);
			thread.start();


		}

		
	}catch(IOException e){
		stop();
	}
}

public static void main(String[] args) throws IOException {
	int port = 123;
	ServerSocket serverSocket = new ServerSocket(port);
	Server server = new Server(serverSocket);
	server.startServer();

	
}



}



