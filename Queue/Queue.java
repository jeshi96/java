/**
 * Jessica Shi
 * Queue.java
 * Date: 10/13/14
 * Purpose: The purpose of this class is to hold a queue and include methods
 * for the queue including enqueue and dequeue.
 */

public class Queue{
	private int qIndex=0;
	private int[] qArray;
	private int qSize;
	
	/**
	 * Constructor: Create a new queue of size size.
	 * @param size
	 */
	public Queue(int size){
		qArray=new int[size];
		qSize=size;
	}
	
	/**
	 * Enqueue: Add the new integer num to the queue. If the queue is already
	 * full, return errors.
	 * @param num
	 * @throws Exception 
	 */
	public void enqueue(int num) throws Exception{
		if(qIndex==qSize){
			throw new Exception("Error: Queue is already full.");
		}
		qArray[qIndex]=num;
		qIndex++;
	}//end method
	
	/**
	 * Dequeue: Remove the first integer from the queue. If the queue is empty, 
	 * return errors. Else, return the removed integer.
	 * @return
	 * @throws Exception 
	 */
	public int dequeue() throws Exception{
		if(qIndex<=0){
			throw new Exception("Error: Queue is empty.");
		}
		int num=qArray[0];
		qIndex--;
		for(int i=0;i<=qIndex-1;i++)
			qArray[i]=qArray[i+1];
		return num;
	}//end method
	
}//end class