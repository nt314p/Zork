package com.bayviewglen.zork;

public class Zork {
	public static void main(String[] args) {
		CommandWords cw = new CommandWords();
		System.out.println(cw.isCommand("take"));
		System.out.println(cw.isCommand("eat"));
		System.out.println(cw.isCommand("discard"));

		cw.showAll();
		
		//Game game = new Game();
		//game.play();
	}
}
