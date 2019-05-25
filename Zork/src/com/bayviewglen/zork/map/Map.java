package com.bayviewglen.zork.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.lang.Character;

//import com.bayviewglen.zork.main.DirectionHelper;

public class Map {

	private Place[][][] map;
	private String mapName;

	public static final char[] LETTER_AXES = { 'n', 's', 'u', 'd', 'e', 'w' };
	public static final HashMap<Character, Coordinate> DIRECTIONS = (HashMap<Character, Coordinate>) (java.util.Map
			.ofEntries(java.util.Map.entry('n', new Coordinate(0, -1, 0)),
					java.util.Map.entry('s', new Coordinate(0, 1, 0)),
					java.util.Map.entry('e', new Coordinate(1, 0, 0)),
					java.util.Map.entry('w', new Coordinate(-1, 0, 0)),
					java.util.Map.entry('u', new Coordinate(0, 0, 1)),
					java.util.Map.entry('d', new Coordinate(0, 0, -1))));

	// private Location location;//phase, map, maxcoords

	/**
	 * map constructor - stores places (rooms/sides) *** very important
	 * ***everything is doubled into the 2D array *** a place's x/y/z-value is
	 * double in the array
	 * 
	 * @param x          x-axis (east-west)
	 * @param y          y-axis (north-south)
	 * @param z          z-axis (up-down)
	 * @param checkpoint your starting point in the map
	 * @param goal       your ending point in the map
	 */
	public Map(String mapName, Coordinate maxCoords) {
		this.mapName = mapName;
		int x = (int) ((maxCoords.getX() + 1) * 2);
		int y = (int) ((maxCoords.getY() + 1) * 2);
		int z = (int) ((maxCoords.getZ() + 1) * 2);
		// this.location = location;
		map = new Place[x][y][z];
		Maps.add(this);
	}

	public Map(String mapName) {
		Map m = Maps.getMap(mapName);
		this.map = m.map;
		this.mapName = m.mapName;
	}

	/**
	 * set's a place onto the map ***remember that rooms are on the half (.5) and
	 * sides are on the whole number
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
	 * get the place at the given x,y,z coordinates if the coordinates are out of
	 * bounds, returns null - no room there
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
			return (Room) this.getPlace(coords);
		} catch (Exception InvalidCastException) {
			return null;
		}
	}

	public Side getSide(Coordinate coords) {
		try {
			return (Side) this.getPlace(coords);
		} catch (Exception InvalidCastException) {
			return null;
		}
	}

//	public Location getLocation() {
//		return location;
//	}

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
	 * traversing through the 3D array
	 * 
	 * @param r the room you are looking for
	 * @return the rooms coordinates, displayed in a double array (if the room isn't
	 *         there return null)
	 */
	public Coordinate getCoords(Room r) {
		for (double i = 0.5; i < map.length * 2; i++) {
			for (double j = 0.5; j < map[(int) (i * 2)].length * 2; j++) {
				for (double k = 0.5; k < map[(int) (i * 2)][(int) (j * 2)].length * 2; k++) {
					if (this.getRoom(new Coordinate(i, j, k)).equals(r))
						return new Coordinate(i, j, k);
				}
			}
		}
		return null;
	}

	/**
	 * check if the room is in the map (if it has coords)
	 * 
	 * @param r the room you are looking for
	 * @return true if it's there
	 */
	public boolean roomInMap(Room r) {
		return this.getCoords(r) != null;
	}

	/**
	 * get all the sides of a room
	 * 
	 * @param r the room that you want sides from
	 * @return a HashMap&lt;Character,Side&gt; displaying the &lt;direction, side in
	 *         that direction&gt;
	 */
	public HashMap<Character, Coordinate> getSurroundingSideCoords(Coordinate coords) {

		HashMap<Character, Coordinate> surroundingSideCoords = new HashMap<Character, Coordinate>();
		HashMap<Character, Coordinate> surroundingCoords = getSurroundingCoords(coords, 1);

		Iterator<Entry<Character, Coordinate>> it = surroundingCoords.entrySet().iterator();
		while (it.hasNext()) {
			java.util.Map.Entry<Character, Coordinate> pair = (java.util.Map.Entry<Character, Coordinate>) it.next();
			char key = (char) pair.getKey();
			Coordinate surrCoords = (Coordinate) pair.getValue();
			surroundingSideCoords.put(key, surrCoords);
		}

		return surroundingSideCoords;
	}

	/**
	 * get all the surrounding rooms
	 * 
	 * @param r the room you want to find surrounding rooms
	 * @return a HashMap&lt;Character, Room&gt; displaying the &lt;direction, room
	 *         in that direction&gt;
	 */
	public HashMap<Character, Coordinate> getSurroundingRoomCoords(Coordinate coords) {

		HashMap<Character, Coordinate> surroundingRoomCoords = new HashMap<Character, Coordinate>();
		HashMap<Character, Coordinate> surroundingCoords = getSurroundingCoords(coords, 1);

		Iterator<Entry<Character, Coordinate>> it = surroundingCoords.entrySet().iterator();
		while (it.hasNext()) {
			java.util.Map.Entry<Character, Coordinate> pair = (java.util.Map.Entry<Character, Coordinate>) it.next();
			char key = (char) pair.getKey();
			Coordinate surrCoords = (Coordinate) pair.getValue();
			surroundingRoomCoords.put(key, surrCoords);
		}
		return surroundingRoomCoords;
	}

	private HashMap<Character, Coordinate> getSurroundingCoords(Coordinate coords, double factor) {

		HashMap<Character, Coordinate> surroundingCoords = new HashMap<Character, Coordinate>();

		Iterator<Entry<Character, Coordinate>> it = DIRECTIONS.entrySet().iterator();
		while (it.hasNext()) {
			java.util.Map.Entry<Character, Coordinate> pair = (java.util.Map.Entry<Character, Coordinate>) it.next();
			char key = (char) pair.getKey();
			Coordinate dirCoord = (Coordinate) pair.getValue();
			surroundingCoords.put(key, dirCoord.add(coords.multiply(factor)));
		}

		return surroundingCoords;
	}

	/**
	 * get the room in a given direction
	 * 
	 * @param dir the direction you are searching
	 * @param r   the room from where you are searching from
	 * @return the room in that direction
	 */

	public Coordinate getNextRoomCoords(char dir, Coordinate coords) {
		HashMap<Character, Coordinate> surroundingRooms = this.getSurroundingRoomCoords(coords);
		return surroundingRooms.get(dir);
	}

	public Room getNextRoom(char dir, Coordinate coords) {
		return getRoom(getNextRoomCoords(dir, coords));
	}

	/**
	 * @param r1 room1
	 * @param r2 room2
	 * @return side between room1 and room2, null if there's no surrounding side
	 */
	public Side getSideBetween(Room r1, Room r2) {
		return getSide(r1.getLocation().getCoords().average(r2.getLocation().getCoords()));
	}

	/**
	 * get the side in a given direction
	 * 
	 * @param dir the direction you are searching
	 * @param r   the room from where you are searching from
	 * @return the side in that direction
	 */
	public Coordinate getNextSideCoords(char dir, Coordinate coords) {
		HashMap<Character, Coordinate> surroundingSides = this.getSurroundingSideCoords(coords);
		return surroundingSides.get(dir);
	}

	public Side getNextSide(char dir, Coordinate coords) {
		return getSide(getNextSideCoords(dir, coords));
	}

	/**
	 * make an arraylist of all the exits from a room it's an exit if it 1) has a
	 * non-null neighboring room and 2) if the side in that direction is an opening
	 * or a door
	 * 
	 * @param coords the coordinate
	 * @return an arrayList of all exits
	 */
	public ArrayList<Character> getExits(Coordinate coords) {
		ArrayList<Character> exits = new ArrayList<Character>();

		for (char dir : LETTER_AXES) {
			if (getNextRoomCoords(dir, coords) != null && (getNextSide(dir, coords).isExit()))
				exits.add(dir);
		}
		return exits;
	}

	public boolean isExit(char dir, Coordinate coords) {
		ArrayList<Character> exits = getExits(coords);
		for (char c : exits) {
			if (c == dir)
				return true;
		}
		return false;
	}

	public int numRooms() {
		int count = 0;

		for (double i = 0.5; i < map.length * 2; i++) {
			for (double j = 0.5; j < map[(int) (i * 2)].length * 2; j++) {
				for (double k = 0.5; k < map[(int) (i * 2)][(int) (j * 2)].length * 2; k++) {
					if (!isEmpty(new Coordinate(i, j, k)))
						count++;
				}
			}
		}
		return count;
	}

	public String getName() {
		return mapName;
	}

	public Place[][][] getMap() {
		return map;
	}

	public boolean equals(Map map) {
		return this.mapName.equals(map.getName());
	}

	/**
	 * traverses through 3D array of map makes sure that rooms are on the .5 and
	 * sides are on the whole number
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

//
//	public static ArrayList<Character> loadCharacters(String filePath) {
//		FileReader reader = new FileReader(filePath);
//		String[] lines = reader.getLines();
//		ArrayList<Character> characters = new ArrayList<Character>();
//
//		String concat = "";
//
//		for (String s : lines) {
//			concat += s + "\n";
//		}
//
//		JSONObject obj = new JSONObject(concat);
//		JSONArray textCharacters = obj.getJSONArray("characters");
//
//		for (int i = 0; i < textCharacters.length(); i++) {
//			JSONObject curr = textCharacters.getJSONObject(i);
//
//			// double[]coords = {curr.getDouble("phase"), curr.getDouble("map"),
//			// curr.getDouble("x"), curr.getDouble("y"), curr.getDouble("z")};
//			Coordinate coords = Coordinate.readCoords(curr.getString("coords"));
//			MoveableLocation location = new MoveableLocation(curr.getString("map"), coords);
//			Inventory inventory = Inventory.loadInventory(curr.getString("inventory"));
//
//			HashMap<String, String> descriptions = new HashMap<String, String>();
//			JSONArray JSONDescriptions = curr.getJSONArray("descriptions");
//			for (int j = 0; j < JSONDescriptions.length(); j++) {
//				String temp = JSONDescriptions.getString(j);
//				int index = temp.indexOf(":");
//				descriptions.put(temp.substring(0, index), temp.substring(index + 1));
//			}
//
//		}
//		return characters;
//	}

}
