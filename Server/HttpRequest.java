/*----------------------------------------------------------
 * Authors: Irfan Lakha & Minesh Varu
 * ID: 110418360 & 110814300
 * Email: lakha8360@mylaurier.ca & varu4300@mylaurier.ca
 * Program: Client.java
 * Description: Client GUI, connects with the server and can
 *              perform SET, GET and REMOVE commands
 *----------------------------------------------------------
 */
import java.io.* ;
import java.net.* ;
import java.util.* ;

final class HttpRequest implements Runnable {
    final static String CRLF = "\r\n";
    Socket socket;

    // Constructor
    public HttpRequest(Socket socket) throws Exception {
	this.socket = socket;
    }
    
    // Implement the run() method of the Runnable interface.
    public void run() {
	try {
	    processRequest();
	} catch (Exception e) {
	    System.out.println(e);
	}
    }

    private void processRequest() throws Exception {
	// Get a reference to the socket's input and output streams.
	PrintWriter os = new PrintWriter(socket.getOutputStream(), true);
	InputStream is = socket.getInputStream();
	// Set up input stream filters.
	BufferedReader br = new BufferedReader(new InputStreamReader(is));
	os.println("Welcome to the synonyms library :)"); // statusLine
	
	String clientInput;
	
    while ((clientInput = br.readLine()) != null) {
        System.out.println("Client: " + clientInput);
        os.println(serviceRequest(clientInput));
        if (socket.isOutputShutdown()){
        	break;
        }   
    }
        // Close streams and socket.
        os.close();
        br.close();
        socket.close();
    }

	private String serviceRequest(String readLine) {
	        String result = "";
			// Extract input string
	        StringTokenizer tokens = new StringTokenizer(readLine," ");
	        System.out.println("Tokens: " + tokens.toString());
	        //get the number of tokens
	        int numTokens = tokens.countTokens();
	        System.out.println("Tokens: " + numTokens);
	        
	        if (numTokens == 1){
	        	result =  ("Invalid Request." + "\n" + "Request Format: GET WORD1 OR"
	        			+ "SET WORD1 WORD2 OR REMOVE WORD1");
	        } else if (numTokens == 2){
	            String command = tokens.nextToken();  // must be a get or remove request.
	            String word1 = tokens.nextToken(); // word1
		        System.out.println("Tokens: " + command + word1);
	            if (command.toLowerCase().equals("get")){
	            	//service get request
	            	ArrayList<String> getList = new ArrayList<String>();
	            	getList = Dictionary.getRequest(word1);
	            	result = getList.toString();
	            	       	
	            } else if (command.toLowerCase().equals("remove")){
	            	//service remove request
	            	result = (Dictionary.removeRequest(word1));
	            } else {
	            	//set request with only one word entered
	            	result = ("Invalid Request." + "\n" + "Request Format: GET WORD1 OR"
	            			+ "SET WORD1 WORD2 OR REMOVE WORD1");
	            }
	            //if (word1.)
	        } else if(numTokens == 3){
	            String command = tokens.nextToken();  // must be a set request
	            String word1 = tokens.nextToken(); // word1
	            String word2 = tokens.nextToken(); // word2
	            //check if it is a set request and service request
	            if (command.toLowerCase().equals("set")){
	            	//service set request
	            	result = (Dictionary.setRequest(word1, word2));
	            }else {
	            	result = ("Invalid Request." + "\n" + "Request Format: GET WORD1 OR"
	            			+ "SET WORD1 WORD2 OR REMOVE WORD1");
	            }
	        }
	        System.out.println(result);
		return result;
	}
}
