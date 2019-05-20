package com.bayviewglen.zork.main;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bayviewglen.zork.command.*;
import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

/**
 * 
 * @authors bmerbaum, ntong, eelbaz
 *
 */

public class Game {

	private Parser parser;
	private Player player;
	private ArrayList<Phase> phases;

	private int turn;

	public Game() {
		parser = new Parser();
		phases = new ArrayList<Phase>();
		CommandWords.initialize();
		Preset.initialize();
		Inventory.initialize();
		loadGame("data/game.json");
		player = new Player(100, null, new Inventory(),
				new MoveableLocation("Ice Ice Baby", new Coordinate(0.5, 0.5, 0.5)));

		turn = 0;
	}

	public void processCommand(Command cmd) {
		ArrayList<Item> interactableItems = player.getInteractableItems().toArrayList();

		String[] params = cmd.getParameters();
		ArrayList<Item> commandableItems = new ArrayList<Item>();
		for (int a = 0; a < interactableItems.size(); a++) { // iterating over items
			Item i = interactableItems.get(a);
			for (int j = 0; j < params.length; j++) { // iterating over parameters
				boolean found = false;
				for (int k = 0; k < params.length - j; k++) { // how many words should the name be
					String matchName = "";
					for (int l = 0; l <= k; l++) { // adding parameters to matchName
						matchName += params[j + l];
						if (l != k)
							matchName += " "; // adding a space to create phrases
					}

					if (i.getName().compareToIgnoreCase(matchName) == 0) { // does the name match
						commandableItems.add(i); // adding item to commandable items
						matchName = "";          // because it was mentioned in the command
						j += k; // skipping over j because word was added
						a++; // incrementing 
						found = true;
						break;
					}
				}
				if (found) {
					break;
				}
			}
		}

		Class cls = null;
		String mainCmd = cmd.getCommandWord();
		mainCmd = mainCmd.substring(0, 1).toUpperCase() + mainCmd.substring(1); // capitalizing
		
		try {
			cls = Class.forName("com.bayviewglen.command." + mainCmd + "able");
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find class" + mainCmd + "able");
			e.printStackTrace();
		}
		
		Item commandItem = null;
		ArrayList<Item> otherParamItems = new ArrayList<Item>();
		for (int i = 0; i < commandableItems.size(); i++) {
			Item temp = commandableItems.get(i);
			try {
				cls.cast(temp);
				commandItem = temp;
			} catch (ClassCastException e) {
				otherParamItems.add(temp);
				// command does not implement the command interface
			}
		}
				
		java.lang.reflect.Method method = null;
		
		try {
			method = commandItem.getClass().getMethod(mainCmd);
		} catch (NoSuchMethodException e) {
			for (int i = 0; i < otherParamItems.size(); i++) { // checking which parameters it fits
				try {
					method = commandItem.getClass().getMethod(mainCmd, otherParamItems.get(i).getClass());
				} catch (NoSuchMethodException ex) {
					otherParamItems.remove(i); // removing incorrect parameters
					i--;
				}
			}
		}
		
		if (method != null) {
			try {
				method.invoke(commandItem, otherParamItems.get(0)); // there should be one param left
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace(); // What? The command doesn't exist!
			} 
		}
	}

	public String doTurn() {
		Map.getMap("Ice Ice Baby");
		Location currLoc = player.getLocation();
		String roomDesc = currLoc.getMap().getRoom(currLoc.getCoords()).getLongDescription();

		String exits = "Exits: ";
		boolean added = true;
		for (java.lang.Character i : currLoc.getMap().getExits(currLoc.getCoords())) {
			switch (i) {
			case 'n':
				exits += " North, ";
			case 's':
				exits += " South, ";
			case 'e':
				exits += " East, ";
			case 'w':
				exits += " West, ";
			case 'u':
				exits += " Up, ";
			case 'd':
				exits += " Down, ";
			default:
				added = false;
			}
		}
		if (added)
			exits += "None";
		else
			exits = exits.substring(0, exits.length() - 2);// remove ", "
		exits = exits.substring(0, exits.length() - 2);

		String healthMonitor = "Health" + player.getHealthMonitor().toString();
		String foodMonitor = "Food" + player.getFoodMonitor().toString();
		String waterMonitor = "Water" + player.getWaterMonitor().toString();

		ArrayList<String> inventoryLines = player.getInventory().toStringArray();
		for (int i = 0; inventoryLines.size() < 3; i++) {
			inventoryLines.add("");
		} // give inventoryLines at least 3 to work with for-loop

		int i = 0;
		String ret = String.format("%-20s%s", "STATISTICS", "INVENTORY");
		ret += String.format("\n%-20s%s", "----------", "----------");
		ret += String.format("\n%-20s%s", healthMonitor, inventoryLines.get(i));
		i++;
		ret += String.format("\n%-20s%s", foodMonitor, inventoryLines.get(i));
		i++;
		ret += String.format("\n%-20s%s", waterMonitor, inventoryLines.get(i));
		i++;

		while (i < inventoryLines.size()) {
			ret += String.format("\n%-20s%s", "", inventoryLines.get(i));
			i++;
		}

		ret += "\nLOCATION:";
		ret += "\n---------";
		ret += "\n" + roomDesc + "\n" + exits + "\n";
		return ret;
	}

	public int getTurn() {
		return turn;
	}

	public void incrementTurn() {
		turn++;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

//	public ArrayList<Character> getCharacters() {
//		return characters;
//	}

	public ArrayList<Phase> getPhases() {
		return phases;
	}

	public Player getPlayer() {
		return player;
	}

	public void loadGame(String filePath) {
		FileReader reader = new FileReader(filePath);

		JSONObject obj = new JSONObject(reader.getLinesSingle());
		JSONArray textPhases = obj.getJSONArray("phases");

		for (int i = 0; i < textPhases.length(); i++) {
			phases.add(Phase.getPhase(textPhases.getString(i)));
		}

		player.setLocation((MoveableLocation) Location.loadLocation(obj.getJSONObject("playerStart")));
	}

}