package com.bayviewglen.zork;

import java.util.ArrayList;

public class Player extends Monitor{
	
	static Room currentRoom;
	static Phase currentPhase;
	static int deaths = 0;
	
	static Monitor healthMonitor = new Monitor();
	static Monitor foodMonitor = new Monitor();
	static Monitor waterMonitor = new Monitor();
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
	
	
	/**
	 * eat the food
	 * @param food the food you would like to eat
	 */
	public static void eat(Food food) {
		foodMonitor.increase(food.getFoodValue());
	}
	
	
	/**
	 * drink water if the food is water
	 * @param water the water you would like to drink
	 */
	public static void drink(Food water) {
		if(!water.getItemName().equalsIgnoreCase("water")) {
			System.out.println("You can't eat food");
			return;
		}
		
		waterMonitor.increase(water.getFoodValue());
	}
	
	//public static void heal(Healer healer) {	
	//}

	
	
	

	
	
}
