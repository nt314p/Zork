package com.bayviewglen.zork.item;

import java.util.ArrayList;

public class Food extends Item {
	
	private double foodValue;
	private double waterValue;
	
	public Food(String name, double weight, ArrayList<String> descriptions, double foodValue, double waterValue) {
		super(name, weight, descriptions);
		this.foodValue = foodValue;
	}
	
	public Food(Food food) {
		super(food.getName(), food.getWeight(), food.getDescriptions());
		this.foodValue = food.foodValue;
		this.waterValue = food.waterValue;
	}
	
	public Food(Item item, double foodValue, double waterValue) {
		super(item.getName(), item.getWeight(), item.getDescriptions());
		this.foodValue = foodValue;
		this.waterValue = waterValue;
	}
	
	public String toString() {
		String str = super.toString();
		if(foodValue > 0)
			str += "\nFood value: " + foodValue*100 + "%";
		if(waterValue > 0)
		str += "\nWater value: " + waterValue*100 + "%";

		return str;
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
