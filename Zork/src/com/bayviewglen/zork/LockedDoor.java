package com.bayviewglen.zork;

public class LockedDoor extends Door{
	
	private Item key;
	
	public LockedDoor(String itemName, double itemWeight, boolean open, boolean unlocked, Item key) {
		super(itemName, itemWeight, open, unlocked, new LockedDoorBehaviour());
		
		if(open&&!unlocked)
			throw new IllegalArgumentException("A door cannot be open but locked");
		if(unlocked)
			updateDoorOpenBehaviour();
		
		this.key = key;
	}


	public void lock() {
		if(isUnlocked())
			toggleLock();
	}
	
	/**
	 * Checks if the door is already unlocked, if not checks if the key required matches the key inserted.
	 * If it does, returns true, or else returns false.
	 * @param key the key you want to use to open the door
	 * @return if the door has successfully been unlocked
	 */
	public boolean unlock(Item key) {
		if(isUnlocked())
			return true;
		
		if(this.key.equals(key)) {
			toggleLock();
			return true;
		}
		
		return false;		
	}
	
	
	/**
	 * @return the key required to unlock the door
	 */
	public Item getKey() {
		return key;
	}
	
}
