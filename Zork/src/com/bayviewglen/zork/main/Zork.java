package com.bayviewglen.zork.main;

import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Zork {
	public static void main(String[] args) {
		
		Side s = new Opening();
		System.out.println(s.isExit());
		s = new Wall();
		System.out.println(s.isExit());
		
		Inventory inventory = new Inventory();
		inventory.add(new Item("hi", 5.5, null));
		inventory.add(new Item("hefello", 6.5, null));
		inventory.add(new Item("hello", 6.5, null));
		inventory.add(new Item("hi", 5.5, null));
		inventory.add(new Item("hello", 7.5, null));
		//System.out.println(inventory);
		
		Map map = new Map("mapName",10,10,10);
		
		map.set(new Room("room name", "room description"), 0, 3.5, 6);
		System.out.println(map.get(0,  3.5,  6) instanceof Room);
		System.out.println(map.get(0,  3.5,  6) instanceof Wall);
		
		
		inventory = Inventory.loadInventory("data/inventoryTest.txt");
		System.out.println(inventory);
		
		//Game game = new Game();
		//game.play();
		
	}
}
