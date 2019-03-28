package com.bayviewglen.map;

import java.util.ArrayList;

public class Phase {
	
	private String phaseName;
	private ArrayList<Map> maps;
	private int currentMapIndex;

	
	public Phase(String phaseName, Map map) {
		this.phaseName = phaseName;
		maps = new ArrayList<Map>();
		maps.add(map);
		this.currentMapIndex = 0;
	}
	
	public boolean atPhaseGoal() {
		return currentMapIndex + 1 >= maps.size();
	}
	
	public void checkAndUpdateMap() {
		if(getCurrentMap().atMapGoal())
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
	
	public void addMap(Map map) {
		maps.add(map);
	}
	
	public void addMap(Map map, int index) {
		maps.add(index, map);
	}
	
	public boolean removeMap(int index) {
		if(index>=maps.size())
			return false;
		maps.remove(index);
		return true;
	}
	
	public boolean removeMap(Map map) {
		int temp = this.search(map);
		if(temp == -1)
			return false;
		
		maps.remove(map);
		return true;
	}
	
	public int search(Map map) {
		for(int i = 0; i<maps.size(); i++) {
			if(maps.get(i).equals(map))
				return i;
		}
		return -1;
	}
	
	public int numMaps() {
		return maps.size();
	}
	
	public String getPhaseName() {
		return phaseName;
	}
	
	public boolean equals(Phase phase) {
		return this.phaseName.equals(phase.getPhaseName());
	}
	
	
}
