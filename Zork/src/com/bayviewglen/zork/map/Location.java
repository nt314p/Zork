package com.bayviewglen.zork.map;

import java.util.ArrayList;


import com.bayviewglen.zork.main.Game;

public class Location {
	

	double[] location;
	
	Map map;
	Coordinate coords;
		
	public Location(Phase phase, Map map, Coordinate coords) {
		this.map = map;
		this.coords = coords;

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
		return map;//getPhase().getMaps().get((int)location[1]);
	}
	
	public Room getRoom() {
		//return //getMap().getRoom(location[2], location[3], location[4]);
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
//	public double [] get() {
//		return location;
//	}
	
	
	public void set(Phase phase, Map map, Room room) {
		this.phase = getPhaseNum(phase);
		this.map = getMapNum(map);
		coords.setX(map.getCoords(room)[2]);
		coords.setX(map.getCoords(room)[3];
		coords.setX(map.getCoords(room)[4];
		if(checkLocationErrors())
			throw new IllegalArgumentException("This location is invalid.");
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
	
	public boolean north() {
		if(getMap().isExit('n', getRoom()))
			return false;

		this.set(getPhase(), getMap(), getMap().getNextRoom('n', getRoom()));
		return true;
	}
	
	public boolean south() {
		if(getMap().isExit('s', getRoom()))
			return false;

		this.set(getPhase(), getMap(), getMap().getNextRoom('s', getRoom()));
		return true;
	}
	
	public boolean east() {
		if(getMap().isExit('e', getRoom()))
			return false;

		this.set(getPhase(), getMap(), getMap().getNextRoom('e', getRoom()));
		return true;
	}
	
	public boolean west() {
		if(getMap().isExit('w', getRoom()))
			return false;

		this.set(getPhase(), getMap(), getMap().getNextRoom('w', getRoom()));
		return true;
	}
	
	public boolean up() {
		if(getMap().isExit('u', getRoom()))
			return false;

		this.set(getPhase(), getMap(), getMap().getNextRoom('u', getRoom()));
		return true;
	}
	
	public boolean down() {
		if(getMap().isExit('d', getRoom()))
			return false;

		this.set(getPhase(), getMap(), getMap().getNextRoom('d', getRoom()));
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
	
	public Coordinate getCoords() {
		return coords;
	}
	
	public String toString() {
		String str = "Location:";
		str += "\n\tPhase " + getPhaseNum() + ": " + getPhase().getPhaseName();
		str += "\n\tMap " + getMapNum() + ": " + getMap().getMapName();
		str+= "\n\tRoom: " + getRoom().getRoomName();
		return str;
	}
}