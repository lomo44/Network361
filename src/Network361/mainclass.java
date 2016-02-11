package Network361;
import java.io.*;
public class mainclass {
        // second commit from netbeans
	public static void main(String[] args) throws IOException{
		Thread GBNclientThread = new Thread(new TCPClient("localhost",9876));
		GBNclientThread.start();
	}
}
