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
	private static boolean gameOver = false;//the current game
	private static boolean isPlaying = true;//the overall game

	private static ArrayList<String> characters;//WE NEED TO DO SOMETHING WITH THIS

	private static final String PROMPT_ANY_KEY = "~";
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
		print(intro());
		while (isPlaying) {
			for(int i = 0; i<characters.size(); i++) {
				Character.getCharacter(characters.get(i)).move();
			}
			Character.moveAll();
			System.out.println(Game.processCommand(parser.getCommand()));
			
			player.checkRoomDeath();
			System.out.println(player.checkAirlockDeath());
			System.out.println(player.checkDeath());
			
			print(checkSlidePuzzle());
			
			if(!gameOver)
				System.out.println(displayTurn());
			else {
				if(isPlaying || Parser.playAgain()) {
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

		parser = new Parser();
		CommandWords.initialize();
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

		ArrayList<Item> interactableItems = player.getInteractableItems().toArrayList();
		if (cmd.getParameters().length == 0 || !cmd.getParameters()[0].equals("all")) {
			interactableItems = parser.parseItems(interactableItems, cmd.toSingleString());
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
						for(int k = j + 1; k < interactableItems.size(); k++) {
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
		result += "Use 'c' to display all commands." + PROMPT_ANY_KEY;
		result += "Write a command to play your turn." + PROMPT_ANY_KEY;
		result += "Use cardinal directions to traverse the map." + PROMPT_ANY_KEY;
		result += "Collect keys to open doors and hidden rooms." + PROMPT_ANY_KEY;
		result += "Make it safely back to Earth to win.";
		return result;
	}

	public static String outro() {
		String result = "";
		result += "Press any key to continue." + PROMPT_ANY_KEY;
		result += "Congrats, you made it to the escape pod!" + PROMPT_ANY_KEY;
		result += "You can now steer your way back to Earth and live again!" + PROMPT_ANY_KEY;
		result += "Although it is great to return, you have learned so much through this journey..." + PROMPT_ANY_KEY;
		result += "You have learned how to persevere" + PROMPT_ANY_KEY;
		result += "How to accomplish tasks with grit" + PROMPT_ANY_KEY;
		result += "And most importantly..." + PROMPT_ANY_KEY;
		result += "How to solve slide puzzles." + PROMPT_ANY_KEY;
		result += "\nThe AIs will miss you as you embark on this new chapter of your life." + PROMPT_ANY_KEY;
		result += "Best of luck on your descent" + PROMPT_ANY_KEY;
		result += "And thank you for your service at the space station." + PROMPT_ANY_KEY;
		result += "The world wouldn't be the same without you." + PROMPT_ANY_KEY;
		result += "\n\n" + credits();
		return result;
	}
	
	public static String credits() {
		String result = "";
		result += "Thank you for playing Space Zork." + PROMPT_ANY_KEY;
		result += "We hope you enjoyed." + PROMPT_ANY_KEY;
		result += "Ending statistics:\n" + Game.displayStatistics() + "" + PROMPT_ANY_KEY;
		result += "\n\nDevelopers:" + PROMPT_ANY_KEY + "\tEthan Elbaz" + PROMPT_ANY_KEY + "\tBen Merbaum"
				+ PROMPT_ANY_KEY + "\tNick Tong" + PROMPT_ANY_KEY;
		result += "Space awaits you..." + PROMPT_ANY_KEY;
		result += "\n\"This is one small step for man..." + PROMPT_ANY_KEY;
		result += "\none giant leap for manking\"" + PROMPT_ANY_KEY;
		result += "\t- Neil Armstrong";
		return result;
	}
	

	public static String storyline() {
		String result = "";
		result += "Welcome to space." + PROMPT_ANY_KEY;
		result += "You don't know who you are, what you are, or where you are." + PROMPT_ANY_KEY;
		result += "Are you in hell? Even hell sounds like a vacation to this place." + PROMPT_ANY_KEY;
		result += "All you can see are the stars, but even those look like a ball of fire\n" + "ready to attack."
				+ PROMPT_ANY_KEY;
		result += "You fly through the universe, or are you even in the universe." + PROMPT_ANY_KEY;
		result += "Who really cares anyway, because you're just going to die." + PROMPT_ANY_KEY;
		result += "You open your eyes to a room." + PROMPT_ANY_KEY;
		result += "A room!" + PROMPT_ANY_KEY;
		result += "But where is there a room in the middle of space?" + PROMPT_ANY_KEY;
		result += "Maybe there will be an escape pod somewhere near..." + PROMPT_ANY_KEY;
		result += "To finally get out of this stupid hellhole." + PROMPT_ANY_KEY;
		result += "It's time to find out and go explore.";
		return result;
	}

	public static String intro() {
		String result = "";
		result += ROCKET;
		result += "Press any key to continue." + PROMPT_ANY_KEY;
		result += "Welcome to Space Zork, a completely dynamic adventure game." + PROMPT_ANY_KEY;
		result += "WARNING: Zork has the potential to induce seizures\n"
				+ "for people with photosensitive epilepsy. If you have photosensitive epilepsy\n"
				+ "or feel you may be susceptible to a seizure, DO NOT PLAY ZORK.\n" + "You have been warned.\n"
				+ PROMPT_ANY_KEY;

		result += "HOW TO PLAY\n";
		result += "-----------\n" + PROMPT_ANY_KEY;
		result += help() + "\n" + PROMPT_ANY_KEY;
		result += "Good luck on your adventure. Try not to die." + PROMPT_ANY_KEY;
		result += "But if you do, that's okay as well." + PROMPT_ANY_KEY;
		result += "Just do your best. Because that's all that matters. Right?" + PROMPT_ANY_KEY;
		result += "And try not to rage. It'll be hard, I know." + PROMPT_ANY_KEY;
		result += "Just keep on trying" + PROMPT_ANY_KEY;
		result += "and trying" + PROMPT_ANY_KEY;
		result += "and trying\n" + PROMPT_ANY_KEY;
		result += "And remember: you can quit whenever you feel like it." + PROMPT_ANY_KEY;
		result += "You just won't get the satisfaction of knowing that you beat Zork." + PROMPT_ANY_KEY;
		result += "Shall we get started? ";
		result += PROMPT_START_GAME;
		result += "\n\n" + storyline() + "\n" + PROMPT_ANY_KEY;
		return result;
	}

	public static String commands() {
		return CommandWords.showAll();
	}

	public static void print(String message) {
		String[] lines = message.split(PROMPT_ANY_KEY);
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
			
			if(s.indexOf("SlidePuzzle.play()") != -1) {
				boolean gameWon = SlidePuzzle.play();
				if(gameWon)
					MapDisplay.display(player.getLocation().getMap());
			}

			System.out.print(s);
			Parser.pressAnyKey();
		}
	}
	
	public static String checkSlidePuzzle() {
		String str = "";
		if(player.getLocation().getRoom().getName().equals("map room")) {
			str += "Press any key to continue." + PROMPT_ANY_KEY;
			str += "Welcome to the Slide Puzzle Mini-Game." + PROMPT_ANY_KEY;
			str += "In front of you stands the Golden Map." + PROMPT_ANY_KEY;
			str += "This map gives you access to view all the rooms and corridors of this floor." + PROMPT_ANY_KEY;
			str += "This is the ultimate prize." + PROMPT_ANY_KEY;
			str += "Your opportunity to traverse the ship and finally make it out of space." + PROMPT_ANY_KEY;
			str += "\nAnd all you have to do" + PROMPT_ANY_KEY;
			str += "Is defeat this Slide Puzzle." + PROMPT_ANY_KEY;
			
			str += "\nHOW TO PLAY";
			str += "\n-----------" + PROMPT_ANY_KEY;
			
			str += "UP: 'w'" + PROMPT_ANY_KEY;
			str += "DOWN: 's'" + PROMPT_ANY_KEY;
			str += "LEFT: 'a'" + PROMPT_ANY_KEY;
			str += "RIGHT: 'd'" + PROMPT_ANY_KEY;
			str += "To quit, type 'quit'." + PROMPT_ANY_KEY;
			str += "To shuffle, type 'shuffle'." + PROMPT_ANY_KEY;
			str += "\nOrder the numbers in row-major order to win." + PROMPT_ANY_KEY;
			str += "Achieve this, and the Golden Map is all yours." + PROMPT_ANY_KEY;
			str +=  "Ready?" + PROMPT_START_GAME;
			str += "SlidePuzzle.play()";
		}
		return str;
		
	}
	public static void die() {
		Game.setGameOver(false);
		String result = "";
		result += "Noooo, don't die...\n" + PROMPT_ANY_KEY;
		result += "You're too young for this...\n" + PROMPT_ANY_KEY;
		result += "Your family misses you...\n" + PROMPT_ANY_KEY;
		result += "Please don't fade away...\n" + PROMPT_ANY_KEY;
		result += "...\n" + PROMPT_ANY_KEY;
		result += "..\n" + PROMPT_ANY_KEY;
		result += ".";
		print(result);
	}

}