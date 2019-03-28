package com.bayviewglen.map;

import java.util.ArrayList;

public class Phase {
	
	private ArrayList<Map> maps;
	private int currentMapIndex;

	
	public Phase(Map map) {
		maps = new ArrayList<Map>();
		maps.add(map);
		this.currentMapIndex = 0;
	}
	
	public boolean atGoal() {
		return currentMapIndex + 1 >= maps.size();
	}
	
	public void checkAndUpdateMap() {
		if(getCurrentMap().atGoal())
			nextMap();
	}
	
	public void nextMap() {
		currentMapIndex++;
	}
	
	public Map getCurrentMap() {
		return maps.get(currentMapIndex);
	}
	
	public int getCurrentMapIndex() {
		return currentMapIndex;
	}
	
	public void setCurrentMapIndex(int index) {
		if(index>=maps.size())
			throw new IllegalArgumentException("There is no " + index + "th index.");
		
		this.currentMapIndex = index;
	}
	
	
	public ArrayList<Map> getMaps() {
		return maps;
	}
	
	public void add(Map map) {
		maps.add(map);
	}
	
	public void remove(int index) {
		maps.remove(index);
	}
	
	public int size() {
		return maps.size();
	}
	
	
}
