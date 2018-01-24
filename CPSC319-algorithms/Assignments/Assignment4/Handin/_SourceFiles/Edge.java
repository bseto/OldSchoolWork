
public class Edge {
	private int Weight;
	private Vertex ConnectsTo;
	
	public static int NumberOfEdges = 0; //total edges
	
/*************************************************************************************************************
*Constructors
*************************************************************************************************************/
	public Edge()						//Default Constructor shouldn't really be used
	{
		ConnectsTo = null;
		Weight = 0;
		NumberOfEdges++;
	}
	
	public Edge(int weight, Vertex connectsTo)		//Meant for Dijkstra's
	{
		Weight = weight;
		ConnectsTo = connectsTo;
		NumberOfEdges++;
	}
	
	public Edge(Vertex connectsTo)		//Meant for w/o Dijkstra's
	{
		Weight = 0;
		ConnectsTo = connectsTo;
		NumberOfEdges++;
	}
	
/*************************************************************************************************************
*Public Functions
*************************************************************************************************************/
	public void setEdgeWeight(int weight)
	{
		Weight = weight;
	}
	
	public int getEdgeWeight()
	{
		return Weight;
	}
	
	public Vertex connectsTo()
	{
		return ConnectsTo;
	}
	
	public int countEdges()
	{
		return NumberOfEdges;
	}
	
	
}
