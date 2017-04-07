package woo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	static ServerWindow win;			// GUI object
	static ServerSocket socket;			// Server socket object
	static int port = 9999, num;			// Port and transaction number
	private static String info =
	    "\nIM269 - Coursework 2: EchoServer\nSemester A, 2nd November 1998\n" +
	    "Eamonn Martin (BSc Computing)\nStudent ID: 96/D59682\nefm001@unl.ac.uk\n\n" +
	    "EchoServer [port]\n\n" +
	    "The optional 'port' command-line parameter overrides the default of 9999.\n" +
	    "The 'Reset' button clears the log and resets the transaction counter.\n\n" +
	    "A full listing of this program can be found on the World Wide Web at:\n\n" +
	    "\t\t    http://www2.unl.ac.uk/~efm001/im269/\n";


	// Create the GUI and the server socket and monitor for clients
	public static void main(String[] args) {
		System.out.println(info);
		if (args.length > 0) try {		// Read command-line port?
			port = (Integer.decode(args[0])).intValue();
		} catch (NumberFormatException e) {	// Invalid port number
			System.err.println(args[0]+" is not a valid port number!");
			return;				// Abort program
		}
		try { socket = new ServerSocket(port); }// Create a server socket
		catch (IOException e) {			// No socket created
			System.err.println("Unable to create socket on port "+port+"!");
			return;				// Abort program
		}
		System.out.println("Starting EchoServer...");
		win = new ServerWindow("EchoServer");	// Create server window
		while (true) {				// Wait for connections
			try {	Socket s = socket.accept();
				win.println("\nConnection #" + (++num) + " accepted.");
				new Transaction(num, s);// Create a transaction
			} catch (IOException e) {}
		}
	}
}
