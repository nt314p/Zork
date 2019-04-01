package com.bayviewglen.zork.main;

import java.util.ArrayList;
import com.bayviewglen.zork.map.*;

/**
 * 
 * @authors bmerbaum, ntong, eelbaz
 * @version final final final final final final final 5.3
 *
 */

public class Game{
	private Parser parser;	
	private static Player player = new Player(null, null);
	private static ArrayList<Phase> phases;
	

	public Game() {
		parser = new Parser();
		CommandWords.initialize();
		phases = new ArrayList<Phase>();
	}
	
	public static ArrayList<Phase> getPhases() {
		return phases;
	}
	
	public static Player getPlayer() {
		return player;
	}
	
}