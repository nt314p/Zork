package com.bayviewglen.zork.main;

import java.util.ArrayList;

import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Player extends Character{
	
	private int deaths = 0;

	private static ArrayList<Room> roomsVisited = new ArrayList<Room>();

	public Player(Inventory inventory, Location location) {
		super("Player", inventory, location, 1, 1, 1);
	}

	/**
	 * die - update death count, reset statistics, reset current room to the checkpoint
	 */
	public void die() {
		deaths++;
		this.getMonitor("health").reset();
		this.getMonitor("food").reset();
		this.getMonitor("water").reset();
		getLocation().resetToCheckpoint();
	}
	
	public int getDeaths() {
		return deaths;
	}

	
	public void take(Character character, Item item) {
		if(this.getInventory().remove(item))
			this.getInventory().add(item);
		else
			System.out.println(character.getName() + " does not have " + item.getItemName());
	}
	
	public void give(Character character, Item item) {
		if(this.getInventory().remove(item))
			character.getInventory().add(item);
		else
			System.out.println("You do not have " + item.getItemName());
	}
	
	public void hit(Character character, Weapon weapon) {
		character.getMonitor("health").decrease(weapon.getDamage());
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
