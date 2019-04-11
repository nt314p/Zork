package com.bayviewglen.zork.map;

/**
 * A parent class used for storage of both rooms and sides
 * in maps
 * 
 * A Room is-a place
 * A Side is-a place
 *
 */
public abstract class Place {
	private Location location;

	public Place(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return location;
	}
	
}
