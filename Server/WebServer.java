package lakh8360;

import java.net.* ;

public final class WebServer {
	public static void main(String argv[]) throws Exception {
	// Get the port number from the command line.
	int port = 6789;// new Integer(argv[0]).intValue();
//	System.out.println(sss.charAt(0));
	// Establish the listen socket.
	ServerSocket socket = new ServerSocket(port);
	
	// Process HTTP service requests in an infinite loop.
	while (true) {
		System.out.println("Trying to connect");
	    // Listen for a TCP connection request.
	    Socket connection = socket.accept();
	    
	    //Print message - connection requested by client
	    System.out.println("Connection established");
	    
	    // Construct an object to process the HTTP request message.
	    HttpRequest request = new HttpRequest(connection);
	    
	    // Create a new thread to process the request.
	    Thread thread = new Thread(request);
	    
	    // Start the thread.
	    thread.start();
	}
    }
}
