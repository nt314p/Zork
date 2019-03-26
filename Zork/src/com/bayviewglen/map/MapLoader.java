package com.bayviewglen.map;

import com.bayviewglen.zork.FileReader;

public class MapLoader {
	public static void main(String[] args) {

	}

	public Map loadMap(String FilePath) {
		FileReader mapReader = new FileReader(FilePath);
		String[] lines = mapReader.getLines();
		int[] maxCoords = new int[3];

		for (String line : lines) {
			// gets the coordinates in a string
			if (line.charAt(0) == '(') {
				int[] coords = readCoords(line); // reading out coords from line
				for (int i = 0; i < 3; i++) {
					if (coords[i] > maxCoords[i]) // setting maximum coordinates
						maxCoords[i] = coords[i];
				}
			}
		}
		
		// create new map with max coords
		Map map = new Map(maxCoords[0], maxCoords[1], maxCoords[2]); 
		
		for (String line : lines) {
			if (line.charAt(0) == '(') {
				int[] coords = readCoords(line);
			}
		}

		return map;
	}
	
	public int[] readCoords (String line) {
		line.replaceAll("[ (){]", "");
		String[] coordsString = line.split(","); // split on comma to extract coords
		int[] coords = new int[3];
		for (int i = 0; i < 3; i++)
			coords[i] = Integer.parseInt(coordsString[i]);
		return coords;
	}

}
