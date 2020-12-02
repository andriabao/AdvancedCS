import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class HuffmanEncoding {

	public static HashMap<String, Integer> freq = new HashMap<String, Integer>();
	
	public static void main(String[] args) {
		
		try {
			File in = new File("file.txt"); //instantiate input file
			
			if(!in.exists()) { //check if input file exists
				System.out.println("Input file does not exist");
				return;
			} else {
				System.out.println("Running the program");
			}
			
			FileReader fr = new FileReader(in); //create file reader

			char[] ch = new char[(int)in.length()];
	        fr.read(ch);
	        for (char ch1 : ch){
	         	if(freq.get(String.valueOf(ch1)) != null) {
	        		freq.put(String.valueOf(ch1), freq.get(String.valueOf(ch1)) + 1);
	        	} else {
	        		freq.put(String.valueOf(ch1), 1);
	        	}
	        }
	        
	        PriorityQueue<Branch> pq = new PriorityQueue<Branch>();
	        
	        for(Entry<String, Integer> key : freq.entrySet()) {
	        	pq.add(new Branch(key.getKey(), key.getValue()));
	        }
	        
	        while(pq.length() > 1) {
	        	pq.add(new Branch(pq.getBranch(0), pq.getBranch(1), pq.getBranch(0).info + pq.getBranch(1).info, pq.getBranch(0).priority + pq.getBranch(1).priority));
	        	pq.remove();
	        	pq.remove();
	        }
	        
	        pq.printBranch(pq.getBranch(0), 0);
	        
	        
			System.out.println("Done");
			
		} catch (IOException e) {
			System.out.println("An error occured");
			e.printStackTrace();
		}

	}

}
