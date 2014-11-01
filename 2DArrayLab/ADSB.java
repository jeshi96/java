//Jessica Shi
//Date: 8-31-11
//Course: ADSB, Period 2
//Purpose: The purpose of this class is to take a seating chart (using file reading) and rotate it
//so that it is in the viewpoint of the students rather than the viewpoint of the teacher. The
//class calls a method to output the seating chart, and then another method to rearrange the
//seating the chart. The class finally outputs the new seating chart.
import java.io.*;
import java.util.*;

public class ADSB {

	//main method
	public static void main(String[] args) {
		boolean teacher=true; //teacher boolean (if true, then the rearrange method will flip the array so that it is in the teacher's point of view - the student's point of view does not require the array to be flipped)
		String seatingChart[][]=createSeating(); //call method to initialize seating chart array and directly insert the file's contents into the array
		String teacherSeatingChart[][]=rearrange(seatingChart,teacher); //call method to rearrange the seating chart to teacher's point of view
		System.out.println("Teacher's point of view:"); //label the teacher's point of view
		print(teacherSeatingChart); //output the seating chart array
		System.out.println("\t\t\t\t FRONT OF ROOM"); //label the front of the room
		System.out.println(""); //print an extra line (so everything looks better in the end)
		System.out.println("");
		teacher=false; //in order to rearrange the seating chart for the students, the teacher boolean becomes false
		String studentSeatingChart[][]=rearrange(teacherSeatingChart,teacher); //call method to rearrange the seating chart array so that it is in the viewpoint of the students
		System.out.println("Student's point of view:"); //label the student's point of view
		System.out.println("\t\t\t\t FRONT OF ROOM"); //label the front of the room
		System.out.println("");
		print(studentSeatingChart); //output the new seating chart array
	} //end main
	//method to initialize seating chart array
	public static String[][] createSeating ()
	{
		String[][] seating = new String[5][6]; //initialize seating chart
		Scanner sc; //initialize scanner
		try	//in case of error with scanner
		{
			sc = new Scanner(new File("Students.txt"));	//create new scanner and call Students.txt
			for (int i=0;i<seating.length;i++) //for each element of the array
			{
				for (int j=0;j<seating[i].length;j++)
				{
					if((i==4 && j==0)||(i==4 && j==1)) //put in empty seats (in the last row, there are empty seats)
					{
						seating[i][j]="\t";
					} //end if
					else if(sc.hasNext())
					{ //if there is another name in the file
						seating[i][j]=sc.next()+" "+sc.next(); //put the person in his/her respective place (firstName lastName)
						if(seating[i][j].length()<=7) //if a name won't line up when printing (if it has less than 7 characters)
						{
							seating[i][j]=seating[i][j]+"   "; //add spaces so that it will line up
						} //end if
					} //end else if
					else
					{
						seating[i][j]="\t"; //fill in the rest of the empty seats
					} //end else
				} //end for
			} //end for
		} //end try
		catch (FileNotFoundException e)
		{	//if there's an exception
			e.printStackTrace();	//give error
		} //end catch
		return seating; //return the now filled seating chart array
	} //end method
	//method to output an array
	public static void print(String [][] seating)
	{
		for (int i=0;i<seating.length;i++) //for each element of the array
		{
			for(int j=0;j<seating[i].length;j++)
			{
				System.out.print(seating[i][j]+"\t"); //print the name and then a tab, to line everything up
			} //end for
			System.out.println(); //go to the next line
		} //end for
		System.out.println(); //go to the next line
	} //end method
	//method to rearrange the seating chart array so that it is in the viewpoint of the students
	public static String[][] rearrange(String [][] seating, boolean teacher)
	{
		String[][] newSeating = new String[5][6]; //initialize new seating chart array
		for(int i=0;i<newSeating.length;i++) //for each element of the new seating chart array
		{
			for(int j=0;j<newSeating[i].length;j++)
			{
				newSeating[i][j]=seating[4-i][5-j]; //transfer the elements of the old seating chart array into the new seating chart array so that the new seating chart array will appear rotated 180 degrees
			} //end for
		} //end for
		if(teacher){ //if we are turning the array into the teacher's point of view
			for (int i=0;i<newSeating.length;i++){ //for each row
				for (int j=0;j<newSeating[i].length/2;j++){ //for half of the columns (since we are switching, we do not need to repeat the process for the other half)
					String temp=newSeating[i][j]; //switch the elements of the new array so that the new array is flipped across the y-axis
					newSeating[i][j]=newSeating[i][5-j];
					newSeating[i][5-j]=temp;
				} //end for
			} //end for
		} //end if
		return newSeating; //return the new seating chart array
	} //end method

} //end class
