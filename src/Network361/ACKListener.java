package Network361;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ACKListener implements Runnable {

	private Socket clientSocket;
	private GBNClient master;
	private BufferedReader reader;
	
	public ACKListener(Socket _soc,GBNClient _master) {
		clientSocket = _soc;
		master = _master;
		try {
			reader = new BufferedReader(new InputStreamReader(_soc.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(;;){
			try {
				int lastack = reader.read();
				master.setLastACK(lastack);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}