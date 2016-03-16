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
	private int RoundTripTimes;
	private long TimeOutInterval = 1200;
	private long StartRTT = 0;
	private long AccumulatedRTT = 0;
	private long AvgRTT = 0;
	private long DevRTT = 0;
	private long EstimateRTT = 1200;
	private int MaximumSegmentSize = 1;
	private final double ALPHA = 0.125;
	private final double BETA = 0.25;
	private int NumOfACKReceive = 0;			// Number of Ack received in 1 RTT
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
			NumOfACKReceive = getLastACK();
			Transmitting();
			AdjustingTCPConfiguration();
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
		StartRTT= System.currentTimeMillis();
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
	
	private void AdjustingTCPConfiguration(){
		try {
			setNotifyACK(Packetsent);
			Thread.sleep(TimeOutInterval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			NumOfACKReceive -= getLastACK();
			AdjustingTimeoutValue();
			AdjustingCongestionWindow();
			return;
		}
		System.out.println("Time out");
		if(lastACK == Packetsent){
			NumOfACKReceive -= getLastACK();s
			AdjustingTCPConfiguration();
			AdjustingCongestionWindow();
		}
		else{
			SchedulePacketRetransmit();
		}
	}
	
	private void AdjustingCongestionWindow(){
		if((congestionwindow << 1) > ssthresh){
			congestionwindow++;
		}
		else {
			congestionwindow = congestionwindow << 1;
		}
	}
	
	private void SchedulePacketRetransmit(){
		EstimateRTT = TimeOutInterval;
		ssthresh = congestionwindow >> 1;
		congestionwindow = MaximumSegmentSize;
		Packetsent = lastACK;
	}
	
	private void AdjustingTimeoutValue(){
		long currentRTT = System.currentTimeMillis()-StartRTT;
		AccumulatedRTT += currentRTT;
		AvgRTT = AccumulatedRTT / Packetsent;
		DevRTT = (int)((1-BETA)*DevRTT + BETA*Math.abs(AvgRTT-currentRTT));
		EstimateRTT = (int)((1-ALPHA)*EstimateRTT+ALPHA*AvgRTT);
		TimeOutInterval = EstimateRTT + 4*DevRTT;
		System.out.println("Estimated RTT"+EstimateRTT);
		System.out.println("Dev RTT"+DevRTT);
		System.out.println("New Timeout Value: "+TimeOutInterval);
		System.out.println("AvgRTT: "+ AvgRTT);
	}
}
