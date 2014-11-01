/********************************************************************************************************
 * Name: Jessica Shi																					*
 * Date: 5-26-12																						*
 * Course: Class: Analysis of Algorithms, 1st Period													*
 * Purpose: The Graph class reads a directed or undirected graph from a file and stores the graph		*
 * in an array. The class then outputs the graph to the screen, properly formatted. It calls a method	*
 * to prompt the user for a beginning vertex, an ending vertex, and the type of path desired. It 		*
 * then calls the appropriate method (depending on the type of path desired) to search for the path,	*
 * using an array for the label table (with Info elements which hold one row of the label table). It	*
 * continues to output the label table, and whether the path was found or not (if it was found, the		*
 * path and the path length are printed. The Graph class then prompts the user to either				*
 * select another desired path with the existing graph or read another graph. The program continues		*
 * until the file contains no more graphs.																*
 ********************************************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {

	public static void main(String[] args) {
		Scanner sc=null;//initialize scanner
		try {//try block in case of an error
			sc = new Scanner(new File("graphs.txt"));//use the scanner to read graphs.txt
		}//end try
		catch (FileNotFoundException e) {//if there is an error
			e.printStackTrace();//give error
		}//end catch
		int[][] graphs=createGraph(sc);//read the data file and initialize it to a graph
		printGraph(graphs);//output the graph
		int done=1;//initialize done to 0 as the program must run at least once
		while(done!=0){//if there are more graphs
			if(done==2){//if the user wants to read the next graph
				graphs=createGraph(sc);//create a new graph
				printGraph(graphs);//output the new graph
			}//end if
			ArrayList<Character> inputArray=promptForCharacter();//retrieve the necessary user inputed information
			if(inputArray.get(2)=='D')//if the user wants a depth-first search
				depth(inputArray,graphs);//give a depth-first search
			else if(inputArray.get(2)=='B')//if the user wants a breadth-first search
				breadth(inputArray,graphs);//give a breadth-first search
			else if(inputArray.get(2)=='I')//if the user wants a Dijkstra search
				dijkstra(inputArray,graphs);//give a Dijkstra search
			done=promptAgain();//ask if the user wants to keep searching
			if(done!=1&&!sc.hasNextLine())//if there is another graph and the user does not want to remain on the current graph
				done=0;//end the program
		}//end while
	}//end main method
	
	//method to execute a Dijkstra search
	public static void dijkstra(ArrayList<Character>inputArray,int[][]graphs){
		char begin=inputArray.get(0);//initialize the beginning vertex
		char end=inputArray.get(1);//initialize the ending vertex
		Info[]label=new Info[graphs.length];//initialize the label table
		int charNum=65;//initialize character number at A
		int prevIndex=(int)begin-charNum;//initialize the starting index
		int endIndex=(int)end-charNum;//initialize the ending index
		for(int i=0;i<label.length;i++){//for every letter from A to the maximum character
			label[i]=new Info((char)charNum);//create a new Info slot
			label[i].setWeight(Integer.MAX_VALUE);//set the weight to the maximum
			charNum++;//increment the character
		}//end for
		label[prevIndex].setWeight(0);//initialize the starting weight
		label[prevIndex].setMark(true);//initialize the starting mark
		
		int minWeight=Integer.MAX_VALUE;//initialize the minimum weight
		int done=0;//initialize ending condition (path is completed)
		int force=0;//initialize ending condition (forced means that the path cannot be completed)
		while(done==0&&force==0){//while the ending conditions have not been met
			for(int i=0;i<graphs[prevIndex].length;i++){//for each possible path
				if(graphs[prevIndex][i]>0&&!label[i].getMark()&&done==0){//if the path has not been found, an edge exists between the next vertex, and the next vertex has not been marked
					label[i].setPrev(label[prevIndex].getVertex());//set the previous vertex
					label[i].setWeight(label[prevIndex].getWeight()+graphs[prevIndex][i]);//set the weight
					label[i].setPrevIndex(prevIndex);//set the previous index
				}//end if
			}//end for
			minWeight=Integer.MAX_VALUE;//reset the maximum weight
			int allMark=1;//initialize value to check if there is no path
			for(int i=0;i<label.length;i++){
				if(label[i].getWeight()<minWeight&&!label[i].getMark()&&label[i].getPrev()!='0'){//if the vertex meets all of the necessary conditions
					prevIndex=i;//set the previous index
					minWeight=label[i].getWeight();//set the new minimum weight
				}//end if
				if(!label[i].getMark()&&label[i].getPrev()!='0')//if there is a possible path
					allMark=0;//set the allMark variable to reflect that
			}//end for
			if(allMark==1)//if there are no more elements
				force=1;//stop the search
			else{//if there are more elements
				if(minWeight==Integer.MAX_VALUE){//if the smallest value happens to be the maximum value
					int doneWeight=0;//initialize ending condition
					int counter=0;//initialize counter for the index
					while(doneWeight==0){//while the ending condition has not been met
						if(label[counter].getWeight()==minWeight&&!label[counter].getMark()&&label[counter].getPrev()!='0'){//find the first value that meets the necessary conditions
							prevIndex=counter;//set the previous index
							doneWeight=1;//set ending condition
						}//end if
						counter++;//increment counter
					}//end while
				}//end if
				label[prevIndex].setMark(true);//mark the unmarked vertex with the lowest cumulative weight
				if(label[prevIndex].getVertex()==end)//if this is the last vertex
					done=1;//end the search
			}//end else
		}//end while
		
		System.out.println();//output line for formatting purposes
		System.out.println("Label Table");//output header for label table
		System.out.println("Vertex"+"\t"+"Previous Vertex"+"\t\t"+"Cumulative Weight"+"\t"+"Mark");//set up headers for the label table
		for(int i=0;i<label.length;i++){//for each element of the label table
			String newPrev=""+label[i].getPrev();//initialize previous vertex
			if(newPrev.equals("0"))//if there is no previous vertex
				newPrev="-";//set previous vertex
			String newWeight=""+label[i].getWeight();//initialize cumulative weight
			if(newWeight.equals(""+Integer.MAX_VALUE)&&label[i].getVertex()!=begin)//if there is no weight
				newWeight="-";//set weight
			System.out.print(label[i].getVertex()+"\t"+newPrev+"\t\t\t"+newWeight+"\t\t\t"+label[i].getMark());//output appropriate elements
			System.out.println();//output line for formatting
		}//end for
		
		if(done!=0){//if there is a path
			done=0;//reset done
			String path="";//initialize the path
			int length=label[endIndex].getWeight();//set the path length
			int index=endIndex;//look for the path, starting backwards
			while(done==0){//while the end of the path has not been reached
				path=label[index].getVertex()+path;//add a new vertex to the path
				if(label[index].getPrev()!='0')//if the end of the path has not been reached
					index=label[index].getPrevIndex();//keep on going to the previous vertex
				else//if the end of the path has been reached
					done=1;//set end condition
			}//end while
			System.out.println("The path length is "+length+" and the path is "+path);//output path length and path
		}//end if
		else{//if there is no path
			System.out.println("No path exists.");//output message stating so
		}//end else
	}
	
	//method to execute a breadth-first search
	public static void breadth(ArrayList<Character>inputArray,int[][]graphs){
		char begin=inputArray.get(0);//initialize the beginning vertex
		char end=inputArray.get(1);//initialize the ending vertex
		Info[]label=new Info[graphs.length];//initialize the label table
		int charNum=65;//initialize character number at A
		int prevIndex=(int)begin-charNum;//initialize the starting index
		int endIndex=(int)end-charNum;//initialize the ending index
		for(int i=0;i<label.length;i++){//for every letter from A to the maximum character
			label[i]=new Info((char)charNum);//create a new Info slot
			charNum++;//increment the character
		}//end for
		label[prevIndex].setWeight(0);//initialize the starting weight
		label[prevIndex].setMark(true);//initialize the starting mark
		ArrayList<Character> queue=new ArrayList<Character>();//initialize the queue
		
		int done=0;//initialize ending condition (path is completed)
		int force=0;//initialize ending condition (forced means that the path cannot be completed)
		while(done==0&&force==0){//while the ending conditions have not been met
			for(int i=0;i<graphs[prevIndex].length;i++){//for each possible path
				if(graphs[prevIndex][i]>0&&!label[i].getMark()&&done==0){//if the path has not been found, an edge exists between the next vertex, and the next vertex has not been marked
					queue.add((char)(i+65));//add the next vertex to the queue
					label[i].setPrev(label[prevIndex].getVertex());//set the previous vertex
					label[i].setMark(true);//mark the new vertex
					label[i].setWeight(label[prevIndex].getWeight()+graphs[prevIndex][i]);//set the weight
					label[i].setPrevIndex(prevIndex);//set the previous index
					if(label[i].getVertex()==end)//if this is the last vertex
						done=1;//end the search
				}//end if
			}//end for
			if(queue.size()>0)//if there are more elements in the queue to search through
				prevIndex=((int)queue.remove(0))-65;//move to the next vertex
			else//if there are no more elements
				force=1;//stop the search
		}//end while
		
		System.out.println();//output line for formatting purposes
		System.out.println("Label Table");//output header for label table
		System.out.println("Vertex"+"\t"+"Previous Vertex"+"\t\t"+"Cumulative Weight"+"\t"+"Mark");//set up headers for the label table
		for(int i=0;i<label.length;i++){//for each element of the label table
			String newPrev=""+label[i].getPrev();//initialize previous vertex
			if(newPrev.equals("0"))//if there is no previous vertex
				newPrev="-";//set previous vertex
			String newWeight=""+label[i].getWeight();//initialize cumulative weight
			if(newWeight.equals("0")&&label[i].getVertex()!=begin)//if there is no weight
				newWeight="-";//set weight
			System.out.print(label[i].getVertex()+"\t"+newPrev+"\t\t\t"+newWeight+"\t\t\t"+label[i].getMark());//output appropriate elements
			System.out.println();//output line for formatting
		}//end for
		
		if(done!=0){//if there is a path
			done=0;//reset done
			String path="";//initialize the path
			int length=label[endIndex].getWeight();//set the path length
			int index=endIndex;//look for the path, starting backwards
			while(done==0){//while the end of the path has not been reached
				path=label[index].getVertex()+path;//add a new vertex to the path
				if(label[index].getPrev()!='0')//if the end of the path has not been reached
					index=label[index].getPrevIndex();//keep on going to the previous vertex
				else//if the end of the path has been reached
					done=1;//set end condition
			}//end while
			System.out.println("The path length is "+length+" and the path is "+path);//output path length and path
		}//end if
		else{//if there is no path
			System.out.println("No path exists.");//output message stating so
		}//end else
	}//end method
	
	//method to execute a depth-first search
	public static void depth(ArrayList<Character>inputArray,int[][]graphs){
		char begin=inputArray.get(0);//initialize the beginning vertex
		char end=inputArray.get(1);//initialize the ending vertex
		Info[]label=new Info[graphs.length];//initialize the label table
		int charNum=65;//initialize character number at A
		int prevIndex=(int)begin-charNum;//initialize the starting index
		int endIndex=(int)end-charNum;//initialize the ending index
		for(int i=0;i<label.length;i++){//for every letter from A to the maximum character
			label[i]=new Info((char)charNum);//create a new Info slot
			charNum++;//increment the character
		}//end for
		label[prevIndex].setWeight(0);//initialize the starting weight
		label[prevIndex].setMark(true);//initialize the starting mark
		ArrayList<Character> stack=new ArrayList<Character>();//initialize the stack
		
		int done=0;//initialize ending condition (path is completed)
		int force=0;//initialize ending condition (forced means that the path cannot be completed)
		while(done==0&&force==0){//while the ending conditions have not been met
			for(int i=0;i<graphs[prevIndex].length;i++){//for each possible path
				if(graphs[prevIndex][i]>0&&!label[i].getMark()&&done==0){//if the path has not been found, an edge exists between the next vertex, and the next vertex has not been marked
					stack.add((char)(i+65));//add the next vertex to the stack
					label[i].setPrev(label[prevIndex].getVertex());//set the previous vertex
					label[i].setMark(true);//mark the new vertex
					label[i].setWeight(label[prevIndex].getWeight()+graphs[prevIndex][i]);//set the weight
					label[i].setPrevIndex(prevIndex);//set the previous index
					if(label[i].getVertex()==end)//if this is the last vertex
						done=1;//end the search
				}//end if
			}//end for
			if(stack.size()>0)//if there are more elements in the stack to search through
				prevIndex=((int)stack.remove(stack.size()-1))-65;//move to the next vertex
			else//if there are no more elements
				force=1;//stop the search
		}//end while
		
		System.out.println();//output line for formatting purposes
		System.out.println("Label Table");//output header for label table
		System.out.println("Vertex"+"\t"+"Previous Vertex"+"\t\t"+"Cumulative Weight"+"\t"+"Mark");//set up headers for the label table
		for(int i=0;i<label.length;i++){//for each element of the label table
			String newPrev=""+label[i].getPrev();//initialize previous vertex
			if(newPrev.equals("0"))//if there is no previous vertex
				newPrev="-";//set previous vertex
			String newWeight=""+label[i].getWeight();//initialize cumulative weight
			if(newWeight.equals("0")&&label[i].getVertex()!=begin)//if there is no weight
				newWeight="-";//set weight
			System.out.print(label[i].getVertex()+"\t"+newPrev+"\t\t\t"+newWeight+"\t\t\t"+label[i].getMark());//output appropriate elements
			System.out.println();//output line for formatting
		}//end for
		
		if(done!=0){//if there is a path
			done=0;//reset done
			String path="";//initialize the path
			int length=label[endIndex].getWeight();//set the path length
			int index=endIndex;//look for the path, starting backwards
			while(done==0){//while the end of the path has not been reached
				path=label[index].getVertex()+path;//add a new vertex to the path
				if(label[index].getPrev()!='0')//if the end of the path has not been reached
					index=label[index].getPrevIndex();//keep on going to the previous vertex
				else//if the end of the path has been reached
					done=1;//set end condition
			}//end while
			System.out.println("The path length is "+length+" and the path is "+path);//output path length and path
		}//end if
		else{//if there is no path
			System.out.println("No path exists.");//output message stating so
		}//end else
	}//end method
	
	//method to prompt the user to either (1) select another desired path through the existing graph or (2) read a new graph
	public static int promptAgain(){
		Scanner scan=new Scanner(System.in);//initialize scanner
		System.out.println("Do you want to continue with the existing graph or read a new graph? (0 to end, 1 for continue, 2 for new graph) ");//prompt the user to either continue with the current graph or read a new graph
		String input; //initialize input, which is the user input
		int done=0; //initialize done, which is used in input protection
		int number=0; //initialize number, which is the final integer
		while(done==0){//while a valid user input has not been found
			done=1;//set done to 1, meaning a valid user input is being found
			input=scan.next();//get user input
			try{ //in case of an error with parsing to integer (so if the user has not entered an integer)
				number=Integer.parseInt(input); //parse the input to an integer
			}//end try
			catch(NumberFormatException nFE){ //if there is an error
				done=0;//the valid user input has not been found
				System.out.println("Error. Please enter a valid integer (0 to end, 1 for continue, 2 for a new graph): ");//prompt user for a valid integer
			}//end catch
		}//end while
		return number;//return the user's choice
	}//end method
	
	//method to prompt the user for the beginning and ending vertex of the desired path and the type of path desired
	public static ArrayList<Character> promptForCharacter(){
		String input=""; //initialize input, which is the user input
		int done=0; //initialize done, which is used in input protection
		System.out.println("Please enter the beginning vertex: ");//prompt user for the beginning vertex
		Scanner scan = new Scanner(System.in); //initialize scanner
		while(done==0){//while a valid user input has not been found
			done=1;//set done to 1, meaning a valid user input is being found
			input=scan.nextLine();//get user input
			if(!Character.isLetter(input.charAt(0))){//if the input is not a character
				done=0;//a valid user input has not been found
				System.out.println("Please enter a valid beginning vertex: ");//prompt the user for a valid character
			}//end if
		}//end while
		char begin=Character.toUpperCase(input.charAt(0));//set the beginning character

		done=0;//reset done
		System.out.println("Please enter the ending vertex: ");//prompt user for the ending vertex
		while(done==0){//while a valid user input has not been found
			done=1;//set done to 1, meaning a valid user input is being found
			input=scan.nextLine();//get user input
			if(!Character.isLetter(input.charAt(0))){//if the input is not a character
				done=0;//a valid user input has not been found
				System.out.println("Please enter a valid ending vertex: ");//prompt the user for a valid character
			}//end if
		}//end while
		char end=Character.toUpperCase(input.charAt(0));//set the ending character
		
		done=0;//reset done
		System.out.println("Please enter the type of path desired (D for depth-first, B for breadth-first, I for Dijkstra: ");//prompt user for the type of path desired
		while(done==0){//while a valid user input has not been found
			done=1;//set done to 1, meaning a valid user input is being found
			input=scan.nextLine();//get user input
			if(!Character.isLetter(input.charAt(0))){//if the input is not a character
				done=0;//a valid user input has not been found
				System.out.println("Please enter a valid type of path: ");//prompt the user for a valid character
			}//end if
		}//end while
		char path=Character.toUpperCase(input.charAt(0));//set the path type
		
		ArrayList<Character> inputArray=new ArrayList<Character>();//initialize ArrayList to return information
		inputArray.add(begin);//add the beginning vertex
		inputArray.add(end);//add the ending vertex
		inputArray.add(path);//add the desired path type
		return inputArray;//return the ArrrayList
	}//end method
	
	//method to output the correctly formatted graph
	public static void printGraph(int[][]graphs){
		int charNum=65;//initialize character number at A
		System.out.println();//extra line for formatting
		System.out.print("\t");//tab for formatting
		for(int i=0;i<graphs.length;i++){//for every letter from A to the maximum letter
			System.out.print((char)charNum+"\t");//output the letter, correctly formatted
			charNum++;//increment the character number
		}//end for
		System.out.println();//extra line for formatting
		for(int i=0;i<graphs.length;i++){//for every row in the graph
			System.out.print((char)(i+65)+"\t");//output the row letter
			for(int j=0;j<graphs[i].length;j++){//for every column in the graph
				System.out.print(graphs[i][j]+"\t");//output the corresponding number
			}//end for
			System.out.println();//extra line for formatting
		}//end for
	}//end method
	
	//method to initialize a graph as read from a file
	public static int[][] createGraph(Scanner sc){
		ArrayList <String> graphData = new ArrayList <String> (); //initialize ArrayList
		String check="a";//initialize check to see if the end of the graph has been reached
		while(sc.hasNextLine()&&!check.equals("")){ //if there is another entry and the end of the graph has not been reached
			check=sc.nextLine();//retrieve the next line
			if(!check.equals(""))//if check is not an empty line
				graphData.add(check); //add the next line to the ArrayList
		} //end while
		char max='A';//set the current maximum character at the minimum
		for(int i=1;i<graphData.size();i++){//for loop to find the largest letter
			if(graphData.get(i).charAt(0)>max){//if the next letter is greater than the current largest
				max=graphData.get(i).charAt(0);//replace the maximum
			}//end if
			if(graphData.get(i).charAt(1)>max){//if the next letter is greater than the current largest
				max=graphData.get(i).charAt(1);//replace the maximum
			}//end if
		}//end for
		int[][]graphs=new int[(int)max-65+1][(int)max-65+1];//initialize new 2D array
		int directed = 1;//initialize directed value
		if(graphData.get(0).equals("U"))//if the graph is directed
			directed=0;//set directed value
		for(int i=1;i<graphData.size();i++){//for each edge
			graphs[((int)graphData.get(i).charAt(0))-65][((int)graphData.get(i).charAt(1))-65]=Integer.parseInt(graphData.get(i).substring(2));//place it in the graph
			if(directed==0){//if the graph is undirected
				graphs[((int)graphData.get(i).charAt(1))-65][((int)graphData.get(i).charAt(0))-65]=Integer.parseInt(graphData.get(i).substring(2));//place the opposite in the graph
			}//end if
		}//end for
		return graphs; //return the now filled graph
	}//end method
}//end class
