package com.bayviewglen.map;

import com.bayviewglen.zork.FileReader;
import org.json.*;

public class MapLoader {
	public static void main(String[] args) {
		Map map = loadMap("data/jasontest.json");
	}

	public static Map loadMap(String FilePath) {
		FileReader mapReader = new FileReader(FilePath);
		String[] lines = mapReader.getLines();
		double[] maxCoords = new double[3];
		
		String mastaboi = ""; //change name
		
		for (String s : lines) {
			mastaboi += s + "\n";
		}
		
		JSONObject obj = new JSONObject(mastaboi);
		String mapName = obj.getString("name");
		String startCoords = obj.getString("startcoords");
		String endCoords = obj.getString("endcoords");
		
		System.out.println(mapName);
		
		JSONArray places = obj.getJSONArray("places");
		
		for (int i = 0; i < places.length(); i++) {
			String coordsString = places.getJSONObject(i).getString("coords");
			int[] coords = readCoords(coordsString);
			for (int j = 0; j < 3; j++) {
				if (coords[j] > maxCoords[j]) // setting maximum coordinates
					maxCoords[j] = coords[j];
			}
		}
		
		Map map = new Map(mapName, maxCoords[0], maxCoords[1], maxCoords[2]);
		
		for (int i = 0; i < places.length(); i++) {
			if (places.getJSONObject(i).getString("type").equals("room")) {
				
			} else if (places.getJSONObject(i).getString("type").equals("door")) {
				System.out.println("ITS A GIRL");
			}
			
//			String room = places.getJSONObject(i).getString("coords");
//			System.out.println(room);
		}

		/*
		for (String line : lines) {
			// gets the coordinates in a string
			if (line.charAt(0) == '(') {
				numPlaces++;
				int[] coords = readCoords(line); // reading out coords from line
				for (int i = 0; i < 3; i++) {
					if (coords[i] > maxCoords[i]) // setting maximum coordinates
						maxCoords[i] = coords[i];
				}
			}
		}
		
		// create new map with max coords
		Map map = new Map(maxCoords[0], maxCoords[1], maxCoords[2]); 
		
		int lineNum = 0;
		for (int i = 0; i < numPlaces; i++) {
			int[] coords;
			String type;
			String keyName;
			String line = lines[lineNum];
			if (line.charAt(0) == '(') {
				int[] coords = readCoords(line);
			}
		}
*/
		return map;
	}
	
	public static int[] readCoords (String line) {
		line.replaceAll("[ (){]", "");
		String[] coordsString = line.split(","); // split on comma to extract coords
		int[] coords = new int[3];
		for (int i = 0; i < 3; i++)
			coords[i] = Integer.parseInt(coordsString[i]);
		return coords;
	}

}
