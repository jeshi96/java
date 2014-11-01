/********************************************************************************************************
 * Name: Jessica Shi																					*
 * Date: 4-13-11																						*
 * Course: Class: Analysis of Algorithms, 1st Period													*
 * Purpose: The BSTHeap class reads integers from a file numbers.txt, inputs it into a BST using the	*
 * TreeNode class, outputs an in-order traversal of the BST, deletes a user-inputed number from the		*
 * BST, outputs a post-order traversal to both the screen and to an output file newNumbers.txt, 		*
 * reads the numbers from the output file, inserts them into a heap, deletes the root of the heap,		*
 * prints the heap to the screen, inserts a user-inputed number to the heap, outputs the heap to		*
 * the screen again, and finally sorts the heap in ascending order using the heapSort algorithm, 		*
 * printing each pass to the screen in the meantime.													*
 ********************************************************************************************************/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BSTHeap {
	public static void main(String[] args) {
		TreeNode BST=createBST();//create BST (by reading numbers from a file)
		System.out.print("In Order Traversal: ");
		inOrder(BST);//output the in-order traversal of the BST
		System.out.println();
		PrintWriter outputStream=null;//initialize PrintWriter
		try{//in case of error
			outputStream=new PrintWriter (new FileOutputStream ("newNumbers.txt"));//create new PrintWriter and open newNumbers.txt
		}//end try
		catch(FileNotFoundException e){//if there is an exception
			e.printStackTrace();//give error
		}//end catch
		BST=findBST(BST,inputNumDelete());//delete a user-inputed number from the BST
		System.out.println("In Order Traversal: ");
		inOrder(BST);//output the in-order traversal of the BST
		System.out.println();
		System.out.println("Post Order Traversal: ");
		postOrder(BST,outputStream);//output the post-order traversal of the BST
		outputStream.close();//close outputStream
		System.out.println();
		ArrayList<Integer> heap=new ArrayList<Integer>();//initialize the heap
		heap=createHeap();//create the heap
		System.out.println("Heap: ");
		printHeap(heap);//output the heap
		heap=deleteNum(heap);//delete the root from the heap
		System.out.println();
		System.out.println("Heap (with deleted root):");
		printHeap(heap);//output the heap
		System.out.println();
		heap=insertNum(heap);//insert a number to the heap, based on user input
		System.out.println("Heap (with inserted number):");
		printHeap(heap);//output the heap
		System.out.println();
		System.out.println("Passes of heap sort:");
		ArrayList<Integer> sort=new ArrayList<Integer>();//initialize new sort ArrayList
		sort=heapSort(heap,sort);//call heapSort to sort the heap
	}//end main method
	
	//method to heap up
	public static ArrayList<Integer> heapUp(ArrayList<Integer> heap, int index){
		if(index<=0)//if the number has reached the end of the ArrayList
			return heap;//return the heap
		else{
			int temp;//initialize temp variable for switching purposes
			if(heap.get((index-1)/2)<heap.get(index)){//if the parent is less than the new number
				temp=heap.get((index-1)/2);//switch the parent and the child
				heap.set((index-1)/2, heap.get(index));
				heap.set(index, temp);
				heapUp(heap,(index-1)/2);//call heap up again on the new index
			}//end if
			return heap;//return the heap
		}//end else
	}//end method
	
	//method to insert a number into the heap based on user input
	public static ArrayList<Integer> insertNum(ArrayList<Integer>heap){
		String input; //initialize input, which is the user input
		int done=0; //initialize done, which is used in input protection
		int number=0; //initialize number, which is the final integer
		System.out.println("Please enter a number to insert: ");//prompt user for the integer the user wants to insert into the heap
		Scanner scan = new Scanner(System.in); //initialize scanner
		while(done==0){//while a valid user input has not been found
			done=1;//set done to 1, meaning a valid user input is being found
			input=scan.next();//get user input
			try{ //in case of an error with parsing to integer (so if the user has not entered an integer)
				number=Integer.parseInt(input); //parse the input to an integer
			}//end try
			catch(NumberFormatException nFE){ //if there is an error
				done=0;//the valid user input has not been found
				System.out.println("Error. Please enter a valid integer: ");//prompt user for a valid integer
			}//end catch
		}//end while
		heap.add(number);//add the new number to the heap
		heap=heapUp(heap,heap.size()-1);//call heap up to make it a valid heap
		return heap;//return the heap with its new element
	}//end method
	
	//method to delete a number from a heap
	public static ArrayList<Integer> deleteNum(ArrayList<Integer> heap){
		heap.set(0, heap.get(heap.size()-1));//replace the first number with the last
		heap.remove(heap.size()-1);//delete the last element
		heap=heapDown(heap,0);//heap down to make it a valid heap
		return heap;//return the heap with its root deleted
	}//end method
	
	//method to sort the heap using the heap sort algorithm
	public static ArrayList<Integer> heapSort(ArrayList<Integer> heap,ArrayList<Integer> sort){
		if(heap.size()==0)//if the end of the heap has been reached
			return sort;//return the now completed sorted heap
		sort.add(0, heap.get(0));//add the root of the heap to sort
		heap=deleteNum(heap);//delete the root and heap down
		printHeap(heap);//output the heap and the sort, for one pass of heap sort
		printHeap(sort);
		System.out.println();
		heapSort(heap,sort);//call heapSort again
		return sort;//return the sorted heap
	}//end method
	
	//method to output a heap (ArrayList)
	public static void printHeap(ArrayList<Integer> heap){
		for(int i:heap){//for each element of heap
			System.out.print(i+ " ");//output the element
		}//end for
	}//end method
	
	//method to read integers from a file and create a heap
	public static ArrayList<Integer> createHeap(){
		Scanner sc=null; //initialize scanner
		ArrayList <Integer> data = new ArrayList <Integer> ();//initialize ArrayList
		try{ //in case of error with scanner
			sc = new Scanner(new File("newNumbers.txt"));//create new scanner and call newNumbers.txt
		} //end try
		catch(FileNotFoundException e){ //if there is an exception
			e.printStackTrace();//give error
		} //end catch
		while(sc.hasNextInt()){ //if there is another number
			data.add(sc.nextInt()); //add the next integer to the ArrayList
		} //end while
		int n=(data.size()-2)/2;//initialize first parent to check
		while(n>=0){//for all parents
			data=heapDown(data,n);//heap down, to make it a heap
			n--;//decrement n
		}//end while
		return data;//return the now completed heap
	}//end method
	
	//method to heap down
	public static ArrayList<Integer> heapDown(ArrayList<Integer> heap,int n){
		int length=heap.size()-1;//initialize size of heap
		int larger=0;//initialize larger, to determine which child is larger
		int temp;//initialize temp, for use when switching a child and the parent
		int switched=0;//initialize switched, to determine if previous parents must be revisited
		if(2*n+1<=length){//if the children are within the bounds
			if(2*n+2<=length){//if there is a right child within the bounds
				if (heap.get(2*n+2)>heap.get(2*n+1))//if the right child is greater than the left
					larger=1;//set larger
				else//otherwise, if the right child isn't greater than the left
					larger=0;//set larger
			}//end if
			if(larger==1){//if the right child exists and is greater than the left
				if(heap.get(2*n+2)>heap.get(n)){//if the right child is greater than the parent
					temp=heap.get(n);//switch the right child with its parent
					heap.set(n, heap.get(2*n+2));
					heap.set(2*n+2, temp);
					switched=1;//set switched
				}//end if
			}//end if
			else{//else if the left child is greater than the right (or if the right is non-existent)
				if(heap.get(2*n+1)>heap.get(n)){//if the left child is greater than the parent
					temp=heap.get(n);//switch the left child with its parent
					heap.set(n, heap.get(2*n+1));
					heap.set(2*n+1, temp);
					switched=1;//set switched
				}//end if
			}//end else
		}//end if
		if (switched==1){//if a number was switched
			if(2*n+2<=length)//if the right child is within bounds
				heapDown(heap,2*n+2);//call heapDown on the right child
			if(2*n+1<=length)//if the left child is within bounds
				heapDown(heap,2*n+1);//call heapDown on the left child
		}//end if
		larger=0;//reset larger
		switched=0;//reset switched
		return heap;//return the heap, now that heapDown has been completed
	}//end method
	
	//method to receive a user inputed number (to delete it from the BST)
	public static int inputNumDelete(){
		String input; //initialize input, which is the user input
		int done=0; //initialize done, which is used in input protection
		int number=0; //initialize number, which is the final integer
		System.out.println("Please enter the integer you wish to delete: ");//prompt user for the integer the user wants to delete
		Scanner scan = new Scanner(System.in); //initialize scanner
		while(done==0){//while a valid user input has not been found
			done=1;//set done to 1, meaning a valid user input is being found
			input=scan.next();//get user input
			try{ //in case of an error with parsing to integer (so if the user has not entered an integer)
				number=Integer.parseInt(input); //parse the input to an integer
			}//end try
			catch(NumberFormatException nFE){ //if there is an error
				done=0;//the valid user input has not been found
				System.out.println("Error. Please enter a valid integer: ");//prompt user for a valid integer
			}//end catch
		}//end while
		return number;//return the valid integer
	}//end method
	
	//method to find a user-inputed number in the BST and to call deleteBST to delete aforementioned number
	public static TreeNode findBST(TreeNode BST, int number){
		TreeNode t=BST;//initialize t and t2 to BST
		TreeNode t2=BST;
		int left=1;//initialize left to keep track of whether the next variable should be deleted from the left or right
		while(t!=null&&(Integer)t.getValue()!=number){
			t2=t;//update t2
			if(number<=(Integer)t.getValue()){//if the number is less than or equal to the current t value
				t=t.getLeft();//move to the next t
				left=1;//update left
			}//end if
			else{//otherwise, if the number is greater than the current value in the tree
				t=t.getRight();//move to the next t
				left=0;//update left
			}//end else
		}//end while
		if(t==null){//if the number has not been found in the BST
			System.out.println("Error. Integer not found in the BST.");//output error message
			return BST;//return the BST as is
		}//end if
		BST=deleteBST(BST,left,t,t2);//delete the found number
		return BST;//return the BST, with the number now deleted
	}//end method
	
	//method to delete a number from the BST
	public static TreeNode deleteBST(TreeNode BST,int left,TreeNode delete,TreeNode parent){
		TreeNode t=delete;//initialize t and t2 to BST
		TreeNode t2=parent;
		if(t.getLeft()==null&&t.getRight()==null){//if we want to delete a leaf
			if(left==1)//if the leaf is the left child
				t2.setLeft(null);//delete the left child
			else//else if the leaf is the right child
				t2.setRight(null);//delete the right child
		}//end if
		else if(t.getLeft()==null&&t.getRight()!=null){//if we want to delete a parent with a right child
			if (t==t2)//if the one we want to delete is the root
				BST=BST.getRight();
			else if(left==1)//if the number we want to delete is the left child
				t2.setLeft(t.getRight());//set the grandparent to the right child of the parent
			else//else if the number we want to delete is the right child
				t2.setRight(t.getRight());//set the grandparent to the right child of the parent
		}//end else if
		else if(t.getLeft()!=null&&t.getRight()==null){//if we want to delete a parent with a left child
			if (t==t2)//if the one we want to delete is the root
				BST=BST.getLeft();
			else if(left==1)//if the number we want to delete is the left child
				t2.setLeft(t.getLeft());//set the grandparent to the left child of the parent
			else//else if the number we want to delete is the right child
				t2.setRight(t.getLeft());//set the grandparent to the left child of the parent
		}//end else if
		else if(t.getLeft()!=null&&t.getRight()!=null){//if we want to delete a parent with two children
			TreeNode t3=t.getLeft();//initialize t3, to go left one and then right to receive the next highest number before the number to be deleted
			TreeNode t4=t;//initialize t4, to be the parent of t3
			int left2=1;//initialize left2, to see if t3 is the right or left child of t4
			while(t3.getRight()!=null){//loop to go right until there are no more numbers
				t4=t3;//update the parent
				t3=t3.getRight();//go right
				left2=0;//update left2
			}//end while
			if(t==t2){//if the one we want to delete is the root
				t.setValue(t3.getValue());//replace it with the next highest number
			}//end if
			else{//else if the one we want to delete is not the root
				if(left==1){//if the number is the left child
					t2.getLeft().setValue(t3.getValue());//replace the number with the next highest number
				}//end if
				else{//else if the number is the right child
					t2.getRight().setValue(t3.getValue());//replace the number with the next highest number
				}//end else
			}//end else
			deleteBST(BST,left2,t3,t4);//delete the next highest number (that we have replaced the parent with)
		}//end else if
		return BST;//return the BST, with the number now deleted
	}//end method

	//method to read integers from a file and insert them into a BST
	public static TreeNode createBST(){
		Scanner sc=null; //initialize scanner
		ArrayList <Integer> data = new ArrayList <Integer> (); //initialize ArrayList
		try{ //in case of error with scanner
			sc = new Scanner(new File("numbers.txt"));	//create new scanner and call numbers.txt
		} //end try
		catch(FileNotFoundException e){ //if there is an exception
			e.printStackTrace();//give error
		} //end catch
		while(sc.hasNextLine()){ //if there is another number
			data.add(Integer.parseInt(sc.nextLine())); //add the next line to the ArrayList
		} //end while
		TreeNode BST=null;//initialize BST
		if(data.size()>0)//if there is data
			BST=new TreeNode(data.get(0));//insert the first element as the root
		int index=1;//initialize index
		while(index<data.size()){//if there are more elements
			TreeNode t=BST;//initialize t and t2 to BST
			TreeNode t2=BST;
			int left=1;//initialize left to keep track of whether the next variable should be inserted to the left or right
			while(t!=null){//if t is not null
				t2=t;//update t2
				if(data.get(index)<=(Integer)t.getValue()){//if the next element is less than or equal to the current t value
					t=t.getLeft();//move to the next t
					left=1;//update left
				}//end if
				else{//otherwise, if the next element is greater than the current value in the tree
					t=t.getRight();//move to the next t
					left=0;//update left
				}//end else
			}//end while
			TreeNode newNode=new TreeNode(data.get(index));//initialize new TreeNode with the new element
			if(left==1)//if it should be inserted to the left
				t2.setLeft(newNode);//insert the value to the left
			else//else if it should be inserted to the right
				t2.setRight(newNode);//insert the value to the right
			index++;//increment index
		}//end while
		return BST; //return the now filled data ArrayList
	}//end method
	
	//method to print an in-order traversal of the BST
	public static void inOrder(TreeNode t){
		TreeNode t2=t;
		if(t2!=null){//if there are elements in the tree
			inOrder(t2.getLeft());//output the left element (call inOrder again)
			System.out.print(t2.getValue()+" ");//output the root
			inOrder(t2.getRight());//output the right element (call inOrder again)
		}//end if
	}//end method
	
	//method to print a post-order traversal of the BST to the screen and to a file
	public static void postOrder(TreeNode t, PrintWriter outputStream){
		TreeNode t2=t;
		if(t2!=null){//if there are elements in the tree
			postOrder(t2.getLeft(),outputStream);//output the left element (call postOrder again)
			postOrder(t2.getRight(),outputStream);//output the right element (call postOrder again)
			System.out.print(t2.getValue()+" ");//output the root to the screen
			outputStream.print(t2.getValue()+" ");//output the root to a file
		}//end if
	}//end method
}//end class
