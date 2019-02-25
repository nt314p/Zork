package com.bayviewglen.zork;



public class Player {
	
	
	static double health = 1;
	static double foodMeter = 1;
	static double waterMeter = 1;

	/**
	 *
	 * @return the player's foodMeter - how much food they have, listed from a scale of 0 to 1
	 */
	public static double getFoodMeter() {
		return foodMeter;
	}
	
	/**
	 * Set's the foodMeter (amount of food player has from 0 to 1) to the amount you want to set
	 * @param newFoodMeter the new food meter
	 */
	public static void setFoodMeter(double newFoodMeter) {
		foodMeter = newFoodMeter;
	}
	
	
	/**
	 * eat the food, update the foodMeter
	 * @param food the food you would like to eat
	 */
	public static void eat(Food food) {
		foodMeter += food.getFoodValue();
		
		if(foodMeter>1)
			foodMeter=1;
		
		//need to remove the food from inventory
	}
	
	

	
	
}
