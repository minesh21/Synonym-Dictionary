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
import java.io.PrintWriter;

public class MessageSender implements Runnable{
	//Initialize private variables
	private Thread t;
	private PrintWriter out;
	private BufferedReader in;
	private String message;
	/**
	 * MessageSender Constructor initializes the message, output stream and input stream
	 * @param str
	 * @param output
	 * @param input
	 */
	public MessageSender(String str,PrintWriter output, BufferedReader input) {
		
		message = str;
		out = output;
		in = input;
	}
	/**
	 * Send Message method 
	 * @param s
	 */
	public void sendMessage(String s){
		//prints the message to the output stream
		out.println(message);
		//if the output stream is not null then append the message to the output result text area
		if(out != null){
			ClientGUI.output.append("Client: " + message + "\n");
		}
		else{
			//otherwise notify user that an error occured while trying to send message
			ClientGUI.output.append("Error writing to server\n");
		}
	}
	public void getMessage() throws IOException{
		String fromServer;
		if((fromServer = in.readLine()) != null) {
            ClientGUI.output.append("Server: " + fromServer +"\n");
        }
	}
	public void run() {
		//initialize variable to get string from the server
		String fromServer;
		try {
			//while the input stream is not null
			while ((fromServer = in.readLine()) != null){
				//print the message the server returns
				ClientGUI.output.append("Server: " + fromServer + "\n");
			}
		} catch (IOException e) {
			//notify user that an error occurred while trying to get message
			ClientGUI.output.append("Error getting message from server\n");
		}
	}
	//start the thread
	public void start(){
		t = new Thread(this);
		t.start();
	}
}
