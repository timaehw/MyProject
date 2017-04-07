package woo;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// EchoServer window
class ServerWindow extends AppWindow {
	private TextArea log;

	// Constructor
	ServerWindow(String title) {
		super(title);
		Button b;
		Panel p;

		p = new Panel(new BorderLayout());	// Server log
		p.add(new Label("Transaction Log:"), "North");
		p.add(log = new MessageArea());
		add(p);

		p = new Panel(new GridLayout());	// Button panel
		b = new Button("Reset");		// Reset button
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { resetServer(); }
		});
		p.add(b);
		b = new Button("Quit");			// Quit button
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { dispose(); }
		});
		p.add(b);
		add(p, "South");
 		setSize(350, 250);			// Set window size
		setVisible(true);			// Display the window
		resetServer();				// Initialize the server
	}

	// Reset the server
	private void resetServer() {
		log.setText("");			// Empty transaction log
		EchoServer.num = 0;			// Reset transaction number
		println("EchoServer Transaction Log");
		println(new java.util.Date().toString());
		println("EchoServer listening on port " + EchoServer.port + "...");
	}

	// Write a string to the transaction log area
	void println(String s) { log.append(s + "\n"); }

	// Close the server socket
	public void windowClosed(WindowEvent e) { 
		try {	// Close the server socket (if open)
			if (EchoServer.socket!=null) EchoServer.socket.close();
		} catch (IOException x) {		// Whatever...
		} finally { super.windowClosed(e); }	// Terminate Java system
	}
}



// Create the server socket and the window and process transactions
