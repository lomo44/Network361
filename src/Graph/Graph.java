package Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;


public class Graph {
	
	public ArrayList<Node> nodelist;
	public ArrayList<Edge> edgelist;
	public int NofNode;
	
	public Graph(int NofNode){
		this.NofNode = NofNode;
		nodelist = new ArrayList<Node>();
		for(int i = 0; i < NofNode ; i++){
			nodelist.add(new Node(i));
		}
	}
	public void AddWeightToAdjacencyMatrix(int from, int to, double weight){
		nodelist.get(from).neighbors.add(new Edge(nodelist.get(to), weight));
		//nodelist.get(to).neighbors.add(new Edge(nodelist.get(from), weight));
	}
	public void FindShortesDistance(int sourcenode){
		for (Node x : nodelist) {
			x.Reset();
		}
		PriorityQueue<Node> _Queue = new PriorityQueue<Node>();
		nodelist.get(sourcenode).minDistance = 0;
		_Queue.addAll(nodelist);
		while (_Queue.size()!=0) {
			Node _n = _Queue.poll();
			for(int i = 0; i < _n.neighbors.size();i++){
				Edge _e = _n.neighbors.get(i);
				double _w = _n.minDistance+_e.weight;
				if(_w < _e.target.minDistance){
					_e.target.minDistance = _w;
					_e.target.previous = _n;
				}
			}
		}
	}
	public List<Integer> GetShortesPathTo(int target){
		List<Integer> path = new ArrayList<Integer>();
		Node _preNode = nodelist.get(target);
		for(;_preNode.previous!=null;_preNode = _preNode.previous){
			path.add(_preNode.name);
		}
		path.add(_preNode.name);
		Collections.reverse(path);
		return path;
	}
	
	public void print(){
		for(int i = 0; i <NofNode; i++){
			Node n = nodelist.get(i);
			for(int j = 0; j < n.neighbors.size(); j++){
				Edge edge = n.neighbors.get(j);
				System.out.print(edge.weight + " ");
			}
			System.out.print("\n");
		}
	}
}