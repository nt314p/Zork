package com.bayviewglen.zork.main;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Character{

	private Monitor healthMonitor;
	private Monitor foodMonitor;
	private Monitor waterMonitor;
	
	private Inventory inventory;
	private String name;
	private MoveableLocation location;
	

	public Character(String name, Inventory inventory, MoveableLocation location, double health, double food, double water) {
		this.name = name;
		this.inventory = inventory;
		this.location = location;
		healthMonitor = new Monitor(health);
		foodMonitor = new Monitor(food);
		waterMonitor = new Monitor(water);
	}
	
	public MoveableLocation getLocation() {
		return location;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
		String str = name + "'s Statistics:\n";
		str+="Health: " + healthMonitor.get()*100 + "%\n";
		str+="Food: " + foodMonitor.get()*100 + "%\n";
		str+="Water: " + waterMonitor.get()*100 + "%\n\n";
		
		str+= location.toString() + "\n\n";
		str+= "Inventory: " + inventory.toString();
		
		return str;
	}
	
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
			
			double[]coords = {curr.getDouble("phase"), curr.getDouble("map"), curr.getDouble("x"), curr.getDouble("y"), curr.getDouble("z")};
			MoveableLocation location = new MoveableLocation(coords);
			Inventory inventory = Inventory.loadInventory(curr.getString("inventory"));
			Character c = new Character(curr.getString("name"), inventory, location, curr.getDouble("health"), curr.getDouble("food"), curr.getDouble("water"));
			characters.add(c);
		}
		return characters;
	}
}
