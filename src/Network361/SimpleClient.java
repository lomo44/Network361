package Network361;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SimpleClient extends Client {
	private BufferedReader datainputreader;
	private DataOutputStream dataoutputwriter;
	private Socket clientsocket;
	
	public SimpleClient() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub

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

}
