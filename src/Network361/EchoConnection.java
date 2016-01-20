package Network361;

import java.io.IOException;
import java.net.Socket;

public class EchoConnection extends Connection {
	
	public EchoConnection(Socket _soc) throws IOException {
		super(_soc);
		System.out.println("EchoConnection Established\r");
		this.writeLineToDataOutputStream("Echoing");
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void doRequest() throws IOException {
		if(this.isConnected()){
			String inputString = this.readLineFromBufferedReader();
			if(inputString.equalsIgnoreCase("quit")){
				return;
			}
			else {
				this.writeLineToDataOutputStream(inputString);
			}
		}
	}
	@Override
	public void run() {
		for(;;)
		{
			try {
				doRequest();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
