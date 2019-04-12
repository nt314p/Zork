package com.bayviewglen.zork.map;

import java.util.ArrayList;


import com.bayviewglen.zork.main.Game;

public class Location {
	

	double[] location;
		
	public Location(Phase phase, Map map, Coordinate coords) {
		set(phase, map, coords);

		if(checkLocationErrors())
			throw new IllegalArgumentException("This location is invalid.");
	}
	
	public Location() {
		setStart();
	}
	
	public Location(double[]location) {
		set(location);
	}
	
	public Location(int phase, int map, Coordinate coords) {
		double [] temp = coords.toArray();
		double [] location = {phase, map, temp[0], temp[1], temp[2]};
		set(location);
	}

	
	public Phase getPhase() {
		return Game.getPhases().get((int)location[0]);
	}
	
	public Map getMap() {
		return getPhase().getMaps().get((int)location[1]);
	}
	
	public Coordinate getCoords() {
		return new Coordinate(location[2], location[3], location[4]);
	}
	
	public Room getRoom() {
		return getMap().getRoom(getCoords());
	}
	
	
	/**
	 * @return location [phase, map, x, y, z]
	 */
	public double [] get() {
		return location;
	}
	
	
	public void set(Phase phase, Map map, Coordinate coords) {
		location[0] = getPhaseNum(phase);
		location[1] = getMapNum(map);
		location[2] = coords.getX();
		location[3] = coords.getY();
		location[4] = coords.getZ();
		if(checkLocationErrors())
			throw new IllegalArgumentException("This location is invalid.");
	}
	
	public void set(Phase phase, Map map, Room room) {
		set(phase, map, room.getLocation().getCoords());
	}
	
	public void set(double[] location) {
		this.location = location;
		if(checkLocationErrors())
			throw new IllegalArgumentException("This location is invalid.");
	}
	
	public void setStart() {
		location[0] = 0;
		location[1] = 0;
		setRoom(getMap().getCheckpoint());
	}
	
	public boolean checkLocationErrors() {
		try {
			Room r = getPhase().getMaps().get(getMapNum()).getRoom(getCoords());
			r.getLocation().getMap().getExits(r);
			return true;
		} catch (Exception NullPointerException) {
			return false;
		}
	}
	
	public boolean atLastPhase() {
		return getPhaseNum() + 1 >= Game.getPhases().size();
	}
	
	public boolean atLastMap() {
		return getMapNum() + 1 >= getPhase().getMaps().size();
	}
	
	public boolean atMapGoal() {
		return getRoom().equals(getMap().getGoal());
	}
	
	public boolean atPhaseGoal() {
		return atLastMap() && atMapGoal();
	}
	
	public boolean atGameGoal() {
		return atLastPhase() && atPhaseGoal();
	}
	
	public boolean atMapCheckpoint() {
		return getRoom().equals(getMap().getCheckpoint());
	}
	
	public void resetToCheckpoint() {
		setRoom(getMap().getCheckpoint());
	}
	
	
	public void setRoom(Room r) {
		set(getPhase(), getMap(), r);
	}
	
	public void setMap(Map m) {
		set(getPhase(), m, getMap().getCheckpoint());
	}
	
	public void setPhase(Phase p) {
		Map temp = p.getMaps().get(0);
		set(p, temp, temp.getCheckpoint());
	}
	
	public int getPhaseNum(Phase phase) {
		return Game.getPhases().indexOf(phase);
	}
	
	public int getPhaseNum() {
		return getPhaseNum(getPhase());
	}
	
	public int getMapNum(Phase phase, Map map) {
		return phase.getMaps().indexOf(map);
	}
	
	public int getMapNum(Map map) {
		return getPhase().getMaps().indexOf(map);
	}
	
	public int getMapNum() {
		return getMapNum(getMap());
	}
	
	public String toString() {
		String str = "Location:";
		str += "\n\tPhase " + getPhaseNum() + ": " + getPhase().getPhaseName();
		str += "\n\tMap " + getMapNum() + ": " + getMap().getMapName();
		str+= "\n\tRoom: " + getRoom().getRoomName();
		return str;
	}
}