package com.bayviewglen.zork.map;

import java.util.HashMap;

import com.bayviewglen.zork.command.Lockable;
import com.bayviewglen.zork.command.Unlockable;
import com.bayviewglen.zork.item.*;

public class Door extends Side implements Unlockable, Lockable{
	
	private boolean locked;
	private Key key; // unlocked door = null key	

	public Door(String doorName, HashMap<String, String> descriptions, boolean open, boolean locked, Key key, Location location) {
		super(doorName, descriptions, open, location);
		this.locked = locked;
		this.key = key;
		
		if(key == null && locked) {
			System.out.println("You cannot lock a door that has no key");
		}
		
		if(open && locked) {
			System.out.println("A door cannot be open and locked");
		}
		
		updateKey();
	}
	
	public Door(String doorName, HashMap<String, String> descriptions, boolean open, boolean locked, Key key) {
		super(doorName, descriptions, open);
		this.locked = locked;
		this.key = key;
		
		if(key == null && locked) {
			System.out.println("You cannot lock a door that has no key");
		}
		
		if(open && locked) {
			System.out.println("A door cannot be open and locked");
		}
		
		updateKey();
	}
	
	public Door(Door door) {
		super(door.getName(), door.getDescriptions(), door.isOpen(), door.getLocation());
		this.locked = door.locked;
		this.key = door.key;
	}
	
	public String toString() {
		return "Door: " + isOpen() + ", " + isLocked();
	}
	
	public void open() {
		if(!locked) {
			setExit(true);
		}
	}

	/**
	 * close the door
	 */
	public void close() {
		setExit(false);
	}	
	
	/**
	 * 
	 * @return true if the door is open, false if closed
	 */
	public boolean isOpen() {
		return isExit();
	}
	
	/**
	 * Checks if the door is unlocked
	 * @return true if the door is unlocked
	 */
	public boolean isLocked() {
		return locked;
	}
	
	
	public void updateKey() {
		if(key == null)
			locked = false;
	}
	
	public void lock() {
		if(key != null) {
			close();
			locked = true;
		}
		else
			System.out.println("You cannot lock a door without a key");
	}
	
	public boolean unlock(Key key) {
		if(!locked)
			return false;
		
		if(this.key.equals(key)) {
			locked = false;
			return true;
		}
		
		return false;		
	}
	
	public Key getKey() {
		return key;
	}
	
}
