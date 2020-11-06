import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Translator {
	
	public static HashMap<String, String> dict = new HashMap<String, String>();

	public Translator() throws IOException {
		
		BufferedReader in = new BufferedReader (new FileReader("EnglishToArabicDictionary.txt"));
		
		for(String line = in.readLine(); line!= null; line = in.readLine()) {
			
			String key = in.readLine();
			dict.put(line, key);
			
		}
		
		in.close();
		
		run();
	}
	
	public void run() {
		Scanner s = new Scanner(System.in);
		String entered = s.nextLine();
		
		while(entered != "q") {
			
			String trans = dict.get(entered);
			
			System.out.println("This word in Arabic is " + trans + "\n");
			
			entered = s.nextLine();
		}	
		
		
	}
	
	public static void main(String[] args) throws IOException {
		new Translator();
	}

}
