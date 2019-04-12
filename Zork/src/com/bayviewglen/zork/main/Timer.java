package com.bayviewglen.zork.main;

public class Timer {
	private int turns;
	
	public Timer(int turns) {
		this.turns = turns;
	}
	
	public int getTimer() {
		return turns;
	}
	
	public void setTimer(int turns) {
		this.turns = turns;
	}
	
	public void decrementTimer() {
		turns--;
	}
	
	public void incrementTimer() {
		turns++;
	}
	
	public boolean timerUp() {
		return turns<=0;
	}
}
