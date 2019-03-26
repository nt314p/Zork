package com.bayviewglen.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Map {

	// very important *** everything is doubled into the 2D array - a place's
	// x-value is double in the array

	private Place[][][] map;

	public Map(double x, double y, double z) {
		map = new Place[(int) (x * 2)][(int) (y * 2)][(int) (z * 2)];
	}

	public void set(double x, double y, double z, Place p) {
		map[(int) (x * 2)][(int) (y * 2)][(int) (z * 2)] = p;
	}

	public Place get(double x, double y, double z) {
		try {
			return map[(int) (x * 2)][(int) (y * 2)][(int) (z * 2)];
		} catch (Exception IndexOutOfBoundsException) {
			return null;
		}

	}

	public boolean isEmpty(double x, double y, double z) {
		return map[(int) (x * 2)][(int) (y * 2)][(int) (z * 2)].equals(null);
	}

	public double[] getCoords(Room r) {

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				for (int k = 0; k < map[j].length; k++) {
					if (map[i][j][k].equals(r))
						return new double[] { (double) (i / 2), (double) (j / 2), (double) (k / 2) };
				}
			}
		}
		return null;

	}

	public boolean roomInMap(Room r) {
		return this.getCoords(r).equals(null);
	}

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
	
	public Room getNextRoom(char c, Room r) {
		
		HashMap<Character, Room> surroundingRooms = this.getSurroundingRooms(r);
		return surroundingRooms.get(c);
	}

	public void checkMapBuildingErrors() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				for (int k = 0; k < map[j].length; k++) {

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
