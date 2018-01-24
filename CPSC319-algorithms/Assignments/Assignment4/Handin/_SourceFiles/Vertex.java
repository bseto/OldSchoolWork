import java.util.ArrayList;


public class Vertex {

	private int VertexID;
	private boolean Visited;
	private int VertexWeight;
	private ArrayList<Edge> EdgeArray = new ArrayList<Edge>();
	private int Precursor; // required for breadth
	
	private static int iD = 0;
	
/*************************************************************************************************************
*Constructors
*************************************************************************************************************/

	public Vertex()	//should create a clean Vertex, then simply add and add.
	{
		Precursor = -1;
		VertexID = iD++;
		Visited = false;
		VertexWeight = Integer.MAX_VALUE;	//Used for Dijkstra's Shortest Path
	}
	
	public Vertex(Vertex other)	//not really a use, probs not consistent
	{
		Precursor = -1;
		VertexID = iD++;
		Visited = false;
		VertexWeight = Integer.MAX_VALUE;
		setConnectsTo(other);
	}
	
	
	
	
/*************************************************************************************************************
*Public Functions
*************************************************************************************************************/
	//Will create a new Edge connection from called, to other and add to called's 
	//EdgeArray
	public void setConnectsTo(Vertex other)
	{
		Edge newEdge = new Edge(other);
		EdgeArray.add(newEdge);
	}
	
	public void setConnectsTo(Vertex other, int weight)
	{
		Edge newEdge = new Edge(weight, other);
		EdgeArray.add(newEdge);
	}
	
	public ArrayList<Edge> getConnectsTo()
	{
		return EdgeArray;
	}
	
	//will return the Vertex that this edge connects to
	public Vertex getConnectsTo(int specificEdge)
	{
		if (specificEdge < EdgeArray.size())
			return EdgeArray.get(specificEdge).connectsTo();
		else
			System.out.println("getConnectsTo: Invalid Edge, Edge out of bounds. Returning 0");
		
		return null;
	}
	
	public int getVertexWeight()
	{
		return VertexWeight;
	}
	
	public void setVertexWeight(int weight)
	{
		VertexWeight = weight;
	}
	
	public int getNumberOfEdges()
	{
		return EdgeArray.size();
	}
	
	public boolean getVisited()
	{
		return Visited;
	}
	
	public void setVisited()
	{
		Visited = true;
	}
	
	public void setNotVisited()	//just in case later.
	{
		Visited = false;
	}
	
	public int getID()
	{
		return VertexID;
	}
	
	public int getPrecursor()
	{
		return Precursor;
	}
	
	public void setPrecursor(int precursor)
	{
		Precursor = precursor;
	}
	

	
}
