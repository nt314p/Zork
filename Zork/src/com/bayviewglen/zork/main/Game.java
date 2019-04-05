package com.bayviewglen.zork.main;

import java.util.ArrayList;

import com.bayviewglen.zork.command.*;
import com.bayviewglen.zork.item.Item;
import com.bayviewglen.zork.map.*;

/**
 * 
 * @authors bmerbaum, ntong, eelbaz
 *
 */

public class Game{

	private static Parser parser;	
	private static Player player = new Player(null, null);
	private static ArrayList<Phase> phases;
	private static ArrayList<Character> characters;
	
	private static int turn;
	private static int turnsUntilDeath;
	

	public Game() {
		parser = new Parser();
		CommandWords.initialize();
		phases = new ArrayList<Phase>();
	}
	
	public static void processCommand(Command cmd) {
		ArrayList<Item> interactableItems = player.getInteractableItems().toArrayList();
		String[] params = cmd.getParameters();
		ArrayList<Item> commandableItems = new ArrayList<Item>();
		for (Item i : interactableItems) { // iterating over items
			for (int j = 0; j < params.length; j++) { // iterating over parameters
				if (i.getName().compareToIgnoreCase(params[j]) == 0) {
					commandableItems.add(i);
				}
			}
		}
		/*
		java.lang.reflect.Method method;
		try {
			  method = i.getClass().getMethod(methodName, param1.class, param2.class, ..);
			} catch (SecurityException e) { ... }
			  catch (NoSuchMethodException e) { ... }*/
	}
		
	public static int getTurn() {
		return turn;
	}
	
	public static void incrementTurn() {
		turn++;
	}
	
	public static void setTurn(int turn) {
		Game.turn = turn;
	}
	
	
	public static int getTurnsUntilDeath() {
		return turnsUntilDeath;
	}
	
	public static void decrementTurnsUntilDeath() {
		turnsUntilDeath--;
	}
	
	public static void setTurnsUntilDeath(int x) {
		turnsUntilDeath = x;
	}
	
	public static ArrayList<Character> getCharacters() {
		return characters;
	}
	
	public static ArrayList<Phase> getPhases() {
		return phases;
	}
	
	public static Player getPlayer() {
		return player;
	}
	
}