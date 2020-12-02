package HuffmanEncoding;
import java.util.ArrayList;

public class PriorityQueue<E> {
	
	public ArrayList<Branch> queue = new ArrayList<Branch>();
	
	public void add(Branch newB) {
		
		if(queue.size() == 0) {
			queue.add(newB);
		} else if (queue.get(0).priority > newB.priority){
			queue.add(0, newB);
		} else if (queue.get(queue.size()-1).priority < newB.priority) {
			queue.add(newB);
		} else {
			int low = 0;
			int high = queue.size()-1;
			
			while(low < high) {
				int mid = (low + high)/2;
				
				if(queue.get(mid).priority < newB.priority) {
					low = mid+1;
				} else {
					high = mid;
				}
			}
			
			queue.add(low, newB);
		}
	}
	
	public void remove() {
		queue.remove(queue.get(0));
	}
	
	public int length() {
		return queue.size();
	}
	
	public Branch getBranch(int i) {
		return queue.get(i);
	}
	
	public void print() {
		for(Branch i : queue) {
			System.out.print(i.priority);
			System.out.print(i.info);
			System.out.println();
		}
	}
	
	public void printBranch(Branch head, int level) {
		
		if(head.isLeaf) { //prints a bit weirdly, but it works (level shows what level of the tree it is on)
			System.out.println(head.info + " " + level);
		} else {
			System.out.println(head.info + " " + level);
			printBranch(head.child1, level + 1);
			printBranch(head.child2, level + 1);
		}
		
	}

}
