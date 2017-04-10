package launchServerWithContent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
	public class ServerProtocol {
		  private static final int WAITING = 0;
 		    private static final int NEXTCOMMAND = 1;
		    private static final int TEXT = 2;
		    private static final int MUSIC = 3;
		    private static final int QUIT = 4;
		    private static final int STREAM = 5;
		    private int state = WAITING;
 			Socket socket;
			public boolean finished = false;
		    public ServerProtocol(Socket socket) {
		    	this.socket = socket;
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
		        	}else if(theInput.equalsIgnoreCase("Streaming")){
		        		theOutput = "streaming";
		        	}else{
		        		theOutput = "Please use one of the commands";
		        		if(finished == true){
		        		state = WAITING;
		        		System.out.println("Finished!");
		        		}
		        	}
		        }else if(state == TEXT){
 		        	if(theInput.equalsIgnoreCase("quit") || theInput.equalsIgnoreCase("exit")){
		        		theOutput = "";
 		        		state = NEXTCOMMAND;
		        	}else{
		        		theOutput = theInput;
		        	}
		        }else if(state == MUSIC){
		        	theOutput = "File now streaming";
		        	if(theInput.equalsIgnoreCase("Quit")){
			        	state = NEXTCOMMAND;	
		        	}
		        }else if(state == QUIT){
		        	theOutput = "Bye.";
		        }else if(state == STREAM){
		        	
		        }
 		        return theOutput;
		    }
		}
	
	