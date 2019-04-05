package com.bayviewglen.zork.item;

import java.util.ArrayList;

public class Weapon extends Item{
	
	private double damage; // out of 1
	
	public Weapon(String name, double weight, ArrayList<String> descriptions, double damage) {
		super(name, weight, descriptions);
		this.damage = damage;
	}
	
	public Weapon(Weapon weapon) {
		super(weapon.getName(), weapon.getWeight(), null);
		this.damage = weapon.damage;
	}
	
	public Weapon(Item item, double damage) {
		super(item.getName(), item.getWeight(), item.getDescriptions());
		this.damage = damage;
	}
	
	public String toString() {
		String str = super.toString();
		str += "\nWeapon damage: " + damage*100 + "%";

		return str;
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
	 * 
	 * @return the weapon damage
	 */
	public double getDamage() {
		return damage;
	}
	

}
