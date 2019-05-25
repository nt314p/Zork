package com.bayviewglen.zork.map;

import java.util.HashMap;

public class Opening extends Side {
	
	private static final boolean isExit = true;
	
	public Opening(String name, HashMap<String, String> descriptions, Location location) {
		super(name, descriptions, isExit, location);
	}
	
	public Opening(String name, HashMap<String, String> descriptions) {
		super(name, descriptions, isExit);
	}
	
	public Opening(Opening opening) {
		super(opening.getName(), opening.getDescriptions(), opening.isExit());
	}
	
	public String moveThrough() {
		return ""; // Hardcoding at its finest
	}
	
	public String toString() {
		return getClass().getSimpleName();
	}

}
