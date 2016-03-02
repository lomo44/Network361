package Network361;

import java.awt.List;
import java.awt.print.Printable;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.lang.model.element.Element;

import Graph.Graph;

public class RoutingClient extends SimpleClient {

	private Scanner userScanner;
	private int NofNode;
	private Graph routeGraph;
	
	public RoutingClient(String hostname, int portnumber) {
		try {
			Connect(hostname, portnumber);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userScanner = new Scanner(System.in);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		AskForUserInput();
		SendInitialInformation();
		ReceiveInfoAndCreateGraph();
		//routeGraph.print();
		PrintMatrix();
		PrintAllPath();
		
	}
	
	private void AskForUserInput(){
		System.out.println("Please Enter Number of Node");
		NofNode = userScanner.nextInt();
		routeGraph = new Graph(NofNode);
	}
	
	private void SendInitialInformation(){
		try {
			this.WriteIntToOutput(NofNode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void ReceiveInfoAndCreateGraph(){
		try {
			String serverinfo = this.ReadLineFromInputReader();
			StringTokenizer converter = new StringTokenizer(serverinfo);
			for(int i = 0 ; i < NofNode ; i++){
				for(int j = 0; j< NofNode; j++){
					String t = converter.nextToken();
					double weight;
					if(t.equalsIgnoreCase("Infinity")){
						weight = Double.POSITIVE_INFINITY;	
					}
					else{
						 weight = Double.parseDouble(t);
					}
					//System.out.print("i:"+i+"j:"+j+"weight:"+weight);
					routeGraph.AddWeightToAdjacencyMatrix(i, j, weight);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void PrintMatrix(){
		System.out.println("Adjacency Matrix:");
		routeGraph.print();
	}
	private void PrintPath(java.util.List<Integer> list){
		double totaltima = routeGraph.nodelist.get(list.get(list.size()-1)).minDistance;
		System.out.print("Total Time To Reach Node " + (list.get(list.size()-1)) + ": "+ totaltima + " ms, ");
		System.out.print("Path: [");
		for(int i = 0; i < list.size();i++){
			if(i!=list.size()-1)
			System.out.print(list.get(i)+",");
			else{
				System.out.print(list.get(i));
			}
		}
		System.out.print("]\n");	
	}
	private void PrintAllPath(){
		for(int i = 0; i < NofNode; i++){
			routeGraph.FindShortesDistance(i);
			System.out.println("\nNode "+i);
			for(int j = 0; j < NofNode; j++){
				PrintPath(routeGraph.GetShortesPathTo(j));
			}		
		}
	}

}