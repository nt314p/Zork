package com.bayviewglen.zork.map;

import java.util.ArrayList;
import java.util.HashMap;

public class Link {
	
	private static HashMap<Location, Location> links = new HashMap<Location, Location>();
	
	/**
	 * 
	 * @param coord1
	 * @param coord2
	 * @param dir -1 = back, 0 = both, 1 = forward
	 */
	public Link(Location coord1, Location coord2, int dir) {
		if(dir == 1 || dir == 0)
			links.put(coord1, coord2);
		if (dir == -1 || dir == 0)
			links.put(coord2, coord1);
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
