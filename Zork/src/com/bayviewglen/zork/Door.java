package com.bayviewglen.zork;

public abstract class Door extends Item{
	
	private boolean open;
	private boolean unlocked;
	private DoorOpenBehaviour doorOpenBehaviour;

	public Door(String itemName, double itemWeight, boolean open, boolean unlocked, DoorOpenBehaviour d) {
		super(itemName, itemWeight, false);
		this.open = open;
		this.unlocked = unlocked;
		doorOpenBehaviour = d;
	}	
	
	public void setDoorOpenBehaviour(DoorOpenBehaviour d) {
		doorOpenBehaviour = d;
	}
	
	public void updateDoorOpenBehaviour() {
		if(unlocked)
			doorOpenBehaviour = new UnlockedDoorBehaviour();
		else
			doorOpenBehaviour = new LockedDoorBehaviour();
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
	
	public void toggleOpen() {
		if(open)
			open = false;
		else
			open = true;
	}
	
	public void toggleLock() {
		if(unlocked)
			unlocked = false;
		else
			unlocked = true;		
	}
	

	

}
