package com.bayviewglen.zork.map;

public class Location {

	private Map map;
	private Coordinate coords;
	// private double[] location;

	public Location(Map map, Coordinate coords) {
		this.map = map;
		this.coords = coords;

//		if (checkLocationErrors())
//			throw new IllegalArgumentException("This location is invalid.");
	}
	
	public Location() {
		this.map = null;
		this.coords = null;
	}

	public Location(String mapName, Coordinate coords) {
		this.map = Map.getMap(mapName);
		this.coords = coords;
	}

	public Location(String mapName, double[] coords) {
		this.map = Map.getMap(mapName);
		this.coords = new Coordinate(coords);
	}

	public Map getMap() {
		return map;
	}

	public Coordinate getCoords() {
		return coords;
	}

	public Room getRoom() {
		return map.getRoom(coords);
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

	protected void set(Map map, Coordinate coords) {
		this.map = map;
		this.coords = coords;
	}	
	
	protected void setMap(Map map) {
		this.map = map;
	}
	
	protected void setCoords(double[] coords) {
		this.coords = new Coordinate(coords);
	}
	
	protected void setCoords(Coordinate coords) {
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

	public int getMapNum(Phase phase, Map map) {
		return phase.getMaps().indexOf(map);
	}

	public String toString() {
		String str = "Location:";
		str += "\n\tMap: " + map.getMapName();
		str += "\n\tRoom: " + getRoom().getName();
		return str;
	}

	public boolean equals(Location location) {
		return map.equals(location.map) && coords.equals(location.coords);
	}
}