package Network361;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;


public class TCPClient extends ARQClient {
	protected int congestionwindow;
	protected int ssthresh;
	protected int totalNumOfPacketSent;
	private int RoundTripTimes;
	protected long TimeOutInterval = 1200;
	private long StartRTT = 0;
	private long AccumulatedRTT = 0;
	private long AvgRTT = 0;
	private long DevRTT = 0;
	protected long EstimateRTT = 1200;
	protected int MaximumSegmentSize = 1;
	private final double ALPHA = 0.125;
	private final double BETA = 0.25;
	private int NumOfACKReceive = 0;			// Number of Ack received in 1 RTT
	private int NumOfPACSend = 0;
	private Scanner userinputscanner;
	
	public TCPClient(String hostname,int portnumber) {
            
		// TODO Auto-generated constructor stub
		congestionwindow = MaximumSegmentSize;
		userinputscanner = new Scanner(System.in);
		totalNumOfPacketSent = 0;
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
			NumOfACKReceive = getNumOfAckReceived();
			Transmitting();
			AdjustingTCPConfiguration();
		}
		double transmition_end_time = (System.currentTimeMillis() - transmition_start_time)/1000;
		System.out.println("Data Transimit Finished, Time: "+transmition_end_time + " S");
		System.out.println("Total Round Trip Time: " + RoundTripTimes);
		closeSocket();
		// TODO Auto-generated method stub
	}
	
	protected void AskUserForInput(){
		System.out.println("Please Enter Number of Packet: ");
		totalNumberOfPacket = userinputscanner.nextInt();
		System.out.println("Please Enter Slow Start Thresholde value: ");
		ssthresh = userinputscanner.nextInt();
	}
	
	protected void SendingInitialInformation(){
		try {
			System.out.println("Sending Number of Packet to Server.");
			sendIntToOutput(totalNumberOfPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void Transmitting(){
		System.out.println("Congestion window size: " + congestionwindow);
		StartRTT= System.currentTimeMillis();
		NumOfPACSend = 0;
		for(int i = MaximumSegmentSize; 
				i<= congestionwindow && totalNumOfPacketSent < totalNumberOfPacket ;
				i+= MaximumSegmentSize){
			
			totalNumOfPacketSent++;
			NumOfPACSend++;
			System.out.println("Sending Packet " + totalNumOfPacketSent);
			SendPacket(totalNumOfPacketSent);
		}
		RoundTripTimes++;
		//System.out.println("Conegestion Window Close, Waiting For Acknowledgement.");
	}
	
	protected boolean isTransmittingFinished(){
		return lastACK >= totalNumberOfPacket;
	}
	
	private void AdjustingTCPConfiguration(){
		try {
			setNotifyACK(totalNumOfPacketSent);
			Thread.sleep(TimeOutInterval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			NumOfACKReceive -= getNumOfAckReceived();
			AdjustingTimeoutValue();
			AdjustingCongestionWindow();
			return;
		}
		System.out.println("Time out");
		if(lastACK == totalNumOfPacketSent){
			NumOfACKReceive -= getNumOfAckReceived();
			AdjustingTCPConfiguration();
			AdjustingCongestionWindow();
		}
		else{
			SchedulePacketRetransmit();
		}
	}
	
	private void AdjustingCongestionWindow(){
		if((congestionwindow << 1) > ssthresh){
			// Congestion Avoidance
			if(NumOfACKReceive == NumOfPACSend)
				congestionwindow+=MaximumSegmentSize;
		}
		else {
			// Slow Start
			congestionwindow = congestionwindow + NumOfACKReceive * MaximumSegmentSize;
		}
	}
	
	protected void SchedulePacketRetransmit(){
		EstimateRTT = TimeOutInterval;
		ssthresh = congestionwindow >> 1;
		congestionwindow = MaximumSegmentSize;
		totalNumOfPacketSent = lastACK;
	}
	
	protected void AdjustingTimeoutValue(){
		long currentRTT = System.currentTimeMillis()-StartRTT;
		AccumulatedRTT += currentRTT;
		AvgRTT = AccumulatedRTT / totalNumOfPacketSent;
		DevRTT = (int)((1-BETA)*DevRTT + BETA*Math.abs(AvgRTT-currentRTT));
		EstimateRTT = (int)((1-ALPHA)*EstimateRTT+ALPHA*AvgRTT);
		TimeOutInterval = EstimateRTT + 4*DevRTT;
		System.out.println("Estimated RTT"+EstimateRTT);
		System.out.println("Dev RTT"+DevRTT);
		System.out.println("New Timeout Value: "+TimeOutInterval);
		System.out.println("AvgRTT: "+ AvgRTT);
	}
	protected void SendPacket(int packetnumber) {
		try {
			sendIntToOutput(packetnumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
