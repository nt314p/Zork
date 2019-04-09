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
		Side s = new Opening();
		System.out.println(s.isExit());
		s = new Wall();
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
		Map myMap = Map.loadMap("data/jasontest.json");
		
		Map map = new Map("mapName",10,10,10);
		map.set(new Room("room name", "room description"), 0, 3.5, 6);
		System.out.println(map.get(0,  3.5,  6) instanceof Room);
		System.out.println(map.get(0,  3.5,  6) instanceof Wall);
	}
}
