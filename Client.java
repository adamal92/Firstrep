/**
 *  my first client in java
 */
package training;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * @author adam l
 * @version Oct 14, 2019
 */
public class Client
{

	/**
	 * @param args
	 *//*
	public static void main(String[] args) throws Exception
	{
		try{
			InetAddress address = InetAddress.getLocalHost();
			int port = 6022;
			Socket myClient = new Socket(address, port);
			SocketAddress server = new Address();
			myClient.connect(server);
		}
		catch(Exception e){
			throw e;
		}
	}*/
	private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
 
    public void startConnection(String ip, int port) throws UnknownHostException, IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
 
    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }
 
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    @Test(clss = "Client") // annotation
    public static void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() throws UnknownHostException, IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage("hello server");
        System.out.println(response);
//        assertEquals("hello client", response);
    }
    
    public static void main(String[] args) throws Exception{
    	try{
    		givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect();
    	}catch(Exception e){ throw e;}
    }
}
