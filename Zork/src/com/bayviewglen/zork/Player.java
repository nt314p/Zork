package com.bayviewglen.zork;

import java.util.ArrayList;
import com.bayviewglen.item.*;
import com.bayviewglen.map.*;

public class Player{
	
	private static int deaths = 0;
	
	private Monitor healthMonitor;
	private Monitor foodMonitor;
	private Monitor waterMonitor;
	
	private Inventory inventory;
	private String playerName;
	

	public Player(String playerName, Inventory inventory, int health, int food, int water) {
		this.playerName = playerName;
		this.inventory = inventory;
		healthMonitor = new Monitor(health);
		foodMonitor = new Monitor(food);
		waterMonitor = new Monitor(water);
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * die - update death count, reset statistics, reset current room to the checkpoint
	 */
	public void die() {
		deaths++;
		healthMonitor.reset();
		foodMonitor.reset();
		waterMonitor.reset();
		//currentRoom = currentPhase.getCurrentMap().getCheckpoint();
		//need to reset location
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public Monitor getMonitor(String monitor) {
		if(monitor.equals("health"))
			return healthMonitor;
		else if(monitor.equals("food"))
			return foodMonitor;
		return waterMonitor;
	}
	
	public int getDeaths() {
		return deaths;
	}

	/**
	 * check if healthMonitor/foodMonitor/waterMonitor is 0
	 * @return if you're dead
	 */
	public boolean checkDeath() {
		return healthMonitor.isDead() || foodMonitor.isDead() || waterMonitor.isDead();
	}
	
	public void increase(String monitor, double amount) {
		if(monitor.equals("health"))
			healthMonitor.increase(amount);
		else if(monitor.equals("food"))
			foodMonitor.increase(amount);
		else if(monitor.equals("water"))
			waterMonitor.increase(amount);
		else
			System.out.println(monitor + " is not a monitor.");
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
	
	public void take(Player player, Item item) {
		if(player.inventory.remove(item))
			inventory.add(item);
		else
			System.out.println(player.getPlayerName() + " does not have " + item.getItemName());
	}
	
	public void give(Player player, Item item) {
		if(inventory.remove(item))
			player.inventory.add(item);
		else
			System.out.println("You do not have " + item.getItemName());
	}
	
	public void say(Player player, String toSay) {
		System.out.println(toSay);
	}
	
	public void hit(Player player, Weapon weapon) {
		player.healthMonitor.decrease(weapon.getDamage());
	}
	
}
