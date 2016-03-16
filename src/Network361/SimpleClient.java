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
	public void Connect(Socket _soc){
		try {
			datainputreader = new BufferedReader(new InputStreamReader(_soc.getInputStream()));
			dataoutputwriter = new DataOutputStream(clientsocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Socket Not Reachable");
			e.printStackTrace();
		}	
	}
	protected BufferedReader getDataInputReader() {
		return datainputreader;
	}
	protected DataOutputStream getDataOutputWriter() {
		return dataoutputwriter;
	}
	protected void sendIntToOutput(int i) throws IOException{
		getDataOutputWriter().write(i);
	}
	protected void sendLineToOutput(String s) throws IOException{
		dataoutputwriter.writeBytes(s + "\r\n");
	}
	protected void SendPacketToOutPut(Packet _Packet){
		_Packet.SendViaDataStream(dataoutputwriter);
	}
	protected int ReadIntFromInput()throws IOException{
		return getDataInputReader().read();
	}
	protected String ReadLineFromInputReader() throws IOException{
		return datainputreader.readLine();
	}
	protected Socket getSocket(){
		return clientsocket;
	}
	protected void closeSocket(){
		try {
			clientsocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
