package com.bayviewglen.zork;

/*
 * Parser reads user input
 * Checks input to see if it is a command
 * Returns interpreted command as an instance of the class Command
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Parser {
	private CommandWords commands; // holds all valid command words
	private final int maxWordCommandLen = 2;

	public Parser() {
		commands = new CommandWords();
	}

	public Command getCommand() {
		String input = ""; // holds input
		String mainCommandWord; // main
		ArrayList<String> params = new ArrayList<String>(); // holds parameters for the main word
		String[] words; // holds the words in the input
		System.out.print("->");

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		try { // attempting to read input
			input = reader.readLine();
		} catch (java.io.IOException e) {
			System.out.println("Error during reading: " + e.getMessage());
		}

		words = input.split(" ");

		String mainCommand = null;
		int paramStartIndex = 0; // at what index in words do the parameters start?
		String commandPhrase = "";

		// outer loop - # of words to take as the main command
		for (int i = 1; i <= Math.min(maxWordCommandLen, words.length); i++) {
			// adding words to command phrase
			for (int j = 0; j < i; j++) {
				commandPhrase += words[j];
			}

			mainCommand = commands.isCommand(commandPhrase);
			if (mainCommand != null) {
				paramStartIndex = i;
				break; // if the command isn't null (it has found a match), break out of loop
			}
		}

		for (int i = paramStartIndex; i < words.length; i++) {
			params.add(words[i]);
		}
		
		return new Command(mainCommand, (String[]) params.toArray());
	}

	/*
	 * public Command getCommand() { String inputLine = ""; // will hold the full
	 * input line String word1; String word2; System.out.print("> "); // print
	 * prompt BufferedReader reader = new BufferedReader(new
	 * InputStreamReader(System.in)); try { inputLine = reader.readLine(); } catch
	 * (java.io.IOException exc) {
	 * System.out.println("There was an error during reading: " + exc.getMessage());
	 * }
	 * 
	 * 
	 * 
	 * StringTokenizer tokenizer = new StringTokenizer(inputLine); if
	 * (tokenizer.hasMoreTokens()) word1 = tokenizer.nextToken(); // get first word
	 * else word1 = null; if (tokenizer.hasMoreTokens()) word2 =
	 * tokenizer.nextToken(); // get second word else word2 = null; // note: we just
	 * ignore the rest of the input line. // Now check whether this word is known.
	 * If so, create a command // with it. If not, create a "nil" command (for
	 * unknown command). if (commands.isCommand(word1)) return new Command(word1,
	 * word2); else return new Command(null, word2); }
	 */
	/**
	 * Print out a list of valid command words.
	 */
	public void showCommands() {
		commands.showAll();
	}
}
