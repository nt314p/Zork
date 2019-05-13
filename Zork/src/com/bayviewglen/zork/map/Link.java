package com.bayviewglen.zork.map;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Link {
	
	private static HashMap<Location, Location> links = new HashMap<Location, Location>();
	
	/**
	 * 
	 * @param loc1
	 * @param loc2
	 * @param dir -1 = back, 0 = both, 1 = forward
	 */
	public static void add(Location loc1, Location loc2, int dir) {
		if(dir == 1 || dir == 0)
			links.put(loc1, loc2);
		if (dir == -1 || dir == 0)
			links.put(loc1, loc2);
	}
	
	public static boolean hasLink(Location loc) {
		return links.get(loc) != null;
	}
	
	public static Location getLink(Location loc) {
		return links.get(loc);
	}
	
	public static void remove(Location loc) {
		links.remove(loc);
	}
	
	public static HashMap<Location, Location> getLinks() {
		return links;
	}
	
	
}
