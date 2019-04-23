package com.bayviewglen.zork.map;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.main.FileReader;
import com.bayviewglen.zork.main.Game;
import com.bayviewglen.zork.map.*;

import java.util.ArrayList;

public class Map {
	
	private static HashMap<String, Map> mapList = new HashMap<String, Map>();

	private Place[][][] map;
	private String mapName;
	private final char[] directions = {'n','e','s','w','u','d'};
	
	private Room checkpoint;
	private Room goal;
	private Location location;//phase, map, maxcoords

	/**
	 * map constructor - stores places (rooms/sides)
	 * *** very important ***everything is doubled into the 2D array
	 * 				*** a place's x/y/z-value is double in the array
	 * 
	 * @param x x-axis (east-west)
	 * @param y y-axis (north-south)
	 * @param z z-axis (up-down)
	 * @param checkpoint your starting point in the map
	 * @param goal your ending point in the map
	 */
	public Map(String mapName, Location location) {
		this.mapName = mapName;
		this.location = location;
		Coordinate maxCoords = location.getCoords();
		map = new Place[(int) (maxCoords.getX() * 2)+1][(int) (maxCoords.getY() * 2)+1][(int) (maxCoords.getZ() * 2)+1];
		mapList.put(mapName, this);
	}

	/**
	 * set's a place onto the map
	 * ***remember that rooms are on the half (.5) and sides are on the whole number
	 * 
	 * @param p the new Place to be added
	 * @param x the new x-value (east-west)
	 * @param y the new y-value (north-south)
	 * @param z the new z-value (up-down)
	 */
	public void set(Place p, Coordinate coords) {
		map[(int) (coords.getX() * 2)][(int) (coords.getY() * 2)][(int) (coords.getZ() * 2)] = p;
	}

	/**
	 * get the place at the given x,y,z coordinates
	 * if the coordinates are out of bounds, returns null - no room there
	 * 
	 * @param x the x-value (east-west)
	 * @param y the y-value (north-south)
	 * @param z the z-value (up-down)
	 * @return the place at the coordinates
	 */
	public Place getPlace(Coordinate coords) {
		try {
			return map[(int) (coords.getX() * 2)][(int) (coords.getY() * 2)][(int) (coords.getZ() * 2)];
		} catch (Exception IndexOutOfBoundsException) {
			return null;
		}
	}
	
	public Room getRoom(Coordinate coords) {
		try {
			return (Room)this.getPlace(coords);
		} catch (Exception InvalidCastException){
			return null;			
		}
	}
	
	public Location getLocation() {
		return location;
	}

	/**
	 * checks if the room at the given coordinates is empty (null)
	 * 
	 * @param x the x-value (east-west)
	 * @param y the y-value (north-south)
	 * @param z the z-value (up-down)
	 * @return true if null
	 */
	public boolean isEmpty(Coordinate coords) {
		return getPlace(coords) == null;
	}
	/**
	 * get the coordinates (x,y,z) of a room with a simple sequential search method
	 *  traversing through the 3D array
	 *  
	 * @param r the room you are looking for
	 * @return the rooms coordinates, displayed in a double array (if the room isn't there return null)
	 */
	public Coordinate getCoords(Room r) {

		for (double i = 0.5; i < map.length*2; i++) {
			for (double j = 0.5; j < map[(int)(i*2)].length*2; j++) {
				for (double k = 0.5; k < map[(int)(i*2)][(int)(j*2)].length*2; k++) {
						if(this.getRoom(new Coordinate(i, j, k)).equals(r))
							return new Coordinate(i, j, k);
				}
			}
		}
		return null;

	}

	/**
	 * check if the room is in the map (if it has coords)
	 * @param r the room you are looking for
	 * @return true if it's there
	 */
	public boolean roomInMap(Room r) {
		return this.getCoords(r) != null;
	}

	/**
	 * get all the sides of a room
	 * @param r the room that you want sides from
	 * @return a HashMap&lt;Character,Side&gt; displaying the &lt;direction, side in that direction&gt;
	 */
	public HashMap<Character, Side> getRoomSides(Room r) {
		HashMap<Character, Side> roomSides = new HashMap<Character, Side>();

		Coordinate coords = this.getCoords(r);
		int x = (int) (coords.getX() * 2);
		int y = (int) (coords.getY() * 2);
		int z = (int) (coords.getZ() * 2);

		roomSides.put('e', (Side) this.getPlace(new Coordinate(x - 0.5, y, z)));
		roomSides.put('w', (Side) this.getPlace(new Coordinate(x + 0.5, y, z)));
		roomSides.put('n', (Side) this.getPlace(new Coordinate(x, y - 0.5, z)));
		roomSides.put('s', (Side) this.getPlace(new Coordinate(x, y + 0.5, z)));
		roomSides.put('u', (Side) this.getPlace(new Coordinate(x, y, z + 0.5)));
		roomSides.put('d', (Side) this.getPlace(new Coordinate(x, y, z - 0.5)));

		return roomSides;

	}
	
	/**
	 * get all the surrounding rooms
	 * @param r the room you want to find surrounding rooms
	 * @return a HashMap&lt;Character, Room&gt; displaying the &lt;direction, room in that direction&gt;
	 */
	public HashMap<Character, Room> getSurroundingRooms(Room r){
		
		HashMap<Character, Room> surroundingRooms = new HashMap<Character, Room>();

		Coordinate coords = this.getCoords(r);
		int x = (int) (coords.getX() * 2);
		int y = (int) (coords.getY() * 2);
		int z = (int) (coords.getZ() * 2);
		
		surroundingRooms.put('e', (Room) this.getPlace(new Coordinate(x - 1, y, z)));
		surroundingRooms.put('w', (Room) this.getPlace(new Coordinate(x + 1, y, z)));
		surroundingRooms.put('n', (Room) this.getPlace(new Coordinate(x, y - 1, z)));
		surroundingRooms.put('s', (Room) this.getPlace(new Coordinate(x, y + 1, z)));
		surroundingRooms.put('u', (Room) this.getPlace(new Coordinate(x, y, z + 1)));
		surroundingRooms.put('d', (Room) this.getPlace(new Coordinate(x, y, z - 1)));

		return surroundingRooms;
		
	}
	
	/**
	 * get the room in a given direction
	 * @param dir the direction you are searching
	 * @param r the room from where you are searching from
	 * @return the room in that direction
	 */
	public Room getNextRoom(char dir, Room r) {
		HashMap<Character, Room> surroundingRooms = this.getSurroundingRooms(r);
		return surroundingRooms.get(dir);
	}
	
	/**
	 * get the side in a given direction
	 * @param dir the direction you are searching
	 * @param r the room from where you are searching from
	 * @return the side in that direction
	 */
	public Side getNextSide(char dir, Room r) {
		HashMap<Character, Side> roomSides = this.getRoomSides(r);
		return roomSides.get(dir);
	}
	
	/**
	 * make an arraylist of all the exits from a room
	 * 	it's an exit if it 1) has a non-null neighboring room and
	 * 2) if the side in that direction is an opening or a door
	 * @param r the room
	 * @return an arrayList of all exits
	 */
	public ArrayList<Character> getExits(Room r) {
		ArrayList<Character> exits = new ArrayList<Character>();
		
		for (char dir : directions) {
			Side side = this.getNextSide(dir, r);
			if (this.getNextRoom(dir, r) != null && (side.isExit()))
				exits.add(dir);
		}
		return exits;

	}
	
	public boolean isExit(char dir, Room r) {
		ArrayList<Character> exits = getExits(r);
		for(char c:exits) {
			if(c == dir)
				return true;
		}
		return false;
	}

	public Room getCheckpoint() {
		return checkpoint;
	}

	public Room getGoal() {
		return goal;
	}

	public void setCheckpoint(Room checkpoint) {
		this.checkpoint = checkpoint;
	}
	
	public void setGoal(Room goal) {
		this.goal = goal;
	}
  
	public int numRooms() {
		int count = 0;
		
		for (double i = 0.5; i < map.length*2; i++) {
			for (double j = 0.5; j < map[(int)(i*2)].length*2; j++) {
				for (double k = 0.5; k < map[(int)(i*2)][(int)(j*2)].length*2; k++) {
						if(!isEmpty(new Coordinate(i,j,k)))
							count++;
				}
			}
		}
		return count;
	}
	
	public String getMapName() {
		return mapName;
	}
	
	public boolean equals(Map map) {
		return this.mapName.equals(map.getMapName());
	}
	
	/**
	 * traverses through 3D array of map
	 * makes sure that rooms are on the .5 and sides are on the whole number
	 */
	public void checkMapBuildingErrors() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				for (int k = 0; k < map[i][j].length; k++) {
					if (i % 2 == 1 && j % 2 == 1) { // true if x or y ends in .5
						Place temp = map[i][j][k];
						if (temp instanceof Door || temp instanceof Wall || temp instanceof Opening)
							throw new IllegalStateException("The Side at coordinates " + (double) (i / 2) + ", "
									+ (double) (j / 2) + ", " + (double) (k / 2) + " should be a Room.");

					} else if (map[i][j][k] instanceof Room)
						throw new IllegalStateException("The Room at coordinates " + (double) (i / 2) + ", "
								+ (double) (j / 2) + ", " + (double) (k / 2) + " should be a Side.");
				}
			}
		}
	}
	
	public static Map loadMap(String filePath, int phaseNum, int mapNum) {
		FileReader mapReader = new FileReader(filePath);
		String[] lines = mapReader.getLines();
		Coordinate maxCoords = new Coordinate();
		
		String concat = "";
		
		for (String s : lines) {
			concat += s + "\n";
		}
		
		JSONObject obj = new JSONObject(concat);
		String mapName = obj.getString("name");
		
		JSONArray places = obj.getJSONArray("places");
		
		for (int i = 0; i < places.length(); i++) {
			String coordsString = places.getJSONObject(i).getString("coords");
			Coordinate coords = readCoords(coordsString);
			
			if(coords.getX() > maxCoords.getX())
				maxCoords.setX(coords.getX());
			
			if(coords.getY() > maxCoords.getY())
				maxCoords.setY(coords.getY());
			
			if(coords.getZ() > maxCoords.getZ())
				maxCoords.setZ(coords.getZ());
		}
		
		double [] temp = {phaseNum, mapNum, maxCoords.getX(), maxCoords.getY(), maxCoords.getZ()};
		Location mapLocation = new Location(temp);
		Map map = new Map(mapName, mapLocation);
		
		for (int i = 0; i < places.length(); i++) {
			JSONObject curr = places.getJSONObject(i);
			String type = places.getJSONObject(i).getString("type");
			
			Coordinate coords = readCoords(curr.getString("coords"));
			Location aLocation = new Location(phaseNum, mapNum, coords);
			
			if (type.equals("room") || type.equals("deathRoom")) {
				boolean isDeath = type.equals("deathRoom");
				
				Room r = new Room(curr.getString("name"), curr.getString("description"), aLocation, isDeath);
				map.set(r, coords);
			} else if (type.equals("door")) {
				Key key;
				Door d;
				
				try {
					JSONObject keyInfo = curr.getJSONObject("key");
					key = new Key(keyInfo.getString("name"), keyInfo.getDouble("weight"), null, keyInfo.getString("code"));
				} catch(JSONException ex) {
					key = null;
				}
				
				try {
					d = new Door(aLocation, curr.getString("name"), curr.getBoolean("open"), curr.getBoolean("locked"), key);
				} catch(JSONException ex) {
					d = new Door(aLocation, curr.getString("name"), false, curr.getBoolean("locked"), key);
				}
				map.set(d, coords);
				
			} else if(type.equals("wall")) {
				Wall wall = new Wall(aLocation);
				map.set(wall, coords);
				
			} else if(type.equals("opening")) {
				Opening opening = new Opening(aLocation);
				map.set(opening, coords);
			}
		}
		
		String startCoords = obj.getString("startcoords");
		Coordinate start = readCoords(startCoords);
		map.setCheckpoint(map.getRoom(start));
		
		String endCoords = obj.getString("endcoords");
		Coordinate end = readCoords(endCoords);
		map.setGoal(map.getRoom(end));
		
		return map;
	}
	
	private static Coordinate readCoords (String line) {
		line.replaceAll("[ (){]", "");
		String[] coordsString = line.split(","); // split on comma to extract coords
		double[] coords = new double[3];
		for (int i = 0; i < 3; i++)
			coords[i] = Double.parseDouble(coordsString[i]);
		return new Coordinate(coords);
	}

}
