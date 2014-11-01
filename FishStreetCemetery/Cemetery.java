import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Cemetery {
	private TreeMap<Date,Tombstone> residents=new TreeMap<Date,Tombstone>();//TreeMap to hold all of the Tombstone objects
	
	//Cemetery constructor, which reads the Cemetery.txt data file and instantiates a Tombstone object for each burial record
	public Cemetery(){
		createMap();//call method to read the Cemetery.txt data file and instantiate a Tombstone object for each burial record
	}//end constructor
	
	//method to read the Cemetery.txt data file and instantiate a Tombstone object for each burial record
	public void createMap(){
		Scanner sc;//initialize scanner
		try{ //in case of error with scanner
			sc = new Scanner(new File("Cemetery.txt"));	//create new scanner and call Cemetery.txt
			//skip the first four lines
			sc.nextLine();
			sc.nextLine();
			sc.nextLine();
			sc.nextLine();
			while(sc.hasNextLine()){ //if there is another entry
				addMap(sc.nextLine()); //add the next line to the map
			} //end while
		} //end try
		catch(FileNotFoundException e){ //if there is an exception
			e.printStackTrace();//give error
		} //end catch
		outputInfo();//call method to output the number of people buried in the cemetery, the burials and names of everyone, and the number and average age of people from Little Carter Lane buried during the period of January 1, 1814 to December 31, 1852 inclusive
	}//end method
	
	//method to output the number of people buried in the cemetery, the burials and the names of everyone, and the number and average age of people from Little Carter Lane buried during the period of January 1, 1814 to December 31, 1852 inclusive
	public void outputInfo(){
		System.out.println("Number of people: "+residents.size());//output the number of people in the cemetery
		Set<Date> residentSet=residents.keySet();//initialize a set to equal the keys of the TreeMap
		Iterator<Date> itr=residentSet.iterator();//initialize an iterator for the set
		Date temp;//initialize temp, to temporarily hold the keys
		int carterLane=0;//initialize the number of people in Little Carter Lane
		double carterSum=0;//initialize the average of the ages of the people in Little Carter Lane
		while(itr.hasNext()){//while there are more Tombstones to go through
			temp=itr.next();//set temp to the next date
			System.out.println(temp.getDate()+" belongs to "+residents.get(temp).getName());//output the date and the name of each Tombstone
			if(residents.get(temp).getAddress().indexOf("Little Carter Lane")!=-1&&residents.get(temp).getDateDate().getYear()>1813&&residents.get(temp).getDateDate().getYear()<1852){//if the person was from Little Carter Lane and within the necessary time constraints
				carterLane++;//increment the population of Little Carter Lane
				carterSum+=(residents.get(temp).getAge())/365.25;//increment the sum of the ages of the people of Little Carter Lane
			}//end if
		}//end while
		carterSum=carterSum/carterLane;//calculate the average ages
		System.out.println();
		System.out.println("Number of people in Little Carter Lane: "+carterLane);//output the number of people in Little Carter Lane
		System.out.println("Average age of people in Little Carter Lane: "+carterSum);//output the average age of people in Little Carter Lane
	}//end method
	
	//method to create and add a Tombstone to a TreeMap
	public void addMap(String line){
		String name=line.substring(0,25);//retrieve the name from the String
		String address=line.substring(42);//retrieve the address from the String
		line=line.substring(25);//cut out the name from the String (it is unnecessary)
		String splitString[]=line.split(" ");//place the rest into an array, as split by spaces
		ArrayList<String> inputString=new ArrayList<String>();//initialize ArrayList to get rid of the empty slots in the array
		for(String i:splitString){//for each element of the array
			if(!i.equals(""))//if the element is not empty
				inputString.add(i);//place it in the ArrayList
		}//end for
		Tombstone temp=new Tombstone(inputString.get(3),address.trim(),inputString.get(0)+" "+inputString.get(1)+" "+inputString.get(2),name.trim());//place the necessary information in a new Tombstone
		if(residents.get(temp.getDateDate())==null)//if the dates do not conflict
			residents.put(temp.getDateDate(),temp);//add the Tombstone to the TreeMap
		else{//if the dates do conflict
			while(residents.get(temp.getDateDate())!=null)//while the dates still conflict
				temp.getDateDate().addN();//increment n
			residents.put(temp.getDateDate(),temp);//add the Tombstone to the TreeMap
		}//end else
	}//end method
}
