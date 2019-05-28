package com.bayviewglen.zork.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.bayviewglen.zork.command.Command;
import com.bayviewglen.zork.command.Parser;
import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Zork {
	public static void main(String[] args) {

		//SlidePuzzle.play();
		Game.play("data/game.json");
//		Game game = new Game();
//		game.doTurn();

		// testCommandParse();
		// SlidePuzzle.play();
		// testMyVerySmallMapBoi();
		
		//Music.play("data/music/door_open.mp3");
		
		
		// testRiddle();
		// testSides();
		// testInventory();
		// testInventoryLoader();
		// testPresetInventoryOverride();
		// testCharacterLoader();
		// testMap();
		// testRoomDescription();
		// testPresets();
		// testLink();
//		Item i = new Food("myItem", 17.9, null, 0, 0.9);
//		Item t = Item.copy(i);
//		System.out.println(i + "\n" + t);
		
		//Game.print(Game.intro());

	}

	public static String testCommandExe(Command cmd) {
		return Game.processCommand(cmd);
	}

	public static void testMyVerySmallMapBoi() {
		Inventory.initialize();
		Preset.initialize();
		Maps.initialize();
		Map m = new Map("TESTING TESTER");
		m.getClass();
	}

	public static void testCommandParse() {
		// Player player = new Player(100, null, new Inventory(), new
		// MoveableLocation("Ice Ice Baby", new Coordinate(0.5, 0.5, 0.5)));
		ArrayList<Item> interactableItems = new ArrayList<Item>(Arrays.asList(new Item("Green Lantern", 30),
				new Item("Batman", 50), new Item("Wonder Woman", 40), new Item("Spider Man", 14)));

		// player.getInteractableItems().toArrayList();
		String s = "I want to see Green Lantern fight Spider Man, that would be a Wonder Man!";
		String[] params = s.split("[ ,!]");
		ArrayList<Item> commandableItems = new ArrayList<Item>();
		for (int a = 0; a < interactableItems.size(); a++) { // iterating over items
			Item i = interactableItems.get(a);
			for (int j = 0; j < params.length; j++) { // iterating over parameters
				boolean found = false;
				for (int k = 0; k < params.length - j; k++) { // how many words should the name be
					String matchName = "";
					for (int l = 0; l <= k; l++) {
						matchName += params[j + l];
						if (l != k)
							matchName += " ";
					}

					if (i.getName().compareToIgnoreCase(matchName) == 0) {
						commandableItems.add(i);
						matchName = "";
						j += k;
						a++;
						found = true;
						break;
					}
				}
				if (found) {
					break;
				}
			}
		}
		for (int i = 0; i < commandableItems.size(); i++) {
			System.out.println(commandableItems.get(i));
		}
	}

	public static void testLink() {
		Location l = new Location();
		Location loc = new Location(new Map("a", new Coordinate(7, 7, 7)), new Coordinate(3, 4, 6));
		Link.add(loc, l, 0);
		System.out.println(Link.getLink(l));
	}

	public static void testPresetInventoryOverride() {
		Inventory.initialize();
		Preset.initialize();
		Map m = Maps.getMap("Ice Ice Baby");
		Room r = m.getRoom(new Coordinate(1.5, 1.5, 0.5));
		System.out.println(r.getRoomItems());
	}

	public static void testCharacterLoader() {
		Inventory.initialize();
		Character.initialize();

		Character elvis = Character.getCharacter("Elvis AI");
		System.out.println(elvis);
		System.out.println(elvis.getInventory());
		System.out.println(elvis.getInventory().getItem("battery"));

	}

	public static void testPresets() {
		Preset.initialize();
		Item x = Preset.get("Thin Ice");
		System.out.println(x.getDescriptions());
		System.out.println(x.getName());
	}

	public static void testRiddle() {
		Riddle riddle = new Riddle("What is the answer?", "hi");
		System.out.println(riddle.getAnswerChar());

		ArrayList<String> sup = new ArrayList<String>();
		sup.add("hi");
		sup.add("hey");
		sup.add("wassup");
		Riddle riddle2 = new Riddle("Sup", sup, 1);
		System.out.println(riddle2);
		System.out.println(riddle2.isAnswer(1));
		System.out.println(riddle2.displayAll());

		Riddle riddle3 = new Riddle("Sup", "hey");
		System.out.println(riddle3.equals(riddle2));
		System.out.println(riddle3.displayAll());
	}

	public static void testSides() {
		Side s = new Opening("hello", null, new Location());
		System.out.println(s.isExit());
		s = new Wall("hi", null, new Location());
		System.out.println(s.isExit());
	}

	public static void testInventory() {
		Inventory inventory = new Inventory();
		inventory.add(new Item("hi", 5.5, null));
		inventory.add(new Item("hefello", 6.5, null));
		inventory.add(new Item("hello", 6.5, null));
		inventory.add(new Item("hi", 5.5, null));
		inventory.add(new Item("hello", 7.5, null));
		System.out.println(inventory);
	}

	public static void testInventoryLoader() {
		Inventory.initialize();
		Inventory inventory = Inventory.getInventory("TestRoomInv");
		System.out.println(inventory);
		System.out.println(inventory.getItem("Knowledge"));
		System.out.println(inventory.getItem("Chug Jug"));
	}

	public static void testMap() { // OUTDATED
		Maps.initialize();
		Map map = Maps.getMap("Test Map");
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("short", "You are in a test room.");
		Room r = new Room("Test room", h, new Location("TestMap", new Coordinate(0, 03.5, 6)));
		map.set(r, new Coordinate(0, 3.5, 6));
		System.out.println(map.getPlace(new Coordinate(0, 3.5, 6)) instanceof Room);
		System.out.println(map.getPlace(new Coordinate(0, 3.5, 6)) instanceof Wall);
	}

	public static void testRoomDescription() { // OUTDATED
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("short", "You are in a test room.");
		Room r = new Room("Test room", h, new Location("TestMap", new Coordinate(0.5, 0.5, 0.5)));
		Item i1 = new Item("beer bottle", 0.5);
		Item i2 = new Item("lamp", 0.5);
		Item i3 = new Item("anchor", 0.5);
		i1.addDescription("location", "on the bed");
		i2.addDescription("location", "on a table");
		i3.addDescription("location", "on the floor");

		r.getRoomItems().add(i1);
		r.getRoomItems().add(i2);
		r.getRoomItems().add(i3);
		System.out.println(r.getLongDescription());
		System.out.println(r.getShortDescription());

	}
}
