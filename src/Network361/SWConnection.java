package Network361;

import java.io.IOException;
import java.net.Socket;

public class SWConnection extends Connection {

	private int noPack;
	private int lastack;
	
	public SWConnection(Socket _soc) {
		super(_soc);
		lastack = 0;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			doRequest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doRequest() throws IOException {
		// TODO Auto-generated method stub
		int noPack = readIntegerFromBufferedReader();
		System.out.println("Server side expect " + noPack + " packets. \n");
		for(int i = 1; i <= noPack;){
			int pack = readIntegerFromBufferedReader();
			if(pack == lastack + 1){
				System.out.println("Packet "+pack+" received, sending ack");
				writeIntToDataOutputStream(pack);
				i +=1;
				lastack+=1;
			}
		}
	}

}
