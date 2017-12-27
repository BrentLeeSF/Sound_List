package project1;


public class LinkedList {

	MusicLinkedList musList;

	private Link head, tail, firstNode, lastNode = null;
	
	/*private int y = 0;
	private int x = 0;*/
	private int length;
	

	/* Constructor creates head, tail, first and last node, and length, set them to null or zero */
	public LinkedList() {
		head = tail = firstNode = lastNode = null;
		length = 0;
	}

	/* Clears linked list. Sets tail to head */
	/*public void clear() {
		head.setNextX(null);
		tail = head;
		length = 0;
	}*/

	/* Returns length of linked list */
	/*public int size() {
		return length;
	}*/
	
	/* Returns number of samples */
	/*public int length() {
		return musList.getNumSamples();
	}

	public void print() {
		
		if(isEmpty() ) { 
			System.out.printf("Empty List");
		}
		
		else {
			
			Link current = head;
			
			while(current != null) {
				
				System.out.printf("%s \n", current.element());
				current = current.getNextX();
			}
		}
		
	}*/

	/*public boolean isEmpty() {
		return head == null;
	}


	public Link getFirstNode() {
		return firstNode;
	}

	public Link setFirstNode(Link firstNode) {
		this.firstNode = firstNode;
		return firstNode;
	}

	public Link getLastNode() {
		return lastNode;
	}

	public Link setLastNode(Link lastNode) {
		this.lastNode = lastNode;
		return lastNode;
	}*/
	
}
