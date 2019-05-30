package com.bayviewglen.zork.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.bayviewglen.zork.command.NoEffectCommands;
import com.bayviewglen.zork.command.PlayerCommands;
import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public class Player extends Character implements NoEffectCommands, PlayerCommands {

	private static final String[] SCREAMING = { "Aaaaarrrrg", "Aahhhhh", "WHAAAAAAATTTT????", "NIICCKK TONG!!!!",
			"What the hell are you doing with your life???" };
	private static final String[] BREATHING = { "...breath...breath...breath", "You forgot how to breathe lol",
			"Congrats, do you want an award for breathing?", "Breath", "Wow you are so cool" };
	private static final String[] JUMPING = { "Careful, don't fall.",
			"Careful not to break the floorboards (oh wait you're in space)",
			"If you jump too far you'll go out of orbit next time!",
			"Careful - don't get sucked into the space vacuum!" };
	private static final String[] FALLING = { "You hurt yourself in your own confusion!",
			"You hurt yourself in your own confusion!", "Stop being so clumsy!", "Be careful next time.",
			"Good job (sarcasm)", "Bleeding sucks." };
	private static final String[] TAKING = { "You're becoming rich.", "It's so precious!", "Isn't that cool?",
			"That's amazing", "You are making great progress!" };
	private static final String[] GIVING = { "What? You're giving up the item :(", "Noooo :(", "You're becoming broke.",
			"You're losing so many items!!!" };
	private static final String[] HELLO = { "Hi.", "Hello.", "Good morning."};
	private static final String[] SWEARING = { "Such language is prohibited in Zork.", "Please don't swear.",
			"That language is extremely rude.", "Oops, please don't use that language.", "Don't say that! The AIs are disappointed in you." };
	private static final String[] SMELLING = { "Smells delicious.", "A beautiful odor surrounds the room.",
			"It smells of stench and sweat.", "It smells like space!" };
	private static final String[] PRAYING = { "You prayed.", "May the force be with you.",
			"May you live through this crisis, may you survive space.", "The AIs join you in your prayer." };

	private static final int ODDS_OF_DISPLAYING = 3;

	private static ArrayList<Room> roomsVisited = new ArrayList<Room>();

	public Player(double weight, HashMap<String, String> descriptions, Inventory inventory, Location location) {
		super("Player", weight, descriptions, inventory, location, new double[] { 1, 1, 1 }, null, null);
	}

	public Inventory getInteractableItems() {
		Inventory i = new Inventory();
		Map m = getLocation().getMap();
		HashMap<java.lang.Character, Coordinate> cc = m.getSurroundingSideCoords(getLocation().getCoords());

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
		return Game.getRandom(SCREAMING);
	}

	public String breathe() {
		return Game.getRandom(BREATHING);
	}

	public String jump() {
		return "You jumped. " + Game.getRandom(JUMPING);
	}

	public String fall() {
		getHealthMonitor().setToPercent(0.5);
		return "You fell and are now bleeding. " + Game.getRandom(FALLING);
	}

	public String die() {
		Music.play("data/music/loud_siren.mp3");
		Game.die();
		return "\nYou died. Game over.";
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
					+ ((int) (Math.random() * ODDS_OF_DISPLAYING) == 0 ? " " + Game.getRandom(GIVING) : "");// one in 3
		}
		return "You do not have " + i.getName();
	}

	public String give(Item i, Character c) {
		return give(c, i);
	}
	
	public String destroy(Item i) {
		if(getInventory().remove(i)) 
			return i.getName() + " was removed from inventory and broken. Good job.";
		return "You do not have " + i.getName();
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
			return "You went " + Game.directionWords.get(dir + "") + ".\n"
					+ getLocation().getRoom().getDescription("short");
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
					+ ((int) (Math.random() * ODDS_OF_DISPLAYING) == 0 ? " " + Game.getRandom(GIVING) : "");// one in 3
																											// ;

		return "You do not have " + i.getName() + ".";
	}

	public String pickUp(Item i) {
		if (getInventory().canAdd(i))
			return "You picked up " + i.getName() + "."
					+ ((int) (Math.random() * ODDS_OF_DISPLAYING) == 0 ? " " + Game.getRandom(TAKING) : "");// one in 3

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

	public void checkRoomDeath() {
		HashMap<String, String> roomDescriptions = getLocation().getRoom().getDescriptions();
		if (roomDescriptions.containsKey("death") || roomDescriptions.containsValue("death"))
			die();
	}

	public String checkAirlockDeath() {
		Room r = getLocation().getRoom();
		Map m = getLocation().getMap();
		if (r.getName().indexOf("airlock") != -1) {
			HashMap<java.lang.Character, Coordinate> h = m.getSurroundingSideCoords(r.getLocation().getCoords());

			for (int i = 0; i < Map.LETTER_AXES.length; i++) {
				Side s = m.getSide(h.get(Map.LETTER_AXES[i]));
				if (s.getName().equals("hatch") && s.isExit())
					return "You got sucked out of an airlock. " + die();

			}
		}
		return "";
	}

	public String checkMonitorDeath() {
		if (getHealthMonitor().isDead())
			return "You ran out of health.";
		else if (getFoodMonitor().isDead())
			return "You ran out of food.";
		else if (getWaterMonitor().isDead())
			return "You ran out of water.";
		else
			return "";
	}

	public String hi() {
		return Game.getRandom(HELLO);
	}

	public String swear() {
		return Game.getRandom(SWEARING);
	}

	public String smell() {
		return Game.getRandom(SMELLING);
	}

	public String pray() {
		return Game.getRandom(PRAYING);
	}

}
