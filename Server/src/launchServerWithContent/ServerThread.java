package launchServerWithContent; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
public class ServerThread extends Thread {
    private Socket socket = null;
    public ServerThread(Socket socket) {
        super("ServerThread");
        this.socket = socket;
    }
    public void run() {
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        		OutputStream oout = socket.getOutputStream();
        		
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            ServerProtocol srv = new ServerProtocol(socket, oout);
            outputLine = srv.processInput(null);
            out.println(outputLine);
            while ((inputLine = in.readLine()) != null){
            	outputLine = srv.processInput(inputLine);
            	out.println(outputLine);
            	
                if (outputLine.equals("Bye.")){
                    break;
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
