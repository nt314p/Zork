package com.bayviewglen.zork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {

	private static List<Item> items = new ArrayList<Item>();

	public Inventory() {
		items = new ArrayList<Item>();
	}
	/**
	 * sort the inventory list
	 */
	public static void sort() {
		Collections.sort(items);
	}

	/**
	 * 
	 * @param target the item you are looking for in the list
	 * @return the first occurrence of the item
	 */
	public static int getIndexOf(Item target) {
		return items.indexOf(target);
	}

	/**
	 * 
	 * @param target the item you are looking for in the list
	 * @return the number of occurrences of target
	 */
	public static int getNumMultiples(Item target) {
		int count = 0;

		for (Item i : items) {
			if (i.equals(target))
				count++;
		}
		return count;
	}

	/**
	 * 
	 * @param target the item you are looking for in the list
	 * @return if the item is in the inventory
	 */
	public static boolean isInInventory(Item target) {
		return getIndexOf(target) != -1;
	}

	/**
	 * puts the inventory in a clean, neat, organized string like so: ...  
	 * Bird(5x): 5lbs each
	 * Pile of leaves(2x): 10lbs each
	 */
	public String toString() {
		String result = "";

		for (Item i : items) {
			result += i.getItemName();
			if (getNumMultiples(i) > 1)
				result += "(" + getNumMultiples(i) + "x)";
			result += ": " + i.getItemWeight() + "lbs";
			if (getNumMultiples(i) > 1)
				result += " each";
			result += ".";
		}
		return result;
	}
	
	/**
	 * 
	 * @return the total amount of weight in the inventory
	 */
	public static double getTotalWeight() {
		double count = 0;
		for(Item i:items) {
			count+=i.getItemWeight();
		}
		return count;
	}
	
	/**
	 * adds an item to the list
	 * @param item the item you want to add to the list
	 */
	public static void add(Item item) {
		items.add(item);
		sort();
	}
	
	/**
	 * removes the first occurrence of a target
	 * @param target the item you want to remove
	 * @return if the item was in the list and removed
	 */
	public static boolean removeOne(Item target) {
		if(getIndexOf(target)==-1)
			return false;
		
		items.remove(getIndexOf(target));
		return true;
	}
	
	/**
	 * removes every occurrence of a target
	 * @param target the item you want to remove
	 * @return if at least 1 item was removed from the list
	 */
	public static boolean removeAll(Item target) {
		boolean result = false;
		
		while(getIndexOf(target)!=-1) {
			removeOne(target);
			result = true;
		}
		return result;
	}
	
	/**
	 * 
	 * @return the size of the inventory
	 */
	public static int size() {
		return items.size();
	}
	
	/**
	 * clear everything in the inventory - useful for switching to the next phase
	 */
	public static void clearInventory() {
		while(items.size()>0) {
			items.remove(0);
		}
	}
	
//	public static void Main(String [] args) {
//		Inventory inventory = new Inventory();
//		inventory.items.add(new Item("hi", 5.5, true));
//		inventory.items.add(new Item("hello", 6.5, true));
//		System.out.println(inventory);
//		
//	}



}
