package Network361;

import java.io.IOException;
import java.util.Scanner;

public class TCPClient extends SimpleClient {
	private int congestionwindow;
	private int ssthresh;
	private int NoPackets;
	private int Packetsent;
	private volatile int lastAck;
	private Scanner userinputscanner;
	
	public TCPClient() {
		// TODO Auto-generated constructor stub
		congestionwindow = 1;
		userinputscanner = new Scanner(System.in);
		Packetsent = 0;
	}

	@Override
	public void run() {
		AskUserForInput();
		SendingInitialInformation();
		while(!isTransmittingFinished()){
			Transmitting();
		}
		// TODO Auto-generated method stub
	}
	
	public void AskUserForInput(){
		System.out.println("Please Enter Number of Packet: ");
		NoPackets = userinputscanner.nextInt();
	}
	
	public void SendingInitialInformation(){
		try {
			System.out.println("Sending Number of Packet to Server.");
			WriteIntToOutput(NoPackets);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Transmitting(){
		for(int i = 1; i<= congestionwindow;i++){
			System.out.println("Sending Packet " + Packetsent);
			Packetsent++;
			try {
				WriteIntToOutput(Packetsent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Conegestion Window Close, Waiting For Acknowledgement.");
	}
	public boolean isTransmittingFinished(){
		return lastAck == Packetsent;
	}
}
