package clientLauncher;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
public class ExtendedLauncher {
	public String hostName = "localhost";
    public int portNumber = 4444;
    public Socket srvSocket;
	public BufferedWriter bw;
	public FileWriter fw;
	public TabbedPane tp = new TabbedPane();
	public String newLine = System.lineSeparator();
	public StringBuilder fromUI;
	public Path p1 = Paths.get(System.getProperty("user.home"), "ServerResponses.txt");
	public File f1;
 	public ExtendedLauncher(){
		run();
	}

	public void run() {
		tp.createAndShowGUI();
         try (
            Socket srvSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(srvSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(srvSocket.getInputStream()));
        ) {
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
                tp.setUI(fromServer);
                tp.setUI(newLine);
            
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
                if(tp.fromUI != null){
                StringBuilder fromUI = tp.getfromUI();
                System.out.println(fromUI.toString());
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
}