package com.bayviewglen.zork.main;

public class DirectionHelper {
	
	public static final char [] DIRECTIONS = {'n','s','u','d','e','w'};
	public static String toString(char dir) {
		switch(dir) {
		case 'n': return "North";
		case 's': return "South";
		case 'e': return "East";
		case 'w': return "West";
		case 'u': return "Up";
		default: return "Down";
		}
	}
	
	public static char toChar(String dir) {
		if(dir.equalsIgnoreCase("north"))
			return 'n';
		else if(dir.equalsIgnoreCase("south"))
			return 's';
		else if(dir.equalsIgnoreCase("east"))
			return 'e';
		else if(dir.equalsIgnoreCase("west"))
			return 'w';
		else if(dir.equalsIgnoreCase("up"))
			return 'u';
		else
			return 'd';
		
	}

}
