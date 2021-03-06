package Network361;

import java.io.IOException;
import java.util.ArrayList;

import javax.print.attribute.Size2DSyntax;

import Graph.Path;

public class FTPTCPClient extends TCPClient {
	
	private RoutingClient routingClient;
	private int segmentsize = 1000;
	private ArrayList<Packet> packetsList;
	private int transferfilesize = 0;
	private String filepath;
	
	public FTPTCPClient(String hostname, int portnumber) {
		super(hostname, portnumber);
		routingClient = new RoutingClient(getSocket());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void SendPacket(int packetnumber){
		packetsList.get(packetnumber-1).SendViaDataStream(getDataOutputWriter());
	}
	
	public void GetRoutingInfo(){
		int nofnode = 0;
		try {
			nofnode = Integer.parseInt(ReadLineFromInputReader());
			routingClient.InitializeRoutingGraph(nofnode);
			routingClient.ReceiveInfoAndCreateGraph();
			Path path = routingClient.GetShortestPath(0, nofnode-1);
			System.out.println(path.m_PacketList.toString());
			sendLineToOutput(path.m_PacketList.toString());
			EstimateRTT = (long) path.totalweight;
			TimeOutInterval = (long) path.totalweight * 2 + 200;
			
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void PrepareFileTransfer() throws IOException{
		FileSplitter splitter = new FileSplitter(filepath, segmentsize);
		packetsList = splitter.CheckOutPackets();
		transferfilesize = splitter.getFileLength();
		MaximumSegmentSize = packetsList.get(0).getPacketSize();
		congestionwindow = MaximumSegmentSize;
		totalNumberOfPacket = packetsList.size();
	}
	@Override
	protected void AdjustingTimeoutValue(){
		/*Overwrite timeout value so that it is fixed.
		 * */
	}
	@Override
	protected void AskUserForInput(){
		GetRoutingInfo();
		System.out.println("Please enter the file path: ");
		try {
			filepath = getUserInput();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void SendingInitialInformation(){	
		try {
			sendLineToOutput(filepath);
			PrepareFileTransfer();
			sendLineToOutput(Integer.toString(packetsList.size()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
