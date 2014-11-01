public class Node
{
	private int data;
	private Node next;

	//Constructors
	
	public Node()
	{
		data = 0;
		next = null;
	}// end default constructor

	public Node(int newData, Node newNext)
	{
		data = newData;
		next = newNext;
	}// end constructor with parameters
	
	//setter methods
	
	public void setData(int newData)
	{
		data = newData;
	}// end setData()
	
	public void setNext(Node newNext)
	{
		next = newNext;
	}// end setNext()
	
	//getter methods
	
	public int getData()
	{
		return data;
	}// end getData()
	
	public Node getNext()
	{
		return next;
	}// end getNext()
	
}// end class linkedList
		
			
			
		

