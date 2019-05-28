package com.bayviewglen.zork.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.bayviewglen.zork.command.NoEffectCommands;
import com.bayviewglen.zork.command.PlayerCommands;
import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Player extends Character implements NoEffectCommands, PlayerCommands {

	private static String[] screaming = { "Aaaaarrrrg", "Aahhhhh", "WHAAAAAAATTTT????", "NIICCKK TONG!!!!",
			"What the hell are you doing with your life???" };
	private static String[] breathing = { "...breath...breath...breath", "You forgot how to breathe lol",
			"Congrats, do you want an award for breathing?", "Breath", "Wow you are so cool" };
	private static String[] jumping = { "Careful, don't fall.",
			"Careful not to break the floorboards (oh wait you're in space)",
			"If you jump too far you'll go out of orbit next time!",
			"Careful - don't get sucked into the space vacuum!" };
	private static String[] falling = { "Stop being so clumsy!", "Be careful next time.", "Good job (sarcasm)",
			"Bleeding sucks." };
	private static String[] dying = { "Noooo, don't die", "You're too young for this", "You're family misses you",
			"Nooo", "..", "." };
	private static String[] taking = { "You're becoming rich.", "It's so precious!", "Isn't that cool?",
			"That's amazing", "You are making great progress!" };
	private static String[] giving = { "What? You're giving up the item :(", "Noooo :(", "You're becoming broke.",
			"You're losing so many items!!!" };

	private static final int ODDS_OF_DISPLAYING = 3;

	private static ArrayList<Room> roomsVisited = new ArrayList<Room>();

	public Player(double weight, HashMap<String, String> descriptions, Inventory inventory, Location location) {
		super("Player", weight, descriptions, inventory, location, new double[] { 1, 1, 1 }, null, null);
	}

	public Inventory getInteractableItems() {
		Inventory i = new Inventory();
		HashMap<java.lang.Character, Coordinate> cc = new HashMap<java.lang.Character, Coordinate>();
		Map m = getLocation().getMap();
		cc = m.getSurroundingSideCoords(getLocation().getCoords());

		for (char dir : Map.LETTER_AXES) {
			Side s = m.getNextSide(dir, getLocation().getCoords());
			if (s != null) {
				i.add(Item.clone(s));
			}
		}

		try {
			Room r = getLocation().getRoom();
			i.add(r);
			i.addAll(r.getRoomItems());
		} catch (NullPointerException e) {

		}
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
		if (!hasVisited(getLocation().getRoom()))
			roomsVisited.add(getLocation().getRoom());
	}

	public static boolean hasVisited(Room room) {
		for (Room i : roomsVisited) {
			if (i.equals(room))
				return true;
		}

		return false;
	}

	public static ArrayList<Room> getRoomsVisited() {
		return roomsVisited;
	}

	public String scream() {
		return Game.getRandom(screaming);
	}

	public String breathe() {
		return Game.getRandom(breathing);
	}

	public String jump() {
		return "You jumped. " + Game.getRandom(jumping);
	}

	public String fall() {
		getHealthMonitor().setToPercent(0.5);
		return "You fell and are now bleeding. " + Game.getRandom(falling);
	}

	public String die() {
		Game.setGameOver(false);
		String result = "";
		for (String s : dying) {
			result += s + "\n\n...\n\n";
		}
		result += "You died. Game over.";
		return result;
	}

	public String quit() {
		Game.setGameOver(false);
		return "Game quit.";
	}

	public String restart() {
		Game.setGameOver(true);
		return "Game restarted.";
	}
	
	
	// Characters DROP items into the room inventory for the player to take
//	public String take(Character c, Item i) {
//		if (c.getInventory().remove(i)) {
//			if (getInventory().add(i))
//				return i.getName() + " added to inventory."
//						+ ((int) (Math.random() * ODDS_OF_DISPLAYING) == 0 ? " " + Game.getRandom(taking) : "");// one
//																												// in 3
//																												// //
//																												// description
//			return "This item is too heavy for you to pick up.";
//		}
//		return c.getName() + " does not have " + i.getName() + ".";
//	}

	public String give(Character c, Item i) {
		if (getInventory().remove(i)) {
			c.getInventory().add(i);
			return i.getName() + " removed from inventory and given to " + c.getName() + "."
					+ ((int) (Math.random() * ODDS_OF_DISPLAYING) == 0 ? " " + Game.getRandom(giving) : "");// one in 3
		}
		return "You do not have " + i.getName();
	}
	
	public String give(Item i, Character c) {
		return give(c, i);
	}

	public String hit(Character c, Weapon w) {
		c.getHealthMonitor().decrease(w.getDamage());
		return String.format("You hit %s with %s.\n%s current health%s", c.getName(), w.getName(), c.getName(),
				c.getHealthMonitor().toString());
	}
	
	public String hit(Weapon w, Character c) {
		return hit(c, w);
	}

	public String getHit(Character c, Weapon w) {
		getHealthMonitor().decrease(w.getDamage());
		return String.format("%s hit you with %s.\nYour current health%s", c.getName(), w.getName(),
				getHealthMonitor().toString());
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
		Side s = getLocation().getMap().getNextSide(dir, getLocation().getCoords());
		Room r = getLocation().getMap().getNextRoom(dir, getLocation().getCoords());
		if (s != null && s.isExit() && r != null) {
			getLocation().setCoords(getLocation().getCoords().add(Map.DIRECTIONS.get(dir)));
			return "You went " + Game.directionWords.get(dir + "") + ".\n" + getLocation().getRoom().getDescription("short");
		} else {
			return s.moveThrough();
		}
	}
	
	public void move() {

	}

	public String enter(Side s) {
		char sideDirection = getLocation().getCoords().direction(s.getLocation().getCoords());

		if (sideDirection == 0) {
			return s.getName() + " is not in your immediate surroundings.";
		}

		if (s.isExit()) {
			return move(sideDirection);
		} else {
			return s.moveThrough();
		}
	}

	public String enter(Room r) {
		char roomDirection = getLocation().getCoords().direction(r.getLocation().getCoords());

		if (roomDirection == 0) {
			return r.getName() + " is not in your immediate surroundings.";
		}

		Side s = getLocation().getMap().getSide(Map.DIRECTIONS.get(roomDirection).add(getLocation().getCoords()));

		if (s.isExit()) {
			return move(roomDirection);
		} else {
			return r.getName() + " is not an exit.";
		}
	}

	public String drop(Item i) {
		if (getInventory().remove(i))
			return i.getName() + " dropped from inventory."
					+ ((int) (Math.random() * ODDS_OF_DISPLAYING) == 0 ? " " + Game.getRandom(giving) : "");// one in 3
																											// ;

		return "You do not have " + i.getName() + ".";
	}

	public String pickUp(Item i) {
		if (getInventory().canAdd(i))
			return "You picked up " + i.getName() + "."
					+ ((int) (Math.random() * ODDS_OF_DISPLAYING) == 0 ? " " + Game.getRandom(taking) : "");// one in 3

		return "This item is too heavy for you to pick up.";
	}

	public String inventory() {
		return getInventory().toString();
	}
	
	public String look() {
		return getLocation().getRoom().getLongDescription();
	}

	public String look(Item i) {
		if (i instanceof Room) {
			return ((Room) i).getLongDescription();
		}
		return i.getDescription("look");
	}

	public String getAllOf(String type) {
		return getInventory().getAllOf(type);
	}

}
