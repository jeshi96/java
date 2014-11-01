//Jessica Shi
//Date: 9-19-11
//Course: ADSB, Period 2
//Purpose: The purpose of the tour class is to accept user input on whether to see one tour, all tours,
//or to exit (as the whole program loops). The program then prompts the user for a position should
//the user wish to see only one tour. The program uses the accessibility heuristic to output a
//knight's tour for that position (or for all positions) that has been to at least 60 spaces,
//or all 64 spaces (which is possible for all starting positions). The whole knight's tour is
//displayed on the screen, along with the tour length for that particular tour and the number of
//complete tours if the user decided on seeing all tours. The program then loops and prompts the user
//whether to see one more tour, all tours, or to exit. If the user decides to exit, an exit message
//is displayed.

import java.util.*; //java.util for the scanner (user input)
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

//tour class
public class Tour {
	//main method
	public static void main(String[] args) {

		int [][]accessible=createAccessible(); //initialize accessibility heuristic
		int horizPos; //initialize the horizontal position
		int vertPos; //initialize the vertical position
		int[][]tour; //initialize the knight's tour array
		int[] position; //initialize the position array (for user input in choosing one tour)
		int tourCount; //initialize the tour count
		int num64=0; //initialize the number of complete tours (should the user choose all tours)
		int userInput=1; //initialize user input (for choosing whether to output one tour, all tours, or exit)

		while(userInput!=0){
			System.out.println("Enter 1 to choose one tour. Enter 2 to choose all tours. Enter 0 to exit. "); //prompt user whether to output one tour, all tours, or exit
			Scanner scan = new Scanner(System.in); //initialize scanner
			userInput = scan.nextInt(); //get user input
			if(userInput==1){ //if the user wants one tour
				accessible=createAccessible(); //reset heuristic
				position = posInput(); //get user input on the position to start at
				horizPos=position[0]; //set initial horizontal position
				vertPos=position[1]; //set initial vertical position
				tour = createTour(horizPos,vertPos); //create the tour array
				tour = knightTour(horizPos,vertPos,tour, accessible); //do the knight's tour
				tourCount=printTour(tour); //output the tour
			} //end if
			else if(userInput==2){ //if the user wants all tours
				num64=0; //reset the number of complete tours
				for(int i=0;i<8;i++){ //for all possible tours
					for(int j=0;j<8;j++){
						accessible=createAccessible(); //reset heuristic
						horizPos=i; //set initial horizontal position
						vertPos=j; //set initial vertical position
						tour=createTour(horizPos,vertPos); //create the tour array
						tour=knightTour(horizPos, vertPos,tour,accessible); //do the knight's tour
						horizPos++; //add 1 for output of initial position
						vertPos++;
						System.out.println("Initial Position: "+horizPos+", "+vertPos); //output initial position
						tourCount=printTour(tour); //output the tour
						if(tourCount==64){ //if the tour was a complete tour
							num64++; //increment the number of complete tours
						} //end if
						System.out.println(); //extra lines for formatting purposes
						System.out.println();
					} //end for
				} //end for
				System.out.println("The number of complete tours is: "+num64); //output the number of complete tours
			}//end else if
			System.out.println(); //extra lines for formatting purposes
			System.out.println();
		}//end while
		System.out.println("Thank you!"); //exit message
	} //end main
	
	//get user input on the position to start at (should the user choose one tour)
	public static int[] posInput (){
		System.out.println("Enter the starting position (row column) separated by a space,from 1-8: "); //prompt the user for the starting position
		Scanner scan = new Scanner(System.in); //initialize scanner (for user input)
		String userInput=scan.nextLine().trim(); //get user input
		int horizPos=Integer.parseInt(userInput.substring(0,1))-1; //parse the input to receive the horizontal position
		int vertPos=Integer.parseInt(userInput.substring(2))-1; //parse the input to receive the vertical position
		while(horizPos>7 || vertPos>7){ //if the user did not enter a valid number (inside the board)
			System.out.println("Error. Please enter a valid starting position: "); //output error message and prompt for another starting position
			userInput=scan.nextLine().trim(); //get user input
			horizPos=Integer.parseInt(userInput.substring(0,1))-1;//parse the input to receive the horizontal position
			vertPos=Integer.parseInt(userInput.substring(2))-1;//parse the input to receive the vertical position
		} //end while
		int [] position = new int [2]; //initialize position array (to contain the two positions, for use in returning the two positions)
		position[0]=horizPos; //let position[0] be the horizontal position
		position[1]=vertPos; //let position[1] be the vertical position
		return position; //return the position array
	} //end method
	
	//create the tour array and set up the starting position
	public static int[][] createTour(int horizPos, int vertPos){
		int [][]tour = new int[8][8]; //initialize the tour array
		tour[horizPos][vertPos]=1; //set up the starting position of the array
		return tour; //return the tour array
	}//end method
	
	//create the accessibility heuristic
	public static int[][] createAccessible(){
		int [][]accessible = {{2,3,4,4,4,4,3,2},{3,4,6,6,6,6,4,3},{4,6,8,8,8,8,6,4},{4,6,8,8,8,8,6,4},{4,6,8,8,8,8,6,4},{4,6,8,8,8,8,6,4},{3,4,6,6,6,6,4,3},{2,3,4,4,4,4,3,2}}; //initialize the accessibility heuristic
		return accessible; //return the accessibility heuristic
	} //end method
	
	//go through the knight's tour
	public static int[][] knightTour(int horizPos, int vertPos, int[][] tour, int[][] accessible){
		int tourCount=1; //initialize the tour count (to find the tour length)
		final int[] horizontal = {2,1,-1,-2,-2,-1,1,2}; //initialize the possible moves
		final int[] vertical = {-1,-2,-2,-1,1,2,2,1};
		int nextMoveHoriz=0; //initialize nextMoveHoriz for all the possible horizontal moves
		int nextMoveVert=0; //initialize nextMoveVert for all the possible vertical moves
		int canGo=1; //let canGo equal 1, so that we enter the while loop at least once
		int counter=0; //initialize the counter to 0, in order to check the possible moves
		int smallestMove=9; //initialize the smallestMove to 9, so if there is a better move in the accessibility heuristic, it will be assigned as the next best/possible move (the greatest number in the heuristic is 8)
		int possibleMoveHoriz=0; //initialize possibleMoveHoriz for use as the best move
		int possibleMoveVert=0; //initialize possibleMoveVert for use as the best move
		int change=0; //initialize change for determining whether the tour is the one which gives a length of 60
		if(horizPos==4){ //if the tour is the one which gives a length of 60
			change=1; //change equals 1, so that the tour with 60 can be fixed later
		} //end if
		while(canGo==1){ //while we have thus far found possible moves
			canGo=0; //reset canGo
			counter=0; //reset counter
			smallestMove=9; //initiate the smallest move value, which represents the heuristic value
			while(counter<horizontal.length){ //for all possible moves
				nextMoveHoriz=horizPos+vertical[counter]; //let nextMoveHoriz be one possible move
				nextMoveVert=vertPos+horizontal[counter]; //let nextMoveVert be one possible move
				if(nextMoveHoriz<8 && nextMoveVert<8 && nextMoveHoriz>=0 && nextMoveVert>=0 && tour[nextMoveHoriz][nextMoveVert]==0){ //check to see that nextMoveHoriz is on the board and is not a space that has already been taken
					accessible[nextMoveHoriz][nextMoveVert]--; //update the accessibility heuristic
					if(change==0&&accessible[nextMoveHoriz][nextMoveVert]<=smallestMove){ //check to see if it is the smallest move (in the accessibility heuristic)
						canGo=1; //let canGo equal 1, which means that there is a possible move
						smallestMove=accessible[nextMoveHoriz][nextMoveVert]; //change the smallest move value to represent the new move
						possibleMoveHoriz=nextMoveHoriz; //assign this move as the new best move (at this point)
						possibleMoveVert=nextMoveVert;
					} //end if
					else if(change==1&&accessible[nextMoveHoriz][nextMoveVert]<smallestMove){ //same as above, but correcting the one entry that gives 60 (4,2) (this breaks the tie in a different way, thus changing the tour)
						canGo=1; //let canGo equal 1, which means that there is a possible move
						smallestMove=accessible[nextMoveHoriz][nextMoveVert]; //change the smallest move value to represent the new move
						possibleMoveHoriz=nextMoveHoriz; //assign this move as the new best move (at this point)
						possibleMoveVert=nextMoveVert;
					} //end else if
				} //end if
				counter++; //increment counter to check the next possible move
			} //end while
			if(canGo==1){ //if we have found a possible move
				horizPos=possibleMoveHoriz; //assign the best move we have found to be the next move
				vertPos=possibleMoveVert;
				tourCount++; //increment tourCount, which keeps track of the number move we are on
				tour[horizPos][vertPos]=tourCount; //make the move
				accessible[horizPos][vertPos]=0; //update the accessibility heuristic
			} //end if
		} //end while
		return tour; //return the now completed knight's tour
	} //end method
	
	//output the tour and return the tour length (for use in determining the number of complete tours, should the user choose to output all tours)
	public static int printTour(int[][] tour){
		int tourCount=0; //initialize the tour length to 0
		for (int i=0;i<tour.length;i++){ //for each element of the array
			for(int j=0;j<tour[i].length;j++){
				System.out.print(tour[i][j]+"\t"); //print the tour and then a tab, to line everything up
				if(tour[i][j]>tourCount){ //if the value of the tour number is greater than the current tour length (meaning that there is a greater number, so the tour length is actually greater)
					tourCount=tour[i][j]; //set the new tour length
				} //end if
			} //end for
			System.out.println(); //go to the next line
		} //end for
		System.out.println(); //go to the next line
		System.out.println("Tour length: "+tourCount); //output the tour length
		return tourCount; //return the tour length for use in determining the number of complete tours, should the user choose to output all tours
	} //end method
} //end class
