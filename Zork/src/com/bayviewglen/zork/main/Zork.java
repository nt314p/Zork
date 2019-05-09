package com.bayviewglen.zork.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Zork {
	public static void main(String[] args) {
		

		//Game game = new Game();

		
		//testRiddle();
		//testSides();
		//testInventory();
		//testInventoryLoader();
		//testMap();
		//testRoomDescription();
		testPresets();

		
	}
	
	public static void testPresets() {
		Preset.initialize();
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
		Inventory inventory = Inventory.loadInventory("data/inventoryTest.json");
		System.out.println(inventory);
		System.out.println(inventory.get(0));
	}
	
	public static void testMap() {
		Map map = Map.loadMap("data/jasontest.json");	
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("short", "You are in a test room.");
		Room r = new Room("Test room", h , new Location("TestMap",new Coordinate(0,03.5,6)));
		map.set(r, new Coordinate(0, 3.5, 6));
		System.out.println(map.getPlace(new Coordinate(0,  3.5,  6)) instanceof Room);
		System.out.println(map.getPlace(new Coordinate(0,  3.5,  6)) instanceof Wall);
	}
	
	public static void testRoomDescription() {
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
