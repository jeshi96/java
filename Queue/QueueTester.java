/**
 * Jessica Shi
 * QueueTester.java
 * Date: 10/13/14
 * Purpose: The purpose of this class is to test the Queue object.
 */

import java.util.Scanner;

public class QueueTester {
	
	private static Scanner scan; /*Reads standard input*/
	private static final String DEQUEUE_COMMAND="dequeue"; /*User command to dequeue*/
	
	/**
	 * Main: Accepts a queue size through a command-line argument. Reads
	 * from standard input either an integer, in which it will enqueue, or 
	 * a dequeue command, in which it will dequeue.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		scan = new Scanner(System.in);
		Queue test=new Queue(Integer.parseInt(args[0]));
		while(scan.hasNext()){
			if(scan.hasNextInt())
				test.enqueue(scan.nextInt());
			else{
				String str=scan.next();
				if(str.equals(DEQUEUE_COMMAND))
					System.out.println(test.dequeue());
			}
		}		
	}
	
}