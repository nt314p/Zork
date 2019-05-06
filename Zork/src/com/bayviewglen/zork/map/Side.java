package com.bayviewglen.zork.map;

import java.util.HashMap;

/**
 * Parent class for doors, openings, and walls
 * 
 * Uses polymorphism to store an array f
 * 
 *
 */

public abstract class Side extends Place{
	private boolean isExit;
	
	public Side(String name, HashMap<String, String> descriptions, boolean isExit, Location location) {
		super(name, descriptions, location);
		this.isExit = isExit;
	}
	
	public boolean isExit() {
		return isExit;
	}
	
	public void setExit(boolean isExit) {
		this.isExit = isExit;
	}
	
	public abstract String toString();
	
}
