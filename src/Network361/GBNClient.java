package Network361;

import java.io.IOException;
import java.util.Scanner;

public class GBNClient extends SimpleClient {
	private Scanner intscanner;
	private int nofPackets;
	private int proberror;
	private int lastACK = 0;
	
	public GBNClient(){
		intscanner = new Scanner(System.in);
	}
	public void run() {
		// TODO Auto-generated method stub
		try {
			WriteInitialInformationToServer();
			SendingPacketToServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void WriteInitialInformationToServer() throws IOException{
		System.out.println("Please enter number of packet: ");
		nofPackets = intscanner.nextInt();
		System.out.println("\nPlease enter probability of error: ");
		proberror = intscanner.nextInt();
		System.out.println("\nSending the information to the server.\n");
		WriteIntToOutput(nofPackets);
		WriteIntToOutput(proberror);
	}
	private void SendingPacketToServer() throws IOException{
		for(int i = 1; i <= nofPackets;i++){
			WriteIntToOutput(i);
		}
	}
	private void InitializeACKListener(){
		Thread newthread = new Thread(new ACKListener(getSocket(), this));
		newthread.start();

	}
	public int getLastACK() {
		return lastACK;
	}
	public void setLastACK(int lastACK) {
		this.lastACK = lastACK;
	}

}
