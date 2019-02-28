package com.bayviewglen.zork;

public class UnlockedDoor extends Door {
	
	
	public UnlockedDoor(String itemName, double itemWeight, boolean open) {
		super(itemName, itemWeight, open, true, new UnlockedDoorBehaviour());
	}

}
