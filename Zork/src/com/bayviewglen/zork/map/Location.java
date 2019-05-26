package com.bayviewglen.zork.map;

import org.json.JSONObject;

public class Location {

	private int mapIndex;
	private Coordinate coords;
	// private double[] location;

	public Location(Map map, Coordinate coords) {
		mapIndex = Maps.getMapIndex(map.getName());
		this.coords = coords;

//		if (checkLocationErrors())
//			throw new IllegalArgumentException("This location is invalid.");
	}
	
	public Location() {
		mapIndex = 0;
		this.coords = new Coordinate(0.5,0.5,0.5);
	}

	public Location(String mapName, Coordinate coords) {
		mapIndex = Maps.getMapIndex(mapName);
		this.coords = coords;
	}

	public Location(String mapName, double[] coords) {
		mapIndex = Maps.getMapIndex(mapName);
		this.coords = new Coordinate(coords);
	}
	
	public Location(int mapIndex, Coordinate coords) {
		this.mapIndex = mapIndex;
		this.coords = coords;
	}
	
	public Location(double[] location) {
		location[0] = mapIndex;
		coords = new Coordinate(location[1], location[2], location[3]);
	}

	public Map getMap() {
		return Maps.getMap(mapIndex);
	}

	public Coordinate getCoords() {
		return coords;
	}

	public Room getRoom() {
		return getMap().getRoom(coords);
	}

	/**
	 * @return location [phase, map, x, y, z]
	 */
//	public double[] get() {
//		return location;
//	}

//	public void set(Phase phase, Map map, Coordinate coords) {
//		location[0] = getPhaseNum(phase);
//		location[1] = getMapNum(map);
//		location[2] = coords.getX();
//		location[3] = coords.getY();
//		location[4] = coords.getZ();
//		// if(checkLocationErrors())
//		// throw new IllegalArgumentException("This location is invalid.");
//	}

	public void set(Map map, Coordinate coords) {
		mapIndex = Maps.getMapIndex(map.getName());
		this.coords = coords;
	}
	
	public void set(int mapIndex, Coordinate coords) {
		this.mapIndex = mapIndex;
		this.coords = coords;
	}
	
	public void setMap(Map map) {
		mapIndex = Maps.getMapIndex(map.getName());
	}
	
	public void setMap(int mapIndex) {
		this.mapIndex = mapIndex;
	}
	
	public void setCoords(double[] coords) {
		this.coords = new Coordinate(coords);
	}
	
	public void setCoords(Coordinate coords) {
		this.coords = coords;
	}

//	public void setStart() {
//		location[0] = 0;
//		location[1] = 0;
//		Coordinate coords = getMap().getCheckpoint().getLocation().getCoords();
//		location[2] = coords.getX();
//		location[3] = coords.getY();
//		location[4] = coords.getZ();
//	}

//	public boolean checkLocationErrors() {
//		try {
//			Room r = getRoom();
//			r.getLocation().getMap().getExits(r);
//			return true;
//		} catch (Exception NullPointerException) {
//			return false;
//		}
//	}

//	public boolean atMapGoal() {
//		return getCoords().equals(getMap().getGoal());
//	}
//
//	public boolean atMapCheckpoint() {
//		return getCoords().equals(getMap().getCheckpoint());
//	}

//	public int getPhaseNum(Phase phase) {
//		return Game.getPhases().indexOf(phase);
//	}

//	public int getMapNum(Phase phase, Map map) {
//		return phase.getMaps().indexOf(map);
//	}

	public String toString() {
		String str = "Location:";
		str += "\n\tMap: " + getMap().getName();
		str += "\n\tRoom: " + getRoom().getName();
		return str;
	}

	public boolean equals(Location location) {
		return mapIndex == location.mapIndex && coords.equals(location.coords);
	}
	
	public static Location loadLocation(JSONObject jObj) {
		String mapName = jObj.getString("map");
		Coordinate coords = new Coordinate(jObj.getString("coords"));
		return new Location(mapName, coords);
	}
}