package getpicampicture;

import java.net.*;
import java.io.*;


/**
 * 
 * 
 *
 */
public class TCPServer {

	public TCPServer(String[] args, CommandDispatcher cmdDispatcher, DataOutputStreamWriter clientOutWriter) {
				
		try {

			int serverPort = 5001;

			ServerSocket listenSocket = new ServerSocket(serverPort);
						
			while (true){
				//System.out.println("client connection loop");
				Socket clientSocket = listenSocket.accept();
				Connection c = new Connection(clientSocket, cmdDispatcher, clientOutWriter);
			}
			
		} catch (IOException e) {
			System.out.println("Listen socket: " + e.getMessage());
		}
	}
}

/**
 * 
 * Represents client connection
 * 
 * 		CommandDispatcher - invokes exec command with string received from client
 * 		DataOutputStreamWriter - sets out stream so the caller can write to it
 * 
 */
class Connection extends Thread {

	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	
	long clienttimeout = 10*1000;	// timeout after which we conceder client went away
	
	private CommandDispatcher cmdDisp;
	
	public Connection (Socket aClientSocket, CommandDispatcher cmdDispatcher, DataOutputStreamWriter clientOutWriter) {
		try {
			clientSocket = aClientSocket;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			
			clientOutWriter.setOut(out);	// TODO: move to while() in run()?
			//System.out.println("create client connection");
			cmdDisp = cmdDispatcher;
			
			this.start();
		} catch (IOException e) {
			System.out.println("Conection: " + e.getMessage());
		}
	}
	
	public void run() {
		
		long lastread = System.currentTimeMillis();
		
		try {
			
			boolean isread;
			
			//while (true){
			while (!clientSocket.isClosed()){
								
				isread = false;
				
				while (in.available() > 0) {
					
					String command = in.readUTF();

					System.out.println("Received from TCP client: " + command);
					cmdDisp.execCommand(command);
					
					isread = true;
					lastread = System.currentTimeMillis();
					
					//out.writeUTF("write from server");
					
				}
				
				Thread.sleep(8);
				
				if ((System.currentTimeMillis() - lastread > clienttimeout) && !isread) {
					System.out.println("client went away");
					clientSocket.close();
					return;
				}								
				
			}
			System.out.println("client closed");
			
		} catch (EOFException e) {
			System.out.println("EOF: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("IE: " + e.getMessage());
		}
	}

}
