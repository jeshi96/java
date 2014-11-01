
public class Date implements Comparable{
	//day, month, and year to hold the information of the burial date; n in case two or more people were buried on the same day
	private String day;
	private String month;
	private String year;
	private int n=0;
	
	//Date constructor, which creates an empty Date Object
	public Date(){
	}//end constructor
	
	//method to set the date given a string formatted in a specific manner
	public void setDate(String aDate){
		aDate=aDate.trim();//remove trailing white spaces
		String[] splitString=aDate.split(" ");//split the date into an array based on spaces
		day=""+Integer.parseInt(splitString[0].trim());//the first part is the day
		setMonth(splitString[1].trim());//call a method to set the month, with the second part as a parameter
		year=splitString[2].trim();//the third part is the month
	}//end method
	
	//method to override the compareTo method
	public int compareTo(Object xDate){
		Date aDate=(Date)xDate;//initialize the object as a Date object
		if(aDate.getYear()>Integer.parseInt(year))//if the year of the new object is greater than the year of this object
			return -1;//return a negative
		else if(aDate.getYear()<Integer.parseInt(year))//if the year of the new object is less than the year of this object
			return 1;//return a positive
		else if(aDate.getYear()==Integer.parseInt(year)){//if the two years are equal
			if(aDate.getMonth()>this.getMonth())//if the month of the new object is greater than the month of this object
				return -1;//return a negative
			else if(aDate.getMonth()<this.getMonth())//if the month of the new object is less than the month of this object
				return 1;//return a positive
			else{//if the months are the same
				if(aDate.getDay()>Integer.parseInt(day))//if the day of the new object is greater than the day of this object
					return -1;//return a negative
				else if(aDate.getDay()<Integer.parseInt(day))//if the day of the new object is less than the day of this object
					return 1;//return a positive
				else{//if the days are the same
					if(aDate.getN()>n)//if the n of the new object is greater than the n of this object
						return -1;//return a negative
					else if(aDate.getN()<n)//if the n of the new object is less than the n of this object
						return 1;//return a positive
				}//end else
			}//end else
		}//end else if
		return 0;//return 0, if all else fails
	}//end method
	
	//method to retrieve the month, as an integer value (for comparisons)
	public int getMonth(){
		if(month.toLowerCase().equals("january"))//if the month if January
			return 1;
		else if(month.toLowerCase().equals("february"))//if the month is February
			return 2;
		else if(month.toLowerCase().equals("march"))//if the month is March
			return 3;
		else if (month.toLowerCase().equals("april"))//if the month is April
			return 4;
		else if(month.toLowerCase().equals("may"))//if the month is May
			return 5;
		else if(month.toLowerCase().equals("june"))//if the month is June
			return 6;
		else if(month.toLowerCase().equals("july"))//if the month is July
			return 7;
		else if(month.toLowerCase().equals("august"))//if the month is August
			return 8;
		else if(month.toLowerCase().equals("september"))//if the month is September
			return 9;
		else if(month.toLowerCase().equals("october"))//if the month is October
			return 10;
		else if(month.toLowerCase().equals("november"))//if the month is November
			return 11;
		else if(month.toLowerCase().equals("december"))//if the month is December
			return 12;
		else//if all else fails
			return 0;
	}//end method
	
	//method to retrieve the n
	public int getN(){
		return n;//retrieve the n
	}//end method
	
	//method to increment the n (if dates are the same)
	public void addN(){
		n++;//increment the n
	}//end method
	
	//method to retrieve the day as an integer
	public int getDay(){
		return Integer.parseInt(day);//retrieve the day as an integer
	}//end method
	
	//method to set the year
	public void setYear(String aYear){
		year=aYear;//set the year
	}//end method
	
	//method to set the month
	public void setMonth(String aMonth){
		month=aMonth;//set the month
		//get rid of the abbreviations
		if(month.toLowerCase().equals("jan"))//if the month is January
			month="January";
		else if(month.toLowerCase().equals("feb"))//if the month is February
			month="February";
		else if(month.toLowerCase().equals("mar"))//if the month is March
			month="March";
		else if(month.toLowerCase().equals("apr"))//if the month is April
			month="April";
		else if(month.toLowerCase().equals("may"))//if the month is May
			month="May";
		else if(month.toLowerCase().equals("jun"))//if the month is June
			month="June";
		else if(month.toLowerCase().equals("jul"))//if the month is July
			month="July";
		else if(month.toLowerCase().equals("aug"))//if the month is August
			month="August";
		else if(month.toLowerCase().equals("sep"))//if the month is September
			month="September";
		else if(month.toLowerCase().equals("oct"))//if the month is October
			month="October";
		else if(month.toLowerCase().equals("nov"))//if the month is November
			month="November";
		else if(month.toLowerCase().equals("dec"))//if the month is December
			month="December";
	}//end method
	
	//method to retrieve the year as an integer
	public int getYear(){
		return Integer.parseInt(year.trim());//retrieve the year as an integer
	}//end method
	
	//method to retrieve the whole date as a string
	public String getDate(){
		return month+" "+day+", "+year;//format and retrieve the date as a string
	}//end method
}//end class
