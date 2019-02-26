package com.bayviewglen.zork;

public class Player {
	
	static Room currentRoom;
	static Phase currentPhase;
	static int deaths = 0;
	
	static Monitor healthMonitor = new Monitor();
	static Monitor foodMonitor = new Monitor();
	static Monitor waterMonitor = new Monitor();
	//*****IF THERE ARE TOO MANY OF THESE, COULD MAKE A LIST OF MONITORS


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
