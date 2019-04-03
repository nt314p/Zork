package com.bayviewglen.zork.map;

/**
 * Parent class for doors, openings, and walls
 * 
 * Uses polymorphism to store an array f
 * 
 *
 */

public abstract class Side extends Place{
	private boolean isExit;
	
	public Side(boolean isExit) {
		this.isExit = isExit;
	}
	
	public boolean isExit() {
		return isExit;
	}
	
	public abstract boolean checkIsExit();
	
	public void updateIsExit() {
		isExit = checkIsExit();
	}
	
}
