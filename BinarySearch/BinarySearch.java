//Jessica Shi
//Date: 11/9/11
//Course: ADSB, Period 2
//Purpose: The purpose of this class is to use file reading to insert integers from a text file into
//an ArrayList and then use a recursive binary search to find a user input number inside the array.
//The createArrayList method creates the ArrayList using file reading from the text file. The sortArrayList
//method then uses selection sort to put the data in the ArrayList in order, from least to greatest.
//The promptForInteger method then asks the user to input the number the user wishes to search for in the ArrayList,
//with input protection and exit sentinel -999. The searchArrayList method then uses a recursive binary search to
//search the ArrayList for the number. The outputSearchResults method finally prints out the target integer
//and the position in which it was found in the sorted ArrayList. The program then continues to prompt for integers
//to search for until the user chooses to quit.

import java.util.*; //java.util for the scanner (user input)
import java.io.*; //java util for the scanner (file input)

//BinarySearch class
public class BinarySearch {
	//main method
	public static void main(String[] args) {
		ArrayList <Integer> data = new ArrayList <Integer>(); //initialize ArrayList data
		int number=0;
		int index=0;
		while(number!=-999){
			data = createArrayList(); //call createArrayList method, which reads the file and puts it into the ArrayList
			data = sortArrayList(data); //call sortArrayList method, which uses selection sort to put the data in order from least to greatest
			number=promptForInteger(); //call promptForInteger method, which prompts the user for an integer to search for
			if(number!=-999){
				index=searchArrayList(data,number, 0, data.size()-1);//call searchArrayList method, which uses the sorted ArrayList and the user input to find the integer in the array
				outputSearchResults(index,number);//call outputSearchResulsts method, which outputs the target integer and the position in which the integer was found (or no position, if the integer was not found)
			}
		}
		System.out.println("Thank you!");
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
	
	//method to sort ArrayList using selection sort
	public static ArrayList<Integer> sortArrayList(ArrayList<Integer> data){
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
	
	//method to prompt the user for an integer to search for
	public static int promptForInteger(){
		String input; //initialize input, which is the user input
		int done=0; //initialize done, which is used in input protection
		int number=0; //initialize number, which is the final integer
		System.out.println("Please enter the integer you wish to search for (enter -999 to quit): ");//prompt user for the integer the user wants to search for inside the ArrayList
		Scanner scan = new Scanner(System.in); //initialize scanner
		while(done==0){//while a valid user input has not been found
			done=1;//set done to 1, meaning a valid user input is being found
			input=scan.next();//get user input
			try{ //in case of an error with parsing to integer (so if the user has not entered an integer)
				number=Integer.parseInt(input); //parse the input to an integer
			}//end try
			catch(NumberFormatException nFE){ //if there is an error
				done=0;//the valid user input has not been found
				System.out.println("Error. Please enter a valid integer (enter -999 to quit): ");//prompt user for a valid integer
			}//end catch
		}//end while
		return number;//return the number the user wants to search for in the ArrayList
	}//end method
	
	//method to recursively search an array for a number (using binary search)
	public static int searchArrayList(ArrayList<Integer> data,int number, int low, int high){
		if(low>high)//if all of the elements in the array have been searched
			return -999;//return -999, which says that the integer has not been found in the array
		int middle=(low+high)/2;//initialize middle to be the middle index
		if (data.get(middle)==number)//if the middle index happens to be where the number is
			return middle;//return the middle index
		else if (data.get(middle)>number)//if the number at the middle index is greater than the number
			return searchArrayList(data,number,low,middle-1);//recursion:repeat this process with the lower half of the array
		else//if the number at the middle index is less than the number
			return searchArrayList(data,number,middle+1,high);//recursion: repeat this process with the upper half of the array
	}//end method
	
	//method to output the results of the binary search (the target integer and the position of the integer)
	public static void outputSearchResults(int index, int number){
		if(index==-999){//if the integer was not found within the array
			System.out.println("The target integer, "+number+", was not found.");//output the number and that the position was not found
		}//end if
		else{//if the integer was found within the array
			System.out.println("The target integer, "+number+", was found at position "+index+".");//output the number and the position it was found
		}//end else
	}//end method
	
}//end class
