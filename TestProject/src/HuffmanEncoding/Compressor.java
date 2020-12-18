package HuffmanEncoding;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Compressor {

	public static HashMap<String, Integer> freq = new HashMap<String, Integer>();
	public static HashMap<String, String> codes = new HashMap<String, String>();
	
	public Compressor () {
		
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
	        
	        getCode(pq.getBranch(0), String.valueOf(0));
	       
	        BufferedBitWriter compressed = new BufferedBitWriter("compressed.txt");
	        
	        for (char ch1 : ch){
	        	for(char ch2 : codes.get(String.valueOf(ch1)).toCharArray()) {
	        		int i = Character.getNumericValue(ch2);
	        		if(i == 0) {
	        			compressed.writeBit(false);
	        		} else {
	        			compressed.writeBit(true);
	        		}
	        	}
	        }
	        
	        compressed.close();
	        
	        codeMap();
	        
			System.out.println("Done");
			
		} catch (IOException e) {
			System.out.println("An error occured");
			e.printStackTrace();
		}

	}
	
	public void codeMap() throws IOException {
		try {
			File codeMap = new File("codes.txt");
			if (codeMap.createNewFile()) {
		    	FileWriter myWriter = new FileWriter("codes.txt");  
		  		for(Entry<String,String> entry : codes.entrySet()) {
					myWriter.write(entry.getKey() + "\n");
					myWriter.write(entry.getValue() + "\n");
				}
		  		
		  		myWriter.close();
		    	  
		      } else {
		        System.out.println("File already exists.");
		      }
		} catch (IOException e) {
		    System.out.println("An error occurred.");
		    e.printStackTrace();
	   }
	
	}
	
	public void getCode(Branch head, String code) {
		
		if(head.isLeaf) {
			codes.put(head.info, code);
		} else {
			getCode(head.child1, code + "" + 0);
			getCode(head.child2, code + "" + 1);
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		new Compressor();
	}

}
