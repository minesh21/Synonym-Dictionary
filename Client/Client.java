/*----------------------------------------------------------
 * Authors: Irfan Lakha & Minesh Varu
 * ID: 110418360 & 110814300
 * Email: lakha8360@mylaurier.ca & varu4300@mylaurier.ca
 * Program: Client.java
 * Description: Client GUI, connects with the server and can
 *              perform SET, GET and REMOVE commands
 *----------------------------------------------------------
 */
import javax.swing.JFrame;


public class Client extends JFrame{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//creating the GUI for the Client side
		JFrame panel = new JFrame("Synonyms");
		ClientGUI view = new ClientGUI();
		view.registerComponents();
		panel.setContentPane(view);
		panel.setSize(350,760);
		panel.setLocation(0,0);
		panel.setResizable(false);
		panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setVisible(true);


	}

}
