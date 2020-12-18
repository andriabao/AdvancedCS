package HuffmanEncoding;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

 
public class BufferedBitWriter {
	private byte currentByte;     		
	private int numBitsWritten;  			
	public static int maxBytes = 1000000000;
	private int totalBytes;					
	private BufferedOutputStream output; 	

	public BufferedBitWriter(String pathName) throws FileNotFoundException {
		currentByte = 0;
		numBitsWritten = 0;
		totalBytes = 0;
		output = new BufferedOutputStream(new FileOutputStream(pathName));
	}

	public void writeBit(boolean bit) throws IOException {
		numBitsWritten++;
		
		currentByte |= (bit?1:0) << (8 - numBitsWritten);
		
		if(numBitsWritten == 8) { 
			output.write(currentByte);
			numBitsWritten = 0;
			currentByte = 0;
			totalBytes++;
			
			if (totalBytes >= maxBytes) 
				throw new IOException("file overflow - do you have an infinite loop??");
		}
	}

	public void close() throws IOException {
		output.write(currentByte);
		output.write(numBitsWritten);
		output.close();
	}
}