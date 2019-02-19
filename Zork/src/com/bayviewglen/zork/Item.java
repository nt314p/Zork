package com.bayviewglen.zork;

//The parent class for everything - all objects in the game, ones that can be picked up and ones
//that are staionary, food, doorways, etc.

//To add:
	//item locations, in-inventory boolean

public class Item {
	
	private String itemName = "";
	private boolean moveable = false;
	
	public Item(String itemName, boolean moveable) {
		this.moveable = moveable;
		this.itemName = itemName;
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
	 * Compares 2 item's names to see if they are equal
	 * @param item the item you want to compare
	 * @return true if they have the same name
	 */
	public boolean equals(Item item) {
		return this.itemName.equals(item.getItemName());
	}

}
