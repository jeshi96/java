public class ListNode
{	//variables
	private Object value;
	private ListNode next;
	//constructor
	public ListNode (Object initValue, ListNode initNext)
	{	
		value = initValue; 
		next = initNext;
	}
	//getters
	public Object getValue( )
	{
		return value;
	}
	public ListNode getNext( )
	{
		return next;
	}
	//setters
	public void setValue(Object theNewValue)
	{
		value = theNewValue;
	}
	public void setNext(ListNode theNewNext)	
	{
		next = theNewNext;
	}
}// end ListNode