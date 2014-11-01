import java.io.File;
import java.io.FileNotFoundException;
/********************************************************************************************************
 * Name: Jessica Shi																					*
 * Date: 4-16-12																						*
 * Course: Class: Analysis of Algorithms, 1st Period													*
 * Purpose: The Maps class reads the names and ID numbers from the file HashData.txt. It inserts the	*
 * names and the ID numbers into the Record class (which has the appropriate getters and setters). It	*
 * then inserts the Record objects into a TreeMap using the name as the key. It inserts the Record 		*
 * objects into a HashMap using the ID number as the key. It outputs the contents of the Record in the	*
 * TreeMap using an iterator. It outputs the names and the corresponding ID numbers from the HashMap	*
 * using an iterator. It prompts the user repeatedly for an ID number of a student to search for in		*
 * the HashMap, and prints the student's name and ID number if found or a message indicating that the ID*
 * number was not found.																				*
 ********************************************************************************************************/

import java.util.*;

public class Maps {

	//main method
	public static void main(String[] args) {
		int number=0;//initialize number for later use in retrieving user inputed ID number
		ArrayList<Record> data=createArrayList();//initialize the ArrayList to hold the data (initially)
		Map<String,Record> treeMap=createTreeMap(data);//insert the Record objects into a TreeMap
		Map<Integer,Record> hashMap=createHashMap(data);//insert the Record objects into a HashMap
		outputTreeMap(treeMap);//output the contents of the Record in TreeMap
		System.out.println();
		outputHashMap(hashMap);//output the names with the corresponding ID numbers from the HashMap
		System.out.println();
		while(number!=-999){//while the user has not told the program to stop
			number=promptForInteger(); //call promptForInteger method, which prompts the user for an ID to search for
			if(number!=-999)//if the user has not told the program to stop
				find(hashMap,number);//find the ID in the table
		}//end while
	}//end main method
	
	//method to search for an ID number in the HashMap
	public static void find(Map<Integer,Record> names,int ID){
		if(names.containsKey(ID))//if ID is found
			System.out.println(ID+" belongs to "+names.get(ID).getName());//output student's name and ID
		else//if ID was not found
			System.out.println(ID+" was not found.");//output message indicating ID was not found	
	}//end method
	
	//method to prompt the user for an integer to search for
	public static int promptForInteger(){
		String input; //initialize input, which is the user input
		int done=0; //initialize done, which is used in input protection
		int number=0; //initialize number, which is the final integer
		System.out.println("Please enter the integer you wish to search for (enter -999 to quit): ");//prompt user for the integer the user wants to search for
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
		return number;//return the number the user wants to search for
	}//end method
	
	//method to initialize ArrayList
	public static ArrayList<Record> createArrayList(){
		Scanner sc; //initialize scanner
		ArrayList <Record> data = new ArrayList <Record> (); //initialize ArrayList
		try{ //in case of error with scanner
			sc = new Scanner(new File("HashData.txt"));	//create new scanner and call HashData.txt
			while(sc.hasNextLine()){ //if there is another entry
				data.add(new Record(sc.nextLine(),Integer.parseInt(sc.nextLine()))); //add the next line to the ArrayList
			} //end while
		} //end try
		catch(FileNotFoundException e){ //if there is an exception
			e.printStackTrace();//give error
		} //end catch
		return data; //return the now filled data ArrayList
	}//end method
	
	//method to create a TreeMap and insert the Record objects in the TreeMap using the name as the key
	public static Map <String,Record>createTreeMap(ArrayList<Record> data){
		Map<String,Record> names=new TreeMap<String,Record>();//initialize a TreeMap
		for(Record i:data){//for each element in data
			names.put(i.getName(),i);//put it in the TreeMap
		}//end for
		return names;//return the now filled map
	}//end method
	
	//method to create a HashMap and insert the Record objects in the HashMap using the ID number as the key
	public static Map<Integer,Record> createHashMap(ArrayList<Record> data){
		Map<Integer,Record>names=new HashMap<Integer,Record>();//initialize a HashMap
		for(Record i:data)//for each element in data
			names.put(i.getID(), i);//put it in the HashMap
		return names;//return the now filled map
	}//end method
	
	//method to output the contents of the Records in the TreeMap using the iterator to access them
	public static void outputTreeMap(Map<String,Record> names){
		System.out.println("TreeMap Records: ");//output header
		Set<String> nameSet=names.keySet();//initialize a set to equal the keys of the TreeMap
		Iterator<String> nameItr=nameSet.iterator();//initialize an iterator for the set
		while(nameItr.hasNext()){//while there are more Records to go through
			String tempName=nameItr.next();//initialize tempName to hold the current name
			Record tempRecord=names.get(tempName);//initialize tempRecord to hold the current Record
			System.out.println(tempRecord.getName()+", "+tempRecord.getID());//output the contents of the Record
		}//end while
	}//end method
	
	//method to output the names with the corresponding ID numbers from the HashMap, using the iterator to access them
	public static void outputHashMap(Map<Integer,Record> names){
		System.out.println("HashMap Records: ");//output header
		Set<Integer> IDSet=names.keySet();//initialize a set to equal the keys of the HashMap
		Iterator<Integer> IDItr=IDSet.iterator();//initialize an iterator for the set
		while(IDItr.hasNext()){//while there are more Records to go through
			int tempID=IDItr.next();//initialize tempID to hodl the current ID
			Record tempRecord=names.get(tempID);//initialize tempRecord to hold the current Record
			System.out.println(tempRecord.getName()+", "+tempID);//output the names with the corresponding ID numbers
		}//end while
	}//end method

}
