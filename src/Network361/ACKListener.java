package Network361;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ACKListener implements Runnable {

	private Socket clientSocket;
	private ARQClient master;
	private Thread masterThread;
	private BufferedReader reader;
	private volatile int notifyACK = -1;
	
	public ACKListener(Socket _soc,ARQClient _master, Thread _masterThread) {
		clientSocket = _soc;
		master = _master;
		masterThread = _masterThread;
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
				//int lastack = reader.read();
				int lastack = Integer.parseInt(reader.readLine());
				if(lastack > master.getLastACK()){
					System.out.println("Acknoledgement " +lastack +" received");
					master.setLastACK(lastack);
					int temp = master.getNumOfAckReceived()+1;
					master.setNumOfAckReceived(temp);
					if(lastack == notifyACK){
						notifyACK = -1;
						masterThread.interrupt();
					}
					if(lastack == master.getTotalACK()){
						break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void SetNotifyACK(int ack){
		System.out.println("Set notification: "+ack);
		notifyACK = ack;
	}

}
