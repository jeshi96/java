//Name: Jessica Shi
//Date: 4-13-11
//Course: Class: ADSA, 4th Period
//Purpose: The Sphere class has three different constructors and uses user input from the TestSphere class. It 
//takes the radius as a double value and calculates the volume using the radius. The class also keeps a class 
//variable, count, to determine how many spheres have been created so far.

public class Sphere {	//Sphere class
	public static int count=0;	//Initialize class variable count
	double radius;	//Initialize radius
	double centerx;	//Initialize x coordinate
	double centery;	//Initialize y coordinate
	double centerz;	//Initialize z coordinate
	public Sphere(double r,double xcoor,double ycoor,double zcoor){	//Constructor 1, 4 parameters
		radius=r;	//set radius to user inputed r
		centerx=xcoor;	//set centerx to user inputed xcoor (x coordinate of center)
		centery=ycoor;	//set centery to user inputed ycoor (y coordinate of center)
		centerz=zcoor;	//set centerz to user inputed zcoor (z coordinate of center)
		Sphere.count++;	//one more Sphere has been created - add one to class variable count
	}
	public Sphere(){	//Constructor 2, 0 parameters
		radius=1;	//create a unit sphere (set radius to 1)
		centerx=0;	//create a sphere at origin (set centerx to 0)
		centery=0;	//create a sphere at origin (set centery to 0)
		centerz=0;	//create a sphere at origin (set centerz to 0)
		Sphere.count++;	//one more Sphere has been created - add one to class variable count
	}
	public Sphere(double xcoor, double ycoor, double zcoor){	//Constructor 3, 3 parameters
		radius=1;	//create a unit sphere (set radius to 1)
		centerx=xcoor;	//set centerx to user inputed xcoor (x coordinate of center)
		centery=ycoor;	//set centery to user inputed ycoor (y coordinate of center)
		centerz=zcoor;	//set centerz to user inputed zcoor (z coordinate of center)
		Sphere.count++;	//one more Sphere has been created - add one to class variable count
	}
	public double getVolume(){	//method to calculate and return volume of sphere
		double volume=(4.0/3)*Math.PI*Math.pow(radius,3.0);	//calculate the volume of the sphere
		return volume;	//return the volume of the sphere
	}
	public static int getCount(){	//method to return class variable count
		return Sphere.count;	//return class variable count
	}
}
