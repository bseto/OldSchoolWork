import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;



public class Assign4 {


	static ArrayList<ArrayList<Integer>> AdjacencyMatrix = new ArrayList<ArrayList<Integer>>();

	
	static int Num_Of_Vertices = 0;
/*************************************************************************************************************
*Public Functions
*************************************************************************************************************/

	
	/*************************************************************************************************************
	 * Public Function: ReadFile
	 * Requires: String[] args with the 0th element holding a valid name of input file
	 * Promises: To read the File from args, and then copies the Textfile given, and places it into a Adjacency
	 *           Matrix that can be used later.
	 *************************************************************************************************************/
	public static void ReadFile(String[] args) throws FileNotFoundException
	{
		if(args.length >= 1 && args[0] != null)			//checks to see if there EXISTS an input filename
		{
			int input;
			Scanner a = new Scanner(new FileReader(args[0]));		//read the "input" of the args
			
			while(a.hasNextLine())
			{
				a.nextLine();
				Num_Of_Vertices++;
				ArrayList<Integer> temp = new ArrayList<Integer>();
				AdjacencyMatrix.add(temp);
			}
			a.close();
	
			a = new Scanner(new FileReader(args[0]));
			
			for(int Vertex = 0; a.hasNextLine() && Vertex < Num_Of_Vertices; Vertex++)
			{
				for(int element = 0; element < Num_Of_Vertices && a.hasNextInt(); element++)
				{
					input = a.nextInt();
					//System.out.println("ReadFile: Vertex is: " + Vertex);
					InsertToTable(Vertex,input);

				}
			}
			
			a.close();
			System.out.println("ReadFile: Done reading File, Done Inserting into Table");
		}
		else
		{
			System.out.println("ReadFile: No Input filename Detected!");
		}

	}
	
	/*************************************************************************************************************
	 * Public Function: ReadFile
	 * Requires: String[] args with the 0th element holding a valid name of input file
	 * Promises: To read the File from args, and then copies the Textfile given, and places it into a Adjacency
	 *           Matrix that can be used later.
	 * @throws FileNotFoundException 
	 *************************************************************************************************************/

	public static void ReadQuery(String args[]) throws FileNotFoundException
	{
		if (args.length > 1 && args[1] != null)
		{
			//setting up the graph
			Graph Assign4Graph = new Graph();
			Assign4Graph.PopulateVertexArray(AdjacencyMatrix);
			
			int Start;
			int End;
			
			//------------------------------------------------------------------------------
			//Depth First
			//------------------------------------------------------------------------------
			
			Scanner a = new Scanner(new FileReader(args[1]));		//read the "input" of the args
			Scanner checker = new Scanner(new FileReader(args[1]));
			String output = "";
			

		
			while(checker.hasNextLine())
			{
				checker.nextLine();
				Start = a.nextInt();
				End = a.nextInt();
				output = output + Assign4Graph.DepthFirstSearch(Start,End) + "\n";
				Assign4Graph.resetVertices();
			}
			
			PrintToFile(output, args[2], "Depth First");
			
			a.close();
			checker.close();
			System.out.println("DepthFirstSearch: Done");
			
			//------------------------------------------------------------------------------
			//Breadth First
			//------------------------------------------------------------------------------
			
			a = new Scanner(new FileReader(args[1]));		//read the "input" of the args
			checker = new Scanner(new FileReader(args[1]));
			output = "";

		
			while(checker.hasNextLine())
			{
				checker.nextLine();
				Start = a.nextInt();
				End = a.nextInt();
				output = output + Assign4Graph.BreadthFirstSearch(Start,End) + "\n";
				
				Assign4Graph.resetVertices();
			}
			PrintToFile(output, args[3], "Breadth First");
			
			a.close();
			checker.close();
			System.out.println("BreadthFirstSearch: Done");
			
			
			//------------------------------------------------------------------------------
			//Dijkstra's 
			//------------------------------------------------------------------------------
			a = new Scanner(new FileReader(args[1]));
			checker = new Scanner(new FileReader(args[1]));
			output = "";
			
		
			while(checker.hasNextLine())
			{
				checker.nextLine();
				Start = a.nextInt();
				End = a.nextInt();
				output = output + Assign4Graph.FindShortestPath(Start, End) + "\n";
				Assign4Graph.resetVertices();
			}
			PrintToFile(output, args[4], "Dijkstra's");
			
			a.close();
			checker.close();
			System.out.println("Dijkstra's Algorithm: Done");
			
			//System.out.println(Assign4Graph.BreadthFirstSearch(1, 8));
		}
		else
		{
			System.out.println("No Query Arguement Name Detected!");
		}
	}
	
	public static void main(String args[]) throws FileNotFoundException
	{
		ReadFile(args);		//populates the Adjacency Matrix
		ReadQuery(args);	//Read Query and Outputs results to files
		
		
/*		
		Graph assign4 = new Graph();
		assign4.PopulateVertexArray(AdjacencyMatrix);
		
		assign4.resetVertices();
		assign4.FindShortestPath(0, 7);
		assign4.resetVertices();
		assign4.FindShortestPath(0, 8);

		
		*/
	}
	
/*************************************************************************************************************
*Private Functions
*************************************************************************************************************/

	/*************************************************************************************************************
	 * Private Function: InsertToTable
	 * Caller: Meant for Public Function
	 * Requires: Vertex and ConnectsTo to be valid Vertex's
	 * Promises: To add the ConnectsTo to the Vertex
	 *************************************************************************************************************/
	private static void InsertToTable(int Vertex, int ConnectsTo)
	{
		AdjacencyMatrix.get(Vertex).add(ConnectsTo);				//Adds ConnectsTo to the Vertex.
	}
	
	private static void PrintToFile(String Output, String Filename, String Type) throws FileNotFoundException
	{
		PrintWriter writeToFile = new PrintWriter(Filename);
		writeToFile.println("**NOTE** Open the .txt file with Notepad++ or Eclipse as notepad doesn't display properly");
		writeToFile.println("Byron's CPSC Assignment 4 Graph Traversals for " + Type);
		writeToFile.println("---------------------------------------------------------------------");
		writeToFile.println(Output);
		
		writeToFile.close();
		
	}
	

}
