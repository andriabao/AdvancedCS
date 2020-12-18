package HuffmanEncoding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Decompressor {
	
	public static HashMap<String, String> codes = new HashMap<String, String>();
	
	public Decompressor() throws IOException{
		
		BufferedReader fr = new BufferedReader (new FileReader( new File("codes.txt")));
		
		String line = fr.readLine();
		while(line != null) {
			String nextLine = fr.readLine();
			if(line != null) {
				codes.put(nextLine, line);
			}
			line = fr.readLine();
		}
			
		fr.close();
				
		BufferedBitReader reader = new BufferedBitReader("compressed.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter("decompressed.txt"));
		String code = "";
		
		while (reader.hasNext() == true) {
			Boolean b = reader.readBit();
			
			code += Integer.toString(b?1:0);
			
			if (codes.get(code) != null) {
				writer.write(codes.get(code));
				code = "";
			}
		}
		
		reader.close();
		writer.close();
		
		 if (new File("codes.txt").delete()) {
			 System.out.println("Done");
		 } else {
		      System.out.println("Failed to delete the file.");
		 } 
	
	}
	
	public static void main(String[] args) throws IOException {
		new Decompressor();
	}
}
