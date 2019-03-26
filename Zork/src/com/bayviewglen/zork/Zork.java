package com.bayviewglen.zork;

import com.bayviewglen.item.*;
import com.bayviewglen.map.*;

public class Zork {
	public static void main(String[] args) {
		
		Inventory inventory = new Inventory();
		inventory.add(new Item("hi", 5.5, true));
		inventory.add(new Item("hefello", 6.5, true));
		inventory.add(new Item("hello", 6.5, true));
		inventory.add(new Item("hi", 5.5, true));
		inventory.add(new Item("hello", 7.5, true));
		//System.out.println(inventory);
		
		Map map = new Map(10,10,10);
		map.set(0, 3.5, 6, new Room());
		System.out.println(map.get(0,  3.5,  6).getClass().getSimpleName().equals("Room"));
		System.out.println(map.get(0,  3.5,  6).getClass().getSimpleName().equals("Wall"));
		
	
		
		Game game = new Game();
		game.play();
		
	}
}
