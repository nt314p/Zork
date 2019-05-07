package com.bayviewglen.zork.map;

import java.util.HashMap;

public class Opening extends Side {
	
	private static final boolean isExit = true;
	
	public Opening(String name, HashMap<String, String> descriptions, Location location) {
		super(name, descriptions, isExit, location);
	}
	
	public String toString() {
		return getClass().getSimpleName();
	}

}
