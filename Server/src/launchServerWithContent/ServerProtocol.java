package launchServerWithContent;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
	public class ServerProtocol {
		  private static final int WAITING = 0;
 		    private static final int NEXTCOMMAND = 1;
		    private static final int TEXT = 2;
		    private static final int MUSIC = 3;
		    private static final int QUIT = 4;
		    private int state = WAITING;
			private OutputStream out;
			private Socket socket;
 
		    public ServerProtocol(Socket socket, OutputStream out) {
		    	this.socket = socket;
		    	this.out = out;
		    }

			public String processInput(String theInput) {
		        String theOutput = null;
		        if(state == WAITING){
		        	theOutput = "Welcome to the server, please use 'Text', 'Music' or 'Quit / exit'";
		        	state = NEXTCOMMAND;
		        }else if(state == NEXTCOMMAND){
		        	if(theInput.equalsIgnoreCase("Quit")|| theInput.equalsIgnoreCase("exit")){
		        		theOutput = "Quitting Server, please hit enter";
		        		state = QUIT;
		        	}else if(theInput.equalsIgnoreCase("Music")){
 		        		theOutput = "Press enter to go to Music mode";
		        		state = MUSIC;
		        	}else if(theInput.equalsIgnoreCase("Text")){
 		        		theOutput = "Entering echo mode, use quit or exit to leave.";
		        		state = TEXT;
		        	}else{
		        		theOutput = "Please use one of the commands";
		        		state = NEXTCOMMAND;
		        	}
		        }else if(state == TEXT){
 		        	if(theInput.equalsIgnoreCase("quit") || theInput.equalsIgnoreCase("exit")){
		        		theOutput = "";
 		        		state = NEXTCOMMAND;
		        	}else{
		        		theOutput = theInput;
		        	}
		        }else if(state == MUSIC){
		        	theOutput = "Music yet to be implemented";
	 
		        	state = NEXTCOMMAND;
		        }else if(state == QUIT){
		        	theOutput = "Bye.";
		        }
 		        return theOutput;
		    }
		}
	
	