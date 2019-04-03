package com.bayviewglen.zork.main;

import java.util.ArrayList;

import com.bayviewglen.zork.command.CommandWords;
import com.bayviewglen.zork.command.Parser;
import com.bayviewglen.zork.map.*;

/**
 * 
 * @authors bmerbaum, ntong, eelbaz
 * @version final final final final final final final 5.3
 *
 */

public class Game{

	private static Parser parser;	
	private static Player player = new Player(null, null);
	private static ArrayList<Phase> phases;
	private static ArrayList<Character> characters;
	
	private static int turn;
	private static int turnsUntilDeath;
	

	public Game() {
		parser = new Parser();
		CommandWords.initialize();
		phases = new ArrayList<Phase>();
	}
	
	public static int getTurn() {
		return turn;
	}
	
	public static void incrementTurn() {
		turn++;
	}
	
	public static void setTurn(int turn) {
		Game.turn = turn;
	}
	
	
	public static int getTurnsUntilDeath() {
		return turnsUntilDeath;
	}
	
	public static void decrementTurnsUntilDeath() {
		turnsUntilDeath--;
	}
	
	public static void setTurnsUntilDeath(int x) {
		turnsUntilDeath = x;
	}
	
	
	
	public static ArrayList<Character> getCharacters() {
		return characters;
	}
	
	public static ArrayList<Phase> getPhases() {
		return phases;
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	public static int indexOf(Phase phase) {
		for (int i = 0; i < phases.size(); i++) {
			if (phases.get(i).equals(phase)) {
				return i;
			}
		}
		return -1;
	}
	
}