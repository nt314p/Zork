package com.bayviewglen.zork.item;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

	public static Item clone(Item item) {

		Class<? extends Item> cls = item.getClass();
		Constructor<? extends Item> cons;
		try {
			if (cls.getSimpleName().equals("Player"))
				return null;
			cons = cls.getConstructor(cls);
			return cons.newInstance(cls.cast(item));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null; // error
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

	public HashMap<String, Item> getPresets() {
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
	 * Compares items more exactly 1) runs through equals method 2) makes sure every
	 * description is found in the argument item
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
		if (weight > 0)
			str += +weight + "lbs";

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
//
//	public static Item loadItem(String filePath, String name) {
//		Inventory inventory = Inventory.loadInventory(filePath);
//		return inventory.getItem(name);
//	}

	public static Item loadItem(JSONObject jObj) throws JSONException {
		try {
			String presetName = jObj.getString("preset"); // checking for preset
			Item preset = Preset.get(presetName); // getting preset
			try {
				preset = Item.clone(preset);
			} catch (NullPointerException e) {
				System.out.println("Error with " + presetName);
				e.printStackTrace();
			}
			if (preset instanceof Room) { // finding inventory and overriding preset inventory
				try { 
					String invName = jObj.getString("inventory"); // attempting to find inventory
					((Room) preset).setInventory(invName);
				} catch (JSONException e) {
					// no inventory
				}
			}
			return preset; // returning preset
		} catch (JSONException e) {
			// nothing to see here, just no preset
		}
		
		String type = "";
		Class<?> cls = null;
		try {
			type = jObj.getString("type");
			type = type.substring(0, 1).toUpperCase() + type.substring(1); // capitalizing
			String[] pkgs = {"main", "item", "map"};
			for (int i = 0; i < pkgs.length; i++) { // iterating through packages
				try {
					cls = Class.forName("com.bayviewglen.zork." + pkgs[i] + "." + type);
				} catch (ClassNotFoundException e){
					
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		String name = jObj.getString("name");
				
		HashMap<String, String> descriptions = new HashMap<String, String>();
		
		JSONArray JSONDescriptions;
		try {
			JSONDescriptions = jObj.getJSONArray("descriptions");
			for (int j = 0; j < JSONDescriptions.length(); j++) { // splitting descriptions
				String temp = JSONDescriptions.getString(j);
				int index = temp.indexOf(":");
				descriptions.put(temp.substring(0, index), temp.substring(index + 1)); // hashmap insertion
			}
		} catch (JSONException e) {
			// no descriptions
		}
		
		if (type.equals("Room")) {
			Room r = new Room(name, descriptions);
			try {
				String invName = jObj.getString("inventory"); // attempting to find inventory
				r.setInventory(invName);
			} catch (JSONException e) {
				// no inventory
			}
			return r;
		}
		
		if ("WallOpening".indexOf(type) != -1) {
			Constructor<?> cons;
			try {
				cons = cls.getConstructor(String.class, HashMap.class);
				return (Item) cons.newInstance(name, descriptions);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}	
		}
		
		if (type.equals("Door")) {
			String keycode = "";
			Door d = null;

			try {
				keycode = jObj.getString("code");
			} catch (JSONException ex) {
				// no keycode, door cannot be locked or unlocked
			}

			try {
				d = new Door(name, descriptions, jObj.getBoolean("open"), jObj.getBoolean("locked"), keycode);
			} catch (JSONException ex) {
				d = new Door(name, descriptions, jObj.getBoolean("open"), false, keycode);
			}
			return d;
		}
		
		// Key, Food, Health, Item, and Weapon are left
		double weight = jObj.getDouble("weight");
		
		switch (type) {
		case "Key": return new Key(name, weight, descriptions, jObj.getString("code"));
		case "Weapon": return new Weapon(name, weight, descriptions, jObj.getDouble("damage"));
		case "Health": return new Health(name, weight, descriptions, jObj.getDouble("health"));
		case "Food": {
			double fval = 0;
			double wval = 0;
			if (jObj.has("food")) {
				fval = jObj.getDouble("food");
			}
			if (jObj.has("water")) {
				wval = jObj.getDouble("water");
			}
			return new Food(name, weight, descriptions, fval, wval);
		}
		case "Item": return new Item(name, weight, descriptions);
		}
		
		return null;
	}

}
