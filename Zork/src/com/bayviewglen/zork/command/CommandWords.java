package com.bayviewglen.zork.command;
import java.util.ArrayList;
import java.util.HashMap;

import com.bayviewglen.zork.main.FileReader;

/* This class is used for command word recognition */

public class CommandWords {
	// a constant array that holds all valid command words

	// array list of array list of strings

	// outer array holds rows
	// inner array list holds main commands and alternate commands
	private static ArrayList<String>[] validCommands;

	// setting up file to read from
	private static final String commandFile = "data/main_command_words.txt";
	private static FileReader fReader = new FileReader(commandFile);
	
	private static final int [] COMMAND_WORDS_BREAKS = {6,10,17,20,25,31};
	
	private static int maxWordsInCommand = 0;

	/**
	 * Constructor - nothing to see here.
	 */
	public CommandWords() {
		
	}
	
	/**
	 * initialize the command words into an arrayList
	 */
	@SuppressWarnings("unchecked")
	public static void initialize() {
		String[] lines = fReader.getLines();

		// initializing valid commands ArrayList array
		validCommands = (ArrayList<String>[]) new ArrayList[lines.length];
		for (int i = 0; i < lines.length; i++) {
			// splitting string on : or ,
			String[] phrases = lines[i].split("[,:]");
			
			validCommands[i] = new ArrayList<String>();

			// loading split phrases into valid commands
			for (int j = 0; j < phrases.length; j++) {
				String currentCommand = phrases[j];
				int wordsInCommand = currentCommand.split(" ").length;
				if (wordsInCommand > maxWordsInCommand) {
					maxWordsInCommand = wordsInCommand; // setting max words in command
				}	
				validCommands[i].add(currentCommand);
			}
		}
	}

	/**
	 * Check whether a given String is a valid command word. Return the main command
	 * word if it is, or null if it is false
	 * 
	 * @param str The string name of the command to check
	 * @return A string of the main command name if the command is found, else returns null
	 * 
	 */
	public static String isCommand(String str) {
		for (int i = 0; i < validCommands.length; i++) {
			for (int j = 0; j < validCommands[i].size(); j++) {
				if (validCommands[i].get(j).equalsIgnoreCase(str)) {
					// main command resides at validCommands[anyindex].get(0)
					// located at the first index (0)
					return validCommands[i].get(0);
				}
			}
		}
		// if we get here, the string was not found in the commands
		return null;
	}

	/**
	 * Print all valid commands to System.out.
	 */
	public static String showAll() {
		String result = "";
		for (int i = 0; i < validCommands.length; i++) {
			String s = validCommands[i].get(0);
			
			for(int j = 1; j<s.length(); j++) {
				if(s.charAt(j)>='A' && s.charAt(j)<= 'Z') {
					s = s.substring(0,j) + " " + s.substring(j);
					j++;
				}
				
			}
			
			s = s.substring(0, 1).toUpperCase() + s.substring(1);
			if(isCommandWordBreak(i))
				s = "\n" + s;
			String d = validCommands[i].get(validCommands[i].size() - 1);
			result += s + ": " + d + "\n";// get main command word only
		}
		return result;
	}
	
	/**
	 * returns the maxWordsInCommand
	 */
	public static int getMaxWordsInCommand() {
		return maxWordsInCommand;
	}
	
	public static boolean isCommandWordBreak(int num) {
		for(int i:COMMAND_WORDS_BREAKS) {
			if(i == num)
				return true;
		}
		return false;
	}
}