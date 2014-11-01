/**
 * Jessica Shi
 * Date: 3/7/12
 * Course: ADSB, Period 1
 * Purpose: The purpose of this class is to read a list of expressions from a file expressions.data and evaluate
 * it. The expression will either be a fully parenthesized infix expression, a postfix expression, or invalid, and
 * the program differentiates between those types of expressions. If the expression is invalid, the program
 * outputs "invalid expression."
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InfixPostfix {
	//main method
	public static void main(String[] args) {
		Scanner sc=null;//initialize scanner
		try{//in case of error with scanner
			sc=new Scanner (new File("expressions.data"));//create new scanner and call expressions.data
		}//end try
		catch(FileNotFoundException e){//if there is an exception
			e.printStackTrace();//give error
		}//end catch
		while(sc.hasNextLine()){//while there is another expression
			String exp=sc.nextLine();//retrieve expression and put in a string
			if(exp.charAt(0)=='('){//if the first character is an opening parentheses (infix)
				System.out.print(exp+" = ");
				inFix(exp);//call inFix to evaluate and output the answer
			}//end if
			else{//else if the first character isn't an opening parentheses (postfix)
				System.out.print(exp+ " = ");
				postFix(exp);//call postFix to evaluate and output the answer
			}//end else
			System.out.println();
		}//end while
	}//end main method
	
	//method to evaluate a postfix expression using stacks and output the answer
	public static void postFix(String exp){
		Stack<Double> num= new Stack<Double>();//initialize number stack
		int index=0;//initialize index, to keep track of which character has been used
		int error=0;//initialize error, in case the stack is not valid
		while(index<exp.length()){//while there are more characters
			if(Character.isDigit(exp.charAt(index))||exp.charAt(index)=='.'){//if the character is a digit
				String number=""+exp.charAt(index);//initialize number to be the first digit
				while(index+1<exp.length()&&exp.charAt(index+1)!=' '){//if there are more digits
					index++;//increment index
					number=number+exp.charAt(index);//add the digits to the number
				}//end while
				try{ //in case of an error with parsing to integer (so if this is not a valid expression)
					num.push(Double.parseDouble(number.trim())); //parse the character to an integer and push into the stack
				}//end try
				catch(NumberFormatException nFE){ //if there is an error
					error=1;//set error
				}//end catch
			}//end else if
			else if(exp.charAt(index)!='('&&exp.charAt(index)!=')'&&exp.charAt(index)!=' '){//if the character is neither a digit nor a parentheses nor a white space
				double num1=0;//initialize the two numbers upon which the operation will be performed
				double num2=0;
				char operator=' ';//initialize the operator
				try{//in case of error with retrieving another integer from the num stack
					num1=num.pop();//retrieve the numbers from the num stack
					num2=num.pop();
					operator=exp.charAt(index);//set operator
				}//end try
				catch(EmptyStackException e){//if there is an empty stack
					error=1;//set error
				}//end catch
				switch(operator){//switch on the operator
				case '*'://if the operator is multiplication
					num.push(num1*num2);//multiply the numbers and push into num stack
					break;
				case '+'://if the operator is addition
					num.push(num1+num2);//add the numbers and push into num stack
					break;
				case '-'://if the operator is subtraction
					num.push(num2-num1);//subtract the numbers and push into num stack
					break;
				case '/'://if the operator is division
					num.push(num2/num1);//divide the numbers and push into num stack
					break;
				case '%'://if the operator is mod
					num.push(num2%num1);//mod the numbers and push into num stack
					break;
				case '^'://if the operator is to the power of
					num.push(Math.pow(num2,num1));//take num1 to the power of num2
					break;
				default://if the operator is not valid
					error=1;//set error
					break;
				}//end switch
			}//end else if
			index++;//go to the next character
		}//end while
		double answer=0;//initialize answer
		try{//in case of error with retrieving an integer from the num stack
			answer=num.pop();//retrieve the answer
		}//end try
		catch(EmptyStackException e){//if there is an empty stack
			error=1;//set error
		}//end catch
		if(num.isEmpty()&&error==0)//if it is a valid expression
			System.out.print(answer);//output answer
		else//else if it isn't a valid expression
			System.out.print("invalid expression");//output error message
	}//end method
	
	//method to evaluate an infix expression using stacks and output the answer
	public static void inFix(String exp){
		Stack<Double> num= new Stack<Double>();//initialize number and operator stacks
		Stack<Character> operation=new Stack<Character>();
		int index=0;//initialize index, to keep track of which character has been used
		int error=0;//initialize error, in case the stack is not valid
		int paren=0;//initialize paren, to check if the parentheses line up
		while(index<exp.length()){//while there are more characters
			if(exp.charAt(index)==')'){//if the character is a closing parentheses
				paren--;//decrement paren
				double num1=0;//initialize the two numbers upon which the operation will be performed
				double num2=0;
				char operator=' ';//initialize the operator
				try{//in case of error with retrieving another integer from the num stack
					num1=num.pop();//retrieve the numbers from the num stack
					num2=num.pop();
					operator=operation.pop();//retrieve the operator from the operation stack
				}//end try
				catch(EmptyStackException e){//if there is an empty stack
					error=1;//set error
				}//end catch
				switch(operator){//switch on the operator
				case '*'://if the operator is multiplication
					num.push(num1*num2);//multiply the numbers and push into num stack
					break;
				case '+'://if the operator is addition
					num.push(num1+num2);//add the numbers and push into num stack
					break;
				case '-'://if the operator is subtraction
					num.push(num2-num1);//subtract the numbers and push into num stack
					break;
				case '/'://if the operator is division
					num.push(num2/num1);//divide the numbers and push into num stack
					break;
				case '%'://if the operator is mod
					num.push(num2%num1);//mod the numbers and push into num stack
					break;
				case '^'://if the operator is to the power of
					num.push(Math.pow(num2,num1));//take num1 to the power of num2
					break;
				default://if the operator is not valid
					error=1;//set error
					break;
				}//end switch
			}//end if
			else if(Character.isDigit(exp.charAt(index))||exp.charAt(index)=='.'){//if the character is a digit
				String number=""+exp.charAt(index);//initialize number to be the first digit
				while(index+1<exp.length()&&(Character.isDigit(exp.charAt(index+1))||exp.charAt(index+1)=='.')){//if there are more digits
					index++;//increment index
					number=number+exp.charAt(index);//add the digits to the number
				}//end while
				try{ //in case of an error with parsing to integer (so if this is not a valid expression)
					num.push(Double.parseDouble(number)); //parse the character to an integer and push into the stack
				}//end try
				catch(NumberFormatException nFE){ //if there is an error
					error=1;//set error
				}//end catch
			}//end else if
			else if(exp.charAt(index)!='('){//if the character is neither a digit nor a parentheses
				operation.push(exp.charAt(index));//push the character into the operation stack
			}//end else if
			else if(exp.charAt(index)=='(')//if the character is an opening parentheses
				paren++;//increment paren
			index++;//go to the next character
		}//end while
		double answer=0;//initialize answer
		try{//in case of error with retrieving an integer from the num stack
			answer=num.pop();//retrieve the answer
		}//end try
		catch(EmptyStackException e){//if there is an empty stack
			error=1;//set error
		}//end catch
		if(num.isEmpty()&&operation.isEmpty()&&error==0&&paren==0)//if it is a valid expression
			System.out.print(answer);//output answer
		else//else if it isn't a valid expression
			System.out.print("invalid expression");//output error message
	}//end method
}//end class
