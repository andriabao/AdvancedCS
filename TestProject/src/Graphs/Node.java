package Graphs;

public class Node<E> {
	
	double priority;
	E info;
	
	public Node(double p, E i) {
		priority = p;
		info = i;
	}
	
	public String toString() {
		return priority + " " + info;
	}
	

}
