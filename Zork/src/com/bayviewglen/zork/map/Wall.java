package com.bayviewglen.zork.map;

import java.util.HashMap;

public class Wall extends Side {
	
	private static final boolean isExit = false;

	public Wall(String name, HashMap<String, String> descriptions, Location location) {
		super(name, descriptions, isExit, location);
	}
	
	public String toString() {
		return getClass().getSimpleName();
	}
	
}
