/********************************************************************************************************
 * Name: Jessica Shi																					*
 * Date: 3-26-12																						*
 * Course: Class: Analysis of Algorithms, 1st Period													*
 * Purpose: The Hash class reads data from the file HashData.txt, which contains names and ID numbers. 	*
 * It reads each data pair from the file and inserts the data into a Record object. It then inserts the *
 * Record into a hash table, with the ID number as the key, using the hash function h(x)=x%23, with 	*
 * collisions solved by chaining. It uses the ListNode class to create the linked list chains. It counts*
 * the number of collisions that occur during the insertion phase. After this is completed, it writes 	* 
 * out the names in the hash table and outputs that and the number of collisions. It prompts the user 	*
 * for an ID number to search for until the user chooses to stop by entering -999. An appropriate 		*
 * message is printed after each ID is entered, stating the name that was found and after how many steps*
 * or a message stating that the name was not found.													*
 ********************************************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Hash {
	//main method
	public static void main(String[] args) {
		int number=0;//initialize number, for user input later
		Node[] table=new Node[23];//initialize new hash table
		ArrayList<Record> data=createArrayList();
		int collide=0;//initialize collide to keep track of the number of collisions
		for(Record i:data){//for each ID and name
			collide+=hash(table,i);//insert it into the table
		}//end for
		outputTable(table);//output the table
		System.out.println("Number of collisions: " +collide);//output the number of collisions
		while(number!=-999){//while the user has not told the program to stop
			number=promptForInteger(); //call promptForInteger method, which prompts the user for an ID to search for
			if(number!=-999)//if the user has not told the program to stop
				find(table,number);//find the ID in the table
		}//end while
	}//end main method
	
	//method to print the hash table, correctly formatted
	public static void outputTable(Node[] table){
		int counter=0;//initialize counter to keep track of the row number
		for(Node i:table){//for each node inside the table
			System.out.print(counter+"  ");//output the row number
			if(i!=null){//if there is anything in the node
				Node t=i;//create a new temporary node to traverse the node
				System.out.print(t.getData().getName());//output the first name
				if(t.getData().getName().length()<=11)//if the name is of a certain length, add an extra tab (for formatting)
					System.out.print("\t");
				t=t.getNext();//go to the next name
				while(t!=null){//while there are more names
					System.out.print("\t"+t.getData().getName());//output the next name
					if(t.getData().getName().length()<=11)//if the name is of a certain length, add an extra tab (for formatting)
						System.out.print("\t");
					t=t.getNext();//get the next name
				}//end while
			}//end if
			System.out.println();//output an extra line (for formatting)
			counter++;//increment the row number
		}//end for
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
	
	//method to find ID or name
	public static void find(Node[]table,int toFind){
		int index=toFind%23;//initialize the index at which to search for the ID
		if(table[index]==null)//if there is nothing at that index
			System.out.println(""+toFind+" was not found.");//the ID does not exist
		else{//else if there was something at that index
			int done=0;//initialize done, to tell if we have finished searching
			int time=1;//initialize time, to output the steps
			Node t=table[index];//initialize a temporary node to traverse the hash table
			if(t.getData().getID()==toFind)//if the first element is the ID
				done=1;//the ID was found
			while(t.getNext()!=null&&done==0){//while there are more elements to search and while the search is not over
				t=t.getNext();//go to the next element
				time++;//increment the number of steps
				if(t.getData().getID()==toFind)//if the ID has been found
					done=1;//the ID was found
			}//end while
			if(done==1)//if an ID was found
				System.out.println(t.getData().getName()+" was found after "+time+" step(s)");//output the ID
			else//else
				System.out.println(""+toFind+" was not found.");//the ID was not found
		}//end else
	}//end method
	
	//method to hash
	public static int hash(Node[]table, Record data){
		int collide=0;//initialize collide to keep track of collisions
		int index=data.getID()%23;//initialize index at which to insert the ID
		if (table[index]==null)//if there is nothing at that index
			table[index]=new Node(data,null);//create a new Node
		else{//else if there was something at that index
			collide=1;//there is a collision
			Node t=table[index];//initialize temporary node to traverse the hash table
			while(t.getNext()!=null){//while there is another node
				t=t.getNext();//go to the next node
			}//end while
			t.setNext(new Node(data,null));//at the end of the linked list, add a new Node
		}//end else
		return collide;//return the number of collisions
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

}//end class
