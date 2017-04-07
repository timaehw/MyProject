package woo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//Client transactions
class Transaction extends Thread {
	private Socket socket;				// Client socket
	private String id;				// Transaction ID string

	// Constructor
	Transaction(int id, Socket socket) {
		this.socket = socket;			// Set client socket
		this.id = "#" + id + ": ";		// Set transaction ID
		start();				// Start transaction thread
	}

	// Echo string from socket input stream to socket output stream
	// Incoming data is echoed byte-by-byte without buffering
	// End of data is indicated by the end of the input stream (not a newline)
	// Thus avoids readLine() (noted in TYJ - pp181) and allows multi-line text
	private String echoString(Socket socket) {
		DataInputStream in = null;		// Input stream
		DataOutputStream out = null;		// Output stream
		String s = "";				// String buffer
		try {					// Read/write string
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			for (int c; (c = in.read())!=-1; out.write(c)) s += (char)c;
		} catch (IOException e) {		// Operation failed
			s = null;
		} finally {				// Close i/o streams
			try { if (out!=null) out.close(); }
			catch (IOException e) { s = null; }
			try { if (in!=null) in.close(); }
			catch (IOException e) { s = null; }
		}
		return s;				// Echoed-string or null
	}

	// Execute transaction and report progress
	public void run() {
		String s;
		println(new java.util.Date().toString());
		println("Remote IP: " + socket.getInetAddress());
		println("Remote Port: " + socket.getPort());
		if ((s = echoString(socket))!=null) println("Text Received:\n" + s);
		println((s!=null) ? "Transaction complete." : "EchoServer I/O error!");
		try { socket.close(); } catch (IOException e) {}
	}

	// Report a message prefixed with the transaction ID number
	private void println(String s) { EchoServer.win.println(id + s); }
}