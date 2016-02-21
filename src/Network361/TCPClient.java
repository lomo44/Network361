package Network361;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class TCPClient extends ARQClient {
	private int congestionwindow;
	private int ssthresh;
	private int Packetsent;
	private long TimeOutInterval = 1200;
	private int RoundTripTimes;
	private Scanner userinputscanner;
	
	public TCPClient(String hostname,int portnumber) {
            
		// TODO Auto-generated constructor stub
		congestionwindow = 1;
		userinputscanner = new Scanner(System.in);
		Packetsent = 0;
		RoundTripTimes = 0;
		try {
			Connect(hostname, portnumber);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		AskUserForInput();
		SendingInitialInformation();
		InitializeACKListener();
		long transmition_start_time = System.currentTimeMillis(); 
		while(!isTransmittingFinished()){
			Transmitting();
			AdjusitngCongestionWindow();
		}
		double transmition_end_time = (System.currentTimeMillis() - transmition_start_time)/1000;
		System.out.println("Data Transimit Finished, Time: "+transmition_end_time + " S");
		System.out.println("Total Round Trip Time: " + RoundTripTimes);
		closeSocket();
		// TODO Auto-generated method stub
	}
	
	private void AskUserForInput(){
		System.out.println("Please Enter Number of Packet: ");
		nofPackets = userinputscanner.nextInt();
		System.out.println("Please Enter Slow Start Thresholde value: ");
		ssthresh = userinputscanner.nextInt();
	}
	
	private void SendingInitialInformation(){
		try {
			System.out.println("Sending Number of Packet to Server.");
			WriteIntToOutput(nofPackets);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void Transmitting(){
		System.out.println("Congestion window size: " + congestionwindow);
		for(int i = 1; i<= congestionwindow && Packetsent < nofPackets ;i++){
			Packetsent++;
			System.out.println("Sending Packet " + Packetsent);
			if(Packetsent <= nofPackets){
				try {
					WriteIntToOutput(Packetsent);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				break;
			}
		}
		RoundTripTimes++;
		//System.out.println("Conegestion Window Close, Waiting For Acknowledgement.");
	}
	private boolean isTransmittingFinished(){
		return lastACK == nofPackets;
	}
	
	private void AdjusitngCongestionWindow(){
		try {
			Thread.sleep(TimeOutInterval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(lastACK == Packetsent){
			if((congestionwindow << 1) > ssthresh){
				congestionwindow++;
			}
			else {
				congestionwindow = congestionwindow << 1;
			}
		}
		else{
			ssthresh = congestionwindow >> 1;
			congestionwindow = 1;
			Packetsent = lastACK;
		}
	}
}
