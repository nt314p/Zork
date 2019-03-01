package com.bayviewglen.zork;

public abstract class Door extends Item{
	
	private boolean open;
	private boolean unlocked;
	private Item key; // unlocked door = null key

	public Door(String itemName, double itemWeight, boolean open, boolean unlocked, Item key) {
		super(itemName, itemWeight, false);
		this.open = open;
		this.unlocked = unlocked;
		this.key = key;
		
		if(key == null && !unlocked) {
			System.out.println("You cannot lock a door without a key");
			unlocked = true;
		}
	}
	
	
	public void open() {
		if(unlocked)
			open = true;
	}

	/**
	 * close the door
	 */
	public void close() {
		this.open = false;
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
	
	public void lock() {
		if(key != null)
			unlocked = false;
		else
			System.out.println("You cannot lock a door without a key");
	}
	
	public boolean unlock(Item key) {
		if(unlocked)
			return true;
		
		if(this.key.equals(key)) {
			unlocked = true;
			return true;
		}
		
		return false;		
	}
	
	public Item getKey() {
		return key;
	}
	

	

}
