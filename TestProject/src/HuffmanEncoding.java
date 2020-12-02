import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
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
	         		System.out.println("hi");
	        		freq.put(String.valueOf(ch1), freq.get(String.valueOf(ch1)) + 1);
	        	} else {
	        		freq.put(String.valueOf(ch1), 1);
	        	}
	        }
	        
	        System.out.println(freq);
			System.out.println("Done");
			
		} catch (IOException e) {
			System.out.println("An error occured");
			e.printStackTrace();
		}

	}

}
