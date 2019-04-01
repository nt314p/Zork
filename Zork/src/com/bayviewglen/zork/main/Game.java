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
	
	private ArrayList<Phase> phases;
	private int currentPhaseIndex;

	
	public Game(Phase phase) {
		phases = new ArrayList<Phase>();
		phases.add(phase);
		this.currentPhaseIndex = 0;
		
		parser = new Parser();
		CommandWords.initialize();
		
	}
	
	/**
	 * @return location [phase, map, x, y, z]
	 */
	public double [] getLocation() {
		double[] location = new double[5];
		location[0] = currentPhaseIndex;
		location[1] = getCurrentPhase().getCurrentMapIndex();
		
		Map temp = getCurrentPhase().getCurrentMap();
		location[2] = temp.getCoords(temp.getCurrentRoom())[0];
		location[3] = temp.getCoords(temp.getCurrentRoom())[1];
		location[4] = temp.getCoords(temp.getCurrentRoom())[2];
		return location;
	}
	
	/**
	 * @param location [phase, map, x, y, z]
	 */
	public void setLocation(double [] location) {
		setCurrentPhaseIndex((int)location[0]);
		getCurrentPhase().setCurrentMapIndex((int)location[1]);
		
		Map temp = getCurrentPhase().getCurrentMap();
		temp.setCurrentRoom(temp.getRoom(location[2], location[3], location[4])); 
		//getCurrentPhase().getCurrentMap().setCurrentRoom(getCurrentPhase().getCurrentMap().getRoom(location[2], location[3], location[4])); 
	}
	
	public boolean atGameGoal() {
		return currentPhaseIndex + 1 >= phases.size();
	}
	
	public void checkAndUpdatePhase() {
		if(getCurrentPhase().atPhaseGoal())
			nextPhase();
	}
	
	public void nextPhase() {
		currentPhaseIndex++;
	}
	
	public Phase getCurrentPhase() {
		return phases.get(currentPhaseIndex);
	}
	
	public int getCurrentPhaseIndex() {
		return currentPhaseIndex;
	}
	
	public void setCurrentPhaseIndex(int index) {
		if(index>=phases.size())
			throw new IllegalArgumentException("There is no " + index + "th index.");
		
		this.currentPhaseIndex = index;
	}
	
	public ArrayList<Phase> getPhases() {
		return phases;
	}
	
	public void addPhase(Phase phase) {
		phases.add(phase);
	}
	
	public void addPhase(Phase phase, int index) {
		phases.add(index, phase);
	}	
	
	public boolean removePhase(int index) {
		if(index>=phases.size())
			return false;
		
		phases.remove(index);
		return true;
	}
	
	public boolean removePhase(Phase phase) {
		int temp = this.search(phase);
		if(temp == -1)
			return false;
		
		phases.remove(temp);
		return true;
	}
		
	
	public int search(Phase phase) {
		for(int i = 0; i<phases.size(); i++) {
			if(phases.get(i).equals(phase))
				return i;
		}
		return -1;
	}
	

	public int numPhases() {
		return phases.size();
	}
	
}
