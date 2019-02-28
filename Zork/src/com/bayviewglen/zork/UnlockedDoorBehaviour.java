package com.bayviewglen.zork;

public class UnlockedDoorBehaviour implements DoorOpenBehaviour {

	
	public boolean openDoor(Door door) {
		if(!door.isOpen());
			door.toggleOpen();
		return true;
	}

}
