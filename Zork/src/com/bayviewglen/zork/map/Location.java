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
	
	public boolean checkAndUpdate() {
		boolean updated = false;
		
		if(atMapGoal()) {
			nextMap();
			updated = true;
		}
		
		if(atPhaseGoal()) {
			nextPhase();
			updated = true;
		}
		
		return updated;
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
		if(getMapNum() == -1)
			return true;
		if(getRoom()==null)
			return true;
		return false;
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
	
	public boolean go(char dir) {
		if(getMap().isExit(dir, getRoom()))
			return false;
		this.set(getPhase(), getMap(), getMap().getNextRoom(dir, getRoom()).getLocation().getCoords());
		return true;
	}
	
	
	
	public void nextMap() {
		ArrayList<Map> maps = getPhase().getMaps();
		if(atLastMap())
			nextPhase();
		else {
			location[1] = getMapNum() + 1;
			setRoom(getMap().getCheckpoint());
		}
	}
	
	public void nextPhase() {
		if(atLastPhase())
			System.out.println("No more phases in the game - you win.");
		else {
			location[0] = getPhaseNum() + 1;
			location[1] = 0;
			setRoom(getMap().getCheckpoint());
			}
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