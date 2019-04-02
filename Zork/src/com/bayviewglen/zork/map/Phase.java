package com.bayviewglen.zork.map;

import java.util.ArrayList;

import com.bayviewglen.zork.item.Item;

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

	public String getPhaseName() {
		return phaseName;
	}
	
	public boolean equals(Phase phase) {
		return this.phaseName.equals(phase.getPhaseName());
	}
	
	public int indexOf(Map map) {
		for (int i = 0; i < maps.size(); i++) {
			if (maps.get(i).equals(map)) {
				return i;
			}
		}
		return -1;
	}
	
	
}
