
public class Tombstone {
	//the four necessary variables to hold the information of each resident: name, burial date, age, and residential address
	private double age;
	private String address;
	private Date date=new Date();
	private String name;
	
	//Tombstone constructor, which creates an empty Tombstone
	public Tombstone(){
	}//end constructor
	
	//Tombstone constructor, with four parameters for age, address, date, and name
	public Tombstone (String aAge, String aAddress, String aDate, String aName){
		setAge(aAge);//set the age
		address=aAddress;//set the address
		name=aName;//set the name
		date.setDate(aDate);//set the date
	}//end constructor
	
	//method to retrieve the age
	public double getAge(){
		return age;//retrieve the age
	}//end method
	
	//method to retrieve the address
	public String getAddress(){
		return address;//retrieve the address
	}//end method
	
	//method to retrieve the date as a string
	public String getDateString(){
		return date.getDate();//retrieve the date as a string
	}//end method
	
	//method to retrieve the date as a Date object
	public Date getDateDate(){
		return date;//retrieve the date as a Date object
	}//end method
	
	//method to retrieve the name
	public String getName(){
		return name;//retrieve the name
	}//end method
	
	//method to set the age
	public void setAge(String aAge){
		if(aAge.indexOf("w")!=-1)//if the age is followed by a "w"
			age=Double.parseDouble(aAge.substring(0,aAge.indexOf("w")))*7;//multiply the weeks by 7 to retrieve the number of days
		else if(aAge.indexOf("d")!=-1)//if the age is followed by a "d"
			age=Double.parseDouble(aAge.substring(0,aAge.length()-1));//retrieve the number of days
		else if(aAge.indexOf(".")!=-1){//if the age is followed by a "."
			String splitAge[]=aAge.trim().split("\\.");//initialize an array to hold the contents of the string, as split by the "."
			age=Double.parseDouble(splitAge[0])*365.25+Double.parseDouble(splitAge[1])*30.5;//multiply the years part by 365.35 and the months part by 30.5 to retrieve the number of days
		}//end else if
		else//if the age is not followed by a special character
			age=Double.parseDouble(aAge)*365.25;//multiply the years by 365.25 to retrieve the number of days
	}//end method
	
	//method to set the address
	public void setAddress(String aAddress){
		address=aAddress;//set the address
	}//end method
	
	//method to set the date
	public void setDate(String aDate){
		date.setDate(aDate);//set the date
	}//end method
	
	//method to set the name
	public void setName(String aName){
		name=aName;//set the name
	}//end method
	
}//end class
