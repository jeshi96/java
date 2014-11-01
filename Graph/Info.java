/********************************************************************************************************
 * Name: Jessica Shi																					*
 * Date: 5-26-12																						*
 * Course: Class: Analysis of Algorithms, 1st Period													*
 * Purpose: The Info class holds the info in one row of the label table, including the vertex, the 		*
 * previous vertex, the cumulative weight, and the mark. It also holds the index of the previous vertex,*
 * for ease in constructing the path. It has the appropriate getters and setters for each column. It	*
 * sets the previous vertex to 0 by default, for ease in determining unused rows of the label table		*
 * (for formatting purposes when printing the table to the screen).										*
 ********************************************************************************************************/

public class Info {
	//private variables for vertex letter, previous vertex, cumulative weight, mark, and index of the previous vertex
	private char vertex;
	private char prev='0';
	private int weight;
	private boolean mark=false;
	private int prevIndex;
	
	public Info(){//null constructor
	}//end constructor
	public Info(char avertex){//constructor with one parameter
		vertex=avertex;//set the vertex letter
	}//end constructor
	public Info(char avertex,char aprev){//constructor with two parameters
		vertex=avertex;//set the vertex letter
		prev=aprev;//set the previous vertex letter
	}//end constructor
	public Info(char avertex,char aprev,int aweight){//constructor with three parameters
		vertex=avertex;//set the vertex letter
		prev=aprev;//set the previous vertex letter
		weight=aweight;//set the cumulative weight
	}//end constructor
	public Info(char avertex,char aprev,int aweight,boolean amark){//constructor with four parameters
		vertex=avertex;//set the vertex letter
		prev=aprev;//set the previous vertex letter
		weight=aweight;//set the cumulative weight
		mark=amark;//set the mark
	}//end constructor
	public void setVertex(char avertex){//method to set the vertex letter
		vertex=avertex;
	}//end method
	public void setPrev(char aprev){//method to set the previous vertex letter
		prev=aprev;
	}//end method
	public void setWeight(int aweight){//method to set the cumulative weight
		weight=aweight;
	}//end method
	public void setMark(boolean amark){//method to set the mark
		mark=amark;
	}//end method
	public char getVertex(){//method to retrieve the vertex letter
		return vertex;
	}//end method
	public char getPrev(){//method to retrieve the previous vertex letter
		return prev;
	}//end method
	public int getWeight(){//method to retrieve the cumulative weight
		return weight;
	}//end method
	public boolean getMark(){//method to retrieve the mark
		return mark;
	}//end method
	public int getPrevIndex(){//method to retrieve the index of the previous vertex
		return prevIndex;
	}//end method
	public void setPrevIndex(int aprevIndex){//method to set the index of the previous vertex
		prevIndex=aprevIndex;
	}//end method
}//end class
