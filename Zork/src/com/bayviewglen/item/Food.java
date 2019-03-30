package com.bayviewglen.item;

import com.bayviewglen.zork.*;

public class Food extends Item {
//work on - how water is implemented when you drink it (foodValue)
	//how food value is affected by the item weight
	
	private double foodValue;
	private double waterValue;
	
	public Food(String itemName, double itemWeight, double foodValue, double waterValue) {
		super(itemName, itemWeight, null);
		this.foodValue = foodValue;
	}
	
	public Food(Food food) {
		super(food.getItemName(), food.getItemWeight(), null);
		this.foodValue = food.foodValue;
		this.waterValue = food.waterValue;
	}


	/**
	 * sets the food value of a food to a different number
	 * @param foodValue the updated food value
	 */
	public void setFoodValue(double foodValue) {
		this.foodValue = foodValue;
	}	
	
	/**
	 * 
	 * @return the value of the food - this is how much it boosts your foodMeter when you consume it
	 */
	public double getFoodValue() {
		return this.foodValue;
	}
	
	/**
	 * sets the water value of a food to a different number
	 * @param waterValue the updated water value
	 */
	public void setWaterValue(double waterValue) {
		this.waterValue = waterValue;
	}	
	
	/**
	 * 
	 * @return the value of the water - this is how much it boosts your waterMeter when you consume it
	 */
	public double getWaterValue() {
		return this.waterValue;
	}
	

	
	
	
}
