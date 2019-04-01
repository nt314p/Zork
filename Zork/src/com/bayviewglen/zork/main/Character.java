package com.bayviewglen.zork.main;

import java.util.ArrayList;

import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Character{
	
	
	private Monitor healthMonitor;
	private Monitor foodMonitor;
	private Monitor waterMonitor;
	
	private Inventory inventory;
	private String name;
	

	public Character(String name, Inventory inventory, int health, int food, int water) {
		this.name = name;
		this.inventory = inventory;
		healthMonitor = new Monitor(health);
		foodMonitor = new Monitor(food);
		waterMonitor = new Monitor(water);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public Monitor getMonitor(String monitor) {
		if(monitor.equals("health"))
			return healthMonitor;
		else if(monitor.equals("food"))
			return foodMonitor;
		else if(monitor.equals("water"))
			return waterMonitor;
		else
			System.out.println(monitor + " is not a monitor.");
			return null;
	}

	/**
	 * check if healthMonitor/foodMonitor/waterMonitor is 0
	 * @return if you're dead
	 */
	public boolean checkDeath() {
		return healthMonitor.isDead() || foodMonitor.isDead() || waterMonitor.isDead();
	}
	
	
	/**
	 * eat the food
	 * @param food the food you would like to eat
	 */
	public void eat(Food food) {
		foodMonitor.increase(food.getFoodValue());
	}
	

	/**
	 * drink the food
	 * @param food the food you would like to drink
	 */
	public void drink(Food food) {
		waterMonitor.increase(food.getWaterValue());
	}
	

	/**
	 * heal yourself
	 * @param health the object you want to use to heal yourself
	 */
	public void heal(Health health) {	
		healthMonitor.increase(health.getHealthValue());
	}
}
