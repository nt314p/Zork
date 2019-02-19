package com.bayviewglen.zork;

public class Food extends Item {

	private static double foodMeter = 1;//listed as a decimal up to 1
	private double foodValue = 0;
	
	public Food(String itemName, int foodValue) {
		super(itemName, true);
		this.foodValue = foodValue;
	}
	
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
	 * 
	 * @return the value of the food - this is how much it boosts your foodMeter when you consume it
	 */
	public double getFoodValue() {
		return this.foodValue;
	}
	
	
}
