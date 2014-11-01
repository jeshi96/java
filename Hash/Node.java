public class Node
{
	private Record data;
	private Node next;

	//Constructors
	
	public Node()
	{
		data = new Record("",0);
		next = null;
	}// end default constructor

	public Node(Record newData, Node newNext)
	{
		data = newData;
		next = newNext;
	}// end constructor with parameters
	
	//setter methods
	
	public void setData(Record newData)
	{
		data = newData;
	}// end setData()
	
	public void setNext(Node newNext)
	{
		next = newNext;
	}// end setNext()
	
	//getter methods
	
	public Record getData()
	{
		return data;
	}// end getData()
	
	public Node getNext()
	{
		return next;
	}// end getNext()
	
}// end class linkedList
		
			
			
		

