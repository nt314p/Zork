package com.bayviewglen.zork.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * File Reader class
 * 
 * Takes a file path as a parameter
 * 
 * Returns all lines of a file as an array of strings
 * 
 * Only to be used with small files (less than 1000 lines)
 *
 */

public class FileReader {
	
	private Scanner reader;

	/**
	 * 
	 * @param filePath The path to the file to read in
	 */
	public FileReader (String filePath) {
		try {
			reader = new Scanner(new File(filePath));
		} catch (FileNotFoundException e) {
			System.out.println("File: " + filePath + " not found!");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method reads the defined file and returns the lines as a string array
	 * 
	 * @return An array in which each index holds one line of the file
	 * 
	 */
	public String[] getLines() {
		ArrayList<String> linesList = new ArrayList<String>();
		while (reader.hasNextLine()) {
			linesList.add(reader.nextLine());
		}
		
		String[] lines = new String[linesList.size()];
		
		for (int i = 0; i < lines.length; i++) {
			lines[i] = linesList.get(i);
		}
		return lines;
	}
	
	/**
	 * This method reads the defined file and returns the lines as a single string
	 * 
	 * @return A single string that consists of the whole file in one string with newlines
	 */
	public String getLinesSingle() {
		String[] lines = getLines();
		
		String ret = "";
		for (int i = 0; i < lines.length; i++) {
			ret += lines[i];
			if (i != lines.length - 1) // if the line isn't the last line,
				ret += "\n"; // add a new line
		}
		
		return ret;
	}
	
}
