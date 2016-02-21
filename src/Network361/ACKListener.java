package Network361;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ACKListener implements Runnable {

	private Socket clientSocket;
	private ARQClient master;
	private BufferedReader reader;
	
	public ACKListener(Socket _soc,ARQClient _master) {
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
		System.out.println("Acknowledgement Listener Established.Waiting for ack");
		for(;;){
			try {
				if(clientSocket.isClosed()){
					break;
				}
				int lastack = reader.read();
				if(lastack > master.getLastACK()){
					System.out.println("Acknoledgement " +lastack +" received");
					master.setLastACK(lastack);
					if(lastack == master.getNumberOfPacket()){
						break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
