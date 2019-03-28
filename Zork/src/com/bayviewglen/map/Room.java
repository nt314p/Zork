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

import com.bayviewglen.zork.*;

import java.util.HashMap;
import java.util.Iterator;

public class Room extends Place{
	private String roomName;
	private String description;
	private Inventory roomItems;

	/**
	 * Create a room described "description". Initially, it has no exits.
	 * "description" is something like "a kitchen" or "an open court yard".
	 */
	public Room(String roomName, String description) {
		this.roomName = roomName;
		this.description = description;
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
	
	public Inventory getRoomItems() {
		return roomItems;
	}
}
