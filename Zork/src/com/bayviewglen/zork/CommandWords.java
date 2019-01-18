package com.bayviewglen.zork;

import java.util.ArrayList;

/* This class is used for command word recognition */

class CommandWords {
	// a constant array that holds all valid command words

	// array list of array list of strings

	// outer array holds rows
	// inner array list holds main commands and alternate commands
	private static ArrayList<String>[] validCommands;

	// setting up file to read from
	private static final String commandFile = "data/main_command_words.txt";
	private static FileReader fReader = new FileReader(commandFile);

	/**
	 * Constructor - initialize the command words.
	 */
	public CommandWords() {
		String[] lines = fReader.getLines();

		// initializing valid commands arraylist array
		validCommands = (ArrayList<String>[]) new ArrayList[lines.length];

		for (int i = 0; i < lines.length; i++) {
			// splitting string on : or ,
			String[] phrases = lines[i].split("[,:]");
			
			validCommands[i] = new ArrayList<String>();

			// loading split phrases into valid commands
			for (int j = 0; j < phrases.length; j++) {
				validCommands[i].add(phrases[j]);
			}
		}
	}

	/**
	 * Check whether a given String is a valid command word. Return the main command
	 * word if it is, or null if it is false
	 **/
	public String isCommand(String str) {
		for (int i = 0; i < validCommands.length; i++) {
			for (int j = 0; j < validCommands[i].size(); j++) {
				if (validCommands[i].get(j).equals(str)) {
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
	public void showAll() {
		for (int i = 0; i < validCommands.length; i++) {
			System.out.print(validCommands[i].get(0) + "  "); // get main command word only
		}
		System.out.println();
	}
}
