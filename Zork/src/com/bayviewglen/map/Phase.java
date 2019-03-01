package com.bayviewglen.map;

public class Phase {
	
	private Map map;
	private Room checkpoint;
	private Room goal;
	
	public Phase(Room checkpoint, Room goal, Map map) {
		this.checkpoint = checkpoint;
		this.goal = goal;
		this.map = map;
	}
	
	/**
	 * check if you are at the phase goal
	 * @param currentRoom your current location
	 * @return if your room equals the phase goal
	 */
	public boolean goalReached(Room currentRoom) {
		return currentRoom.equals(goal);
	}
	
	/**
	 * 
	 * @return the phase's map
	 */
	public Map getPhaseMap() {
		return map;
	}
	
	/**
	 * 
	 * @return the phase's checkpoint
	 */
	public Room getCheckpoint() {
		return checkpoint;
	}
	
	/**
	 * 
	 * @return the phase's goal
	 */
	public Room goal() {
		return goal;
	}
	
	
}
