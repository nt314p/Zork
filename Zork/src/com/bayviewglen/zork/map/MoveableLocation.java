package com.bayviewglen.zork.map;

public class MoveableLocation extends Location{
	
	public MoveableLocation(Map map, Coordinate coords) {
		super(map, coords);
	}
	
	public MoveableLocation(String mapName, Coordinate coords) {
		super(mapName, coords);
	}
	
	public MoveableLocation(String mapName, double[] coords) {
		super(mapName, coords);
	}
	
	public MoveableLocation() {
		super();
	}
	
	public void set(Map map, Coordinate coords) {
		super.set(map, coords);
	}
	
	public void setMap(Map map) {
		super.setMap(map);
	}
	
	public void setCoords(double[] coords) {
		super.setCoords(coords);;
	}
	
	public void setCoords(Coordinate coords) {
		super.setCoords(coords);
	}
	
	public boolean go(char dir) {
		if(getMap().isExit(dir, getCoords()))
			return false;
		
		setCoords(getMap().getNextRoomCoords(dir, getCoords()));
		
		return true;
	}
	
//	public void nextPhase() {
//		if(atLastPhase())
//			System.out.println("No more phases in the game - you win.");
//		else {
//			set(0, getPhaseNum()+1);
//			set(1, 0);
//			setRoom(getMap().getCheckpoint());
//			}
//	}
	
	
//	public void resetToCheckpoint() {
//		setCoords(getMap().getCheckpoint());
//	}
	
	
//	public void setRoom(Room r) {
//		Coordinate coords = r.getLocation().getCoords();
//		setRoom(coords);
//	}
	
	
	
//	private boolean checkAndUpdate() {
//		boolean updated = false;
//		if(atMapGoal()) {
//			nextMap();
//			updated = true;
//		}
//		if(atPhaseGoal()) {
//			nextPhase();
//			updated = true;
//		}
//		return updated;
//	}




}
