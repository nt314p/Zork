package com.bayviewglen.zork.map;

import com.bayviewglen.zork.item.Item;
import com.bayviewglen.zork.main.*;

public class Room extends Place{
	private String roomName;
	private String description;
	private Inventory roomItems;
	
	private boolean isDeathRoom;

	/**
	 * Create a room described "description". Initially, it has no exits.
	 * "description" is something like "a kitchen" or "an open court yard".
	 */
	
	public Room(String roomName, String description) {
		this.roomName = roomName;
		this.description = description;
		this.isDeathRoom = false;
		this.roomItems = new Inventory();
	}	
	
	public Room(String roomName, String description, boolean isDeathRoom) {
		this.roomName = roomName;
		this.description = description;
		this.isDeathRoom = isDeathRoom;
		this.roomItems = new Inventory();
	}
	
	public Room(String roomName, String description, boolean isDeathRoom, Inventory inventory) {
		this.roomName = roomName;
		this.description = description;
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
	 * @param room the room to compare
	 * @return if they are identical
	 */
	public boolean equals(Room room) {
		return roomName.equals(room.roomName) && description.equals(room.description);
	}

	/**
	 * Gives the description - defined in constructor
	 * @return the description of the room
	 * *****doesn't include exits - need to access the maps for that***********
	 */
	public String toString() {
		return "Room: " + roomName + "\n\n" + description;
	}

	/**
	 * Get the name of the room
	 * @return the room name
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 *Sets the current room to the room being passed in (parameter roomName)
	 * @param roomName the name of the room you are currently in
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	/**
	 * Get the short description of the room
	 * @return the string description of the room
	 */
	public String getShortDescription() {
		return description;
	}
	
	/**
	 * Get the long description of the room
	 * @return the string description of the room
	 */
	public String getLongDescription() {
		String ret = description + " ";
		for (int i = 0; i < roomItems.size(); i++) {
			Item currItem = roomItems.get(i);
			ret += "There is a" + ("aeiou".indexOf(currItem.getName().charAt(0)) != -1 ? "n " : " ");
			ret += currItem.getName();
			ret += " " + currItem.getDescription("location") + ". ";
		}
		//Side[] sides = OH NO WHAT?? ROOM CAN'T ACCESS ITS OWN SIDES?? NO WAY!
		return ret;
	}

	/**
	 * Set your description being passed in as the current description
	 * @param description the description to be set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Inventory getRoomItems() {
		return roomItems;
	}
}
