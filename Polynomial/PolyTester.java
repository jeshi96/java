//Jessica Shi
//Date: 12/21/11
//Course: ADSB, Period 2
//Purpose: The purpose of the PolyTester class is to test the Poly class. It uses file reading to receive the coefficients of
//polynomials and a value at which to calculate the polynomials at every three lines. It creates a polynomial for p, q,
//sum, difference, and product, and outputs each polynomial (using the methods of Poly). It then outputs the value
//of each of these at the aforementioned value (using the value method of Poly).

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class PolyTester {

	//main method
	public static void main(String[] args) {
		Scanner sc; //initialize scanner
		ArrayList <String> polyArray = new ArrayList <String> (); //initialize ArrayList
		try{ //in case of error with scanner
			sc = new Scanner(new File("polyData.txt"));//create new scanner and call names.txt
			while(sc.hasNextLine()){ //if there is another name
				polyArray.add(sc.nextLine()); //add the next line to the ArrayList
			} //end while
		} //end try
		catch(FileNotFoundException e){ //if there is an exception
			e.printStackTrace();//give error
		} //end catch
		while(polyArray.isEmpty()==false){//while loop to continue until all lines have been used
			//initialize polynomials
			Poly p=new Poly();
			Poly q=new Poly();
			Poly sum=new Poly();
			Poly difference=new Poly();
			Poly product=new Poly();
			//store inputed lines into arrays to retrieve coefficients
			String[] splitArray=polyArray.get(0).split(" ");
			String[] splitArray2=polyArray.get(1).split(" ");
			boolean isZero=true;//initialize boolean to check if all the values in the array are 0
			for(int i=0;i<splitArray.length;i++){//for each element of the array
				if(Double.parseDouble(splitArray[i])!=0.0)//if the value is not 0
					isZero=false;//not all coefficients are 0
			}//end for
			if (isZero==false){//if not all coefficients are 0
				//store the coefficients into their respective places in a polynomial
				for(int i=0;i<splitArray.length;i++){//for each element of the array
					p.insertCoeff(Double.parseDouble(splitArray[i]));//insert the coefficient into the polynomial
				}//end for
			}//end if
			isZero=true;//reset isZero
			for(int i=0;i<splitArray2.length;i++){//for each element of the array
				if(Double.parseDouble(splitArray2[i])!=0.0)//if the value is not 0
					isZero=false;//not all coefficients are 0
			}//end for
			if (isZero==false){//if not all coefficients are 0
				//store the coefficients into their respective places in a polynomial
				for(int i=0;i<splitArray2.length;i++){//for each element of the array
					q.insertCoeff(Double.parseDouble(splitArray2[i]));//insert the coefficient into the polynomial
				}//end for
			}//end if
			System.out.println("P: "+p);//output p
			System.out.println("Q: "+q);//output q
			double num=Double.parseDouble(polyArray.get(2));//initialize num to the inputed value
			//remove the used lines
			polyArray.remove(0);
			polyArray.remove(0);
			polyArray.remove(0);
			//carry out all operations for two polynomials
			sum=p.add(q);
			product=p.multiply(q);
			difference=p.subtract(q);
			System.out.println("Sum: "+sum);//output the sum polynomial
			System.out.println("Difference: "+difference);//output the difference polynomial
			System.out.println("Product: "+product); //output the product polynomial
			System.out.println("P(x): "+p.value(num));//calculate and output the value of p at num
			System.out.println("Q(x): "+q.value(num));//calculate and output the value of q at num
			System.out.println("Sum(x): "+sum.value(num));//calculate and output the value of p+q at num
			System.out.println("Difference(x): "+difference.value(num));//calculate and output the value of p-q at num
			System.out.println("Product(x): "+product.value(num));//calculate and output the value of p*q at num
			System.out.println();
			System.out.println();
		}//end while
	}//end main method

}
