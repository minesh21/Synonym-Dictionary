/*----------------------------------------------------------
 * Authors: Irfan Lakha & Minesh Varu
 * ID: 110418360 & 110814300
 * Email: lakha8360@mylaurier.ca & varu4300@mylaurier.ca
 * Program: Client.java
 * Description: Client GUI, connects with the server and can
 *              perform SET, GET and REMOVE commands
 *----------------------------------------------------------
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ClientGUI extends JPanel{

	private Socket sock;

    private String toServer;
	//Initalization of components for the Client Interface
    private ClientSocket socket = null;
	private JLabel l1 = new JLabel("Input IP address   :");
	private JLabel l2 = new JLabel("Input Port number:");
	private JLabel l4 = new JLabel("Input pair of synonyms:");
	private JLabel l5 = new JLabel("Output results:");
	//Initialize button 
	public static JButton connect = new JButton("Connect");
	public static JButton disconnect = new JButton("Disconnect");
	//Initialize TextFields
	public static JTextField ip = new JTextField(20);
	public static JTextField pn = new JTextField(20);
	//Initialize Text area
	public static JTextArea message = new JTextArea(15,25);
	public static JTextArea output = new JTextArea(15, 30);
	//Public constructor which initializes the layout view of the client interface
	public ClientGUI(){
		//Register layout view and buttons
		layoutView();

	}
	
	public void layoutView(){ 
		//creating scroll bars for message and output textarea
		JScrollPane pane1 = new JScrollPane(message, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JScrollPane pane2 = new JScrollPane(output,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//Initialize JPanels
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();
		JPanel panel7 = new JPanel();
		JPanel panel8 = new JPanel();
		JPanel panel9 = new JPanel();
		disconnect.setEnabled(false);
		message.setEditable(false);
		output.setEditable(false);
		
		//set bounds for scrollbar
		pane1.setBounds(10, 11, 455, 249);
		pane2.setBounds(10, 11, 455, 249);
		//adding the label and text field input to panel 1 for ip address
		panel1.add(l1);
		panel1.add(ip);
		//adding the label and text field input to panel2 for the port number
		panel2.add(l2);
		panel2.add(pn);
		//adding the disconnect/connect button to panel 3
		panel3.add(connect);
		panel3.add(disconnect);
		//adding the command label SET, GET and REMOVE and textfield to panel 4
		//adding label for message input to panel 5
		panel5.add(l4);
		//adding text area for message with scroll bar when needed to panel 6
		panel6.add(pane1);
		//adding label for output result
		panel7.add(l5);
		//adding text area with scroll bar when needed to panel 8
		panel8.add(pane2);
		//Adding components to the Frame in main
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
		this.add(panel5);
		this.add(panel6);
		this.add(panel7);
		this.add(panel8);
		this.add(panel9);
	}
	public void registerComponents()
	{
		//Giving the connect button an action
		connect.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				//get the ip address and port number from the text fields
				String host = ip.getText();
				int port = Integer.parseInt(pn.getText());
				
				try {
					socket = new ClientSocket(host, port);
					socket.connect();
					message.setEditable(true);
					connect.setEnabled(false);
					disconnect.setEnabled(true);
				} catch (UnknownHostException e1) {
					//Notify user that the connection cannot be made because hostname is incorrect
					output.append("Unknown host:" + host + ",please try again\n");
				} catch (IOException e1) {
					//Notify user that a connection can't be made
					output.append("Cannot establish a connection to:"+ host + "\n");
				}
				
				
			}
		});
		//giving disconnect button an action
		disconnect.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0) {
			
				try {
					//disconnect from the server 
					socket.disconnect();
					//set input area to not editable
					message.setEditable(false);
					//set connect button to true
					connect.setEnabled(true);
					//set disconnect to false
					disconnect.setEnabled(false);
				} catch (UnknownHostException e) {
					//out put error message
					output.append("You have already disconnected from the server\n");
				} catch (IOException e) {
					output.append("You have already disconnected from the server\n");
				}
			}
			
		});
		//Input area to enter commands
		message.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {
				//initialize the keypressed
				int key = e.getKeyCode();
				//initialize empty array of string
				String [] temp = {};
				//if the key entered is enter then
				if(key == KeyEvent.VK_ENTER){
					//ensure event doesn't trigger any other event listeners 
					e.consume();
					//get the text in the input message area
					message.getText();
					//append a new line character to the input
					message.append("\n");
					//split the text in the string by the new line character delimiter
					temp = message.getText().split("\n");
					//get the length of the array
					int strLength = temp.length;
					//get the last input in the text
					toServer = temp[strLength - 1];
					//send to the server
					socket.sendMessage(toServer);
					//get response from the server
					try {
						socket.getMessage();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
			public void keyReleased(KeyEvent arg0) {}
			public void keyTyped(KeyEvent arg0) {}	
		});
	}
	

	
}
