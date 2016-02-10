package Network361;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class GBNClient extends SimpleClient {
	private Scanner intscanner;
	private int nofPackets;
	private int proberror;
	private int windowSize;
	private int timeout;
	private volatile int lastACK;
	private long timeoutarray[];
	
	public GBNClient(String hostname, int portnum){
		try {
			this.Connect(hostname, portnum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		intscanner = new Scanner(System.in);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			InitializeACKListener();
			WriteInitialInformationToServer();
			try {
				SendingPacketToServer();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void WriteInitialInformationToServer() throws IOException{
		System.out.println("Please enter number of packet: ");
		nofPackets = intscanner.nextInt();
		System.out.println("Please enter probability of error: ");
		proberror = intscanner.nextInt();
		System.out.println("Please enter the window size: ");
		windowSize = intscanner.nextInt();
		timeoutarray = new long[windowSize];
		System.out.println("Please enter the timeout value: ");
		timeout = intscanner.nextInt();
		System.out.println("Sending the information to the server.\n");
		WriteIntToOutput(nofPackets);
		WriteIntToOutput(proberror);
	}
	private void SendingPacketToServer() throws IOException, InterruptedException{
		long start_time = System.currentTimeMillis();
		int sent = 0;
		for(int i = 1 ;i <= windowSize;i++){
			sent++;
			System.out.println("Sending Package " + sent);
			WriteIntToOutput(sent);
			timeoutarray[(sent-1)%windowSize] = System.currentTimeMillis();
		}
		
		for(;lastACK < nofPackets;){
			if((sent - lastACK) <= windowSize && sent < nofPackets){
				sent++;
				System.out.println("Sending Package " + sent);
				WriteIntToOutput(sent);
				timeoutarray[(sent-1)%windowSize] = System.currentTimeMillis();
			}
			long currenttime = System.currentTimeMillis();
			if(currenttime - timeoutarray[lastACK%windowSize] >= timeout){
				System.out.println("Time out");
				sent = lastACK;
			}
		}
		long end_time = System.currentTimeMillis();
		double duration = (end_time - start_time)/1000;
		System.out.println("All Package Sent. Time Used: " + duration + " Seconds");
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
	public int getNofPacket(){
		return nofPackets;
	}
}
