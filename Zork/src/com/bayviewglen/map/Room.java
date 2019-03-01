package com.bayviewglen.map;

/*
 * Class Room - a room in an adventure game.
 *
 * Author:  Michael Kolling
 * Version: 1.1
 * Date:    August 2000
 * 
 * This class is part of Zork. Zork is a simple, text based adventure game.
 *
 * "Room" represents one location in the scenery of the game.  It is 
 * connected to at most four other rooms via exits.  The exits are labelled
 * north, east, south, west.  For each direction, the room stores a reference
 * to the neighbouring room, or null if there is no exit in that direction.
 */
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

public class Room {
	private String roomName;
	private String description;
	private HashMap<String, Room> exits; // stores exits of this room.

	/**
	 * Create a room described "description". Initially, it has no exits.
	 * "description" is something like "a kitchen" or "an open court yard".
	 */
	public Room(String description) {
		this.description = description;
		exits = new HashMap<String, Room>();
	}

	public Room() {
		// default constructor.
		roomName = "DEFAULT ROOM";
		description = "DEFAULT DESCRIPTION";
		exits = new HashMap<String, Room>();
	}
	
	/**
	 * a .equals method comparing 2 rooms - roomName and roomDescription
	 * @param room the room to compare
	 * @return if they are identical
	 */
	public boolean equals(Room room) {
		return roomName.equals(room.roomName) && description.equals(room.description);
	}

	/**
	 * Places player room and direction wishing to go on the exits map to later confirm if move is possible.	 * 
	 * 
	 * @param direction the direction player wishes to go
	 * @param r the room the player is in
	 * @throws Exception
	 */
	public void setExit(char direction, Room r) throws Exception {
		String dir = "";
		switch (direction) {
		case 'E':
			dir = "east";
			break;
		case 'W':
			dir = "west";
			break;
		case 'S':
			dir = "south";
			break;
		case 'N':
			dir = "north";
			break;
		case 'U':
			dir = "up";
			break;
		case 'D':
			dir = "down";
			break;
		default:
			throw new Exception("Invalid Direction");

		}

		exits.put(dir, r);
	}

	/**
	 * Find the exits of this room, put them on the map.
	 * All directions either lead to another room or contain null in that direction (no exits).
	 * 
	 * @param room the room you are in 
	 * @param north the room to your north
	 * @param east the room to your east
	 * @param south the room to your south
	 * @param west the room to your west
	 * @param up the room above
	 * @param down the room below
	 */
	public void setExits(Room north, Room east, Room south, Room west, Room up, Room down) {
		if (north != null)
			exits.put("north", north);
		if (east != null)
			exits.put("east", east);
		if (south != null)
			exits.put("south", south);
		if (west != null)
			exits.put("west", west);
		if (up != null)
			exits.put("up", up);
		if (up != null)
			exits.put("down", down);

	}

	/**
	 * Gives the short description - defined in constructor
	 * @return the short description of the room
	 */
	public String shortDescription() {
		return "Room: " + roomName + "\n\n" + description;
	}

	/**
	 * Gives the long description of the room, on the form:
	 * You are in the kitchen. You see stairs down, a ladder up, a closed window, and a pile of leaves.
	 * Exits: north west
	 * 
	 * @return the long description of the room
	 */
	public String longDescription() {

		return "Room: " + roomName + "\n\n" + description + "\n" + exitString();
	}

	/**
	 * Write up the exits of the room - ex. "Exits: north, south, up"
	 * @return string containing room's exits
	 */
	private String exitString() {//maybe a for loop would work best for this - control where you want a comma
		String returnString = "Exits:";
		Set keys = exits.keySet();
		for (Iterator iter = keys.iterator(); iter.hasNext();)
			returnString += ", " + iter.next();
		return returnString;
	}

	/**
	 * @param direction the direction wishing to go
	 * @return the room in that direction, or if no room return null
	 */
	public Room nextRoom(String direction) {
		return (Room) exits.get(direction);
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
	 * Get the description of the room
	 * @return the string description of the room
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set your description being passed in as the current description
	 * @param description the description to be set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
