package com.bayviewglen.zork.command;

import com.bayviewglen.zork.main.Game;

public interface GameCommands {
	
	public static String commands() {
		return CommandWords.showAll();
	}
	public static String help() {
		String result = "";
		result += "Use 'c' to display all commands.\n";
		result += "Write a command to play your turn.\n";
		result += "Use cardinal directions to traverse the map.\n";
		result += "Collect keys to open doors and hidden rooms.\n";
		result += "Make it safely back to Earth to win.";
		return result;

	}
	
	public static String outro() {
		String result = "";
		result += "Thank you for playing Space Zork.\n";
		result += "We hope you enjoyed.\n";
		result += "Ending statistics:\n" + Game.displayStatistics();
		result += "\n\nDevelopers:\n\tEthan Elbaz\n\tBen Merbaum\n\tNick Tong";
				
		return result;
	}
	
	public static String storyline() {
		return null;
	}
	
	public static String intro() {
		String result = "";
		result += "Welcome to Space Zork, a completely dynamic adventure game.\n\n";
		result += "STORYLINE\n";
		result += "---------\n\n";
		result += storyline() + "\n\n";
		result += "HOW TO PLAY\n";
		result += "-----------\n\n";
		result += help() + "\n\n";
		result += "Good luck on your adventure. Try not to die.";
		result += "But if you do, that's okay as well.";
		result += "You'll just keep on trying\nand trying\nand trying!";
		result += "Enjoy!";
		return result;
	}
	

}
