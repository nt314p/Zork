package com.bayviewglen.zork.map;

import java.util.ArrayList;

public class PhaseList {
	
	static ArrayList<Phase> phaseList;
	
	public static void initialize(ArrayList<Phase> phaseList) {
		PhaseList.phaseList = phaseList;
	}
	
	public static ArrayList<Phase> getPhaseList() {
		return phaseList;
	}
	
	public static Phase get(int index) {
		if(index >= size())
			throw new IllegalArgumentException("Index " + index + " is out of bounds");
		
		return phaseList.get(index);
	}
	
	public static void addPhase(Phase phase) {
		phaseList.add(phase);
	}
	
	public static void addPhase(Phase phase, int index) {
		phaseList.add(index, phase);
	}	
	
	public static boolean removePhase(int index) {
		if(index>=phaseList.size())
			return false;
		
		phaseList.remove(index);
		return true;
	}
	
	public static boolean removePhase(Phase phase) {
		int temp = search(phase);
		if(temp == -1)
			return false;
		
		phaseList.remove(temp);
		return true;
	}
		
	
	public static int search(Phase phase) {
		for(int i = 0; i<phaseList.size(); i++) {
			if(phaseList.get(i).equals(phase))
				return i;
		}
		return -1;
	}
	

	public static int size() {
		return phaseList.size();
	}
}
