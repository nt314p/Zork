package com.bayviewglen.zork.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Zork {
	public static void main(String[] args) {

		Game game = new Game();
		game.doTurn();

		
		//testRiddle();
		//testSides();
		//testInventory();
		//testInventoryLoader();
		//testPresetInventoryOverride();
		//testCharacterLoader();
		//testMap();
		//testRoomDescription();
		//testPresets();
		//testLink();
//		Item i = new Food("myItem", 17.9, null, 0, 0.9);
//		Item t = Item.copy(i);
//		System.out.println(i + "\n" + t);

		
	}
	
	public static void testLink() {
		Location l = new Location();
		Location loc = new Location(new Map("a", new Coordinate(7,7,7)), new Coordinate(3,4,6));
		Link.add(loc, l, 0);
		System.out.println(Link.getLink(l));
	}
	
	public static void testPresetInventoryOverride() {
		Inventory.initialize();
		Preset.initialize();
		Map m = Map.getMap("Ice Ice Baby");
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
		Map.initialize();
		Map map = Map.getMap("Test Map"); 
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("short", "You are in a test room.");
		Room r = new Room("Test room", h , new Location("TestMap",new Coordinate(0,03.5,6)));
		map.set(r, new Coordinate(0, 3.5, 6));
		System.out.println(map.getPlace(new Coordinate(0,  3.5,  6)) instanceof Room);
		System.out.println(map.getPlace(new Coordinate(0,  3.5,  6)) instanceof Wall);
	}
	
	public static void testRoomDescription() { // OUTDATED
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("short", "You are in a test room.");
		Room r = new Room("Test room", h , new Location("TestMap",new Coordinate(0.5,0.5,0.5)));
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
