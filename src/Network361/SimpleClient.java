package Network361;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;


public abstract class SimpleClient extends Client {
	private BufferedReader datainputreader;
	private DataOutputStream dataoutputwriter;
	private Socket clientsocket;
	
	public SimpleClient() {
		// TODO Auto-generated constructor stub
	}
	public void Connect(String hostname, int portnumber) throws UnknownHostException, IOException{
		this.clientsocket = super.ConnectToHost(hostname, portnumber);
		datainputreader = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
		dataoutputwriter = new DataOutputStream(clientsocket.getOutputStream());
	}
	protected BufferedReader getDataInputReader() {
		return datainputreader;
	}
	protected DataOutputStream getDataOutputWriter() {
		return dataoutputwriter;
	}
	protected void WriteIntToOutput(int i) throws IOException{
		getDataOutputWriter().write(i);
	}
	protected int ReadIntFromInput()throws IOException{
		return getDataInputReader().read();
	}
	protected Socket getSocket(){
		return clientsocket;
	}

}
