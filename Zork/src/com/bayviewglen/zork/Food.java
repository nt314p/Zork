package com.bayviewglen.zork;

public class Food extends Item {
//work on - how water is implemented when you drink it (foodValue)
	//how food value is affected by the item weight
	
	private double foodValue;
	
	public Food(String itemName, double itemWeight, int foodValue) {
		super(itemName, itemWeight, true);
		this.foodValue = foodValue;
	}
	
	public Food(Food food) {
		super(food.getItemName(), food.getItemWeight(), true);
		this.foodValue = food.foodValue;
	}

	/**
	 * sets the food value of a food to a different number
	 * @param newFoodValue the updated food value
	 */
	public void setFoodValue(double newFoodValue) {
		this.foodValue = newFoodValue;
	}	
	
	/**
	 * 
	 * @return the value of the food - this is how much it boosts your foodMeter when you consume it
	 */
	public double getFoodValue() {
		return this.foodValue;
	}
	
	
	
}
