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
	
	public boolean checkLocationErrors() {
		if(phase.indexOf(map) == -1)
			return true;
		if(map.getCoords(room)==null)
			return true;
		return false;
	}
	
	public boolean atLastPhase() {
		return Game.indexOf(phase) + 1 >= Game.getPhases().size();
	}
	
	public boolean atLastMap() {
		return phase.indexOf(map) + 1 >= phase.getMaps().size();
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
		int mapIndex = phase.indexOf(map);
		if(atLastMap())
			nextPhase();
		else {
			map = maps.get(mapIndex + 1);
			room = map.getCheckpoint();
		}
	}
	
	public void nextPhase() {
		int phaseIndex = Game.indexOf(phase);
		if(atLastPhase())
			System.out.println("No more phases in the game - you win.");
		else {
			phase = Game.getPhases().get(phaseIndex+1);
			map = phase.getMaps().get(0);
			room = map.getCheckpoint();
		}
	}
	
	/**
	 * @return location [phase, map, x, y, z]
	 */
	public double [] getLocation() {
		double[] location = new double[5];
		location[0] = Game.indexOf(phase);
		location[1] = phase.indexOf(map);		
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
}