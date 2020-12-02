
public class Branch {
	
	public Branch child1, child2;
	public int priority;
	public String info;
	public boolean isLeaf;
	
	public Branch(Branch c1, Branch c2, String i, int p) {
		child1 = c1;
		child2 = c2;
		info = i;
		priority = p;
		isLeaf = false;
	}
	
	public Branch(String i, int p) {
		info = i;
		priority = p;
		isLeaf = true;
	}
	
}
