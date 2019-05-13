package com.bayviewglen.zork.item;

import java.util.HashMap;

public class Food extends Item {
	
	private double foodValue;
	private double waterValue;
	
	public Food(String name, double weight, HashMap<String, String> descriptions, double foodValue, double waterValue) {
		super(name, weight, descriptions);
		this.foodValue = foodValue;
		this.waterValue = waterValue;
	}
	
	public Food(Food food) {
		super(food.getName(), food.getWeight(), food.getDescriptions());
		this.foodValue = food.foodValue;
		this.waterValue = food.waterValue;
	}
	
	public Food(Item item, double foodValue, double waterValue) {
		super(item);
		this.foodValue = foodValue;
		this.waterValue = waterValue;
	}
	
	public String toString() {
		String str = super.toString();
		return str += "\nFood value: " + foodValue*100 + "%" + "\nWater value: " + waterValue*100 + "%";
	}


	public void setFoodValue(double foodValue) {
		this.foodValue = foodValue;
	}	
	

	public double getFoodValue() {
		return this.foodValue;
	}
	

	public void setWaterValue(double waterValue) {
		this.waterValue = waterValue;
	}	
	

	public double getWaterValue() {
		return this.waterValue;
	}
	

	
	
	
}
