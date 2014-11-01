//Jessica Shi
//Date: 11/21/11
//Course: ADSB, Period 2
//Purpose: The purpose of this class is to create methods for the linkedListMain class and to use the Node class. The
//IntLinkedList class can set the head to null (in the constructor), return the number of Nodes in a list, add a Node to the
//front of a list, add a Node to the end of the list, delete the first Node, delete the last Node, and print the list horizontally.

public class IntLinkedList
{
	private Node head;
	
	//constructor
	public IntLinkedList() //sets head to null
	{// end default constructor
		head=null;
	}//
	
	public int length() //returns number of Nodes in list
	{	
		Node t=head;
		int lengthNode=0;
		while(t!=null){
			t=t.getNext();
			lengthNode++;
		}
		return lengthNode;
	}//end length()
	
	public void addAtBeginning(int newData) //adds Node to front of list
	{
		head=new Node(newData,head);
	}//end addAtBeginning()
	
	public void addAtEnd(int newData) // adds Node to end of list
	{
		if(head==null)
			head=new Node(newData,null);
		else{
			Node t=head;
			while(t.getNext()!=null){
				t=t.getNext();
			}
			t.setNext(new Node(newData,null));
		}
	}//end addAtEnd
	
	public void deleteAtBeginning() // deletes first node
	{
		if(head!=null){
			head=head.getNext();
		}
	}//end deleteAtBeginning
	
	public void deleteAtEnd() // deletes last node in list
	{	
		if(head!=null){
			Node t=head;
			Node t2=head;
			t=head.getNext();
			if(t==null)
				head=null;
			else if(t.getNext()==null)
				t2.setNext(null);
			else{
				while(t.getNext()!=null){
					t2=t;
					t=t.getNext();
				}
				t2.setNext(null);
			}
		}
	}// end deleteAtEnd
			
	public void printList() // prints the list horizontally
							// prints a return to next line
	{
		if(head!=null){
			Node t=head;
			System.out.print(t.getData());
			t=t.getNext();
			while(t!=null){
				System.out.print(", "+t.getData());
				t=t.getNext();
			}
		}
		System.out.println();
	}// end printList()
	
}// end class IntLinkedList