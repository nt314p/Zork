package com.bayviewglen.zork.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Character extends Item {

	private static HashMap<String, Character> characters;
	private static String[] eating = { "It was delicious.", "Yummy!", "Very flavourful!",
			"The food tasted bad but at least your eating", "Disgusting!" };
	private static String[] drinking = { "Mmm, water...", "It tasted delicious (if water can have taste).",
			"Isn't water amazing?" };
	private static String[] healing = { "No more blood!", "You might survive!!!", "Nice.",
			"Let's try not to get injured next time.", "Congrats on not dying." };

	private Monitor healthMonitor;
	private Monitor foodMonitor;
	private Monitor waterMonitor;

	private Inventory inventory;
	private Location location;

	private Coordinate startPoint;
	private Coordinate endPoint;

	public Character(String name, double weight, HashMap<String, String> descriptions, Inventory inventory,
			Location location, double[] stats, Coordinate startPoint, Coordinate endPoint) {
		super(name, weight, descriptions);
		this.inventory = inventory;
		this.location = location;
		addToRoom();
		healthMonitor = new Monitor(stats[0]);
		foodMonitor = new Monitor(stats[1]);
		waterMonitor = new Monitor(stats[2]);
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	public Character(Character character) {
		super(character.getName(), character.getWeight(), character.getDescriptions());
		this.inventory = character.getInventory();
		this.location = character.getLocation();
		addToRoom();
		healthMonitor = character.getHealthMonitor();
		foodMonitor = character.getFoodMonitor();
		waterMonitor = character.getWaterMonitor();
		startPoint = character.getStartPoint();
		endPoint = character.getEndPoint();
	}

	public void addToRoom() {
		if (!this.location.getRoom().getRoomItems().contains(this)) {
			this.location.getRoom().getRoomItems().add(this);
		}
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

	public double getMoveableArea() {
		double x = Math.abs(endPoint.getX() - startPoint.getX());
		double y = Math.abs(endPoint.getY() - startPoint.getY());
		double z = Math.abs(endPoint.getZ() - startPoint.getZ());

		return x * y * z;
	}

	public Coordinate getRandomCoord() {
		double x = (int) (Math.random() * (endPoint.getX() - startPoint.getX() + 1) + startPoint.getX()) / 2;
		double y = (int) (Math.random() * (endPoint.getY() - startPoint.getY() + 1) + startPoint.getY()) / 2;
		double z = (int) (Math.random() * (endPoint.getZ() - startPoint.getZ() + 1) + startPoint.getZ()) / 2;

		return new Coordinate(x, y, z);
	}

	public boolean isInCoordinateArea(Coordinate c) {
		if (c.getX() < startPoint.getX() || c.getX() > endPoint.getX())
			return false;

		if (c.getY() < startPoint.getY() || c.getY() > endPoint.getY())
			return false;

		if (c.getZ() < startPoint.getZ() || c.getZ() > endPoint.getZ())
			return false;

		return true;
	}

	public Location getRandomMove() {
		Coordinate c = location.getCoords();
		Map m = location.getMap();

		ArrayList<java.lang.Character> exits = m.getExits(c);
		ArrayList<Location> moves = new ArrayList<Location>();

		for (int i = 0; i < exits.size(); i++) {
			Coordinate temp = m.getNextRoomCoords(exits.get(i), c);

			if (isInCoordinateArea(temp))
				moves.add(m.getRoom(temp).getLocation());
		}

		if (exits.size() == 0 || moves.size() == 0)
			return location;

		int randIndex = (int) (Math.random() * moves.size());
		return moves.get(randIndex);
	}

	public void move() {
		this.location.getRoom().getRoomItems().remove(this);
		location = getRandomMove();
		this.location.getRoom().getRoomItems().add(this);
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location loc) {
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

	public Coordinate getStartPoint() {
		return startPoint;
	}

	public Coordinate getEndPoint() {
		return endPoint;
	}

	/**
	 * check if healthMonitor/foodMonitor/waterMonitor is 0
	 * 
	 * @return the monitor that dies or none if they're all intact
	 */
	public boolean checkDeath() {
		return healthMonitor.isDead() || foodMonitor.isDead() || waterMonitor.isDead();
	}

	public Monitor checkDeathMonitor() {
		if (healthMonitor.isDead())
			return healthMonitor;
		else if (foodMonitor.isDead())
			return foodMonitor;
		else if (waterMonitor.isDead())
			return waterMonitor;

		return null;
	}

	/**
	 * eat the food
	 * 
	 * @param food the food you would like to eat
	 * @return
	 */
	public String eat(Food food) {
		foodMonitor.increase(food.getFoodValue());
		return food.getName() + " has been eaten. " + Game.getRandom(eating);
	}

	/**
	 * drink the food
	 * 
	 * @param food the food you would like to drink
	 */
	public String drink(Food water) {
		waterMonitor.increase(water.getWaterValue());
		return water.getName() + " has been drank. " + Game.getRandom(drinking);
	}

	/**
	 * heal yourself
	 * 
	 * @param health the object you want to use to heal yourself
	 */
	public String heal(Health health) {
		healthMonitor.increase(health.getHealthValue());
		return health.getName() + " has been used to heal yourself. " + Game.getRandom(healing);
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

	public static void moveAll() {
		for (java.util.Map.Entry<String, Character> characters : characters.entrySet()) {
			characters.getValue().move();
		}
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

		Location loc = Location.loadLocation(obj);

		double[] stats = { obj.getDouble("health"), obj.getDouble("food"), obj.getDouble("water") };
		Character c = new Character(obj.getString("name"), obj.getDouble("weight"), descriptions, inventory, loc, stats,
				new Coordinate(obj.getString("start")), new Coordinate(obj.getString("end")));
		characters.put(obj.getString("name"), c);
	}
}
