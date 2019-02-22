package com.bayviewglen.zork;

public class Door extends Item{
	
	private boolean open = false;
	private boolean unlocked = true;
	private Item key;
	
	/**
	 * constructor for a door with no locks/keys
	 */
	public Door(String itemName, double itemWeight, boolean open) {
		super(itemName, itemWeight, false);
		this.open = false;
	}
	
	/**
	 * constructor for a door if there is a lock, and you need a key to unlock it
	 */
	public Door(String itemName, double itemWeight, boolean open, boolean unlocked, Item key) {
		super(itemName, itemWeight, false);
		this.unlocked = false;
		this.key = key;
	}
	
	/**
	 * Opens the door if it's unlocked
	 * @return true if the door has successfully been opened
	 */
	public boolean openDoor() {
		if(unlocked) {		
			open = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Closes the door
	 */
	public void closeDoor() {
		open = false;
	}
	
	/**
	 * Checks if the door is already unlocked, if not checks if the key required matches the key inserted.
	 * If it does, returns true, or else returns false.
	 * @param key the key you want to use to open the door
	 * @return if the door has successfully been unlocked
	 */
	public boolean unlockDoor(Item key) {
		if(unlocked)
			return true;// all locked have keys
		
		if(this.key.equals(key)) {
			unlocked = true;
			return true;
		}
		
		return false;		
	}
	
	/**
	 * 
	 * @return true if the door is open, false if closed
	 */
	public boolean checkDoorOpen() {
		return open;
	}
	
	/**
	 * Checks if the door is unlocked
	 * @return true if the door is unlocked
	 */
	public boolean checkUnlocked() {
		return unlocked;
	}
	
	/**
	 * @return the key required to unlock the door
	 */
	public Item getKey() {
		return key;
	}
	

}
