package com.bayviewglen.zork.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.bayviewglen.zork.command.NoEffectCommands;
import com.bayviewglen.zork.command.PlayerCommands;
import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Player extends Character implements NoEffectCommands, PlayerCommands{
	
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
	
	public String displayInteractableItems() {
		return getInteractableItems().toString();
	}
	
	
//	public ArrayList<Character> getInteractableCharacters(){
//		ArrayList<Character> characters = new ArrayList<Character>();
//		for(Character c:Game.getCharacters()) {
//			if(c.getLocation().getRoom().equals(getLocation().getRoom()))
//				characters.add(c);
//		}
//		return characters;
//	}


	
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


	public String die() {
		Game.setGameOver(false);
		return "You died.";
	}
	
	public String quit() {
		Game.setGameOver(false);
		return "Game quit.";
	}
	
	public String restart() {
		Game.setGameOver(true);
		return "Game restarted.";
	}
	
	public String take(Character c, Item i) {
		if(c.getInventory().remove(i)) {
			getInventory().add(i);
			return i.getName() + " added to inventory.";
		}
		return c.getName() + " does not have " + i.getName() + ".";
	}
	
	public String give(Character c, Item i) {
		if(getInventory().remove(i)) {
			c.getInventory().add(i);
			return i.getName() + " removed from inventory and given to " + c.getName() + ".";
		}
		return "You do not have " + i.getName();
	}
	
	public String hit(Character c, Weapon w) {
		c.getHealthMonitor().decrease(w.getDamage());
		return String.format("%s hit with %s.\n%sCurrent health%s", c.getName(), w.getName(), c.getName(), c.getHealthMonitor().toString());
	}

	public String north() {
		return move('n');
	}

	public String south() {
		return move('s');
	}

	public String east() {
		return move('e');
	}

	public String west() {
		return move('w');
	}

	public String up() {
		return move('u');
	}

	public String down() {
		return move('d');
	}

	public String move(char dir) {
		getLocation().go(dir);
		return "You went " + DirectionHelper.toString(dir);
	}

	public String enter(Door d) {
		Coordinate temp = d.getLocation().getCoords();
		HashMap<java.lang.Character, Coordinate> sides = getLocation().getMap().getSurroundingSideCoords(getLocation().getCoords());
		
		for(int i = 0; i<DirectionHelper.DIRECTIONS.length; i++) {
			if(sides.get(DirectionHelper.DIRECTIONS[i]).equals(temp)) {
				if(d.isOpen())
					return move(DirectionHelper.DIRECTIONS[i]);
				else
					return d.getName() + " is closed.";
			}
		}
		return d.getName() + " is not in your immediate surroundings.";
	}

	public String enter(Room r) {
		HashMap<java.lang.Character, Coordinate> rooms = getLocation().getMap().getSurroundingRoomCoords(getLocation().getCoords());
		
		for(int i = 0; i<DirectionHelper.DIRECTIONS.length; i++) {
			Room tempRoom = (Room)(r.getLocation().getMap().getPlace(rooms.get(DirectionHelper.DIRECTIONS[i])));
			if(tempRoom.equals(r)) {
				if(r.getLocation().getMap().getSideBetween(r,tempRoom).isExit())
					return move(DirectionHelper.DIRECTIONS[i]);
				else
					return r.getName() + " is not an exit.";
			}
		}
		return r.getName() + " is not in your immediate surroundings.";
	}

	public String drop(Item i) {
		if(getInventory().remove(i))
			return i.getName() + " dropped from inventory";
			
		return "You do not have " + i.getName();
	}

	public String pickUp(Item i) {
		getInventory().add(i);
		return "You picked up " + i.getName();
	}

}
