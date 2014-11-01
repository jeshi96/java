//Name: Jessica Shi
//Date: 12-6-11
//Course: Class: ADSB, 2nd Period
//Purpose: The Survivor class has seven methods, all of which eventually lead to a game of Survivor. The
//getNames method first retrieves names from the file names.txt. The findLastName method then finds the
//last name of each name in the list, so that the names can be inserted in alphabetical order by last name
//into a LinkedList using the putInLinkedList method. The alphabetically sorted names are then printed
//using the printNames method, and the LinkedList is made into a circular LinkedList in the makeCircular
//method. The user is prompted for a positive integer to use as the elimination number in the game of
//Survivor, through the promptForInteger method. The whoWillSurvive method is then used to carry out the
//game of Survivor, eliminating every Nth name (where N is the elimination number) and providing a reason
//for each elimination (as read from a text file of reasons)until only one name is left. The last name
//is declared the survivor.

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Survivor {
	//main method
	public static void main(String[] args) {
		ArrayList<String> namesArray=getNames(); //initialize the ArrayList of names (unsorted)
		ListNode[] listNodeArray = new ListNode[2];//initialize an array of listNodes, for use in returning both the list of full names and the list of last names in the putInLinkedList method
		//initialize the list of full names and last names respectively
		ListNode list=null;
		ListNode lastNameList=null;
		for(String i:namesArray){//for each name in the namesArray
			listNodeArray=putInLinkedList(findLastName(i),i,list,lastNameList);//put the name into the list in alphabetical order
			//update list and lastNameList in case the first listNode changed
			list=listNodeArray[0];
			lastNameList=listNodeArray[1];
		}//end for
		printNames(list);//output the list of the sorted names
		list=makeCircular(list);//make the list circular
		int number=promptForInteger();//prompt the user for an elimination number to use in the Survivor game
		whoWillSurvive(list,number);//execute the Survivor game
	}//end method
	
	//method to obtain the names of the participants of the Survivor game using a scanner from a text file
	public static ArrayList <String> getNames(){
		Scanner sc; //initialize scanner
		ArrayList <String> namesArray = new ArrayList <String> (); //initialize ArrayList
		try{ //in case of error with scanner
			sc = new Scanner(new File("names.txt"));//create new scanner and call names.txt
			while(sc.hasNextLine()){ //if there is another name
				namesArray.add(sc.nextLine()); //add the next line to the ArrayList
			} //end while
		} //end try
		catch(FileNotFoundException e){ //if there is an exception
			e.printStackTrace();//give error
		} //end catch
		return namesArray; //return the now filled names ArrayList
	}//end method
	
	//method to obtain the last name of each inputed name
	public static String findLastName(String name){
		if(name.indexOf(' ')==-1){//if there is no space
			return name;//there is only one name
		}//end if
		else if(name.indexOf(',')!=-1){//if there is a comma
			return findLastName(name.substring(0,name.indexOf(',')));//call the method again, excluding the honorifics after the comma, and return the resulting last name found
		}//end else if
		else if(name.lastIndexOf('.')!=-1){//if there is a period (since there is no comma, as ascertained previously, this period is after an initial
			return name.substring(name.lastIndexOf('.')+2);//return the part of the name after the last period, which must be the last name
		}//end else if
		else if(name.indexOf(' ')==name.lastIndexOf(' ')){//if there is only one space
			return name.substring(name.indexOf(' ')+1);//return the second part of the name, which must be the last name (since there is no period, only one space, and no comma)
		}//end else if
		else if(name.indexOf(' ')!=name.lastIndexOf(' ')){//if there is more than one space
			int end=0;//initialize end condition
			String temp=name.substring(name.lastIndexOf(' ')).trim();//let temp be the last part of the name
			for(int i=0;i<temp.length();i++){//for each letter in the last part of the name
				if(Character.isLowerCase(temp.charAt(i))){ //if there are lowercase characters in the last part of the name
					end=1;//end
				}//end if
			}//end for
			if(end==0){ //if all of the characters in the last part of the name are uppercase, meaning that it is a suffix to a king or a pope or some person of the kind
				return findLastName(name.substring(0,name.lastIndexOf(' '))); //call the program again without the suffix
			}//end if
			else{//if some of the characters in the last part of the name are lowercase, meaning that it is a legitimate last name
				temp=name.substring(name.lastIndexOf(' '));//let temp be the last part of the name
				return temp.trim(); //return the last part (the last name)
			}//end else
		}//end else if
		else{//if none of the above apply
			return name;//return the name (to satisfy java, and so that the program won't crash if there is some weird exception)
		}//end else
	}//end method
	
	//method to put all of the names in alphabetical order in a LinkedList (accepts one name/last name, and what has already been sorted)
	public static ListNode[] putInLinkedList(String lastName, String name, ListNode list,ListNode lastNameList){
		if(list==null){//if we are inserting the first element (list is null)
			list=new ListNode(name,null);//insert the name in the beginning of the list
			lastNameList=new ListNode(lastName,null);//insert the last name in the beginning of the list
		}//end if
		else{//else if we are not on the first element (meaning we have to find the correct position of the name in the list, alphabetically)
			//initialize temporary variable for list and lastNameList, and an exit condition
			ListNode t=list;
			ListNode t2=list;
			ListNode k=lastNameList;
			ListNode k2=lastNameList;
			int exit=0;
			while(t!=null&&exit==0){//while we haven't checked every name in the list and while we haven't found the correct spot for the current name
				if(((String) k.getValue()).compareToIgnoreCase(lastName)==0){//if the current last name and the last name in the list are the same
					if(name.indexOf(' ')==-1&&((String)t.getValue()).indexOf(' ')!=-1){//if the current name contains just a first name (no spaces) and the name in the list does not
						exit=1;//exit (current name goes first)
					}//end if
					else if(((String)t.getValue()).indexOf(' ')==-1&&name.indexOf(' ')!=-1){//if the name in the list contains just a first name (no spaces) and the current name does not
						//move down the list (current name goes second)
						k2=k;
						k=k.getNext();
						t2=t;
						t=t.getNext();
					}//end else if
					else if(((String)t.getValue()).compareToIgnoreCase(name)>=0){//if the first name shows that the current name comes second alphabetically
						exit=1;//exit (insert the current name before the name in the list)
					}//end else if
					else{//if the first name shows that the current name comes first alphabetically
						//move down the list
						k2=k;
						k=k.getNext();
						t2=t;
						t=t.getNext();
					}//end else
				}//end if
				else if(((String)k.getValue()).compareToIgnoreCase(lastName)>0){//if the current name is alphabetically after the name in the list
					exit=1;//exit (insert the current name before the name in the list)
				}//end else if
				else{//else (if the current name is alphabetically before the name in the list)
					//move down the list
					k2=k;
					k=k.getNext();
					t2=t;
					t=t.getNext();
				}//end else
			}//end while
			if(t==t2){//if the two temporary lists are equal (meaning we never moved down the lists, so the name comes first in the list)
				list=new ListNode(name,list);//insert the name in the beginning of the list
				lastNameList=new ListNode(lastName,lastNameList);//insert the last name in the beginning of the list
			}//end if
			else{//else if the two temporary lists aren't equal
				t2.setNext(new ListNode(name,t));//insert the name in its correct position in the list
				k2.setNext(new ListNode(lastName,k));//insert the last name in its correct position in the list
			}//end else
		}//end else
		ListNode[] listNodeArray = new ListNode[2];//initialize array for returning the lists (we have to do this in case the first element of the lists changes)
		listNodeArray[0]=list;
		listNodeArray[1]=lastNameList;
		return listNodeArray;//return the array with two elements, the list and the lastNameList, with all the names in them in alphabetical order
	}//end method
	
	//method to output the sorted names LinkedList
	public static void printNames(ListNode list){
		System.out.println("Survivor Contestants (in alphabetical order):");//output title of the list
		ListNode t=list;//initialize temporary variable to list
		while(t!=null){//while all of the names have not yet been printed
			System.out.println(t.getValue());//output the name
			t=t.getNext();//move on to the next name
		}//end while
		System.out.println();//output an empty line
	}//end method
	
	//method to make the LinkedList into a circular LinkedList
	public static ListNode makeCircular(ListNode list){
		ListNode t=list;//initialize temporary variable to list
		if(list!=null){//if the list is not empty (null)
			while(t.getNext()!=null){//while the end of the list has not been reached
				t=t.getNext();//move on to the next name
			}//end while
			t.setNext(list);//set the end of the list to the beginning
		}//end if
		return list;//return the now circular list
	}//end method
	
	//method to ask the user for a positive integer to use as the elimination number in Survivor
	public static int promptForInteger(){
		String input; //initialize input, which is the user input
		int done=0; //initialize done, which is used in input protection
		int number=0; //initialize number, which is the final integer
		System.out.println("Please enter N: ");//prompt user for the integer the user wants to use as the elimination number in survivor
		Scanner scan = new Scanner(System.in); //initialize scanner
		while(done==0){//while a valid user input has not been found
			done=1;//set done to 1, meaning a valid user input is being found
			input=scan.next();//get user input
			try{ //in case of an error with parsing to integer (so if the user has not entered an integer)
				number=Integer.parseInt(input); //parse the input to an integer
				if(number<=0){//if the number is not positive
					done=0;//the valid user input has not been found
					System.out.println("Error. Please enter a valid N: ");//prompt user for a valid number for survivor
				}//end if
			}//end try
			catch(NumberFormatException nFE){ //if there is an error
				done=0;//the valid user input has not been found
				System.out.println("Error. Please enter a valid N: ");//prompt user for a valid integer
			}//end catch
		}//end while
		return number;//return the number the user wants to use as the elimination number in survivor
	}//end method
	
	//method to execute the Survivor game (eliminating every Nth person), attributing a reason for every elimination along the way
	public static void whoWillSurvive(ListNode list,int number){
		System.out.println();
		System.out.println("Survivor Game, Ace Attorney Style:");//output title of the game
		//retrieving the list of reason for elimination
		Scanner sc; //initialize scanner
		ArrayList <String> death = new ArrayList <String> (); //initialize ArrayList
		try{ //in case of error with scanner
			sc = new Scanner(new File("death.txt"));//create new scanner and call death.txt
			while(sc.hasNextLine()){ //if there is another name
				death.add(sc.nextLine()); //add the next line to the ArrayList
			} //end while
		} //end try
		catch(FileNotFoundException e){ //if there is an exception
			e.printStackTrace();//give error
		} //end catch
		
		//survivor game
		int deathCounter=0;//initialize the deathCounter, which represents each reason for elimination
		if(list!=null){//if the list is not empty
			if(number==1){//if the number is 1
				ListNode t=list;//initialize temporary variable for list
				while(list.getNext()!=t){//while the end of the list has not been reached
					System.out.println(list.getValue()+""+death.get(deathCounter));//output the name and reason for elimination
					deathCounter++;//go to the next reason for elimination (for the next elimination)
					list=list.getNext();//move down the list (eliminating the person - as the number is 1, each person passed is eliminated)
				}//end while
				System.out.println();
				System.out.println(list.getValue()+" is the survivor!");//output the last name, who is the survivor
			}//end if
			else{//otherwise, if the number is not 1
				//initialize temporary variable for list
				ListNode t=list.getNext();
				ListNode t2=list;//t2 is the person previous to t, for the purposes of eliminating someone once counter reaches number
				int counter=2;//this is the second node already (as t is the second node)
				while(t!=t2){//if there is more than one person in the list (meaning that t and t2 are equal)
					if(counter==number){//if this is the Nth person
						counter=1;//reset counter
						System.out.println(t.getValue()+""+death.get(deathCounter));//output the name and reason for elimination
						deathCounter++;//go to the next reason for elimination (for the next elimination)
						//eliminate the Nth person
						t=t.getNext();
						t2.setNext(t);
					}//end if
					else{//if this is not the Nth person
						//go on to the next person
						t2=t;
						t=t.getNext();
						counter++;//increment counter
					}//end else
				}//end while
				System.out.println();
				System.out.println(t.getValue()+" is the survivor!");//output the survivor
			}//end else
		}//end if
	}//end method
}//end class
