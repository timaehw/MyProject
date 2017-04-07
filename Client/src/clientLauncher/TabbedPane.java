package clientLauncher;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class TabbedPane {
	final String BUTTONPANEL = "Tab with JButtons";
    final String TEXTPANEL = "Tab with JTextField";
    final int extraWindowWidth = 100;
    JTextField clientInput = new JTextField();
    String newLine = System.lineSeparator();
    JTextArea serverResponses = new JTextArea();
    JScrollPane jsp = new JScrollPane(serverResponses);
 	public StringBuilder fromUI;
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
        card1.add(new JButton("Button 1"));
        card1.add(new JButton("Button 2"));
        card1.add(new JButton("Button 3"));
        card2.add(jsp, BorderLayout.CENTER);
        card2.add(clientInput, BorderLayout.AFTER_LAST_LINE);
         tabbedPane.addTab(BUTTONPANEL, card1);
        tabbedPane.addTab(TEXTPANEL, card2);
        clientInput.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent arg0) {
				try{
				if(!clientInput.getText().equals(null)){
				setfromUI(clientInput.getText());
				System.out.println(fromUI);
				}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
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
        frame.pack();
        frame.setVisible(true);
    }
	public StringBuilder setfromUI(String text) {
		return fromUI = new StringBuilder(text);
	}
	public StringBuilder getfromUI(){
		return fromUI;
	}
	public void setUI(String fromServer) {
		serverResponses.append(fromServer);
	}
}