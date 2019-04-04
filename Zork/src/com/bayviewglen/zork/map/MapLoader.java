package com.bayviewglen.zork.map;

import com.bayviewglen.zork.item.Item;
import com.bayviewglen.zork.main.FileReader;
import org.json.*;

public class MapLoader {
	public static void main(String[] args) {
		Map map = loadMap("data/TestRoom.json");
	}

	public static Map loadMap(String FilePath) {
		FileReader mapReader = new FileReader(FilePath);
		String[] lines = mapReader.getLines();
		double[] maxCoords = new double[3];
		
		String concat = "";
		
		for (String s : lines) {
			concat += s + "\n";
		}
		
		JSONObject obj = new JSONObject(concat);
		String mapName = obj.getString("name");
		String startCoords = obj.getString("startcoords");
		String endCoords = obj.getString("endcoords");
		
		System.out.println(mapName);
		
		JSONArray places = obj.getJSONArray("places");
		
		for (int i = 0; i < places.length(); i++) {
			String coordsString = places.getJSONObject(i).getString("coords");
			double[] coords = readCoords(coordsString);
			for (int j = 0; j < 3; j++) {
				if (coords[j] > maxCoords[j]) // setting maximum coordinates
					maxCoords[j] = coords[j];
			}
		}
		
		System.out.println(places.length());
		
		Map map = new Map(mapName, maxCoords[0], maxCoords[1], maxCoords[2]);
		
		for (int i = 0; i < places.length(); i++) {
			JSONObject curr = places.getJSONObject(i);
			if (places.getJSONObject(i).getString("type").equals("room")) {
				Room r = new Room(curr.getString("name"), curr.getString("description"));
				double[] coords = readCoords(curr.getString("coords"));
				map.set(r, coords[0], coords[1], coords[2]);
			} else if (places.getJSONObject(i).getString("type").equals("door")) {
				Item key;
				Door d;
				
				try {
					key = new Item(curr.getString("keyname"), curr.getInt("itemweight"));
				} catch(JSONException ex) {
					key = null;
				}
				
				try {
					d = new Door(curr.getString("name"), curr.getBoolean("open"), curr.getBoolean("locked"), key);
				} catch(JSONException ex) {
					d = new Door(curr.getString("name"), false, curr.getBoolean("locked"), key);
				}
				double[] coords = readCoords(curr.getString("coords"));
				map.set(d, coords[0], coords[1], coords[2]);
			}
		}

		return map;
	}
	
	public static double[] readCoords (String line) {
		line.replaceAll("[ (){]", "");
		String[] coordsString = line.split(","); // split on comma to extract coords
		double[] coords = new double[3];
		for (int i = 0; i < 3; i++)
			coords[i] = Double.parseDouble(coordsString[i]);
		return coords;
	}

}
