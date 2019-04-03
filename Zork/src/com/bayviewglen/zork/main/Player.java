package com.bayviewglen.zork.main;

import java.util.ArrayList;

import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Player extends Character{
	
	private static int deaths = 0;

	private static ArrayList<Room> roomsVisited = new ArrayList<Room>();

	public Player(Inventory inventory, Location location) {
		super("Player", inventory, location, 1, 1, 1);
	}

	/**
	 * die - update death count, reset statistics, reset current room to the checkpoint
	 */
	public void die() {
		deaths++;
		getFoodMonitor().reset();
		getWaterMonitor().reset();
		getHealthMonitor().reset();
	}
	
	public static int getDeaths() {
		return deaths;
	}

	
	public void take(Character character, Item item) {
		if(getInventory().remove(item))
			getInventory().add(item);
		else
			System.out.println(character.getName() + " does not have " + item.getItemName());
	}
	
	public void give(Character character, Item item) {
		if(getInventory().remove(item))
			character.getInventory().add(item);
		else
			System.out.println("You do not have " + item.getItemName());
	}
	
	public void hit(Character character, Weapon weapon) {
		character.getHealthMonitor().decrease(weapon.getDamage());
	}
	
	public void updateRoomsVisited() {
		if(!hasVisited(getLocation().getRoom()))
			roomsVisited.add(getLocation().getRoom());
	}
	
	public static boolean hasVisited(Room room) {
		for(Room i: roomsVisited) {
			if(i.equals(room))
				return true;
		}
		
    return false;
	}
	
	public static ArrayList<Room> getRoomsVisited(){
		return roomsVisited;
	}
	
}
