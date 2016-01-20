package Network361;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;



public abstract class Client implements Runnable {
	BufferedReader userinput;
	
	public Client(){
		userinput = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public Socket ConnectToHost(String hostname, int portnumber) throws UnknownHostException, IOException{
		return new Socket(hostname,portnumber);
	}
	
	public String getUserInput() throws IOException{
		return userinput.readLine();
	}
}
