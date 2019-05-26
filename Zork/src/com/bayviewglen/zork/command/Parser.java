package com.bayviewglen.zork.command;

/**
 * Parser reads user input
 * Checks input to see if it is a command
 * Returns interpreted command as an instance of the class Command
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.bayviewglen.zork.item.Item;

public class Parser {
	private int maxWordsInCommand = CommandWords.getMaxWordsInCommand();
	private final String prompt = ">>";
	private static Scanner scanner = new Scanner(System.in);

	public Parser() {
		
	}
	
	public ArrayList<Item> filterItems(ArrayList<Item> items, Class<Item>[] classes) {
		
		ArrayList<Item> temp = new ArrayList<Item>();
		
		for (Item i : items) {
			temp.add(i);
		}
		
		for (int i = 0; i < items.size(); i++) {
			int removeIndex = i;
			for (Class<Item> cls : classes) {
				try { // is the item one of the class types
					cls.cast(items.get(i)); // cast attempt
					removeIndex = -1; // cast successful, don't remove from items
				} catch (ClassCastException e) {
					
				}
			}
			if (removeIndex != -1) {
				items.remove(removeIndex); // item type doesn't match class type
			}
		}
		
		return items;
	}
	
	public ArrayList<Item> parseItems(ArrayList<Item> items, String str) {

		String[] words = str.split("\\W");
		ArrayList<Item> matches = new ArrayList<Item>();
		
		for (int i = 0; i < items.size(); i++) { // iterating over items
			Item item = items.get(i);
			for (int j = 0; j < words.length; j++) { // iterating over words
				boolean found = false;
				for (int k = 0; k < words.length - j; k++) { // how many words should the name be
					String matchName = "";
					for (int l = 0; l <= k; l++) { // adding words to matchName
						matchName += words[j + l];
						if (l != k)
							matchName += " "; // adding a space to create phrases
					}

					if (item.getName().compareToIgnoreCase(matchName) == 0) { // does the name match
						matches.add(item); // adding item to the matching items
						matchName = "";          // because it was mentioned in the string
						j += k; // skipping over j because word was added
						i++; // incrementing 
						found = true;
						break;
					}
				}
				if (found) {
					break;
				}
			}
		}
		return matches;
	}

	/**
	 * Reads and processes input into a Command object
	 * 
	 * @return a Command object
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
		for (int i = 1; i <= Math.min(maxWordsInCommand, words.length); i++) {
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
	
	public static void pressAnyKey() {
		scanner.nextLine();
	}
	
	public static String startGame() {
		String temp = scanner.nextLine();
		if(temp != null && temp.length() > 0 && temp.substring(0,1).equalsIgnoreCase("y"))
			return "You'll be regretting that soon. Let's play!";
		else
			return "I don't care what you say anyways. Let's play!";
	}
}
