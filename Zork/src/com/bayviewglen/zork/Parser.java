package com.bayviewglen.zork;

/**
 * Parser reads user input
 * Checks input to see if it is a command
 * Returns interpreted command as an instance of the class Command
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

class Parser {
	private final int maxWordCommandLen = 2; // longest multi word command. EX: pick up = 2
	private final String prompt = ">>";

	public Parser() {

	}

	/**
	 * 
	 * @return the valid command
	 */
	public Command getCommand() {
		String input = ""; // holds input
		ArrayList<String> params = new ArrayList<String>(); // holds parameters for the main word
		String[] words; // holds the words in the input
		System.out.print(prompt);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		try { // attempting to read input
			input = reader.readLine();
		} catch (java.io.IOException e) {
			System.out.println("Error during reading: " + e.getMessage());
		}

		words = input.split(" "); // splitting input on space, extracting words

		String mainCommand = null;
		int paramStartIndex = -1; // at what index in words do the parameters start?
		String commandPhrase = "";

		// outer loop - # of words to take as the main command
		for (int i = 1; i <= Math.min(maxWordCommandLen, words.length); i++) {
			// adding words to command phrase
			if (i != 1) { // if it's not the first word, add a space
				commandPhrase += " ";
			}
			commandPhrase += words[i - 1]; // add the next word in the input


			
			String tmpCommand = CommandWords.isCommand(commandPhrase);
			if (tmpCommand != null) {
				// a command match is found, set the main command to the match, but do not exit loop
				// this allows the program to match as many words as possible ("give up" overrides "give")
				paramStartIndex = i;
				mainCommand = tmpCommand;
			}
		}

		if (paramStartIndex == -1) { // index not altered, command not found, no need to process parameters
			return new Command(null, null);
		}

		for (int i = paramStartIndex; i < words.length; i++) {
			params.add(words[i]);
		}
		

		// returning command object, casting params to an array of strings
		return new Command(mainCommand, params.toArray(new String[params.size()]));
	}

	/**
	 * Print out a list of valid command words.
	 */
	public void showCommands() {
		CommandWords.showAll();
	}
}
