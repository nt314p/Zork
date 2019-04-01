package com.bayviewglen.zork.map;

import java.util.ArrayList;

public class Phase {
	
	private String phaseName;
	private ArrayList<Map> maps;

	
	public Phase(String phaseName, ArrayList<Map> maps) {
		this.phaseName = phaseName;
		this.maps = maps;
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
