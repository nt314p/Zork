package com.bayviewglen.zork.main;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Character extends Item{

	private Monitor healthMonitor;
	private Monitor foodMonitor;
	private Monitor waterMonitor;
	
	private Inventory inventory;
	private MoveableLocation location;
	

	public Character(String name, double weight, HashMap<String, String> descriptions, Inventory inventory, MoveableLocation location, double [] stats) {
		super(name, weight, descriptions);
		this.inventory = inventory;
		this.location = location;
		healthMonitor = new Monitor(stats[0]);
		foodMonitor = new Monitor(stats[1]);
		waterMonitor = new Monitor(stats[2]);
	}
	
	public Character(Character character) {
		super(character.getName(), character.getWeight(), character.getDescriptions());
		this.inventory = character.getInventory();
		this.location = character.getLocation();
		healthMonitor = character.getHealthMonitor();
		foodMonitor = character.getFoodMonitor();
		waterMonitor = character.getWaterMonitor();
	}
	
	public MoveableLocation getLocation() {
		return location;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public Monitor getHealthMonitor() {
		return healthMonitor;
	}
	
	public Monitor getWaterMonitor() {
		return waterMonitor;
	}
	
	public Monitor getFoodMonitor() {
		return foodMonitor;
	}

	/**
	 * check if healthMonitor/foodMonitor/waterMonitor is 0
	 * @return if you're dead
	 */
	public boolean checkDeath() {
		return healthMonitor.isDead() || foodMonitor.isDead() || waterMonitor.isDead();
	}
	
	
	/**
	 * eat the food
	 * @param food the food you would like to eat
	 */
	public void eat(Food food) {
		foodMonitor.increase(food.getFoodValue());
	}
	

	/**
	 * drink the food
	 * @param food the food you would like to drink
	 */
	public void drink(Food food) {
		waterMonitor.increase(food.getWaterValue());
	}
	

	/**
	 * heal yourself
	 * @param health the object you want to use to heal yourself
	 */
	public void heal(Health health) {	
		healthMonitor.increase(health.getHealthValue());
	}
	
	public String toString() {
		String str = getName() + "'s Statistics:\n";
		str+="Health: " + healthMonitor.get()*100 + "%\n";
		str+="Food: " + foodMonitor.get()*100 + "%\n";
		str+="Water: " + waterMonitor.get()*100 + "%\n\n";
		
		str+= location.toString() + "\n\n";
		str+= "Inventory: " + inventory.toString();
		
		return str;
	}
	/*
	public static ArrayList<Character> loadCharacters(String filePath){
		FileReader reader = new FileReader(filePath);
		String[] lines = reader.getLines();
		ArrayList<Character> characters = new ArrayList<Character>();
		
		String concat = "";
		
		for (String s : lines) {
			concat += s + "\n";
		}
		
		JSONObject obj = new JSONObject(concat);
		JSONArray textCharacters = obj.getJSONArray("characters");
		

		for (int i = 0; i < textCharacters.length(); i++) {
			JSONObject curr = textCharacters.getJSONObject(i);
			
			// double[]coords = {curr.getDouble("phase"), curr.getDouble("map"), curr.getDouble("x"), curr.getDouble("y"), curr.getDouble("z")};
			Coordinate coords = Coordinate.readCoords(curr.getString("coords"));
			MoveableLocation location = new MoveableLocation(curr.getString("map"), coords);
			Inventory inventory = Inventory.loadInventory(curr.getString("inventory"));
			
			HashMap<String, String> descriptions = new HashMap<String, String>();
			JSONArray JSONDescriptions = curr.getJSONArray("descriptions");
			for(int j = 0; j<JSONDescriptions.length(); j++) {
				String temp = JSONDescriptions.getString(j);
				int index = temp.indexOf(":");
				descriptions.put(temp.substring(0, index), temp.substring(index+1));
			}
			
			double[] stats = {curr.getDouble("health"), curr.getDouble("food"), curr.getDouble("water")};
			
			Character c = new Character(curr.getString("name"), curr.getDouble("weight"), descriptions, inventory, location, stats);
			characters.add(c);
		}
		return characters;
	}*/
}
