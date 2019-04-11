package com.bayviewglen.zork.main;

import java.util.ArrayList;

import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Zork {
	public static void main(String[] args) {
		
		//testRiddle();
		//testSides();
		//testInventory();
		//testInventoryLoader();
		//testMap();
		testRoomDescription();
		
		Game game = new Game();
		//game.play();
		
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
		Side s = new Opening(new Location());
		System.out.println(s.isExit());
		s = new Wall(new Location());
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
		Map myMap = Map.loadMap("data/jasontest.json", 0, 0);
		
		Map map = new Map("mapName",new Location(0, 0, new Coordinate(10,10,10)));
		map.set(new Room("room name", "room description",null, false), new Coordinate(0, 3.5, 6));
		System.out.println(map.getPlace(new Coordinate(0,  3.5,  6)) instanceof Room);
		System.out.println(map.getPlace(new Coordinate(0,  3.5,  6)) instanceof Wall);
	}
	
	public static void testRoomDescription() {
		Room r = new Room("Test room", "You are in a test room.", null);
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
