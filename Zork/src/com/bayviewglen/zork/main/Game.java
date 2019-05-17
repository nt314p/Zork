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

	private Parser parser;	
	private Player player;
	private ArrayList<Phase> phases;

	private int turn;
	

	public Game() {
		parser = new Parser();
		phases = new ArrayList<Phase>();
		CommandWords.initialize();
		Preset.initialize();
		Inventory.initialize();
		loadGame("data/gameTest.json");
		player = new Player(100, null, new Inventory(), new MoveableLocation("Ice Ice Baby", new Coordinate(0.5, 0.5, 0.5)));
		
		turn = 0;
	}
	
	public void processCommand(Command cmd) {
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
	
	public String doTurn() {
		Map.loadMap("data/minesweeper2.json");
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
		for(int i = 0; inventoryLines.size() < 3; i++) {
			inventoryLines.add("");
		}//give inventoryLines at least 3 to work with for-loop
		

		int i = 0;
		String ret = String.format("%-20s%s", "STATISTICS", "INVENTORY");
		ret += String.format("\n%-20s%s", "----------", "----------");
		ret += String.format("\n%-20s%s", healthMonitor, inventoryLines.get(i));
		i++;
		ret += String.format("\n%-20s%s", foodMonitor, inventoryLines.get(i));
		i++;
		ret += String.format("\n%-20s%s", waterMonitor, inventoryLines.get(i));
		i++;
		
		while(i < inventoryLines.size()) {
			ret += String.format("\n%-20s%s", "", inventoryLines.get(i));
			i++;
		}
		
		ret += "\nLOCATION:";
		ret += "\n---------";
		ret+= "\n" + roomDesc + "\n" + exits + "\n";
		return ret;
	}
		
	public int getTurn() {
		return turn;
	}
	
	public void incrementTurn() {
		turn++;
	}
	
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
//	public ArrayList<Character> getCharacters() {
//		return characters;
//	}
	
	public ArrayList<Phase> getPhases() {
		return phases;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void loadGame(String filePath) {
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