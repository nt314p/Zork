package com.bayviewglen.zork.main;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bayviewglen.zork.item.*;


public class Inventory {

	private ArrayList<Item> items;
	private double maxWeight;

	public Inventory() {
		items = new ArrayList<Item>();
		maxWeight = Double.MAX_VALUE;
	}
	
	public Inventory(double maxWeight) {
		items = new ArrayList<Item>();
		this.maxWeight = maxWeight;
	}
	
	public Inventory(Inventory inventory) {
		items = new ArrayList<Item>();
		this.addAll(inventory);
		maxWeight = inventory.maxWeight;
	}
	
	public Inventory(ArrayList<Item> items) {
		this.items = items;
		this.maxWeight = Double.MAX_VALUE;
	}
	
	public Inventory(ArrayList<Item> items, double maxWeight) {
		this.items = items;
		this.maxWeight = maxWeight;
	}
	
	
	
	public boolean isInfiniteWeight() {
		return maxWeight == Double.MAX_VALUE;
	}

	/**
	 * formats an inventory list as an arrayList
	 * @param list the list you want to turn into an arrayList
	 * @return the finished arrayList
	 */
	public ArrayList<Item> toArrayList() {
		ArrayList<Item> temp = new ArrayList<Item>();

		for (int i = 0; i < items.size(); i++) {
			temp.add(new Item(items.get(i)));
		}

		return temp;
	}

	/**
	 * sort the inventory list
	 */
	public void sort() {
		Collections.sort(items);
	}

	/**
	 * 
	 * @param target the item you are looking for in the list
	 * @return the first occurrence of the item
	 */
	public int indexOf(Item target) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).equals(target)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 
	 * @param target the item you are looking for in the list
	 * @return the number of occurrences of target
	 */
	public int getNumMultiples(Item target) {
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
	public boolean contains(Item target) {
		return indexOf(target) != -1;
	}

	/**
	 * Checks if all items in the list are found in the inventory
	 * 
	 * @param list inventory-type list of items
	 * @return if contains all items in the list
	 */
	public boolean containsAll(Inventory list) {
		for (int i = 0; i < list.size(); i++) {
			if (!items.contains(list.get(i)))
				return false;
		}
		return true;
	}

	/**
	 * get method
	 * 
	 * @param index the index you want to extract an item from
	 * @return the reference of the item at the index
	 */
	public Item get(int index) {
		return items.get(index);
	}

	/**
	 * puts the inventory in a clean, neat, organized string like so: ... Bird(5x):
	 * 5lbs each Pile of leaves(2x): 10lbs each
	 */
	public String toString() {
		String result = "";
		Inventory usedItems = new Inventory();

		for(int i = 0; i<items.size(); i++) {
			Item j = items.get(i);
			
			if(usedItems.contains(j))
				continue;
			
			int num = getNumMultiples(j);
			String itemName = j.getName();
			String multiples = num>1 ? "(" + getNumMultiples(j) + "x):" : ":";
			String weight = String.format("%.2f kg", j.getWeight());
			String each = num>1? " each" : "";
			
			String part1 = itemName + multiples;
			String part2 = weight + each;
			
			result += String.format("%-15s%s\n", part1, part2);
			usedItems.add(j);
		}
		result+=String.format("%-15s%.2f%s", "Total Weight:", getTotalWeight(), " kg");
		return result;
	}

	/**
	 * 
	 * @return the total amount of weight in the inventory
	 */
	public double getTotalWeight() {
		double count = 0;
		for (Item i : items) {
			count += i.getWeight();
		}
		return count;
	}
	
	public double getMaxWeight() {
		return maxWeight;
	}
	
	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}
	
	public void increaseMaxWeight(double maxWeight) {
		if(maxWeight == Double.MAX_VALUE)
			return;
		this.maxWeight += maxWeight;
	}

	/**
	 * adds an item to the list
	 * 
	 * @param item the item you want to add to the list
	 */
	public boolean add(Item item) {
		if(canAdd(item))
			items.add(item);
		return canAdd(item);
	}

	/**
	 * adds an item to the list multiple times
	 * 
	 * @param item    the item to add to the list
	 * @param nCopies the number of times to add the item
	 */
	public int add(Item item, int nCopies) {
		int added = 0;
		
		for (int i = 0; i < nCopies; i++) {
			if(add(item))
				added++;
		}
		return added;
	}
	
	public boolean canAdd(Item item) {
		return item.getWeight() + getTotalWeight() <= maxWeight;
	}

	/**
	 * removes the first occurrence of a target
	 * 
	 * @param target the item you want to remove
	 * @return if the item was in the list and removed
	 */
	public boolean remove(Item target) {
		if (indexOf(target) == -1)
			return false;

		items.remove(indexOf(target));
		return true;
	}

	/**
	 * removes every occurrence of a target
	 * 
	 * @param target the item you want to remove
	 * @return if at least 1 item was removed from the list
	 */
	public boolean removeAll(Item target) {
		boolean result = false;

		while (indexOf(target) != -1) {
			remove(target);
			result = true;
		}
		return result;
	}
	
	public void removeAll(Inventory inventory) {
		for(Item i:inventory.toArrayList()) {
			remove(i);
		}
	}

	/**
	 * 
	 * @return the size of the inventory
	 */
	public int size() {
		return items.size();
	}

	/**
	 * clear everything in the inventory - useful for switching to the next phase
	 */
	public void clearInventory() {
		while (items.size() > 0) {
			items.remove(0);
		}
	}
	
	public void addAll(Inventory inventory) {
		for(Item i:inventory.toArrayList()) {
			add(i);
		}
	}
	
	public static Inventory loadInventory(String filePath) {
		FileReader reader = new FileReader(filePath);
		String[] lines = reader.getLines();
		Inventory inventory = new Inventory();
		
		String concat = "";
		
		for (String s : lines) {
			concat += s + "\n";
		}
		
		JSONObject obj = new JSONObject(concat);
		JSONArray items = obj.getJSONArray("items");
		

		for (int i = 0; i < items.length(); i++) {
			JSONObject curr = items.getJSONObject(i);
			
			ArrayList<String> descriptions = new ArrayList<String>();
			JSONArray JSONDescriptions = curr.getJSONArray("descriptions");
			for(int j = 0; j<JSONDescriptions.length(); j++) {
				descriptions.add(JSONDescriptions.getString(j));
			}
			Item item = new Item(curr.getString("name"), curr.getDouble("weight"), null);
			if(curr.getString("type").equals("food")) {
				Food food = new Food(item, curr.getDouble("foodValue"), curr.getDouble("waterValue"));
				inventory.add(food);
			} else if(curr.getString("type").equals("health")) {
				Health health = new Health(item, curr.getDouble("healthValue"));
				inventory.add(health);
			} else if(curr.getString("type").equals("weapon")) {
				Weapon weapon = new Weapon(item, curr.getDouble("damage"));
				inventory.add(weapon);
			} else
				inventory.add(item);

		}
		return inventory;
	}



}
