//Jessica Shi
//Date: 1/30/12
//Course: ADSB, Period 1
//Purpose: The purpose of this class is to use file reading to insert names from a text file into an ArrayList and
//then use quick sort to put the names in ascending order . The program outputs the initial
//set of names as they are read, in a vertical column, in createArrayList (where the ArrayList is also initialized
//and the names from names.txt are read). The program then uses quickSort to complete each pass of quick sort.
//The ArrayList is printed in a column after each pass of quick sort, formatted using outputArrayList.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class QuickSort {

	//main method
	public static void main(String[] args) {
		ArrayList<String> names = new ArrayList <String>(); //initialize ArrayList names
		names = createArrayList(); //call createArrayList method, which reads the file and puts it into the ArrayList
		names=quickSort(names,0,names.size()-1); //call quickSort, which quick sorts the names ArrayList
		System.out.println("Sorted: ");
		outputArrayList(names);//call outputArrayList, which outputs the ArrayList in a column
	}//end main method
	
	//method to initialize ArrayList and output initial ArrayList
	public static ArrayList<String> createArrayList(){
		Scanner sc; //initialize scanner
		ArrayList <String> names = new ArrayList <String> (); //initialize ArrayList
		System.out.println("Initial: ");
		try{ //in case of error with scanner
			sc = new Scanner(new File("names.txt"));	//create new scanner and call names.txt
			while(sc.hasNextLine()){ //if there is another number
				names.add(sc.nextLine()); //add the next line to the ArrayList
				System.out.println(names.get(names.size()-1)); //output each name as it is read, in a column
			} //end while
		} //end try
		catch(FileNotFoundException e){ //if there is an exception
			e.printStackTrace();//give error
		} //end catch
		System.out.println();
		return names; //return the now filled data ArrayList
	}//end method
	
	
	//method to quick sort ArrayList of names
	public static ArrayList<String> quickSort(ArrayList<String> names, int start, int end){
		if(names.size()<=1)//if there is only one element left
			return names;//return the ArrayList (nothing to sort)
		int leftI=start;//initialize left index
		int rightI=end;//initialize right index
		String left=names.get(leftI);//initialize left name
		String right=names.get(rightI);//initialize right name
		String pointer=left;//initialize pointer as the first name
		int pointerI=leftI;//initialize pointer index
		int rOL=1;//initialize variable to check whether to go through the ArrayList from the left or the right (start with right)
		while(rightI>=leftI){//while there are still more names to check
			right=names.get(rightI);//go to the next name (to the left)
			left=names.get(leftI);//go to the next name (to the right)
			if(rOL==1){//if going from the right
				if(names.get(rightI).compareTo(pointer)<0){//if there is a value less than the pointer
					names.set(pointerI, right);//set the value at the place where the pointer was
					pointerI=rightI;//set the value's index as the pointer's new index
					rOL=0;//check from the left
				}//end if
				rightI--;//go to the next name (to the left)
			}//end if
			else{//else if going from the left
				if(names.get(leftI).compareTo(pointer)>0){//if there is a value greater than the pointer
					names.set(pointerI, left);//set the value at the place where the pointer was
					pointerI=leftI;//set the value's index as the pointer's new index
					rOL=1;//check from the right
				}//end if
				leftI++;//go to the next name (to the right)
			}//end else
		}//end while
		names.set(pointerI, pointer);//set the pointer in the last remaining spot
		System.out.println("Pass: ");
		outputArrayList(names);//output the pass of quickSort
		if(start<pointerI-1)
			names=quickSort(names,start,pointerI-1);//quickSort the left half
		if(end>pointerI+1)
			names=quickSort(names,pointerI+1,end);//quickSort the right half
		return names;//return the sorted names
	}//end method
	
	//method to output ArrayList
	public static void outputArrayList(ArrayList<String> names){
		for(int i=0;i<names.size();i++){//for loop to print out the elements
			System.out.println(names.get(i));
		}//end for
		System.out.println(); //print out blank line
	}//end method

}//end class
