package clientLauncher;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class ExtendedLauncher implements ActionListener{
	public String hostName = "localhost";
    public int portNumber = 4444;
    public Socket srvSocket;
	public BufferedWriter bw;
	public FileWriter fw;
 	public String newLine = System.lineSeparator();
 	public Path p1 = Paths.get(System.getProperty("user.home"), "ServerResponses.txt");
	public File f1;
    public static final String CHARSET_NAME = "UTF-8";
	final String BUTTONPANEL = "Tab with JButtons";
    public final String TEXTPANEL = "Tab with JTextField";
    public final int extraWindowWidth = 100;
    public JTextField clientInput = new JTextField();
    public JTextArea serverResponses = new JTextArea();
    public JScrollPane jsp = new JScrollPane(serverResponses);
    public PrintWriter out;
  	public ExtendedLauncher(){
		run();
	}
    
    public void addComponentToPane(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();
        serverResponses.setText("Server Repsonses: " + newLine);
        //Create the "cards".
        JPanel card2 = new JPanel(new BorderLayout()) {
			private static final long serialVersionUID = 1L;
			public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };
        JPanel card1 = new JPanel(new GridLayout(2,2));
        serverResponses.setEditable(false);
        card1.add(new JButton("Button 1"));
        card1.add(new JButton("Button 2"));
        card1.add(new JButton("Button 3"));
        card2.add(jsp, BorderLayout.CENTER);
        card2.add(clientInput, BorderLayout.AFTER_LAST_LINE);
        tabbedPane.addTab(BUTTONPANEL, card1);
        tabbedPane.addTab(TEXTPANEL, card2);
        clientInput.addActionListener(this);
        pane.add(tabbedPane, BorderLayout.CENTER);
    }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TabDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        //TabbedPane tp = new TabbedPane();
        addComponentToPane(frame.getContentPane());
        //Display the window.
        frame.setSize(350,275);
        frame.setVisible(true);
    }
 
	public void setUI(String fromServer) {
		serverResponses.append(fromServer);
	}
	
	public void run() {
		createAndShowGUI();
         try (
            Socket srvSocket = new Socket(hostName, portNumber);
            PrintWriter stdOut = new PrintWriter(new OutputStreamWriter(System.out, CHARSET_NAME));
            BufferedReader in = new BufferedReader(
                new InputStreamReader(srvSocket.getInputStream()));
        ) {
             out = new PrintWriter(srvSocket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            f1 = new File(p1.toString());
            fw = new FileWriter(p1.toString());
            bw = new BufferedWriter(fw);
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                bw.write(fromServer);
                bw.newLine();
                setUI(fromServer);
                setUI(newLine);
            
                if (fromServer.equals("Bye.")){
                 	bw.close();
                	fw.close();
                	stdIn.close();
                	in.close();
                	out.close();
                	srvSocket.close();
                	break;
            }	
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }		

                
            } 	
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            e.printStackTrace();
            System.exit(0);
        }
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		out.println(clientInput.getText());
		System.out.println(clientInput.getText());
		clientInput.setText("");
	};			
}