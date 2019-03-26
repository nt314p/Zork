package com.bayviewglen.map;

import com.bayviewglen.zork.FileReader;

public class MapLoader {
	public static void main(String[] args) {
		
	}
	
	public Map loadMap(String FilePath) {
		FileReader mapReader = new FileReader(FilePath);
		String[] lines = mapReader.getLines();
		int[] maxCoords = new int[3];
		
		for(String line : lines) {
			// gets the coordinates in a string
			if (line.charAt(0) == '(') {
				line.replace("[ (){]" ,'\0');
				String[] coordsString = line.split(",");
				int[] coords = new int[3];
				for (int i = 0; i < 3; i++)
					coords[i] = Integer.parseInt(coordsString[i]);
				for (int i = 0; i < 3; i++) {
					if (coords[i] > maxCoords[i])
						maxCoords[i] = coords[i];
				}
			}
		}
		
		Map map = new Map(maxCoords[0], maxCoords[1], maxCoords[2]);
		
		return map;
	}
	
	
}
