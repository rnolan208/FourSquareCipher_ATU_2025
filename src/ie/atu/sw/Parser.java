package ie.atu.sw;

import java.net.*;
import java.io.*;

public class Parser {
	//----------------------------
	//this is for reading in files
	//----------------------------
	public static String readFromFile(String filePath ) throws Exception {
		
		//Create a File object from the given file path
		File file = new File(filePath);
		
		//Opens a stream to the file and wraps it in a BufferedReader 
		//to read file line by line
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		//Use Stringbuilder to build the complete text from the file 
		//without creating a number of objects
		StringBuilder sb = new StringBuilder();
		String line;
		
		//While loop reads through the file content one line at a time 
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
	    }
		
		//closes the contents of the BufferReader to keep system resources free
		br.close();
					
		//Returns contents of file to a string from input into cipher
		return sb.toString();
	}
	
	
	//---------------------------------
	//this is for reading in from a URL
	//---------------------------------
	public static String readFromURL(String urlString) throws Exception {
			
		//Create a URI object from the given URL String 
		//A URI avoids the Java 20+ deprecated warning
		URI uri = URI.create(urlString);
	    URL url = uri.toURL();
			
		//Opens a stream to the URL and wraps it in the InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			
		//Use Stringbuilder to build the complete text from the URL 
		//without creating a number of objects
		StringBuilder sb = new StringBuilder();
		String line;
			
		//While loop reads through the URL content one line at a time 
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
			
		//closes the contents of the BufferReader to keep system resources free
		br.close();
			
		//Returns contents of URL to a string from input into cipher
		return sb.toString();
	}
	

	
	//--------------------------------------------------
	//this is to download a file from a URL (not needed)
	//--------------------------------------------------
	/*
	public static void download(URL url, String fileName) throws Exception {
		InputStream in = url.openStream();
		
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));
		byte[] chunk = new byte[4096];
		int byteRead;
		
		while ((byteRead = in.read(chunk)) > 0) {
			out.write(chunk, 0, byteRead);
		}
		out.flush();
		out.close();
		in.close();
	}
	*/
	
}
