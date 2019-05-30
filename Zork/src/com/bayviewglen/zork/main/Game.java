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

public class Game {

	private static Parser parser = new Parser();
	private static Player player;
	private static boolean gameOver = false;// the current game
	private static boolean isPlaying = true;// the overall game

	private static final String PROMPT_ENTER = "~";
	private static final String PROMPT_START_GAME = "`";
	private static final String ROCKET = "data/music/rocket.mp3";

	private static String[] failedCommands = { "You can't even do that.", "I don't understand what you want to say.",
			"Nope, you can't do that.", "Not possible. Try another command.",
			"That's not a command. Press 'c' to view all valid commands." };

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

	public static void play(String filePath) {
		Game.initializeGame(filePath);
		//print(intro());
		while (isPlaying) {
			Character.moveAll();
			System.out.println(Game.processCommand(parser.getCommand()));

			player.checkRoomDeath();

//			if (!gameOver)
//				System.out.println(displayTurn());
//			else {
			if (gameOver) {
				if (isPlaying || Parser.playAgain()) {
					Game.initializeGame(filePath);
					gameOver = false;
					isPlaying = true;
				} else
					isPlaying = false;
			}
		}
		print(outro());
	}

	public static void initializeGame(String filePath) {

		CommandWords.initialize();
		parser = new Parser();
		Preset.initialize();
		Inventory.initialize();
		Maps.initialize();
		Character.initialize();
		Link.initialize();

		Game.player = new Player(100, null, new Inventory(100),
				new Location("Ice Ice Baby", new Coordinate(0.5, 0.5, 0.5)));

		loadGame(filePath);
	}

	public static String processCommand(Command cmd) {

		if (cmd.getCommandWord() == null) {
			return Game.getRandom(failedCommands);
		}

		ArrayList<Item> interactableItems = new ArrayList<Item>();
		ArrayList<Item> pInteract = player.getInteractableItems().toArrayList();
		if (cmd.getParameters().length == 0 || !cmd.getParameters()[0].equals("all")) {
			pInteract = parser.parseItems(pInteract, cmd.toSingleString());
		}
		
		for (int i = 0; i < pInteract.size(); i++) {
			boolean found = false;
			for (int j = 0; j < interactableItems.size(); j++) {
				if (pInteract.get(i).equals(interactableItems.get(j))) {
					found = true;
				}
			}
			if (!found) {
				interactableItems.add(pInteract.get(i));
			}
		}
		
		String mainCmd = cmd.getCommandWord();

		Class cls = null;
		Object instance = null;
		Method method = null;
		ArrayList<Method> runMethods = new ArrayList<Method>();
		runMethods.add(null);
		runMethods.add(null);
		runMethods.add(null);

		String[] classNames = { "main.Game", "main.Player", "main.Character", "map.Door" }; // where the methods can
																							// run?

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
					runMethods.set(m.getParameterCount(), m);
				}
			}
		}

		if (cls == null) {
			return Game.getRandom(failedCommands);
		}
		if (runMethods.get(1) != null) {
			Class<Item>[] paramTypes = (Class<Item>[]) runMethods.get(1).getParameterTypes();
			interactableItems = parser.filterItems(interactableItems, paramTypes);
		}

		if (cls.getSimpleName().equals("Door")) {
			Class<Item>[] doorClsArr = new Class[1];
			doorClsArr[0] = cls;
			ArrayList<Item> commandableItems = parser.filterItems(interactableItems, doorClsArr);

			if (commandableItems.size() != 1) { // there should only be one door
				return "Please be more specific.";
			}

			instance = commandableItems.get(0);
		}

		if (cls.getSimpleName().equals("Player")) {
			instance = player;
		}

		if (cls.getSimpleName().equals("Game")) {
			instance = null;
		}

		int startIndex = 0;
		if (runMethods.get(1) != null && interactableItems.size() != 0) {
			startIndex = 1;
		}
		if (runMethods.get(1) != null && runMethods.get(0) == null && interactableItems.size() == 0) {
			return "Please be more specific.";
		}

		if (runMethods.get(2) != null && interactableItems.size() != 2) {
			return "Please be more specific.";
		}

		for (int i = startIndex; i < runMethods.size(); i++) {
			if (runMethods.get(i) != null) {
				try {
					return (String) runMethods.get(i).invoke(instance);

				} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException
						| IndexOutOfBoundsException ea) {
				}
				if (interactableItems.size() == 1) {
					for (int j = 0; j < interactableItems.size(); j++) {
						try {
							return (String) runMethods.get(i).invoke(instance, interactableItems.get(j));
						} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException
								| IndexOutOfBoundsException ea) {
						}
					}
				} else if (interactableItems.size() == 2) {
					for (int j = 0; j < interactableItems.size() - 1; j++) {
						ArrayList<Item> args = new ArrayList<Item>();
						for (int k = j + 1; k < interactableItems.size(); k++) {
							args.add(interactableItems.get(j));
							args.add(interactableItems.get(k));
							try {
								return (String) runMethods.get(i).invoke(instance, args);
							} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException
									| IndexOutOfBoundsException ea) {
							}
						}
					}
				}
			}
		}
		return Game.getRandom(failedCommands);
	}

	public static void setGameOver(boolean playWithoutAsking) {
		gameOver = true;
		isPlaying = playWithoutAsking;
	}

	public static boolean gameOver() {
		return gameOver;
	}

	public static String displayTurn() {

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
			String s = directionWords.get(i + "") + ", ";
			s = s.substring(0, 1).toUpperCase() + s.substring(1);
			exits += s;

		}
		if (exits.indexOf(",") == -1)// nothing added
			exits += "None";
		else
			exits = exits.substring(0, exits.length() - 2);// remove ", " at end
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

	public static String getRandom(String[] strings) {
		int randIndex = (int) (Math.random() * strings.length);
		return strings[randIndex];
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

	public static String help() {
		String result = "";
		result += "Use 'c' to display all commands." + PROMPT_ENTER;
		result += "Write a command to play your turn." + PROMPT_ENTER;
		result += "Use cardinal directions to traverse the map." + PROMPT_ENTER;
		result += "Collect keys to open doors and hidden rooms." + PROMPT_ENTER;
		result += "Make it safely back to Earth to win.";
		return result;
	}

	public static String outro() {
		String result = "";
		result += "Press 'enter' to continue." + PROMPT_ENTER;
		result += "Congrats, you made it to the escape pod!" + PROMPT_ENTER;
		result += "You can now steer your way back to Earth and live again!" + PROMPT_ENTER;
		result += "Although it is great to return, you have learned so much through this journey..." + PROMPT_ENTER;
		result += "You have learned how to persevere" + PROMPT_ENTER;
		result += "How to accomplish tasks with grit" + PROMPT_ENTER;
		result += "And most importantly..." + PROMPT_ENTER;
		result += "How to solve slide puzzles." + PROMPT_ENTER;
		result += "\nThe AIs will miss you as you embark on this new chapter of your life." + PROMPT_ENTER;
		result += "Best of luck on your descent" + PROMPT_ENTER;
		result += "And thank you for your service at the space station." + PROMPT_ENTER;
		result += "The world wouldn't be the same without you." + PROMPT_ENTER;
		result += "\n\n" + credits();
		return result;
	}

	public static String credits() {
		String result = "";
		result += "Thank you for playing Space Zork." + PROMPT_ENTER;
		result += "We hope you enjoyed." + PROMPT_ENTER;
		result += "Ending statistics:\n" + Game.displayStatistics() + "" + PROMPT_ENTER;
		result += "\n\nDevelopers:" + PROMPT_ENTER + "\tEthan Elbaz" + PROMPT_ENTER + "\tBen Merbaum" + PROMPT_ENTER
				+ "\tNick Tong" + PROMPT_ENTER;
		result += "Space awaits you..." + PROMPT_ENTER;
		result += "\n\"This is one small step for man..." + PROMPT_ENTER;
		result += "\none giant leap for manking\"" + PROMPT_ENTER;
		result += "\t- Neil Armstrong";
		return result;
	}

	public static String storyline() {
		String result = "";
		result += "Welcome to space." + PROMPT_ENTER;
		result += "You don't know who you are, what you are, or where you are." + PROMPT_ENTER;
		result += "Are you in hell? Even hell sounds like a vacation to this place." + PROMPT_ENTER;
		result += "All you can see are the stars, but even those look like a ball of fire\n" + "ready to attack."
				+ PROMPT_ENTER;
		result += "You fly through the universe, or are you even in the universe." + PROMPT_ENTER;
		result += "Who really cares anyway, because you're just going to die." + PROMPT_ENTER;
		result += "You open your eyes to a room." + PROMPT_ENTER;
		result += "A room!" + PROMPT_ENTER;
		result += "But where is there a room in the middle of space?" + PROMPT_ENTER;
		result += "Maybe there will be an escape pod somewhere near..." + PROMPT_ENTER;
		result += "To finally get out of this stupid hellhole." + PROMPT_ENTER;
		result += "It's time to find out and go explore.";
		return result;
	}

	public static String intro() {
		String result = "";
		result += ROCKET;
		result += "Press 'enter' to continue." + PROMPT_ENTER;
		result += "Welcome to Space Zork, a completely dynamic adventure game." + PROMPT_ENTER;
		result += "WARNING: Zork has the potential to induce seizures\n"
				+ "for people with photosensitive epilepsy. If you have photosensitive epilepsy\n"
				+ "or feel you may be susceptible to a seizure, DO NOT PLAY ZORK.\n" + "You have been warned.\n"
				+ PROMPT_ENTER;

		result += "HOW TO PLAY\n";
		result += "-----------\n" + PROMPT_ENTER;
		result += help() + "\n" + PROMPT_ENTER;
		result += "Good luck on your adventure. Try not to die." + PROMPT_ENTER;
		result += "But if you do, that's okay as well." + PROMPT_ENTER;
		result += "Just do your best. Because that's all that matters. Right?" + PROMPT_ENTER;
		result += "And try not to rage. It'll be hard, I know." + PROMPT_ENTER;
		result += "Just keep on trying" + PROMPT_ENTER;
		result += "and trying" + PROMPT_ENTER;
		result += "and trying\n" + PROMPT_ENTER;
		result += "And remember: you can quit whenever you feel like it." + PROMPT_ENTER;
		result += "You just won't get the satisfaction of knowing that you beat Zork." + PROMPT_ENTER;
		result += "Shall we get started? ";
		result += PROMPT_START_GAME;
		result += "\n\n" + storyline() + "\n" + PROMPT_ENTER;
		return result;
	}

	public static String commands() {
		return CommandWords.showAll();
	}

	public static void print(String message) {
		String[] lines = message.split(PROMPT_ENTER);
		for (String s : lines) {

			if (s.indexOf(PROMPT_START_GAME) != -1) {
				System.out.print(s.substring(0, s.indexOf(PROMPT_START_GAME)));
				System.out.println(Parser.startGame());
				continue;
			}

			if (s.indexOf(ROCKET) != -1) {
				System.out.print(s.substring(0, s.indexOf(ROCKET)));
				Music.play(ROCKET);
			}

			if (s.indexOf("SlidePuzzle.play()") != -1) {
				boolean gameWon = SlidePuzzle.play();
				if (gameWon)
					MapDisplay.display(player.getLocation().getMap());
			}

			System.out.print(s);
			Parser.pressAnyKey();
		}
	}

	public static String checkSlidePuzzle() {
		String str = "";
		if (player.getLocation().getRoom().getName().equals("map room")) {
			str += "Press 'enter' to continue." + PROMPT_ENTER;
			str += "Welcome to the Slide Puzzle Mini-Game." + PROMPT_ENTER;
			str += "In front of you stands the Golden Map." + PROMPT_ENTER;
			str += "This map gives you access to view all the rooms and corridors of this floor." + PROMPT_ENTER;
			str += "This is the ultimate prize." + PROMPT_ENTER;
			str += "Your opportunity to traverse the ship and finally make it out of space." + PROMPT_ENTER;
			str += "\nAnd all you have to do" + PROMPT_ENTER;
			str += "Is defeat this Slide Puzzle." + PROMPT_ENTER;

			str += "\nHOW TO PLAY";
			str += "\n-----------" + PROMPT_ENTER;

			str += "UP: 'w'" + PROMPT_ENTER;
			str += "DOWN: 's'" + PROMPT_ENTER;
			str += "LEFT: 'a'" + PROMPT_ENTER;
			str += "RIGHT: 'd'" + PROMPT_ENTER;
			str += "To quit, type 'quit'." + PROMPT_ENTER;
			str += "To shuffle, type 'shuffle'." + PROMPT_ENTER;
			str += "\nOrder the numbers in row-major order to win." + PROMPT_ENTER;
			str += "Achieve this, and the Golden Map is all yours." + PROMPT_ENTER;
			str += "Ready?" + PROMPT_START_GAME;
			str += "SlidePuzzle.play()";
		}
		return str;

	}

	public static void die() {
		Game.setGameOver(false);
		String result = "";
		result += "Noooo, don't die... (press enter)\n" + PROMPT_ENTER;
		result += "You're too young for this...\n" + PROMPT_ENTER;
		result += "Your family misses you...\n" + PROMPT_ENTER;
		result += "Please don't fade away...\n" + PROMPT_ENTER;
		result += "...\n" + PROMPT_ENTER;
		result += "..\n" + PROMPT_ENTER;
		result += ".";
		print(result);
	}

}