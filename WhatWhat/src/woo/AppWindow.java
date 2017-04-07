package woo;

import java.awt.event.WindowEvent;
public class AppWindow extends JFrame {
	String title;
	public AppWindow(String title){
		this.title = title;
	}
	public void windowClosed(WindowEvent e) {
		System.exit(0);
		
	}

}
