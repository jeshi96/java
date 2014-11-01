/********************************************************************************************************
 * Name: Jessica Shi																					*
 * Date: 4-16-12																						*
 * Course: Class: Analysis of Algorithms, 1st Period													*
 * Purpose: The Record class contains private variables of name and ID number. It has the appropriate 	*
 * constructor class and the getters and setters to allow the user to retrieve the name and the			*
 * corresponding ID number.																				*
 ********************************************************************************************************/

public class Record {
	//initialize private variables
	private String name;
	private int ID;
	
	//constructor, setting the name and ID
	public Record (String aName, int aID){
		name=aName;
		ID=aID;
	}//end constructor
	
	//getter method to return the ID number
	public int getID(){
		return ID;
	}//end method
	
	//getter method to return the name
	public String getName(){
		return name;
	}//end method
	
	//setter method to set the ID number
	public void setID(int aID){
		ID=aID;
	}//end method
	
	//setter method to set the name
	public void setName(String aName){
		name=aName;
	}//end method
}//end class
