package com.bayviewglen.zork.map;

import java.util.HashMap;

public class Wall extends Side {
	
	private static final boolean isExit = false;

	public Wall(String name, HashMap<String, String> descriptions, Location location) {
		super(name, descriptions, isExit, location);
	}
	
	public Wall(String name, HashMap<String, String> descriptions) {
		super(name, descriptions, isExit);
	}
	
	public Wall(Wall wall) {
		super(wall.getName(), wall.getDescriptions(), wall.isExit(), wall.getLocation());
	}
	
	public String moveThrough() {
		return "You bumped into the " + getName();
	}
	
	public String toString() {
		return getClass().getSimpleName();
	}
	
}
