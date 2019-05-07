package com.bayviewglen.zork.item;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.bayviewglen.zork.main.*;
import com.bayviewglen.zork.main.Character;
import com.bayviewglen.zork.map.*;

/* The parent class for everything - all objects in the game, ones that can be picked up and ones
 * that are stationary, food, doorways, etc.
 * 
 * Descriptions - HashMap<String key, String value>
 * Options for string key: look, location
 * 
 * The look string value holds a description that should be printed when the player looks at the item.
 * EX: a stick; value is "The stick is rough and brown."
 * 
 * The location string value holds a description that should be printed when the long description of a
 * room is being printed out.
 * EX: a lamp; value is "on a table"; full sentence reads "There is a lamp on a table."
 * EX: a rope; value is "dangling above you"; full sentence reads "There is a rope dangling above you."
 * 
 * If these two descriptions are the only things we need, we may just create two string variables
 * to hold these descriptions instead of a hashmap for simplicity.
 *
 */

public class Item implements Comparable<Item> {
	
	private static HashMap<String, Item> presets = new HashMap<String, Item>();

	private String name;
	private double weight;
	private HashMap<String, String> descriptions;

	public Item(String name, double weight, HashMap<String, String> descriptions) {
		this.weight = weight;
		this.name = name;
		this.descriptions = descriptions;
	}

	public Item(String name, double weight) {
		this.weight = weight;
		this.name = name;
		this.descriptions = new HashMap<String, String>();
	}

	public Item(Item item) {
		this.weight = item.getWeight();
		this.name = item.getName();
		this.descriptions = item.getDescriptions();
	}
	
	public Item getItem(String preset) {
		Item temp = presets.get(preset);
		String className = temp.getClass().getSimpleName();
		Item x;
		switch(className) {
		case "Food": x = new Food((Food)temp);
		case "Weapon": x = new Weapon((Weapon)temp);
		case "Health": x = new Health((Health)temp);
		case "Room": x = new Room((Room)temp);
		case "Door": x = new Door((Door)temp);
		case "Key": x = new Key((Key)temp);
		case "Character": x = new Character((Character)temp);
		default:x = null;
		}
		
		
		this.name = temp.getName();
		this.weight = temp.getWeight();
		this.descriptions = temp.getDescriptions();
		return temp;
		
	}

	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, String> getDescriptions() {
		return descriptions;
	}
	
	public HashMap<String, Item> getPresets(){
		return presets;
	}
	
	public String getDescription(String key) {
		return descriptions.get(key);
	}

	public void addDescription(String key, String description) {
		descriptions.put(key, description);
	}

	public boolean removeDescription(String key) {
		boolean containsKey = descriptions.containsKey(key);
		descriptions.remove(key);
		return containsKey; // was the key actually removed or did it not exist
	}

	public boolean containsDescription(String description) {
		return descriptions.containsValue(description);
	}

	/**
	 * Compares 2 item's names and weights to see if they are equal
	 * 
	 * @param item the item you want to compare
	 * @return true if they have the same name and weight
	 */
	public boolean equals(Item item) {
		return name.equals(item.name) && weight == item.weight;
	}

	/**
	 * Compares items more exactly 
	 * 1) runs through equals method 
	 * 2) makes sure every description is found in the argument item
	 * 
	 * @param item to check if equals
	 * @return if equal
	 */
	public boolean equalsExactly(Item item) {
		if (!this.equals(item))
			return false;

		return this.descriptions.equals(item.descriptions);
	}

	public int compareTo(Item item) {
		int temp = name.compareTo(item.name);
		switch (temp) {
		case 0:
			return (int) Math.round(item.weight - weight);
		default:
			return temp;
		}
	}

	public String toString() {
		String str = name + ": ";
		if(weight > 0)
			str +=  + weight + "lbs";

		if (descriptions != null) {
			Iterator<Entry<String, String>> descriptionsItr = descriptions.entrySet().iterator();
			while (descriptionsItr.hasNext()) {
				Map.Entry<String, String> mapElement = descriptionsItr.next();
				String value = (String) mapElement.getValue();
				str += " " + mapElement.getKey() + ": " + value;
			}
		}
		str += ".";
		return str;
	}
	
	public static Item loadItem(String filePath, String name) {
		Inventory inventory = Inventory.loadInventory(filePath);
		return inventory.getItem(name);
	}

}
