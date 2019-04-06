package com.bayviewglen.zork.item;

import java.util.ArrayList;

import com.bayviewglen.zork.main.Inventory;

//The parent class for everything - all objects in the game, ones that can be picked up and ones
//that are staionary, food, doorways, etc.

public class Item implements Comparable<Item> {

	private String name;
	private double weight;
	private ArrayList<String> descriptions;

	public Item(String name, double weight, ArrayList<String> descriptions) {
		this.weight = weight;
		this.name = name;
		this.descriptions = descriptions;
	}
	
	public Item(String name, double weight) {
		this.weight = weight;
		this.name = name;
	}
	
	public Item(Item item) {
		this.weight = item.getWeight();
		this.name = item.getName();
		this.descriptions = item.getDescriptions();
	}

	public double getWeight() {
		return weight;
	}

	public String getName() {
		return name;
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
		return name.equals(item.name) && weight == item.weight;
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
		int temp = name.compareTo(item.name);
		switch (temp) {
		case 0:
			return (int)Math.round(item.weight - weight);
		default:
			return temp;
		}
	}

	public String toString() {
		String str = name + ": ";
		if(weight>0)
			str +=  + weight + "lbs";

		if(descriptions != null) {
			for (int i = 0; i < descriptions.size(); i++) {
				str += ", " + descriptions.get(i);
			}
		}
		str += ".";
		return str;
	}
	
	public static Item loadItem(String filePath, int arrNum) {
		Inventory inventory = Inventory.loadInventory(filePath);
		return inventory.get(arrNum);
	}

}
