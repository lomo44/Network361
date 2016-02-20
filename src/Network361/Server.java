package Network361;
import java.net.*;
import java.io.*;
//
public class Server{
	
	private ServerSocket serverSocket;
	private String serverName;
	private ConnectionThreadFactory connectionfactory;
	
	public Server(String _servername) throws UnknownHostException {
		try {
			this.setServerName(_servername);
			System.out.println(_servername + " server created \n");	
			connectionfactory = new ConnectionThreadFactory();
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
	void ServerRun() throws IOException{
		ConnectionType newtype = ConnectionType.Stop_And_Wait;
		listento(8888);
		for(;;){
			connectionfactory.getConnectionThread(newtype, getRequestSocket()).start();
		}
	}
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
