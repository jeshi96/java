public class linkedListMain
	{
		public static void main(String args[])
		{
			IntLinkedList myList = new IntLinkedList();
			CreateList(myList);
			myList.printList();
			
			System.out.println(myList.length());
			
			RemoveSome(myList);
			myList.printList();
			
			EmptyTheList(myList);
			myList.printList();
			
			TestDeleteOnEmptyList(myList);
		}// end main()
		
		public static void CreateList(IntLinkedList list)
		{
			for (int i = 1; i < 10; i++)
			{
				list.addAtBeginning(i);
			}
			list.addAtEnd(12);
		}//	end CreateList()
		
		public static void RemoveSome(IntLinkedList list)
		{
			list.deleteAtBeginning();
			list.deleteAtEnd();
		}// end RemoveSome()
		
		public static void EmptyTheList(IntLinkedList list)
		{
			while (list.length() > 0)
				list.deleteAtEnd();
		}// end EmptyList()
		
		public static void TestDeleteOnEmptyList(IntLinkedList list)
		{
			list.deleteAtBeginning();
			list.deleteAtEnd();
		}// end TestDeleteOnEmptyList()
	
}//end LinkedListRunner	
			