//Jessica Shi
//Date: 1/26/12
//Course: ADSB, Period 1
//Purpose: The purpose of this class is to use file reading to insert integers from a text file into an ArrayList and
//then use shell sort with spans of 5, 3, 2, and 1 to put the integers in ascending order . The program outputs the initial
//set of numbers as they are read, in a horizontal row, in createArrayList (where the ArrayList is also initialized
//and the integers from numbers.txt are read). The program then uses shellSort to complete each pass of shell sort
//with a base sort of selection sort (except for the span of 1, in which the program does one pass of bubble sort).
//The ArrayList is printed in a row after each pass of shell sort, formatted using outputArrayList.

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//ShellSort class
public class ShellSort {

	//main method
	public static void main(String[] args) {
		ArrayList <Integer> data = new ArrayList <Integer>(); //initialize ArrayList data
		data = createArrayList(); //call createArrayList method, which reads the file and puts it into the ArrayList
		//sort the data, using the specified spans (and print the ArrayList in a row after each pass of shell sort)
		System.out.println();
		//span 5
		data = shellSort(5,data);
		System.out.print("Span 5: ");
		outputArrayList(data);
		//span 3
		data = shellSort(3, data);
		System.out.print("Span 3: ");
		outputArrayList(data);
		//span 2
		data = shellSort(2, data);
		System.out.print("Span 2: ");
		outputArrayList(data);
		//span 1
		data = bubbleSort(data);
		System.out.print("Span 1: ");
		outputArrayList(data);
	}//end main method
	
	//method to initialize ArrayList and to output the initial ArrayList
	public static ArrayList<Integer> createArrayList(){
		Scanner sc; //initialize scanner
		ArrayList <Integer> data = new ArrayList <Integer> (); //initialize ArrayList
		System.out.print("Initial: ");
		try{ //in case of error with scanner
			sc = new Scanner(new File("numbers.txt"));	//create new scanner and call data.txt
			while(sc.hasNextLine()){ //if there is another number
				data.add(Integer.parseInt(sc.nextLine())); //add the next line to the ArrayList
				System.out.print(data.get(data.size()-1)+" "); //output each number, in a horizontal row, as it is read
			} //end while
		} //end try
		catch(FileNotFoundException e){ //if there is an exception
			e.printStackTrace();//give error
		} //end catch
		return data; //return the now filled data ArrayList
	}//end method
	
	//method to sort ArrayList using one pass of bubble sort (for the span of 1)
	public static ArrayList<Integer> bubbleSort(ArrayList<Integer> data){
		for(int i=0;i<data.size()-1;i++){//for each element in data (except for the last one)
			if(data.get(i)>data.get(i+1)){//check each pair of two and if they have to be switched
				//switch the two elements
				int temp=data.get(i);
				data.set(i,data.get(i+1));
				data.set(i+1, temp);
			}//end if
		}//end for
		return data; //return the now sorted ArrayList
	}//end method
	
	//method to sort ArrayList using shell sort (one pass of shell sort)
	public static ArrayList<Integer> shellSort(int span, ArrayList<Integer> data){
		ArrayList<Integer> spanData = new ArrayList<Integer>(); //initialize spanData, which will hold the data elements in each index as specified by the span
		ArrayList<Integer> indexData = new ArrayList<Integer>();//initialize indexData, which will hold the indexes as specified by the span
		int counter=0;//initialize counter, which will go through each element of data
		int countSpan=counter;//initialize countSpan, which will keep track of the spanIndexes (for indexData)
		while(counter<data.size()){//for each element of data
			spanData.clear();//clear spanData and indexData, reset countSpan
			indexData.clear();
			countSpan=counter;
			while(countSpan<data.size()){//while there is another span left
				spanData.add(data.get(countSpan));//add the next span data element
				indexData.add(countSpan);//add the corresponding index
				countSpan+=span;//find the next span
			}//end while
			spanData = selectionSort(spanData);//selection sort the numbers found in the spans
			countSpan=0;//reset countSpan to 0
			for(int i:indexData){//for each span index
				data.set(i,spanData.get(countSpan));//replace the sorted span back into the data
				countSpan++;//increment countSpan
			}//end for
			counter++;//increment counter
		}//end while
		return data;//return the now one pass completed ArrayList
	}//end method
	
	//method to sort ArrayList using selection sort
	public static ArrayList<Integer> selectionSort(ArrayList<Integer> data){
		int min;//initialize min, which is the smallest number in the array (to switch with the first number)
		int index;//initialize index, which is the location of min in the array
		int temp;//initialize temp, which is used to swap the numbers
		for(int first=0;first<data.size();first++){//for each element in the array
			min=data.get(first);//set min as the first number (in this "sub-array", to sort)
			index=first;//set index as the index of the first number
			for(int i=first;i<data.size();i++){//for all elements in the array after the first
				if(data.get(i)<min){//if there is a number smaller than min
					min=data.get(i);//let min be this new smallest number
					index=i;//let index be the index of this new smallest number
				}//end if
			}//end for
			temp=data.get(first);//set temp to the first value in the array
			data.set(first, min);//set the smallest number to the first
			data.set(index, temp);//set the temp (the first value) to the place of the smallest number (originally)
		}//end for
		return data;//return the now sorted data ArrayList
	}//end method
	
	//method to output ArrayList
	public static void outputArrayList(ArrayList<Integer> data){
		System.out.print(data.get(0));//print out first element
		for(int i=1;i<data.size();i++){//for loop to print out the other elements
			System.out.print(" "+data.get(i));
		}//end for
		System.out.println(); //print out a blank line
	}//end method

}//end class
