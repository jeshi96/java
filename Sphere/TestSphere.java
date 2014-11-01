//Name: Jessica Shi
//Date: 4-13-11
//Course: Class: ADSA, 4th Period
//Purpose: The TestSphere class tests the Sphere Class. It outputs how many spheres have been created, and allows
//the user to repeat the program. The TestSphere class also uses Scanner to prompt for certain user input
//regarding the sphere depending on the type of sphere the user wishes to create. The class then calculates and outputs 
//the volume of the sphere.

import java.util.Scanner;	//Import Scanner for inputs
public class TestSphere {	//TestSphere class
	public static void main(String[] args) {	//main method
		System.out.println("Welcome to the Sphere creator!");	//Welcome message
		System.out.println("The total number of Spheres created so far is "+Sphere.getCount()+".");	//Prints total number of Spheres created so far
		char again='y';	//Declare and initialize again
		int type;	//Declare and initialize type
		Scanner aScan=new Scanner(System.in);	//Create and initialize aScan
		while (again=='y'){	//If the user wants to repeat...
			System.out.println("Please input the type of Sphere you wish to create: ");	//Prompt for type of sphere the user wants to create
			type=aScan.nextInt();	//Use scanner to input type of Sphere
			if (type==1){	//if the user inputed 1 as type...
				System.out.println("Please enter the radius: ");	//Prompt for the radius of the Sphere
				double radius = aScan.nextDouble();	//Use scanner to input radius of Sphere
				System.out.println("Please enter the x coordinate of the center: ");	//Prompt for x coordinate of the Sphere
				double xcoor = aScan.nextDouble();	//Use scanner to input x coordinate of the Sphere
				System.out.println("Please enter the y coordinate of the center: ");	//Prompt for y coordinate of the Sphere
				double ycoor = aScan.nextDouble();	//Use scanner to input y coordinate of the Sphere
				System.out.println("Please enter the z coordinate of the center: ");	//Prompt for z coordinate of the Sphere
				double zcoor=aScan.nextDouble();	//Use scanner to input z coordinate of the Sphere
				Sphere aSphere=new Sphere(radius,xcoor,ycoor,zcoor);	//Create and initialize Sphere object (with 4 parameters)
				double volume=aSphere.getVolume();	//Calculate volume of the Sphere
				System.out.println("The volume is "+volume+".");	//Print volume of the Sphere
			}
			else if (type==2){	//If user inputed 2 as type...
				Sphere aSphere=new Sphere();	//Create and initialize Sphere object (with 0 parameters)
				double volume=aSphere.getVolume();	//Calculate volume of the Sphere
				System.out.println("The volume is "+volume+".");	//Print volume of the Sphere
			}
			else{
				System.out.println("Please enter the x coordinate of the center: ");	//Prompt for x coordinate of the Sphere
				double xcoor = aScan.nextDouble();	//Use scanner to input x coordinate of the Sphere
				System.out.println("Please enter the y coordinate of the center: ");	//Prompt for y coordinate of the Sphere
				double ycoor = aScan.nextDouble();	//Use scanner to input y coordinate of the Sphere
				System.out.println("Please enter the z coordinate of the center: ");	//Prompt for z coordinate of the Sphere
				double zcoor=aScan.nextDouble();	//Use scanner to input z coordinate of the Sphere
				Sphere aSphere=new Sphere(xcoor,ycoor,zcoor);	//Create and initialize Sphere object (with 3 parameters)
				double volume=aSphere.getVolume();	//Calculate volume of the Sphere
				System.out.println("The volume is "+volume+".");	//Print volume of the Sphere
			}
			System.out.println("Do you want to continue (y/n)? ");	//Ask user to repeat
			again=aScan.next().charAt(0);	//Use scanner to input whether or not the user wants to repeat
		}
		System.out.println("The total number of Spheres created is "+Sphere.getCount()+".");	//Print out total number of Spheres at the end
		System.out.println("Thank you for using the Sphere creator!");	//Thank you message
	}
}
