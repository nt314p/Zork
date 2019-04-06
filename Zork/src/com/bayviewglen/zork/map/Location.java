package com.bayviewglen.zork.map;

import java.util.ArrayList;


import com.bayviewglen.zork.main.Game;

public class Location {
	
	Phase phase;
	Map map;
	Room room;
		
	public Location(Phase phase, Map map, Room room) {
		this.phase = phase;
		this.map = map;
		this.room = room;
		
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
		return phase;
	}
	
	public Map getMap() {
		return map;
	}
	
	public Room getRoom() {
		return room;
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
	
	
	public void set(Phase phase, Map map, Room room) {
		this.phase = phase;
		this.map = map;
		this.room = room;
		if(checkLocationErrors())
			throw new IllegalArgumentException("This location is invalid.");
	}
	
	public void set(double[] location) {
		try {
			phase = Game.getPhases().get((int)location[0]);
			map = phase.getMaps().get((int)location[1]);
			room = map.getRoom(location[2], location[3], location[4]);
		} catch (Exception IndexOutOfBoundsException) {
			throw new IllegalArgumentException("This location is invalid (indexoutofbounds).");
		}
		if(checkLocationErrors())
			throw new IllegalArgumentException("This location is invalid.");

	}
	
	public void setStart() {
		this.phase = Game.getPhases().get(0);
		this.map = phase.getMaps().get(0);
		this.room = map.getCheckpoint();
	}
	
	public boolean checkLocationErrors() {
		if(getMapNum() == -1)
			return true;
		if(getRoomCoords()==null)
			return true;
		return false;
	}
	
	public boolean atLastPhase() {
		return getPhaseNum() + 1 >= Game.getPhases().size();
	}
	
	public boolean atLastMap() {
		return getMapNum() + 1 >= phase.getMaps().size();
	}
	
	public boolean atMapGoal() {
		return room.equals(map.getGoal());
	}
	
	public boolean atPhaseGoal() {
		return atLastMap() && atMapGoal();
	}
	
	public boolean atGameGoal() {
		return atLastPhase() && atPhaseGoal();
	}
	
	public boolean atMapCheckpoint() {
		return room.equals(map.getCheckpoint());
	}
	
	public void resetToCheckpoint() {
		room = map.getCheckpoint();
	}
	
	public boolean north() {
		if(map.isExit('n', room))
			return false;

		this.set(phase, map, map.getNextRoom('n', room));
		return true;
	}
	
	public boolean south() {
		if(map.isExit('s', room))
			return false;

		this.set(phase, map, map.getNextRoom('s', room));
		return true;
	}
	
	public boolean east() {
		if(map.isExit('e', room))
			return false;

		this.set(phase, map, map.getNextRoom('e', room));
		return true;
	}
	
	public boolean west() {
		if(map.isExit('w', room))
			return false;

		this.set(phase, map, map.getNextRoom('w', room));
		return true;
	}
	
	public boolean up() {
		if(map.isExit('u', room))
			return false;

		this.set(phase, map, map.getNextRoom('u', room));
		return true;
	}
	
	public boolean down() {
		if(map.isExit('d', room))
			return false;

		this.set(phase, map, map.getNextRoom('d', room));
		return true;
	}
	
	public void nextMap() {
		ArrayList<Map> maps = phase.getMaps();
		if(atLastMap())
			nextPhase();
		else {
			map = maps.get(getMapNum() + 1);
			room = map.getCheckpoint();
		}
	}
	
	public void nextPhase() {
		if(atLastPhase())
			System.out.println("No more phases in the game - you win.");
		else {
			phase = Game.getPhases().get(getPhaseNum()+1);
			map = phase.getMaps().get(0);
			room = map.getCheckpoint();
		}
	}
	
	/**
	 * @return location [phase, map, x, y, z]
	 */
	public double [] getLocation() {
		double[] location = new double[5];
		location[0] = getPhaseNum();
		location[1] = getMapNum();		
		location[2] = map.getCoords(room)[0];
		location[3] = map.getCoords(room)[1];
		location[4] = map.getCoords(room)[2];
		return location;
	}
	

	public void setRoom(Room r) {
		set(phase, map, r);
	}
	
	public void setMap(Map m) {
		set(phase, m, map.getCheckpoint());
	}
	
	public void setPhase(Phase p) {
		Map temp = p.getMaps().get(0);
		set(p, temp, temp.getCheckpoint());
	}
	
	public int getPhaseNum() {
		return Game.getPhases().indexOf(phase);
	}
	
	public int getMapNum() {
		return phase.getMaps().indexOf(map);
	}
	
	public double[]getRoomCoords(){
		return map.getCoords(room);
	}
	
	public String toString() {
		String str = "Location:";
		str += "\n\tPhase " + getPhaseNum() + ": " + phase.getPhaseName();
		str += "\n\tMap " + getMapNum() + ": " + map.getMapName();
		str+= "\n\tRoom: " + room.getRoomName();
		return str;
	}
}