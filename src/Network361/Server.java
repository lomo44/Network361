package Network361;
import java.net.*;
import java.io.*;
//
public abstract class Server{
	
	private ServerSocket serverSocket;
	private String serverName;
	
	public Server(String _servername) throws UnknownHostException {
		try {
			this.setServerName(_servername);
			System.out.println(_servername + " server created \n");	
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
	abstract void ServerRun() throws IOException;
	public void ServerStop() throws UnknownHostException{
		try {
			this.serverSocket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	protected Socket getRequestSocket() throws IOException {
		return serverSocket.accept();
	}
	
	protected void listento(int portnum) throws IOException{
		serverSocket = new ServerSocket(portnum);
	}
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
}
