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
		Preset.initialize();
		loadGame("data/gameTest.json");
		player = new Player(100, null, new Inventory(), new MoveableLocation());
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
	
	public static String doTurn() {
		Map.loadMap("data/TestMapV2.json");
		Location currLoc = player.getLocation();
		String roomDesc = currLoc.getMap().getRoom(currLoc.getCoords()).getLongDescription();		
		

		String exits = "Exits: ";
		boolean added = true;
		for(java.lang.Character i : currLoc.getMap().getExits(currLoc.getCoords())) {
			switch(i) {
			case 'n': exits += " North, ";
			case 's': exits += " South, ";
			case 'e': exits += " East, ";
			case 'w': exits += " West, ";
			case 'u': exits += " Up, ";
			case 'd': exits += " Down, ";
			default: added = false;
			}
		}
		if(added)
			exits+= "None";
		else
			exits = exits.substring(0, exits.length()-2);//remove ", "
		exits = exits.substring(0, exits.length()-2);
		
		String healthMonitor = "Health" + player.getHealthMonitor().toString();
		String foodMonitor = "Food" + player.getFoodMonitor().toString();
		String waterMonitor = "Water" + player.getWaterMonitor().toString();
		

		ArrayList<String> inventoryLines = player.getInventory().toStringArray();
		for(int i = 0; inventoryLines.size()<3; i++) {
			inventoryLines.add("");
		}//give inventoryLines at least 3 to work with for-loop
		

		int i = 0;
		String ret = String.format("%s%-40s", "STATISTICS", "INVENTORY");
		ret += String.format("\n%s%-40s", "----------", "----------");
		ret += String.format("\n%s%-40s", healthMonitor, inventoryLines.get(i));
		i++;
		ret += String.format("\n%s%-40s", foodMonitor, inventoryLines.get(i));
		i++;
		ret += String.format("\n%s%-40s", waterMonitor, inventoryLines.get(i));
		i++;
		
		while(i < inventoryLines.size()) {
			ret += String.format("\n%s%-40s", "", inventoryLines.get(i));
			i++;
		}
		
		ret += "\nLOCATION:";
		ret += "\n---------";
		ret+= "\n" + roomDesc + "\n" + exits + "\n";
		return ret;
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
				maps.add(Map.loadMap(JSONMaps.getString(j)));
			}
			Phase phase = new Phase(curr.getString("name"), maps);
			phases.add(phase);
		}
	}
	
}