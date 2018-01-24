import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Assign3 {

	//private ArrayList<String> hashTable = new ArrayList<String>();
	public static String[] HashTable;
	static int Num_of_words = 0;
	static int Num_of_steps = 0;
	static int Largest_step = 0;
	static int Collisions = 0;
	
	/*************************************************************************************************************
	 * Public Function: HashFunction
	 * Requires: Key as a String
	 * Promises: Will produce a unique Index for the specific Key, and this unique Index will be consistent 
	 * 	         with the input Key. Index is returned as an int.
	 *************************************************************************************************************/
	public static int HashFunction(String Key)
	{
		char character;
		int hashIndex = 0;
		
		int length = Key.length();
		
        for (int i = 0; i < length; i ++) {
        	 hashIndex = (int) (hashIndex*18 + Key.charAt(i)*(3*length - i));
        }
		
        if (hashIndex < 0)
        	hashIndex = -hashIndex;
        
	//	System.out.println("HashFunction: The Index '" + Key + "' returned: " + (hashIndex) % (HashTable.length-1));
        return (hashIndex) % (HashTable.length-1);
		
	}
	/*************************************************************************************************************
	 * Public Function: InsertToTable
	 * Requires: Key as a String
	 * Promises: Runs key through HashFunction, Checks if there exists a string in the index of the hashtable,
	 * 			 if not, will put the key into the index 
	 *************************************************************************************************************/
	public static void InsertToTable(String key)
	{
		int index = HashFunction(key);
		
		if (HashTable[index] != null)
		{
			Collisions++;
			//System.out.println("InsertToTable: Collision");
			while(HashTable[index] != null)
			{
				index++;
				if(index >= HashTable.length)
					index = 0;
			}
		
		}

		HashTable[index] = key;
	}
	
	/*************************************************************************************************************
	 * Public Function: ReadFile
	 * Requires: String[] args with the 0th element holding a valid name of input file
	 * Promises: Creates HashTable that will be num_of_words / 0.7 size (thus making a 70% load factor)
	 * 			 Will insert the entries into the HashTable afterwards using the hash function and insert to table
	 * 			 functions.
	 *************************************************************************************************************/
	public static void ReadFile(String[] args) throws FileNotFoundException
	{
		if(args.length >= 1 && args[0] != null)			//checks to see if there EXISTS an input filename
		{
			String input = "";
			Scanner a = new Scanner(new FileReader(args[0]));		//read the "input" of the args
			
			while(a.hasNextLine())
			{
				a.nextLine();
				Num_of_words++;
			}
	
			HashTable = new String[(int)(Num_of_words/0.7)];		//HashTables size will now yield a 70% load factor.
			a.close();
			a = new Scanner(new FileReader(args[0]));
			
			while(a.hasNextLine())
			{
				input = a.nextLine();
				InsertToTable(input);
			}
			a.close();
			System.out.println("ReadFile: Done reading File, Done Inserting into Table");
		}
		else
		{
			System.out.println("ReadFile: No Input filename Detected!");
		}

	}
	
	
	public static void SearchFile(String[] args) throws FileNotFoundException
	{
		if(args.length >= 1 && args[0] != null)		//checks to see if there EXISTS an input filename
		{
			Scanner a = new Scanner(new FileReader(args[0]));
			int localSteps;
			String input = "";
			int Index;
			
			while(a.hasNextLine())
			{
				input = a.nextLine();
				Index = HashFunction(input);
				localSteps = 1;
					
				while(HashTable[Index].compareTo(input) != 0)
				{
					Index++;
					localSteps++;
					
					if(Index == (HashTable.length-1))
					{	
						Index = 0;
					}			
				}
				
				Num_of_steps += localSteps;
				if (localSteps > Largest_step)
					Largest_step = localSteps;
				
			}
			a.close();
			System.out.println("SearchFile: Done Searching File");
		}
		else
		{
			System.out.println("SearchFile: No Input filename Detected!");
		}
		
	}
	
	/*************************************************************************************************************
	 * Public Function: WriteFile
	 * Requires: args to have a 2nd index: args[1], where the filename exists.
	 * Promises: prints into the file the Hash Efficiency and other information
	 *************************************************************************************************************/
	public static void WriteFile(String[] args) throws FileNotFoundException
	{
		if (args.length >= 2 && args[1] != null)			//Checks to see if there EXISTS an output filename
		{
			PrintWriter writeToFile = new PrintWriter(args[1]);
			
			double LoadFactor = (double)Num_of_words/(double)HashTable.length;
			double AverageRead = (double)Num_of_steps/(double)Num_of_words;
			writeToFile.println("Byron's CPSC Assignment 3 HashTable Information");
			writeToFile.println("-----------------------------------------------");
			writeToFile.println("Size of Table: " + HashTable.length);
			writeToFile.println("Num of Words: " + Num_of_words);
			writeToFile.println("Num of steps: " + Num_of_steps);
			writeToFile.println("Number of Collisions: " + Collisions);		
			writeToFile.println("Largest step: " + Largest_step);			
			writeToFile.println("LoadFactor: " + LoadFactor*100);
			writeToFile.println("AverageRead/Steps: " + AverageRead);
			writeToFile.println("Hash Efficiency: " + (LoadFactor/AverageRead)* 100);
			writeToFile.println("-----------------------------------------------");
			
			System.out.println("WriteFile: Done Writing to File");
			writeToFile.close();
		}
		else
		{
			System.out.println("WriteFile: No Output filename Detected!");
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException 
	{
		try {
			ReadFile(args);		//Reads File
		} catch (FileNotFoundException e) {
			System.out.println("Readfile: Cannot Find Input File");
		}
		try {
		SearchFile(args);		//Searches the file and records the steps taken
		} catch (FileNotFoundException e){
			System.out.println("SearchFile: Cannot Find Input File");
		}
		
		WriteFile(args);		//Prints to a file the HashTable efficiency and other info.	
		
	}

		

	
}
