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
	 * drink the food
	 * @param food the food you would like to drink
	 */
	public static void drink(Food food) {
		waterMonitor.increase(food.getWaterValue());
	}
	
	/**
	 * heal yourself
	 * @param health the object you want to use to heal yourself
	 */
	public static void heal(Health health) {	
		healthMonitor.increase(health.getHealthValue());
	}

	
	
	

	
	
}
