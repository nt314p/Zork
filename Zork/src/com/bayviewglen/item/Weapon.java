package com.bayviewglen.item;

public class Weapon extends Item{
	
	private double damage; // out of 1
	private double range; // measured in rooms
	
	public Weapon(String itemName, double itemWeight, double damage, double range) {
		super(itemName, itemWeight, true);
		this.damage = damage;
		this.range = range;
	}
	
	public Weapon(Weapon weapon) {
		super(weapon.getItemName(), weapon.getItemWeight(), true);
		this.damage = weapon.damage;
		this.range = weapon.range;
	}
	
	/**
	 * check if an object is within range
	 * @param distance to check if in range
	 * @return true if the weapon's range is greater than or equal to the distance
	 */
	public boolean isInRange(double distance) {
		return range>=distance;		
	}
	
	/**
	 * check if the weapon is worn out
	 * @return if the damage being done is 0
	 */
	public boolean isWornOut() {
		return damage<=0;
	}
	
	/**
	 * set the damage of a weapon
	 * @param damage the new damage
	 */
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	/**
	 * set the range of a weapon
	 * @param range the new range
	 */
	public void setRange(double range) {
		this.range = range;
	}
	
	/**
	 * 
	 * @return the weapon damage
	 */
	public double getDamage() {
		return damage;
	}
	
	/**
	 * 
	 * @return the weapon range
	 */
	public double getRange() {
		return range;
	}

}
