package com.bayviewglen.zork.map;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bayviewglen.zork.main.FileReader;

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
			links.put(loc2, loc1);
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
	
	public static void loadLinks(String filePath) {
		FileReader reader = new FileReader(filePath);
		JSONObject obj = new JSONObject(reader.getLinesSingle());
		JSONArray textLinks = obj.getJSONArray("links");
		
		for(int i = 0; i<textLinks.length(); i++) {
			JSONObject jObj = textLinks.getJSONObject(i);
			int dir = jObj.getInt("dir");
			
			String mapNameA = jObj.getString("map1");
			Coordinate coordsA = new Coordinate(jObj.getString("coords1"));
			Location a = new Location(mapNameA, coordsA);
			
			String mapNameB = jObj.getString("map2");
			Coordinate coordsB = new Coordinate(jObj.getString("coords2"));
			Location b = new Location(mapNameB, coordsB);

			Link.add(a, b, dir);
		}
	}
	
	
}
