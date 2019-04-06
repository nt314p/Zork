package com.bayviewglen.zork.item;

import java.util.ArrayList;
import java.util.HashMap;

public class Health extends Item{

	private double healthValue;// ex. bandages, repairing, etc.
	
	public Health(String name, double weight, HashMap<String, String> descriptions, double healthValue) {
		super(name, weight, descriptions);
		this.healthValue = healthValue;
	}
	
	
	public Health(Health health) {
		super(health.getName(), health.getWeight(), health.getDescriptions());
		this.healthValue = health.healthValue;
	}
	
	public Health(Item item, double healthValue) {
		super(item);
		this.healthValue = healthValue;
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
	
	public String toString() {
		String str = super.toString();
		str += "\nHealth value: " + healthValue*100 + "%";

		return str;
	}
	
}

