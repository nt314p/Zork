package com.bayviewglen.zork.map;

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



}
