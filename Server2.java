/**
 * my first server in java
 */
package training;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.net.Socket.*;
import java.net.SocketImpl.*; // an abstract class

import cyber_project.Gui2;
/**
 * @author adam l
 * @version Oct 14, 2019
 */
public class Server implements Runnable
{	
	protected static Server singleton = null; 
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	public static final int DEFAULTPORT = 6666;

	public static Server getInstance(){
		if(Server.singleton==null)
			Server.singleton = new Server();
		return Server.singleton;
	}

	public void start(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		clientSocket = serverSocket.accept();
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String greeting = in.readLine();
		System.out.println(greeting);
		if ("hello server".equals(greeting)) {
			out.println("hello client");
		}
		else {
			out.println("unrecognised greeting");
		}
	}

	public void stop() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
		serverSocket.close();
	}

	public static void main(String[] args) throws IOException {
		Server ser=new Server();
		ser.start(6666);
	}

	public void run() 
	{
		try{
		Server ser=Server.getInstance();
		ser.start(DEFAULTPORT);}catch(Exception e){System.out.println("server not working");}
	}
	
	public static boolean hasInstance() { // true if there is an instance
		return Server.singleton != null;
	}

}