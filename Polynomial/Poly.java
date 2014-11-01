//Jessica Shi
//Date: 12/21/11
//Course: ADSB, Period 2
//Purpose: The purpose of this class is to implement a polynomial as a linked list of nodes, using the ListNode class. The
//zero polynomial is implemented as an empty list. The class has a constructor to create an empty polynomial. The insertCoeff
//method inserts each coefficient into the ListNode. The value method calculates the polynomial at a number which is passed
//in as a parameter. The add method adds two polynomials, the subtract method subtracts two polynomials, and the multiply
//method multiplies two polynomials. The toString method correctly formats each polynomial into a string, and returns the string.
//The class has private member variables coeff and nodeCount representing the pointer to a singly linked list and the number
//of nodes in that list respectively.

public class Poly {
	//initialize variables
	private ListNode coeff;
	private int nodeCount;
	
	//constructor to set Poly to the zero polynomial
	public Poly(){
		coeff=null;
		nodeCount=0;
	}//end method
	
	//method to insert coefficients into the ListNode and increment nodeCount
	public void insertCoeff(double num){
		coeff=new ListNode(num,coeff);//add the num to the ListNode
		nodeCount++;//increment nodeCount
	}//end method
	
	//method to use synthetic division to find the value of the polynomial at a number passed in
	public double value(double num){
		double answer=0;//initialize the answer
		if(coeff!=null){//if the polynomial is not zero
			ListNode t=coeff;//initialize t to the polynomial
			answer=(Double)(t.getValue())*num;//complete the first step of synthetic division (multiply)
			while(t.getNext()!=null){//while there are more coefficients to calculate
				t=t.getNext();//move to the next coefficient
				answer=num*(answer+(Double)(t.getValue()));//complete another step of synthetic division (add, multiply)
			}//end while
			answer=answer/num;//the answer was multiplied by num one too many times (in the last step)
		}//end if
		return answer;//return the now calculated answer
	}//end method
	
	//method to add two polynomials together
	public Poly add(Poly poly2){
		//initialize variables
		Poly poly1=new Poly();
		poly1=this;
		//if poly1 or poly2 is null
		if(poly1==null){//if poly1 is a zero polynomial
			return poly2;//return poly1 (if both are null, then this will just return the zero polynomial)
		}//end if
		else if(poly2==null){//if poly2 is a zero polynomial
			return poly1;//return poly1
		}//end if
		//initialize variables
		ListNode t=poly1.coeff;
		ListNode t2=poly2.coeff;
		int count1=poly1.nodeCount;
		int count2=poly2.nodeCount;
		double[]addArray;
		//adding the polynomials, with the first space being the largest power
		if(count1>count2){//if poly1 has a greater power than poly2
			addArray=new double[count1];//initialize array to the largest size
			for(int i=0;i<count1-count2;i++){//for all the powers poly1 is greater than poly2
				addArray[i]=(Double)t.getValue();//insert those into the array (don't need to add poly2)
				t=t.getNext();//move to the next coefficient
			}//end for
			for(int i=count1-count2;i<count1;i++){//for the rest of the powers (in which poly2 and poly1 both have coefficients)
				addArray[i]=(Double)t.getValue()+(Double)t2.getValue();//insert the sum into the array
				t=t.getNext();//move to the next coefficients
				t2=t2.getNext();
			}//end for
		}//end if
		else{//if poly2 has a greater power than poly1
			addArray=new double[count2];//initialize array to the largest size
			for(int i=0;i<count2-count1;i++){//for all the powers poly2 is greater than poly1
				addArray[i]=(Double)t2.getValue();//insert those into the array (don't need to add poly1)
				t2=t2.getNext();//move to the next coefficient
			}//end for
			for(int i=count2-count1;i<count2;i++){//for the rest of the powers (in which poly2 and poly1 both have coefficients)
				addArray[i]=(Double)t.getValue()+(Double)t2.getValue();//insert the sum into the array
				t=t.getNext();//move to the next coefficients
				t2=t2.getNext();
			}//end for
		}//end else
		//insert array into a polynomial backwards (as when they were added, the first space was the largest power)
		Poly addPoly=new Poly();//initialize new polynomial
		for(int i=addArray.length-1;i>=0;i--){//for each element of the array
			addPoly.insertCoeff(addArray[i]);//insert the element into the polynomial
		}//end for
		return addPoly;//return the now added polynomial
	}//end method
	
	//method to subtract two polynomials
	public Poly subtract(Poly poly2){
		double[] subtractArray=new double[poly2.nodeCount];//initialize array to be poly2
		ListNode t2=poly2.coeff;//initialize a ListNode t2 to be poly2
		for(int i=0;i<subtractArray.length;i++){//for each element of the array
			subtractArray[i]=(Double)t2.getValue()*-1;//insert the negation of each poly2 coefficient into the array
			t2=t2.getNext();//move to the next coefficient
		}//end for
		Poly subtractPoly=new Poly();//initialize new subtraction polynomial
		for(int i=subtractArray.length-1;i>=0;i--){//for each element in the array
			subtractPoly.insertCoeff(subtractArray[i]);//insert the element into the polynomial (backwards)
		}//end for
		return subtractPoly.add(this);//return the sum of the initial polynomial and poly2, to receive the difference
	}//end method
	
	//method to convert a polynomial into a string, with appropriate formatting
	public String toString(){
		if(coeff==null)//if the polynomial is a zero polynomial
			return "Polynomial is the zero polynomial";//return zero polynomial method
		else{//if the polynomial is not a zero polynomial
			//initialize variables
			String answer="";
			ListNode t=coeff;
			Double temp=0.0;
			String sign="";
			int count=nodeCount;
			for(int i=count-1;i>=0;i--){//for each coefficient (backwards)
				temp=(Double)t.getValue();
				if(temp<0){//if the coefficient is negative
					sign="-";//sign is negative
					temp=temp*-1;//negate temp, because sign does not matter now
				}//end if
				else if(i==count-1)//if this is the first coefficient and sign is not negative
					sign="";//there is no sign
				else//otherwise
					sign="+";//sign is positive
				if(temp!=0.0){//if the coefficient is not zero (meaning we do have to print something)
					answer=answer+sign;//add the sign to the answer
					//check all the special cases
					if(temp==1.0&&i>1)//if the coefficient is 1 and the power is greater than 1
						answer=answer+"x^"+i;
					else if(temp==1.0&&i==1)//if the coefficient is 1 and the power is 1
						answer=answer+"x";
					else if(i==1)//if the power is 1 and the coefficient is not 1
						answer=answer+temp+"x";
					else if(i==0){//if the power is 0
						answer=answer+temp;
					}//end else if
					else//otherwise, if everything's normal
						answer=answer+temp+"x^"+i;
				}//end for
				if(temp==0.0){//if the coefficient is 0
					if(i==count-1){//if the first power is 0
						count--;//decrease count so that the signs will be right for the next power
					}//end if
				}//end if
				t=t.getNext();//move to the next coefficient
			}//end for
			return "Polynomial is: "+answer;//return the fully formatted polynomial
		}//end else
	}//end method
	
	//method to multiply two polynomials
	public Poly multiply(Poly poly2){
		Poly multiplyPoly=new Poly();//initialize new multiplication polynomial
		//if either polynomial is the zero polynomial, return the empty multiplyPoly (zero polynomial)
		if(poly2.coeff==null)
			return multiplyPoly;
		else if(coeff==null)
			return multiplyPoly;
		//initialize variables
		Poly poly1=new Poly();
		poly1=this;
		ListNode t=coeff;
		ListNode t2=poly2.coeff;
		int count1=poly1.nodeCount;
		int count2=poly2.nodeCount;
		int power=count1+count2;
		double[] multArray=new double[power-1];//initialize multiplication array to the highest power
		double temp=(Double)t2.getValue();//initialize temporary variable to contain poly2
		for(int i=count2-1;i>=0;i--){//for loop to go through all variables of poly1 and poly2
			for(int j=count1-1;j>=0;j--){
				multArray[i+j]=multArray[i+j]+(Double)t.getValue()*temp;//multiply poly1 coefficient with poly2 coefficient and add to multArray
				t=t.getNext();//move to the next coefficient (poly1)
			}//end for
			t=coeff;//reset t to go to the start of poly1
			if(t2.getNext()!=null){//if we have not reached the end of poly2
				t2=t2.getNext();//move to the next poly2 coefficient
				temp=(Double)t2.getValue();//move temp to the next coefficient
			}//end if
		}//end for
		for(int i=0;i<multArray.length;i++){//for each element in the array
			multiplyPoly.insertCoeff(multArray[i]);//insert the element into the polynomial
		}//end for
		return multiplyPoly;
	}//end method
	
}//end class
