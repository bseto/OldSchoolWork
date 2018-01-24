
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
	
	public ArrayList<Vertex> VertexArray = new ArrayList<Vertex>();
	
/*************************************************************************************************************
*Public Functions
*************************************************************************************************************/

	
	/*************************************************************************************************************
	 * Public Function: PopulateVertexArray
	 * Requires: AdjacencyMatrix to be an ArrayList<ArrayList<Integer>> type. 
	 * Promises: Will populate the VertexArray with the Edges.
	 *************************************************************************************************************/
	public void PopulateVertexArray(ArrayList<ArrayList<Integer>> AdjacencyMatrix)
	{
		int size = AdjacencyMatrix.size();
		
		//populates VertexArray with empty Vertices
		for(int vertex = 0; vertex < size; vertex++)
		{
			Vertex newVertex = new Vertex();
			VertexArray.add(newVertex);		
		}
		
		int edgeWeight = 0;
		for(int vertex = 0; vertex < size; vertex++)	//Loops for all Vertices
		{
			for(int edges = 0; edges < size; edges++) //loops to check all vertices
			{	
				//System.out.println("vertex = " + vertex + "|| edges = " + edges);
				edgeWeight = AdjacencyMatrix.get(vertex).get(edges);
				
				if(edgeWeight > 0) 
				{
					VertexArray.get(vertex).setConnectsTo(VertexArray.get(edges),edgeWeight);
				}

			}	
	
		}
	}
	
	/*************************************************************************************************************
	 * Public Function: DepthFirstSearch
	 * Requires: Start and End are within the 0 - sizeOfVertices 
	 * Promises: Will find a path from Start to End, and if no path is found, then will return -1 in the ArrayList
	 * 			 If path is found however, will return an ArrayList<Integer> of a valid path from Start to End
	 *************************************************************************************************************/
	public String DepthFirstSearch(int Start, int End)
	{
		
		Deque<Vertex> vertexStack = new ArrayDeque<Vertex>();	//vertexStack as a stack
		boolean foundNewEdge = false;
	
		int onVertex = Start;		//Starting Vertex "onVertex"
		 
		vertexStack.push(VertexArray.get(Start));
		// System.out.println("DepthFirstSearch: Pushed: " + VertexArray.get(Start).getID() + " Into the stack");
		VertexArray.get(Start).setVisited();
		 
		do
		{
			foundNewEdge = false;
			for(int specificEdge = 0; specificEdge < vertexStack.peek().getNumberOfEdges() && foundNewEdge == false; specificEdge++)
			{
				//tries to find a vertex thats not visited from the vertex that is on the top of the stack
				if(vertexStack.peek().getConnectsTo(specificEdge).getVisited() == false)
				{
					vertexStack.peek().getConnectsTo(specificEdge).setVisited(); //sets the vertex as visited
					//System.out.println("DepthFirstSearch: Pushed: " + vertexStack.peek().getConnectsTo(specificEdge).getID() + " Into the stack");
					vertexStack.push(vertexStack.peek().getConnectsTo(specificEdge)); //pushes vertex to top of stack
					foundNewEdge = true;
				}
			}
			
			if(foundNewEdge == false)
			{
				//if the loop didn't find a new edge, or there is no edges, pop the vertex out of stack.
				vertexStack.pop().getID();
				// System.out.println("DepthFirstSearch: Popped: " +  + " Out of Stack");
			}
			 
			if(!vertexStack.isEmpty())
				onVertex = vertexStack.peek().getID();
			 
		}while(!vertexStack.isEmpty() && onVertex != End);
	
		String output = "";
		 
		if(vertexStack.isEmpty())
			output = Start + ", -1, " + End; 
		else
			for(int element = 0; !vertexStack.isEmpty(); element++)
				output = vertexStack.pop().getID() + ", " +output;
		 
			 
		return output;
		 
	}
	
/*************************************************************************************************************
 * Public Function: BreadthFirstSearch
 * Requires: Start and End are within the 0 - sizeOfVertices 
 * Promises: Will find a path from Start to End, and if no path is found, then will return -1 in the ArrayList
 * 			 If path is found however, will return an ArrayList<Integer> of a valid path from Start to End
 *************************************************************************************************************/
	public String BreadthFirstSearch(int Start, int End)
	{
		Queue<Vertex> Queue = new LinkedList<Vertex>();
		Queue.add(VertexArray.get(Start));
		
		Vertex addVertex;
		int onVertex = Start;		//Starting Vertex "onVertex"
		
		
		//System.out.println("BreadthFirstSearch: Pushed: " + VertexArray.get(Start).getID() + " Into the Queue");
		VertexArray.get(Start).setVisited();
		 
		do
		{
			//place all edges that the current vertex contains all to the queue.
			
			for(int specificEdge = 0; specificEdge < Queue.peek().getNumberOfEdges(); specificEdge++)
			{
				addVertex = Queue.peek().getConnectsTo(specificEdge);
				//System.out.println("Specific Edge = " + specificEdge + " and addVertex = " + addVertex.getID());
				if(!addVertex.getVisited())	//if it isn't visited, then add to queue and such
				{
					Queue.add(addVertex);	//places Vertex onto queue
					addVertex.setPrecursor(Queue.peek().getID());	//sets the Precursor of the Vertex
					//System.out.println("BreadthFirstSearch: Pushed: " + addVertex.getID() + " Into the Queue");
					addVertex.setVisited();
				}
				
				if (addVertex.getID() == End)	//checks to see if the VertexAdded is End.
					onVertex = addVertex.getID();
				
				
			}
			Queue.poll();
			//System.out.println("BreadthFirstSearch: Took out: " + .getID());	//takes the current Vertex out of the Queue.

			
			
		}while(!Queue.isEmpty() && onVertex != End);
		
		String output;
		
		if(onVertex == End)
		{
			int Precursor = onVertex;
			output = Precursor + ", ";
			while(Precursor != -1)
			{
				if(VertexArray.get(Precursor).getPrecursor() != -1)
				{
					Precursor = VertexArray.get(Precursor).getPrecursor();
					output = Precursor + ", " + output;
				}
				else if(VertexArray.get(Precursor).getPrecursor() == -1)
					Precursor = VertexArray.get(Precursor).getPrecursor();
			}
		}
		else
			output = Start + ", -1, " + End;
		
		return output;
	}
	
	/*************************************************************************************************************
	 * Public Function: FindShortestPath
	 * Requires: Start and End are within the 0 - sizeOfVertices 
	 * Promises: Will find the shortest path using Dijkstra's algorithm

	 *************************************************************************************************************/
	public String FindShortestPath(int Start, int End)
	{
		//note, the "visited" part of a Vertex will be considered "Solved" in this function
		
		ArrayList<Vertex> VertexQueue = new ArrayList<Vertex>();
		VertexQueue.add(VertexArray.get(Start));
		VertexArray.get(Start).setVisited();
		
		Vertex currentVertex = VertexQueue.get(0);
		currentVertex.setVertexWeight(0);
		
		int numberSolved = 0;

		do
		{
			for(int edgeIndex = 0; edgeIndex < VertexQueue.get(0).getNumberOfEdges(); edgeIndex++)
			{
				int VertexAndEdge = currentVertex.getConnectsTo().get(edgeIndex).getEdgeWeight()+currentVertex.getVertexWeight();
				
				if(VertexAndEdge < currentVertex.getConnectsTo(edgeIndex).getVertexWeight())
				{	
					//System.out.println(currentVertex.getID() + " updated " + currentVertex.getConnectsTo(edgeIndex).getID() + " with " + VertexAndEdge);
					
					//if currentVertex Weight + the Edge Weight to the next Vertex is smaller than the next Vertex's Weight, then re-adjust the weight
					currentVertex.getConnectsTo(edgeIndex).setVertexWeight(VertexAndEdge);
					//sets the precursor, only sets precursor if the path is smaller than current Vertex weight
					currentVertex.getConnectsTo(edgeIndex).setPrecursor(currentVertex.getID());
					
					//if the vertex we are "visiting" is not in the VertexQueue, then add it into the queue
					if(!VertexQueue.contains(currentVertex.getConnectsTo(edgeIndex)))
					{
						VertexQueue.add(currentVertex.getConnectsTo(edgeIndex));
					}	
				}
			}
			VertexQueue.get(0).setVisited();
			numberSolved++;
			VertexQueue.remove(0);
			//puts the vertex with the smallest weight in the front of the ArrayList
			WeightSort(VertexQueue);
			
			if(VertexQueue.size() != 0)
			{
				currentVertex = VertexQueue.get(0);
			}
			
		}while(numberSolved < VertexArray.size() && VertexQueue.size() != 0);
		
		String output = "";
		
		
		int Precursor = VertexArray.get(End).getID();
		
		if(VertexArray.get(Precursor).getPrecursor() == -1)
		{
			output = Start + ", -1, " + End;
		}
		else
		{
			while(Precursor != -1)
			{
				output = Precursor + ", " + output;
				Precursor = VertexArray.get(Precursor).getPrecursor();
			}
		}
		//System.out.println("Path is: " + output);
		
		return output;
		
	}
	
	
	/*************************************************************************************************************
	 * Public Function: resetVertices
	 * Promises: 	Will reset Precursor to -1 and set all vertices to NotVisited
	 *************************************************************************************************************/
	public void resetVertices()
	{
		for(int vertices = 0; vertices < VertexArray.size(); vertices++)
		{
			VertexArray.get(vertices).setPrecursor(-1);
			VertexArray.get(vertices).setNotVisited();
			VertexArray.get(vertices).setVertexWeight(Integer.MAX_VALUE);
		}
	}
	
	/*************************************************************************************************************
	 * Public Function: printGraph
	 * Promises: For Personal Use to check, could be used for other things
	 *************************************************************************************************************/
	public void printGraph()
	{
		System.out.println("---------------Graph---------------");
		System.out.println("Graph has " + VertexArray.size() + " Vertices");
		System.out.println("Graph has " + VertexArray.get(0).getConnectsTo().get(0).countEdges() + " Edges");
		for(int vertex = 0; vertex < VertexArray.size(); vertex++)
		{
			System.out.println("----Vertex " + vertex + "----");
			for(int edges = 0; edges < VertexArray.get(vertex).getNumberOfEdges(); edges ++)
			{
				System.out.println("Connects to " + VertexArray.get(vertex).getConnectsTo(edges).getID());
			}
		}
	}

/*************************************************************************************************************
*Private Functions
*************************************************************************************************************/

	//applies insertionSort
	private static void WeightSort(ArrayList<Vertex> Vertexarray)
	{
		
		for(int currentIndex = 0; currentIndex < Vertexarray.size(); currentIndex++)
		{
			int weight = Vertexarray.get(currentIndex).getVertexWeight();
			Vertex weightVertex = Vertexarray.get(currentIndex);
			int index;
			
			for(index = currentIndex - 1; (index >= 0) && (Vertexarray.get(index).getVertexWeight() > weight); index--)
				Vertexarray.set(index+1, Vertexarray.get(index));
			
			Vertexarray.set(index+1, weightVertex);
		}
	}


	
}

