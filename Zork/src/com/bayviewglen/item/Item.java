package com.bayviewglen.item;

//The parent class for everything - all objects in the game, ones that can be picked up and ones
//that are staionary, food, doorways, etc.

public class Item implements Comparable<Item> {

	private String itemName;
	private double itemWeight;
	private boolean moveable;

	public Item(String itemName, double itemWeight, boolean moveable) {
		this.moveable = moveable;
		this.itemWeight = itemWeight;
		this.itemName = itemName;
	}

	public Item(Item item) {
		this.moveable = item.getMoveable();
		this.itemWeight = item.getItemWeight();
		this.itemName = item.getItemName();
	}

	/**
	 * 
	 * @return the item weight
	 */
	public double getItemWeight() {
		return itemWeight;
	}

	/**
	 * 
	 * @return item's name as a string
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * 
	 * @return true if you can move the item, false if you can't
	 */
	public boolean getMoveable() {
		return moveable;
	}

	/**
	 * Compares 2 item's names and weights to see if they are equal
	 * 
	 * @param item the item you want to compare
	 * @return true if they have the same name and weight
	 */
	public boolean equals(Item item) {
		return itemName.equals(item.itemName) && itemWeight == item.itemWeight;
	}

	public int compareTo(Item item) {
		int temp = itemName.compareTo(item.itemName);
		switch (temp) {
		case 0:
			return (int)Math.round(item.itemWeight - itemWeight);
		default:
			return temp;
		}
	}

}
