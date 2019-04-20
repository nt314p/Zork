package com.bayviewglen.zork.map;

import com.bayviewglen.zork.item.*;

public class MoveableLocation extends Location{
	
	public MoveableLocation(Phase phase, Map map, Coordinate coords) {
		super(phase, map, coords);
	}
	
	public MoveableLocation() {
		super();
	}
	
	public MoveableLocation(double[] location) {
		super(location);
	}
	
	public MoveableLocation(int phase, int map, Coordinate coords) {
		super(phase, map, coords);
	}
	
	public boolean go(char dir) {
		if(getMap().isExit(dir, getRoom()))
			return false;
		
		this.set(getPhase(), getMap(), getMap().getNextRoom(dir, getRoom()).getLocation().getCoords());
		return true;
	}
		
	public void nextMap() {
		
		if(atLastMap())
			nextPhase();
		else {
			set(1, getMapNum()+1);
			setRoom(getMap().getCheckpoint());
		}
	}
	
	public void nextPhase() {
		if(atLastPhase())
			System.out.println("No more phases in the game - you win.");
		else {
			set(0, getPhaseNum()+1);
			set(1, 0);
			setRoom(getMap().getCheckpoint());
			}
	}
	
	
	public void resetToCheckpoint() {
		setRoom(getMap().getCheckpoint());
	}
	
	
	public void setRoom(Room r) {
		Coordinate coords = r.getLocation().getCoords();
		setRoom(coords);

	}
	
	public void setMap(Map m) {
		set(getPhase(), m, getMap().getCheckpoint());
	}
	
	public void setPhase(Phase p) {
		Map temp = p.getMaps().get(0);
		set(p, temp, temp.getCheckpoint());
	}
	

	public void setRoom(Coordinate coords) {
		set(2, coords.getX());
		set(3, coords.getY());
		set(4, coords.getZ());
	}
	
	
	private boolean checkAndUpdate() {
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




}
