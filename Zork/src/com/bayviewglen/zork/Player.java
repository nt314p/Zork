package com.bayviewglen.zork;

import java.util.ArrayList;
import com.bayviewglen.item.*;
import com.bayviewglen.map.*;

public class Player{
	
	private static Room currentRoom;
	private static Phase currentPhase;
	private static int deaths = 0;
	
	private static Monitor healthMonitor = new Monitor();
	private static Monitor foodMonitor = new Monitor();
	private static Monitor waterMonitor = new Monitor();
	//*****IF THERE ARE TOO MANY OF THESE, COULD MAKE A LIST OF MONITORS
	
	private static ArrayList<Room> roomsVisited = new ArrayList<Room>();

	

	/**
	 * die - update death count, reset statistics, reset current room to the checkpoint
	 */
	public static void die() {
		deaths++;
		healthMonitor.reset();
		foodMonitor.reset();
		waterMonitor.reset();
		currentRoom = currentPhase.getCheckpoint();
	}
	
	public static void updateRoomsVisited() {
		if(!hasVisited(currentRoom))
			roomsVisited.add(currentRoom);
	}
	
	public static boolean hasVisited(Room room) {
		for(Room i:roomsVisited) {
			if(i.equals(room))
				return true;
		}
		return false;
	}
	
	public static Room getCurrentRoom() {
		return currentRoom;
	}
	
	/**
	 * sets the current room
	 * @param room the new currentRoom
	 */
	public static void setCurrentRoom(Room room) {
		currentRoom = room;
	}
	
	/**
	 * check if healthMonitor/foodMonitor/waterMonitor is 0
	 * @return if you're dead
	 */
	public static boolean checkDeath() {
		return healthMonitor.isDead() || foodMonitor.isDead() || waterMonitor.isDead();
	}
	
	public static void increase(String monitor, double amount) {
		if(monitor.equals("health"))
			healthMonitor.increase(amount);
		else if(monitor.equals("food"))
			foodMonitor.increase(amount);
		else if(monitor.equals("water"))
			waterMonitor.increase(amount);
		else
			System.out.println(monitor + " is not a monitor.");
	}


	
	
	

	
	
}
