//Jessica Shi
//Date: 9/9/11
//Course: ADSB, Period 2
//Purpose: The purpose of this class is to use file reading to insert integers from a txt file into
//an ArrayList. The program outputs the number of numbers in the list using a for each loop and the
//ArrayList in a horizontal row using a for each loop, and then removes all of the odd integers using
//a for loop. It then prints the number of numbers of the odd ArrayList and the odd ArrayList using
//an iterator. Finally, it outputs the first and last integers in the ArrayList, labeling each one.

import java.util.*;
import java.io.*;

public class ArrayListLab {
	//main method
	public static void main(String[] args) {
		ArrayList <Integer> data = new ArrayList <Integer>(); //initialize ArrayList
		data = createArrayList(); //call createArrayList method, which reads the file and puts it into the ArrayList
		System.out.println("Number of numbers: "+data.size()); //output size of the ArrayList
		System.out.print("Data: "); //label the data
		printArrayList(data); //output each element in a horizontal row
		data = switchToOdd(data); //take out all of the odd integers of the ArrayList
		System.out.println("");
		System.out.print("Number of odd numbers: "+data.size()); //output the size of the new odd ArrayList
		System.out.println("");
		System.out.print("Odd numbers: "); //label the odd ArrayList data
		printArrayListItr(data); //output each element in a horizontal row using an iterator
		System.out.println("");
		System.out.println("First: "+data.get(0)); //output the first integer
		System.out.println("Last: "+data.get(data.size()-1)); //output the last integer
	} //end main
	//method to initialize ArrayList
	public static ArrayList<Integer> createArrayList(){
		Scanner sc; //initialize scanner
		ArrayList <Integer> data = new ArrayList <Integer> (); //initialize ArrayList
		try	//in case of error with scanner
		{
			sc = new Scanner(new File("data.txt"));	//create new scanner and call data.txt
			while(sc.hasNextLine()){ //if there is another number
				data.add(Integer.parseInt(sc.nextLine())); //add the next line to the ArrayList
			} //end while
		} //end try
		catch(FileNotFoundException e){ //if there is an exception
			e.printStackTrace();	//give error
		} //end catch
		return data; //return the now filled data ArrayList
	} //end method
	//method to output the ArrayList (for loop)
	public static void printArrayList(ArrayList <Integer> data){
		for (int i=0;i<data.size();i++){ //for every integer in the ArrayList
			System.out.print(data.get(i)); //output the integer
			if (i!=data.size()-1){ //if it is not the last integer
				System.out.print(", "); //add the proper comma and space
			} //end if
		} //end for
	} //end method
	//method to take out all of the even integers
	public static ArrayList<Integer> switchToOdd(ArrayList <Integer> data){
		int counter=0; //initialize a counter
		while(counter<data.size()){ //while there are still integers in the ArrayList
			if(data.get(counter)%2==0){ //if the integer is even
				counter++; //move on to the next integer
			} //end if
			else{ //if the integer is odd
				data.remove(counter); //remove the integer (do not move on to the next integer, as all of the subsequent elements will be moved up)
			} //end else
		} //end while
		return data; //return the now completely odd ArrayList
	} //end method
	//method to print out the new ArrayList (iterator)
	public static void printArrayListItr(ArrayList <Integer> data){
		Iterator <Integer> itr = data.iterator(); //initialize the iterator
		if(itr.hasNext()) //if there is a first integer
			System.out.print(itr.next()); //print the integer (for formatting purposes)
		while(itr.hasNext()){ //while there is another integer
			System.out.print(", "+itr.next()); //print out the proper space and the integer
		} //end while
	} //end method

} //end class
