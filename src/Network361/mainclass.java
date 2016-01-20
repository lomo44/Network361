package Network361;
import java.io.*;
public class mainclass {
	public static void main(String[] args) throws IOException{
		FTPServer newFTPServer = new FTPServer("JianJian");
		newFTPServer.ServerRun();
	}
}
