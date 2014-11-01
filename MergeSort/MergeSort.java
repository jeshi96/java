//Jessica Shi
//Date: 11/11/11
//Course: ADSB, Period 2
//Purpose: The purpose of this class is to use file reading to insert integers from a text file into an ArrayList and
//then use a recursive merge sort to put the ArrayList in order from least to greatest. The createArrayList method creates
//an ArrayList out of the numbers inputed through the text file. The outputArrayList method then prints out the original
//ArrayList. The mergeSort method recursively merge sorts the ArrayList, and then the outputArrayList method prints out
//the new, sorted ArrayList.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//MergeSort class
public class MergeSort {
	//main method
	public static void main(String[] args) {
		ArrayList <Integer> data = new ArrayList <Integer>(); //initialize ArrayList data
		data = createArrayList(); //call createArrayList method, which reads the file and puts it into the ArrayList
		System.out.println("Original data: ");
		outputArrayList(data);//call outputArrayList method, which prints out data
		mergeSort(data); //call mergeSort method, which sorts the data recursively using merge sort
		System.out.println("Sorted data: ");
		outputArrayList(data);//call outputArrayList method, which prints out the now sorted data
	}//end main method
	
	//method to initialize ArrayList
	public static ArrayList<Integer> createArrayList(){
		Scanner sc; //initialize scanner
		ArrayList <Integer> data = new ArrayList <Integer> (); //initialize ArrayList
		try{ //in case of error with scanner
			sc = new Scanner(new File("numbers.txt"));	//create new scanner and call data.txt
			while(sc.hasNextLine()){ //if there is another number
				data.add(Integer.parseInt(sc.nextLine())); //add the next line to the ArrayList
			} //end while
		} //end try
		catch(FileNotFoundException e){ //if there is an exception
			e.printStackTrace();//give error
		} //end catch
		return data; //return the now filled data ArrayList
	}//end method
	
	//method to recursively mergeSort the data (by recursively splitting the data into sections and then sorting them while replacing them back into data)
	public static void mergeSort(ArrayList<Integer> data){
		if(data.size()<=1){ //if only one element is left
			return; //done
		} //end if
		ArrayList<Integer> topHalf = new ArrayList<Integer>();//initialize top half
		ArrayList<Integer> bottomHalf = new ArrayList<Integer>();//initialize bottom half
		int middle = data.size()/2;//initialize middle to be the middle index
		for(int i=0;i<middle;i++){//split data into two halves (top)
			topHalf.add(data.get(i));
		}//end for
		for(int i=middle;i<data.size();i++){//split data into two halves (bottom)
			bottomHalf.add(data.get(i));
		}//end for
		mergeSort(topHalf);//recursively split and sort top half
		mergeSort(bottomHalf);//recursively split and sort bottom half
		int posTop=0; //initialize top position
		int posBottom=0;//initialize bottom position
		while(posTop+posBottom<data.size()){//loop to sort halves and place back in data in the right place (while we are within bounds of data, meaning all the elements in topHalf and bottomHalf have not yet been considered)
			if(posTop>=topHalf.size()||(posBottom<bottomHalf.size()&&topHalf.get(posTop)>bottomHalf.get(posBottom))){
				//if the top position is out of bounds or if the bottom position is within bounds and the element in the top position is greater than that in the bottom
				data.set(posTop+posBottom,bottomHalf.get(posBottom)); //place the element of the bottom (as least goes first) back into data
				posBottom++;//increment bottom to consider the next element in bottomHalf
			}//end if
			else{//else
				data.set(posTop+posBottom,topHalf.get(posTop)); //place the element of the top (as least goes first) back into data
				posTop++; //increment top to consider the next element in topHalf
			}//end else
		}//end while
	}//end method
	
	//method to output ArrayList
	public static void outputArrayList(ArrayList<Integer> data){
		System.out.print(data.get(0));//print out first element
		for(int i=1;i<data.size();i++){//for loop to print out the other elements
			System.out.print(","+data.get(i));
		}//end for
		System.out.println(); //print out a blank line
	}//end method


}//end class
