package com.bayviewglen.zork;

public class Zork {
	public static void main(String[] args) {
		
		Inventory inventory = new Inventory();
		inventory.add(new Item("hi", 5.5, true));
		inventory.add(new Item("hello", 6.5, true));
		inventory.add(new Item("hello", 6.5, true));
		inventory.add(new Item("hi", 5.5, true));
		inventory.add(new Item("hello", 7.5, true));
		System.out.println(inventory);
		
	
		
		//Game game = new Game();
		//game.play();
		
	}
}
