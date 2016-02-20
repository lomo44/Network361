package Network361;

import java.io.IOException;
import java.util.Scanner;

public class SWClient extends SimpleClient {
	private Scanner intscanner;
	private int noPackets;
	private int sent;
	public SWClient() {
		// TODO Auto-generated constructor stub
		intscanner = new Scanner(System.in);
	}
	@Override
	public void run(){
		noPackets = intscanner.nextInt();
		sent = 1;
		try {
			System.out.println("Sending number of packet to server");
			WriteIntToOutput(noPackets);
			for(int i = sent; i <= noPackets;){
				WriteIntToOutput(sent);
				int ack = ReadIntFromInput();
				if(ack == sent){
					System.out.println("Package " + sent + " received by the server.\n");
					sent = sent +1;
					i++;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
