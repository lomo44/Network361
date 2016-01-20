package Network361;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public abstract class Connection implements Runnable{
	private Socket ConnectionSocket;
	private int ConnectionNumber;
	private boolean isConnected;
	private BufferedReader inputreader;
	private DataOutputStream outputwriter;
	
	public Connection(Socket _soc){
		this.setConnectionSocket(_soc);
		this.setConnected(true);
		try {
			this.inputreader = new BufferedReader(new InputStreamReader(ConnectionSocket.getInputStream()));
			this.outputwriter = new DataOutputStream(ConnectionSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Socket getConnectionSocket() {
		return ConnectionSocket;
	}
	public void setConnectionSocket(Socket connectionSocket) {
		ConnectionSocket = connectionSocket;
	}
	public int getConnectionNumber() {
		return ConnectionNumber;
	}
	public void setConnectionNumber(int connectionNumber) {
		ConnectionNumber = connectionNumber;
	}
	public boolean isConnected() {
		return isConnected;
	}
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	public void close() throws IOException{
		this.ConnectionSocket.close();
	}
	public String readLineFromBufferedReader() throws IOException{
		return inputreader.readLine();
	}
        public int readIntegerFromBufferedReader() throws IOException{
                return Integer.parseInt(inputreader.readLine());
        }
	public void writeLineToDataOutputStream(String s) throws IOException{
		outputwriter.writeBytes(s + "\r\n");
	}
	protected DataOutputStream getOutputwriter() {
		return this.outputwriter;
	}
	abstract protected void doRequest() throws IOException;
}
