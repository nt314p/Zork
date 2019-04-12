package com.bayviewglen.zork.main;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bayviewglen.zork.command.*;
import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

/**
 * 
 * @authors bmerbaum, ntong, eelbaz
 *
 */

public class Game{

	private static Parser parser;	
	private static Player player;
	private static ArrayList<Phase> phases;
	private static ArrayList<Character> characters;

	private static int turn;
	

	public Game() {
		parser = new Parser();
		phases = new ArrayList<Phase>();
		CommandWords.initialize();
		loadGame("data/gameTest.json");
		player = new Player(new Inventory(), new MoveableLocation());
		characters = Character.loadCharacters("data/characterTest.json");
		
		turn = 0;
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
	
	public static ArrayList<Character> getCharacters() {
		return characters;
	}
	
	public static ArrayList<Phase> getPhases() {
		return phases;
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	public static void loadGame(String filePath) {
		FileReader reader = new FileReader(filePath);
		String[] lines = reader.getLines();
		
		String concat = "";
		
		for (String s : lines) {
			concat += s + "\n";
		}
		
		JSONObject obj = new JSONObject(concat);
		JSONArray textPhases = obj.getJSONArray("phases");
		

		for (int i = 0; i < textPhases.length(); i++) {
			JSONObject curr = textPhases.getJSONObject(i);
			
			ArrayList<Map> maps = new ArrayList<Map>();
			JSONArray JSONMaps = curr.getJSONArray("maps");
			
			for(int j = 0; j<JSONMaps.length(); j++) {
				maps.add(Map.loadMap(JSONMaps.getString(j), i, j));
			}
			Phase phase = new Phase(curr.getString("name"), maps);
			phases.add(phase);
		}
	}
	
}