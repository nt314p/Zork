package com.bayviewglen.zork;

import java.util.ArrayList;

/* This class is used for command word recognition */

class CommandWords {
	// a constant array that holds all valid command words
	private static String validCommands[];
	
	// setting up file to read from
	private static final String commandFile = "data/primary_command_words.txt";
	private static FileReader fReader = new FileReader(commandFile);

	/**
	 * Constructor - initialise the command words.
	 */
	public CommandWords() {
		String[] lines = fReader.getLines();
		ArrayList<String> commands = new ArrayList<String>();
		
		for (int i = 0; i < lines.length; i++) {
			// spliting string on : or ,
			String[] phrases = lines[i].split("[,:]");
			for (String s : phrases) {
				commands.add(s); // adding phrases to command list
			}
		}
		
		validCommands = commands.toArray(validCommands); // turning command list into array
	}

	/**
	 * Check whether a given String is a valid command word. Return the main command word
	 * if it is, or -1 if it is false
	 **/
	public boolean isCommand(String aString) {
		for (int i = 0; i < validCommands.length; i++) {
			if (validCommands[i].equals(aString))
				return true;
		}
		// if we get here, the string was not found in the commands
		return false;
	}

	/*
	 * Print all valid commands to System.out.
	 */
	public void showAll() {
		for (int i = 0; i < validCommands.length; i++) {
			System.out.print(validCommands[i] + "  ");
		}
		System.out.println();
	}
}
