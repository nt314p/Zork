package com.bayviewglen.zork.main;

import java.io.File;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Character extends Item {

	private static HashMap<String, Character> characters;

	private Monitor healthMonitor;
	private Monitor foodMonitor;
	private Monitor waterMonitor;

	private Inventory inventory;
	private MoveableLocation location;

	public Character(String name, double weight, HashMap<String, String> descriptions, Inventory inventory,
			MoveableLocation location, double[] stats) {
		super(name, weight, descriptions);
		this.inventory = inventory;
		this.location = location;
		healthMonitor = new Monitor(stats[0]);
		foodMonitor = new Monitor(stats[1]);
		waterMonitor = new Monitor(stats[2]);
	}

	public Character(String name, double weight, HashMap<String, String> descriptions, Inventory inventory,
			double[] stats) {
		super(name, weight, descriptions);
		this.inventory = inventory;
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

	public static void initialize() {

		characters = new HashMap<String, Character>();

		File dir = new File("data/characters/");
		File[] dirList = dir.listFiles();
		if (dirList != null) {
			for (File f : dirList) {
				loadCharacter(f.getAbsolutePath());
			}
		}
	}

	public MoveableLocation getLocation() {
		return location;
	}

	public void setLocation(MoveableLocation loc) {
		location = loc;
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
	 * 
	 * @return if you're dead
	 */
	public boolean checkDeath() {
		return healthMonitor.isDead() || foodMonitor.isDead() || waterMonitor.isDead();
	}

	/**
	 * eat the food
	 * 
	 * @param food the food you would like to eat
	 */
	public void eat(Food food) {
		foodMonitor.increase(food.getFoodValue());
	}

	/**
	 * drink the food
	 * 
	 * @param food the food you would like to drink
	 */
	public void drink(Food food) {
		waterMonitor.increase(food.getWaterValue());
	}

	/**
	 * heal yourself
	 * 
	 * @param health the object you want to use to heal yourself
	 */
	public void heal(Health health) {
		healthMonitor.increase(health.getHealthValue());
	}

	public String toString() {
		String str = getName() + "'s Statistics:\n";
		str += "Health: " + healthMonitor.get() * 100 + "%\n";
		str += "Food: " + foodMonitor.get() * 100 + "%\n";
		str += "Water: " + waterMonitor.get() * 100 + "%\n\n";
		try {
			str += location.toString() + "\n\n";
		} catch (NullPointerException e) {
			// location doesn't exist
		}
		str += "Inventory:\n" + inventory.toString();

		return str;
	}

	public static Character getCharacter(String name) {
		return characters.get(name);
	}

	private static void loadCharacter(String filePath) {
		FileReader reader = new FileReader(filePath);

		JSONObject obj = new JSONObject(reader.getLinesSingle());

		Inventory inventory = new Inventory();
		try {
			inventory = Inventory.getInventory(obj.getString("inventory"));
		} catch (JSONException e) {
			// no inventory
		}

		HashMap<String, String> descriptions = new HashMap<String, String>();
		JSONArray JSONDescriptions;
		try {
			JSONDescriptions = obj.getJSONArray("descriptions");
			for (int j = 0; j < JSONDescriptions.length(); j++) { // splitting descriptions
				String temp = JSONDescriptions.getString(j);
				int index = temp.indexOf(":");
				descriptions.put(temp.substring(0, index), temp.substring(index + 1)); // hashmap insertion
			}
		} catch (JSONException e) {
			// no descriptions
		}

		double[] stats = { obj.getDouble("health"), obj.getDouble("food"), obj.getDouble("water") };

		Character c = new Character(obj.getString("name"), obj.getDouble("weight"), descriptions, inventory, stats);
		characters.put(obj.getString("name"), c);
	}
}
