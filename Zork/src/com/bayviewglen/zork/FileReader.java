package com.bayviewglen.zork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
	
	private Scanner reader;
	
	public FileReader (String filePath) {
		try {
			reader = new Scanner(new File(filePath));
		} catch (FileNotFoundException e) {
			System.out.println("File: " + filePath + " not found!");
			e.printStackTrace();
		}
	}
	
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
	
}
