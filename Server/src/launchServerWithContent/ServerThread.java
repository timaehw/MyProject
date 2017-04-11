package launchServerWithContent; 

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
 import java.net.Socket;
public class ServerThread extends Thread {
    private Socket socket = null;
	private OutputStream oos;
 	String vidURL = "/home/timmeh/workspace/Server/src/vid.mp4"; 
     public ServerThread(Socket socket) {
        super("ServerThread");
        this.socket = socket;
    }
    public void run() {
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            ServerProtocol srv = new ServerProtocol(socket);
            outputLine = srv.processInput(null);
            out.println(outputLine);
           while(true){
            if((inputLine = in.readLine()) != null){
            	outputLine = srv.processInput(inputLine);
            	out.println(outputLine);
            	if(outputLine.equals("File now streaming")){
            		out.flush();
            		File myMusicFile = new File("/home/timmeh/workspace/Server/src/pp.wav");
		        	byte[] buf = new byte[(int) myMusicFile.length()];
		        	FileInputStream fis = null;
		        	oos = null;
		        	try{
		        		fis = new FileInputStream(myMusicFile);
		        	}catch(FileNotFoundException fnfe){
		        		fnfe.printStackTrace();
		        	}
		        	BufferedInputStream bis = new BufferedInputStream(fis);
		        	try{
		        		bis.read(buf, 0, buf.length);
		        		oos = socket.getOutputStream();
		        		oos.write(buf,0,buf.length);
		        		oos.flush();
		        	}catch(IOException io1){
		        		io1.printStackTrace();
		        	}
		        	oos.flush();
		        	fis.close();
		        	oos.close();
  				}if(outputLine.equalsIgnoreCase("Streaming")){
  					new DecodeAndPlayVideo();
					DecodeAndPlayVideo.main(null);
  				}
                if (outputLine.equals("Bye.")){
                    break;
            }
            }
           }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			socket.close();
		} catch (IOException e) {
 			e.printStackTrace();
		}
    }
}
