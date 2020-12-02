import java.util.ArrayList;

public class PriorityQueue<E> {
	
	public ArrayList<Node<E>> queue = new ArrayList<Node<E>>();
	
	public void add(Node<E> newNode) {
		
		if(queue.size() == 0) {
			queue.add(newNode);
		} else if (queue.get(0).priority > newNode.priority){
			queue.add(0, newNode);
		} else if (queue.get(queue.size()-1).priority < newNode.priority) {
			queue.add(newNode);
		} else {
			int low = 0;
			int high = queue.size()-1;
			
			while(low < high) {
				int mid = (low + high)/2;
				
				if(queue.get(mid).priority < newNode.priority) {
					low = mid+1;
				} else {
					high = mid;
				}
			}
			
			queue.add(low, newNode);
		}
	}
	
	public void remove() {
		queue.remove(queue.size()-1);
	}
	
	public void print() {
		for(Node<E> i : queue) {
			System.out.print(i.priority);
			System.out.print(i.info);
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		PriorityQueue<Character> pq = new PriorityQueue<Character>();
				
		for(int i = 0; i < 100; i++) {
			pq.add(new Node((int)(Math.random()*20), ""));
		}
		
		pq.print();
	}

}
