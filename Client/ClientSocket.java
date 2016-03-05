/*----------------------------------------------------------
 * Authors: Irfan Lakha & Minesh Varu
 * ID: 110418360 & 110814300
 * Email: lakha8360@mylaurier.ca & varu4300@mylaurier.ca
 * Program: Client.java
 * Description: Client GUI, connects with the server and can
 *              perform SET, GET and REMOVE commands
 *----------------------------------------------------------
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientSocket {
	//initialize private variables
	private Socket clientSocket = null;
	private String hostname;
	private int portnum;
	private PrintWriter out = null;
    private BufferedReader in = null;
    private MessageSender msg;
    /**
     * Constructor to initialize the host and port number
     * @param host
     * @param port
     */
	public ClientSocket(String host, int port) {
		this.hostname = host;
		this.portnum = port;
	}
	/**
	 * Connect method allows the user to connect to the server
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connect() throws UnknownHostException, IOException{
		String fromServer;
		//Notify user that the an attempt to connect is being made
		ClientGUI.output.setText("Please wait, trying to establish connection...\n");
		//open a socket to the server via the ip address and the port number
		clientSocket = new Socket(hostname, portnum);
		//open an output and input stream to send and receive messages to the server
		out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        if((fromServer = in.readLine()) != null) {
            ClientGUI.output.setText("Server: " + fromServer +"\n");
        }
	}
	public void disconnect() throws UnknownHostException, IOException, SocketException{
		//close output stream
		out.close();
		//close input stream
		in.close();
		//close the socket itself
		clientSocket.close();
		//append a message to notify user they have been disconnected from the server
		ClientGUI.output.append("You are now disconnected from the server\n");
	}
	/**
	 * Send Message method allows the user to send a message
	 * @param str
	 */
	public void sendMessage(String str){
		msg = new MessageSender(str, out, in);
		msg.sendMessage(str);
	}
	/**
	 * Get Message allows the user to send a message to the server
	 * through the use of a thread
	 * @throws IOException 
	 */
	public void getMessage() throws IOException{
		msg.getMessage();
	}
}
