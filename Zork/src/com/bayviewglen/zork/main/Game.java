package com.bayviewglen.zork.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.bayviewglen.zork.map.*;

/**
 * 
 * @authors bmerbaum, ntong, eelbaz
 * @version final final final final final final final 5.3
 *
 */

public class Game{
	private Parser parser;	
	static Player player = new Player(null, null);
	
	public Game(Phase phase) {		
		parser = new Parser();
		CommandWords.initialize();
		PhaseList.initialize(new ArrayList<Phase>());
		
	}

	

	
}
