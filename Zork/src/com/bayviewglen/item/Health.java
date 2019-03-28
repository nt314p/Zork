package com.bayviewglen.item;

import com.bayviewglen.zork.*;

public class Health extends Item{

	private double healthValue;// ex. bandages, repairing, etc.
	
	public Health(String itemName, double itemWeight, double healthValue) {
		super(itemName, itemWeight, null);
		this.healthValue = healthValue;
	}
	
	
	public Health(Health health) {
		super(health.getItemName(), health.getItemWeight(), null);
		this.healthValue = health.healthValue;
	}


	/**
	 * sets the health value of a healing object to a different number
	 * @param healthvalue the updated health value
	 */
	public void setFoodValue(double healthValue) {
		this.healthValue = healthValue;
	}	
	
	/**
	 * 
	 * @return the value of the health - this is how much it boosts your healthMeter when you consume it
	 */
	public double getHealthValue() {
		return this.healthValue;
	}
	
	
	/**
	 * heal yourself
	 * @param health the object you want to use to heal yourself
	 */
	public static void heal(Health health) {	
		Player.increase("health", health.getHealthValue());
	}
}

