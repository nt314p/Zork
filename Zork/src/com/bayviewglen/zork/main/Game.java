package com.bayviewglen.zork.main;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.json.JSONObject;

import com.bayviewglen.zork.command.*;
import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

/**
 * 
 * @authors bmerbaum, ntong, eelbaz
 *
 */

public class Game implements GameCommands {

	private static Parser parser = new Parser();
	private static Player player;
	private static boolean gameOver = false;
	private static boolean isPlaying = true;

	private static int turn = 0;
	
	public static void initializeGame(String filePath, Player player) {
		
		parser = new Parser();
		CommandWords.initialize();
		Preset.initialize();
		Inventory.initialize();
		Maps.initialize();
		Link.initialize();
		loadGame(filePath);
		
		Game.player = player;
	//	player = new Player(100, null, new Inventory(),
		//		new MoveableLocation("Ice Ice Baby", new Coordinate(0.5, 0.5, 0.5)));

	}

	public static void processCommand(Command cmd) {
		
		ArrayList<Item> interactableItems = player.getInteractableItems().toArrayList();
		ArrayList<Item> commandableItems = parser.parseItems(interactableItems, cmd.toSingleString());

		String mainCmd = cmd.getCommandWord();
		mainCmd = mainCmd.substring(0, 1).toUpperCase() + mainCmd.substring(1); // capitalizing
		
		/* NEW - IMPLEMENT
		for (all commandable items)
			try to run main command on the current item
			try all the other items as parameters	
		 */
		
		Class<Item> cls = null;

		try {
			cls = (Class<Item>) Class.forName("com.bayviewglen.command." + mainCmd + "able");
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find class" + mainCmd + "able");
			e.printStackTrace();
		} catch (ClassCastException e) {
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
	
	public static void setGameOver(boolean playWithoutAsking) {
		gameOver = true;
		isPlaying = playWithoutAsking;
	}
	
	public static boolean gameOver() {
		return gameOver;
	}

	public static String doTurn() {
		if(gameOver) {
			return "Game is over.";
		}
		Maps.getMap("Ice Ice Baby");
		Location currLoc = player.getLocation();
		String roomDesc = currLoc.getMap().getRoom(currLoc.getCoords()).getLongDescription();

		
		String ret = displayStatistics();

		ret += "\nLOCATION:";
		ret += "\n---------";
		ret += "\n" + roomDesc + "\n" + displayExits() + "\n";
		return ret;
	}
	
	public static String displayExits() {
		Location currLoc = player.getLocation();
		String exits = "Exits: ";
		for (java.lang.Character i : currLoc.getMap().getExits(currLoc.getCoords())) {
			exits += DirectionHelper.toString(i) + ", ";
			
		}
		if (exits.indexOf(",")==-1)//nothing added
			exits += "None";
		else
			exits = exits.substring(0, exits.length() - 2);// remove ", " at end
		exits = exits.substring(0, exits.length() - 2);
		return exits;
	}
	
	public static String displayStatistics() {
		String healthMonitor = "Health" + player.getHealthMonitor().toString();
		String foodMonitor = "Food" + player.getFoodMonitor().toString();
		String waterMonitor = "Water" + player.getWaterMonitor().toString();

		ArrayList<String> inventoryLines = player.getInventory().toStringArray();
		while(inventoryLines.size()<3){
			inventoryLines.add("");
		} // give inventoryLines at least 3 to work with for-loop (health, food, water)

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
		return ret;
	}

	public static int getTurn() {
		return turn;
	}

	public static void incrementTurn() {
		turn++;
	}

	public static void setTurn(int turn) {
		Game.turn = turn;
	}

//	public ArrayList<Character> getCharacters() {
//		return characters;
//	}


	public static Player getPlayer() {
		return player;
	}

	public static void loadGame(String filePath) {
		FileReader reader = new FileReader(filePath);
		JSONObject obj = new JSONObject(reader.getLinesSingle());
		player.setLocation((MoveableLocation) Location.loadLocation(obj.getJSONObject("playerStart")));
	}


}