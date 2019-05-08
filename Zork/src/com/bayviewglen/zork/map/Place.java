package com.bayviewglen.zork.map;

import java.util.HashMap;

import com.bayviewglen.zork.item.Item;

/**
 * A parent class used for storage of both rooms and sides
 * in maps
 * 
 * A Room is-a place
 * A Side is-a place
 *
 */
public abstract class Place extends Item{
	private Location location;

	public Place(String name, HashMap<String, String> descriptions, Location location) {
		super(name, Double.MAX_VALUE, descriptions);
		this.location = location;
	}
	
	public Place(String name, HashMap<String, String> descriptions) {
		super(name, Double.MAX_VALUE, descriptions);
		this.location = null;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location loc) {
		location = loc;
	}
	
}
