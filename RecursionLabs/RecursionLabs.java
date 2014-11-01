
public class RecursionLabs {
	//main method
	public static void main(String[] args) {
		System.out.println(countVowels("hello"));
	}
	
	//method to draw a half hourglass shape out of X given the size of the top and bottom, with two rows of one X in the middle
	public static void draw(int numDraw){
		for(int i=0;i<numDraw;i++){ //loop to print numDraw X on one line
			System.out.print("X"); //print an X
		}//end for loop
		System.out.println();//go to the next line
		if(numDraw>1){//if we want to draw more than one X (so we won't try to draw 0 X on one line)
			draw(numDraw-1);//recursion: call draw for numDraw-1
		}//end if
		for(int i=0;i<numDraw;i++){ //loop to print numDraw X on one line (so that it prints backwards, for the second time)
			System.out.print("X");//print an X
		}//end for loop
		System.out.println();//go to the next line
	}//end method
	
	//method to draw a half hourglass shape out of X given the size of the top and bottom, with one row of one X in the middle
	public static void draw2(int numDraw){
		for(int i=0;i<numDraw;i++){ //loop to print numDraw X on one line
			System.out.print("X");//print an X
		}//end for loop
		System.out.println();//go to the next line
		if(numDraw>1){//if we want to draw more than one X (so we won't try to draw 0 X on one line)
			draw2(numDraw-1);//recursion: call draw2 for numDraw-1
			for(int i=0;i<numDraw;i++){//loop to print numDraw X on one line (so that it prints backwards, for the second time, but not for numDraw=1)
				System.out.print("X");//print an X
			}//end for loop
			System.out.println();//go to the next line
		}//end if
	}//end method
	
	//method to count all the vowels inside a word, excluding y
	public static int countVowels(String word){
		word=word.toLowerCase();//change everything to lower case (to make checking easier)
		if(word.length()==1){//if there is only one letter in the word
			if(word.equals("a")||word.equals("e")||word.equals("i")||word.equals("o")||word.equals("u")){//if the letter is a vowel
				return 1;//return 1, to add to the count of the number of vowels
			}//end if
			else{//if the letter is not a vowel
				return 0; //return 0
			}//end else
		}//end if
		else{//if there is more than one letter in the word
			return countVowels(word.substring(0,word.length()-1))+countVowels(word.substring(word.length()-1));
			//recursion: split up the word so that each letter is sent through the method individually, eventually (splits the word so that the last letter is separate)
		}//end else
	}//end method
}
