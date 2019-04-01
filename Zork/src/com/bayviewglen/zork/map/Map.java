package com.bayviewglen.zork.map;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Map {

	private Place[][][] map;
	private String mapName;
	private final char[] directions = {'n','e','s','w','u','d'};
	
	private Room checkpoint;
	private Room goal;
	
	private Room currentRoom;
	private static ArrayList<Room> roomsVisited = new ArrayList<Room>();

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
	public Map(String mapName, double x, double y, double z) {
		map = new Place[(int) (x * 2)][(int) (y * 2)][(int) (z * 2)];
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
	public void set(Place p, double x, double y, double z) {
		map[(int) (x * 2)][(int) (y * 2)][(int) (z * 2)] = p;
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
	public Place get(double x, double y, double z) {
		try {
			return map[(int) (x * 2)][(int) (y * 2)][(int) (z * 2)];
		} catch (Exception IndexOutOfBoundsException) {
			return null;
		}

	}
	
	public Room getRoom(double x, double y, double z) {
		try {
			return (Room)this.get(x, y, z);
		} catch (Exception InvalidCastException){
			return null;			
		}
	}

	/**
	 * checks if the room at the given coordinates is empty (null)
	 * 
	 * @param x the x-value (east-west)
	 * @param y the y-value (north-south)
	 * @param z the z-value (up-down)
	 * @return true if null
	 */
	public boolean isEmpty(double x, double y, double z) {
		return map[(int) (x * 2)][(int) (y * 2)][(int) (z * 2)] == null;
	}

	/**
	 * get the coordinates (x,y,z) of a room with a simple sequential search method
	 *  traversing through the 3D array
	 *  
	 * @param r the room you are looking for
	 * @return the rooms coordinates, displayed in a double array (if the room isn't there return null)
	 */
	public double[] getCoords(Room r) {

		for (double i = 0.5; i < map.length*2; i++) {
			for (double j = 0.5; j < map[(int)(i*2)].length*2; j++) {
				for (double k = 0.5; k < map[(int)(i*2)][(int)(j*2)].length*2; k++) {
						if(this.getRoom(i, j, k).equals(r))
							return new double[] {i,j,k};
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

		double[] coords = this.getCoords(r);
		int x = (int) (coords[0] * 2);
		int y = (int) (coords[1] * 2);
		int z = (int) (coords[2] * 2);

		roomSides.put('e', (Side) this.get(x - 0.5, y, z));
		roomSides.put('w', (Side) this.get(x + 0.5, y, z));
		roomSides.put('n', (Side) this.get(x, y - 0.5, z));
		roomSides.put('s', (Side) this.get(x, y + 0.5, z));
		roomSides.put('u', (Side) this.get(x, y, z + 0.5));
		roomSides.put('d', (Side) this.get(x, y, z - 0.5));

		return roomSides;

	}
	
	/**
	 * get all the surrounding rooms
	 * @param r the room you want to find surrounding rooms
	 * @return a HashMap&lt;Character, Room&gt; displaying the &lt;direction, room in that direction&gt;
	 */
	public HashMap<Character, Room> getSurroundingRooms(Room r){
		
		HashMap<Character, Room> surroundingRooms = new HashMap<Character, Room>();

		double[] coords = this.getCoords(r);
		int x = (int) (coords[0] * 2);
		int y = (int) (coords[1] * 2);
		int z = (int) (coords[2] * 2);
		
		surroundingRooms.put('e', (Room) this.get(x - 1, y, z));
		surroundingRooms.put('w', (Room) this.get(x + 1, y, z));
		surroundingRooms.put('n', (Room) this.get(x, y - 1, z));
		surroundingRooms.put('s', (Room) this.get(x, y + 1, z));
		surroundingRooms.put('u', (Room) this.get(x, y, z + 1));
		surroundingRooms.put('d', (Room) this.get(x, y, z - 1));

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
			String nextSide = this.getNextSide(dir, r).getClass().getSimpleName();
			if (this.getNextRoom(dir, r) != null && nextSide.equals("Opening") || nextSide.equals("Door"))
				exits.add(dir);
		}
		return exits;

	}
	
	/**
	 * check if you are at the map's goal
	 * @return if your room equals the map goal
	 */
	public boolean atMapGoal() {
		return currentRoom.equals(goal);
	}
	
	/**
	 * check if you are at the map's checkpoint
	 * @return if your room equals the map checkpoint
	 */
	public boolean atCheckpoint() {
		return currentRoom.equals(checkpoint);
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
	
	
	public void updateRoomsVisited() {
		if(!hasVisited(currentRoom))
			roomsVisited.add(currentRoom);
	}
	
	public static boolean hasVisited(Room room) {
		for(Room i:roomsVisited) {
			if(i.equals(room))
				return true;
		}
		return false;
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room room) {
		currentRoom = room;
	}

	public void resetToCheckpoint() {
		currentRoom = checkpoint;
	}
	
	public int numRooms() {
		int count = 0;
		
		for (double i = 0.5; i < map.length*2; i++) {
			for (double j = 0.5; j < map[(int)(i*2)].length*2; j++) {
				for (double k = 0.5; k < map[(int)(i*2)][(int)(j*2)].length*2; k++) {
						if(!isEmpty(i,j,k))
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

					if (i % 2 == 1 && j % 2 == 1 && k % 2 == 1) {
						if (!map[i][j][k].getClass().getSimpleName().equals("Room"))
							throw new IllegalStateException("The room at coordinates " + (double) (i / 2) + ", "
									+ (double) (j / 2) + ", " + (double) (k / 2) + " SHOULD be a room.");

					} else if (map[i][j][k].getClass().getSimpleName().equals("Room"))
						throw new IllegalStateException("The room at coordinates " + (double) (i / 2) + ", "
								+ (double) (j / 2) + ", " + (double) (k / 2) + " SHOULD NOT be a room.");

				}
			}
		}
	}

}
