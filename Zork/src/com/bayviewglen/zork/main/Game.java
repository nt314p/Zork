package com.bayviewglen.zork.main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

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

	public static final HashMap<String, String> directionWords = new HashMap<String, String>() {
		{

			put("north", "n");
			put("south", "s");
			put("east", "e");
			put("west", "w");
			put("up", "u");
			put("down", "d");
			put("n", "north");
			put("s", "south");
			put("e", "east");
			put("w", "west");
			put("u", "up");
			put("d", "down");
		}
	};
	private static int turn = 0;

	public static void initializeGame(String filePath) {

		parser = new Parser();
		CommandWords.initialize();
		Preset.initialize();
		Inventory.initialize();
		Maps.initialize();
		Link.initialize();

		Game.player = new Player(100, null, new Inventory(),
				new Location("Ice Ice Baby", new Coordinate(0.5, 0.5, 0.5)));

		loadGame(filePath);
	}

	public static String processCommand(Command cmd) {

		ArrayList<Item> interactableItems = player.getInteractableItems().toArrayList();

		String mainCmd = cmd.getCommandWord();

		Class cls = null;
		Object instance = null;
		Method method = null;

		String[] classNames = { "main.Game", "main.Player", "map.Door" }; // where the methods can run?

		for (String className : classNames) {
			Class tempCls = null;
			try {
				tempCls = Class.forName("com.bayviewglen.zork." + className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Method[] methods = tempCls.getMethods();
			for (Method m : methods) {
				if (m.getName().equals(mainCmd)) {
					cls = tempCls;
					method = m;
				}
			}
		}

		if (cls == null) {
			System.out.println("The command method was not found! Does the command txt have it?");
			double n = 0 / 0;
		}

		if (cls.getSimpleName().equals("Door")) {
			Class<Item>[] doorClsArr = new Class[1];
			doorClsArr[0] = cls;
			ArrayList<Item> commandableItems = parser.filterItems(interactableItems, doorClsArr);

			if (commandableItems.size() != 1) { // there should only be one door
				return "Please be more specific.";
			}

			instance = commandableItems.get(0);

			Class<Item>[] paramTypes = (Class<Item>[]) method.getParameterTypes();
			interactableItems = parser.filterItems(interactableItems, paramTypes);

			if (interactableItems.size() != 1) { // no command has no more than one parameter
				return "Please be more specific.";
			}
		}

		if (cls.getSimpleName().equals("Player")) {
			instance = player;
		}

		if (cls.getSimpleName().equals("Game")) {
			instance = null;
		}

		try {
			return (String) method.invoke(instance, interactableItems.get(0));
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException
				| IndexOutOfBoundsException e) {
			try {
				return (String) method.invoke(instance);
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException
					| IndexOutOfBoundsException ea) {
				try {
					return (String) method.invoke(interactableItems.get(0));
				} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException
						| IndexOutOfBoundsException eb) {
					try {
						return (String) method.invoke(null);
					} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException
							| IndexOutOfBoundsException ec) {
						return "You can't even do that.";
					}
				}
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
		if (gameOver) {
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
			exits += directionWords.get(i + "");

		}
		if (exits.indexOf(",") == -1)// nothing added
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
		while (inventoryLines.size() < 3) {
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
		Location loc = Location.loadLocation(obj.getJSONObject("playerStart"));
		player.setLocation(loc);
	}

}