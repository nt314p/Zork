package com.bayviewglen.zork.map;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bayviewglen.zork.item.Item;
import com.bayviewglen.zork.main.*;

public class Maps {
	
	private static ArrayList<Map> mapList;
	
	public static void initialize() {
		mapList = new ArrayList<Map>();
		
		File dir = new File("data/maps/");
		File[] dirList = dir.listFiles();
		if (dirList != null) {
			for (File f : dirList) {
				loadMap(f.getAbsolutePath());
			}
		}
	}
	
	public static void add(Map map) {
		mapList.add(map);
	}
	

	public static Map getMap(int mapIndex) {
		return mapList.get(mapIndex);
	}
	
	
	public static ArrayList<Map> getMaps(){
		return mapList;
	}
	
	public static int getMapIndex(String mapName) {
		for(int i = 0; i<mapList.size(); i++) {
			if(mapList.get(i).getName().equals(mapName))
				return i;
		}
		return -1;
	}
	
	public static Map getMap(String mapName) {
		for(int i = 0; i<mapList.size(); i++) {
			if(mapList.get(i).getName().equals(mapName))
				return mapList.get(i);
		}
		return null;
	}
	
	private static void loadMap(String filePath) {
		FileReader mapReader = new FileReader(filePath);
		Coordinate maxCoords = new Coordinate();

		JSONObject obj = new JSONObject(mapReader.getLinesSingle()); // one line because JSONObject takes string
		String mapName = obj.getString("name");

		JSONArray jPlaces = obj.getJSONArray("places"); // getting array of places

		for (int i = 0; i < jPlaces.length(); i++) { // looping through places
			String coordsString = jPlaces.getJSONObject(i).getString("coords"); // getting coordinates
			String type = Item.loadItem(jPlaces.getJSONObject(i)).getClass().getSimpleName(); // VERY BAD
			double offset = 0; // if the type is a Room, there needs to be one more space for its surrounding sides
			if ("WallOpeningDoor".indexOf(type) != -1) {
				offset = 0.5;
			}
			Coordinate coords = Coordinate.readCoords(coordsString); // finding max coordinates

			if (coords.getX() > maxCoords.getX())
				maxCoords.setX(coords.getX() + offset);

			if (coords.getY() > maxCoords.getY())
				maxCoords.setY(coords.getY() + offset);

			if (coords.getZ() > maxCoords.getZ())
				maxCoords.setZ(coords.getZ() + offset);
		}

		Map tempMap = new Map(mapName, maxCoords); // creating map with max coords
		
		Side defaultSideV = null;
		Side defaultSideH = null;
		Room defaultRoom = null;
		Side defaultBorder = null;
		Side defaultGround = null;
		
		if (obj.has("default-side-vertical")) {
			defaultSideV = (Side) Item.loadItem(obj.getJSONObject("default-side-vertical"));
			

			// filling vertical sides
			for (int i = 0; i < tempMap.getMap().length; i++) { // x
				for (int j = (i + 1) % 2; j < tempMap.getMap()[0].length; j += 2) { // y
					for (int k = 1; k < tempMap.getMap()[0][0].length; k += 2) { // z
						defaultSideV.setLocation(new Location(mapName, new Coordinate(i, j, k, true)));
						tempMap.getMap()[i][j][k] = defaultSideV;
					}
				}
			}
		}
		
		if (obj.has("default-side-horizontal")) {
			defaultSideH = (Side) Item.loadItem(obj.getJSONObject("default-side-horizontal"));
			// filling horizontal sides
			for (int i = 1; i < tempMap.getMap().length; i += 2) { // x
				for (int j = 1; j < tempMap.getMap()[0].length; j += 2) { // y
					for (int k = 0; k < tempMap.getMap()[0][0].length; k += 2) { // z
						defaultSideH.setLocation(new Location(mapName, new Coordinate(i, j, k, true)));
						tempMap.getMap()[i][j][k] = defaultSideH;
					}
				}
			}
		}
		
		if (obj.has("default-border")) {
			defaultBorder = (Side) Item.loadItem(obj.getJSONObject("default-border"));
			
			for (int i = 0; i <= tempMap.getMap().length; i += tempMap.getMap().length-1) {
				for (int j = 1; j < tempMap.getMap()[0].length; j += 2) {
					for (int k = 1; k < tempMap.getMap()[0][0].length; k += 2) { // z
						defaultBorder.setLocation(new Location(mapName, new Coordinate(i, j, k, true)));
						tempMap.getMap()[i][j][k] = defaultBorder;
					}
				}
			}
			
			for (int i = 1; i < tempMap.getMap().length; i += 2) {
				for (int j = 0; j <= tempMap.getMap()[0].length; j += tempMap.getMap()[0].length-1) {
					for (int k = 1; k < tempMap.getMap()[0][0].length; k += 2) { // z
						defaultBorder.setLocation(new Location(mapName, new Coordinate(i, j, k, true)));
						tempMap.getMap()[i][j][k] = defaultBorder;
					}
				}
			}
		}
		
		if (obj.has("default-ground")) {
			defaultGround = (Side) Item.loadItem(obj.getJSONObject("default-ground"));
			
			for (int i = 1; i < tempMap.getMap().length; i += 2) { // x
				for (int j = 1; j < tempMap.getMap()[0].length; j += 2) { // y
					for (int k = 0; k <= tempMap.getMap()[0][0].length; k += tempMap.getMap()[0][0].length-1) { // z
						defaultGround.setLocation(new Location(mapName, new Coordinate(i, j, k, true)));
						tempMap.getMap()[i][j][k] = defaultGround;
					}
				}
			}
		}
		
		if (obj.has("default-room")) {
			defaultRoom = (Room) Item.loadItem(obj.getJSONObject("default-room"));
			for (int i = 1; i < tempMap.getMap().length; i += 2) { // x
				for (int j = 1; j < tempMap.getMap()[0].length; j += 2) { // y
					for (int k = 1; k < tempMap.getMap()[0][0].length; k += 2) { // z
						defaultRoom.setLocation(new Location(mapName, new Coordinate(i, j, k, true)));
						tempMap.getMap()[i][j][k] = defaultRoom;
					}
				}
			}
		}

		for (int i = 0; i < jPlaces.length(); i++) { // looping through places

			Coordinate coords = Coordinate.readCoords(jPlaces.getJSONObject(i).getString("coords"));
			Location aLocation = new Location(mapName, coords);

			Place p = (Place) Item.loadItem(jPlaces.getJSONObject(i));
			p.setLocation(aLocation);
			tempMap.set(p, coords);
		}

		mapList.add(tempMap);
	}

}
