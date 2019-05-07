package com.bayviewglen.zork.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.bayviewglen.zork.item.Item;
import com.bayviewglen.zork.main.Inventory;

public class Room extends Place {
	private String roomName;
	private HashMap<String, String> descriptions;
	private Inventory roomItems;
	private Inventory roomCharacters = new Inventory();

	private boolean isDeathRoom;

	/**
	 * Create a room described "description". Initially, it has no exits.
	 * "description" is something like "a kitchen" or "an open court yard".
	 */

	public Room(String roomName, HashMap<String, String> descriptions, Location location) {
		super(roomName, descriptions, location);
		this.roomName = roomName;
		this.descriptions = descriptions;
		this.isDeathRoom = false;
		this.roomItems = new Inventory();
	}

	public Room(String roomName, HashMap<String, String> descriptions, Location location, boolean isDeathRoom) {
		super(roomName, descriptions, location);
		this.roomName = roomName;
		this.descriptions = descriptions;
		this.isDeathRoom = isDeathRoom;
		this.roomItems = new Inventory();
	}

	public Room(String roomName, HashMap<String, String> descriptions, Location location, boolean isDeathRoom, Inventory inventory) {
		super(roomName, descriptions, location);
		this.roomName = roomName;
		this.descriptions = descriptions;
		this.isDeathRoom = isDeathRoom;
		this.roomItems = inventory;
	}

	public boolean isDeathRoom() {
		return isDeathRoom;
	}

	public void setDeathRoom(boolean isDeathRoom) {
		this.isDeathRoom = isDeathRoom;
	}

	public void setRoomItems(Inventory roomItems) {
		this.roomItems = roomItems;
	}

	/**
	 * .equals method comparing 2 rooms - roomName and roomDescription
	 * 
	 * @param room the room to compare
	 * @return if they are identical
	 */
	public boolean equals(Room room) {
		return roomName.equals(room.roomName) && descriptions.equals(room.descriptions);
	}

	/**
	 * Gives the description - defined in constructor
	 * 
	 * @return the description of the room *****doesn't include exits - need to
	 *         access the maps for that***********
	 */
	public String toString() {
		return "Room: " + roomName + "\n\n" + descriptions;
	}

	/**
	 * Get the name of the room
	 * 
	 * @return the room name
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * Sets the current room to the room being passed in (parameter roomName)
	 * 
	 * @param roomName the name of the room you are currently in
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * Get the short description of the room
	 * 
	 * @return the string description of the room
	 */
	public String getShortDescription() {
		return descriptions.get("short");
	}

	/**
	 * Get the long description of the room
	 * @return the string description of the room
	 */
	public String getLongDescription() {
		String ret = descriptions + " ";
		for (int i = 0; i < roomItems.size(); i++) {
			Item currItem = roomItems.get(i);
			ret += "There is a" + ("aeiou".indexOf(currItem.getName().charAt(0)) != -1 ? "n " : " ");
			ret += currItem.getName();
			ret += " " + currItem.getDescription("location") + ". ";
		}
		ret += "Exits:\n";
		
		Map map = getLocation().getMap();
		Coordinate coords = getLocation().getCoords();
		HashMap<java.lang.Character, Coordinate> sideCoords = map.getSurroundingSideCoords(coords);
		
		Iterator<Entry<java.lang.Character, Coordinate>> it = sideCoords.entrySet().iterator();
		java.util.Map.Entry<java.lang.Character, Coordinate> pair;
		
		while (it.hasNext()) {
			pair = (java.util.Map.Entry<java.lang.Character, Coordinate>) it.next();	
			ret += pair.getKey() + ": " + map.getSide(pair.getValue()).toString();
		}
		
		return ret;
	}

	/**
	 * Set your description being passed in as the current description
	 * 
	 * @param description the description to be set
	 */
	public void setDescription(HashMap<String, String> description) {
		this.descriptions = description;
	}

	public Inventory getRoomItems() {
		return roomItems;
	}

	public Inventory getRoomCharacters() {
		return roomCharacters;
	}
}
