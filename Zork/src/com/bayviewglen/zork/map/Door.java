package com.bayviewglen.zork.map;

import java.util.HashMap;

import com.bayviewglen.zork.command.DoorCommands;

public class Door extends Side implements DoorCommands{
	
	private boolean locked;
	private String keycode;

	public Door(String doorName, HashMap<String, String> descriptions, boolean open, boolean locked, String keycode) {
		super(doorName, descriptions, open);
		this.locked = locked;
		this.keycode = keycode;
		
		if(keycode.equals("") && locked) {
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
		this.keycode = door.keycode;
	}
	
	public String toString() {
		return "Door: " + isOpen() + ", " + isLocked();
	}
	
	public String open() {
		if(!locked) {
			setExit(true);
			return getName() + " opened.";
		}
		return getName() + " is locked and cannot be opened.";
	}

	/**
	 * close the door
	 */
	public String close() {
		setExit(false);
		return getName() + " closed.";
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
	
	
	private void updateKey() {
		if(keycode.equals(""))
			locked = false;
	}
	
	public String lock() {
		if(!keycode.equals("")) {
			close();
			locked = true;
			return getName() + " locked.";
		}
		return getName() + " cannot be locked because there is no key to unlock it.";
	}
	
	public String unlock(String keycode) {
		if(!locked)
			return getName() + " is already unlocked";
		
		if(this.keycode.equals(keycode)) {
			locked = false;
			return getName() + " is successfully unlocked.";
		}
		
		return getName() + " did not have the correct key to unlock.";	
	}
	
	public String moveThrough() {
		if (!isExit()) {
			return getName() + " is closed.";
		}
		return ""; // Door is open, no need to return anything
	}
	
	public String getKeycode() {
		return keycode;
	}
	
}
