package com.bayviewglen.zork.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.bayviewglen.zork.command.NoEffectCommands;
import com.bayviewglen.zork.command.PlayerCommands;
import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Player extends Character implements NoEffectCommands, PlayerCommands{
	
	private static int deaths = 0;
	private static String [] screams = {"Aaaaarrrrg", "Aahhhhh", "WHAAAAAAATTTT????", "NIICCKK TONG!!!!", "What the hell are you doing with your life???"};

	private static ArrayList<Room> roomsVisited = new ArrayList<Room>();

	public Player(double weight, HashMap<String, String> descriptions, Inventory inventory, MoveableLocation location) {
		super("Player", weight, descriptions, inventory, location, new double[] {1,1,1}, null, null);
	}
	
	public Inventory getInteractableItems(){
		Inventory i = new Inventory();
		HashMap<java.lang.Character, Coordinate> cc = new HashMap<java.lang.Character, Coordinate>();
		Map m = getLocation().getMap();
		cc = m.getSurroundingSideCoords(getLocation().getCoords());
		ArrayList<Coordinate> scoords = (ArrayList<Coordinate>) cc.values();
		for (int j = 0; j < scoords.size(); j++) {
			i.add(m.getSide(scoords.get(j)));
		}
		i.addAll(getLocation().getRoom().getRoomItems());
		i.addAll(getInventory());
		
		return i;		
	}
	
//	public ArrayList<Character> getInteractableCharacters(){
//		ArrayList<Character> characters = new ArrayList<Character>();
//		for(Character c:Game.getCharacters()) {
//			if(c.getLocation().getRoom().equals(getLocation().getRoom()))
//				characters.add(c);
//		}
//		return characters;
//	}

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
			System.out.println(character.getName() + " does not have " + item.getName());
	}
	
	public void give(Character character, Item item) {
		if(getInventory().remove(item))
			character.getInventory().add(item);
		else
			System.out.println("You do not have " + item.getName());
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
	
	public String toString() {
		String str = super.toString() + "\n";
		str+= "Deaths: " + deaths;
		return str;
	}

	public String scream() {
		int randIndex = (int)(Math.random()*screams.length);
		return screams[randIndex];
	}

	public String breathe() {
		return "...breath...breath...breath...";
	}

	public String jump() {
		return "You jumped.";
	}


	public String fall() {
		getHealthMonitor().setToPercent(0.5);
		return "You fell and are now bleeding.";
	}

	@Override
	public String eat(Food food) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String drink(Food water) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String heal(Health health) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String quit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String restart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String die() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String north() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String south() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String east() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String west() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String up() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String down() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String move(char dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String enter(Door d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String enter(Room r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String drop(Item i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pickUp(Item i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String give(java.lang.Character c, Item i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String take(java.lang.Character c, Item i) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
