package com.bayviewglen.zork.map;

import com.bayviewglen.zork.item.*;

public class Door extends Side{
	
	private boolean open;
	private boolean unlocked;
	private Key key; // unlocked door = null key
	private String doorName;
	

	public Door(String doorName, boolean open, boolean unlocked, Key key) {
		super(open);
		this.doorName = doorName;
		this.open = open;
		this.unlocked = unlocked;
		this.key = key;
		
		if(key == null && !unlocked) {
			System.out.println("You cannot lock a door without a key");
		}
		
		if(open && !unlocked) {
			System.out.println("A door cannot be open and locked");
		}
		
		updateKey();
		updateDoor();
	}
	
	public String toString() {
		return "Door: " + isOpen() + ", " + isUnlocked();
	}
	
	
	public void open() {
		if(unlocked) {
			open = true;
			setExit(true);
		}
	}

	/**
	 * close the door
	 */
	public void close() {
		open = false;
		setExit(false);
	}	
	
	/**
	 * 
	 * @return true if the door is open, false if closed
	 */
	public boolean isOpen() {
		return open;
	}
	
	/**
	 * Checks if the door is unlocked
	 * @return true if the door is unlocked
	 */
	public boolean isUnlocked() {
		return unlocked;
	}
	
	
	public void updateKey() {
		if(key == null)
			unlocked = true;
	}
	
	public void updateDoor() {
		if(open && !unlocked)
			open = false;
	}
	
	public void lock() {
		if(key != null) {
			open = false;
			unlocked = false;
		}
		else
			System.out.println("You cannot lock a door without a key");
	}
	
	public boolean unlock(Key key) {
		if(unlocked)
			return true;
		
		if(this.key.equals(key)) {
			unlocked = true;
			return true;
		}
		
		return false;		
	}
	
	public Key getKey() {
		return key;
	}
	
	public String getDoorName() {
		return doorName;
	}


	public boolean checkIsExit() {
		updateDoor();
		return open;
	}
	

	

}
