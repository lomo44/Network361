package Graph;

import java.util.ArrayList;

public class Node implements Comparable<Node> {
	public final int name;    // Node’s name
	public ArrayList<Edge> neighbors;  // set of neighbors to this node
	public double minDistance = Double.POSITIVE_INFINITY; //Minimum weight,              											//initially inf
	public Node previous;     // to keep the path
	public Node(int argName)  // constructor to create an instance of this class
	{ 
		name = argName; 
		neighbors = new ArrayList<Edge>();
	}
	@Override
	public int compareTo(Node other)
	{
		return Double.compare(minDistance, other.minDistance);
	}
	public void Reset(){
		minDistance = Double.POSITIVE_INFINITY;
		previous = null;
	}

}