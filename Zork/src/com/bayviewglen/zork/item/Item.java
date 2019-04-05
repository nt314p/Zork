package com.bayviewglen.zork.item;

import java.util.ArrayList;

//The parent class for everything - all objects in the game, ones that can be picked up and ones
//that are staionary, food, doorways, etc.

public class Item implements Comparable<Item> {

	private String itemName;
	private double itemWeight;
	private ArrayList<String> descriptions;

	public Item(String itemName, double itemWeight, ArrayList<String> descriptions) {
		this.itemWeight = itemWeight;
		this.itemName = itemName;
		this.descriptions = descriptions;
	}
	
	public Item(String itemName, double itemWeight) {
		this.itemWeight = itemWeight;
		this.itemName = itemName;
	}
	
	public Item(Item item) {
		this.itemWeight = item.getWeight();
		this.itemName = item.getName();
		this.descriptions = item.getDescriptions();
	}

	public double getWeight() {
		return itemWeight;
	}

	public String getName() {
		return itemName;
	}
	
	public ArrayList<String> getDescriptions(){
		return descriptions;
	}
	
	public void addDescription(String description) {
		descriptions.add(description);		
	}
	
	public boolean removeDescription (String description) {
		int temp = this.searchDescriptions(description);
		if(temp == -1)
			return false;
		
		descriptions.remove(temp);
		return true;
	}
	
	public int searchDescriptions(String description) {
		for(int i = 0; i<descriptions.size(); i++) {
			if(descriptions.get(i).equals(description))
				return i;
		}
		return -1;
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
	
	/**
	 * Compares items more exactly
	 * 1) runs through equals method   2) makes sure every description is found in the argument item
	 * @param item to check if equals
	 * @return if equal
	 */
	public boolean equalsExactly(Item item) {
		if(!this.equals(item))
			return false;
		
		for(String description:descriptions) {
			if(item.searchDescriptions(description) == -1)
				return false;
		}
		return true;
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
