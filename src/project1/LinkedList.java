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

}
