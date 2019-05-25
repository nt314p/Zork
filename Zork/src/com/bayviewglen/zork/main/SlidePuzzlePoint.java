package com.bayviewglen.zork.main;

public class SlidePuzzlePoint {
	private int x;
	private int y;
	
	public SlidePuzzlePoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void set(SlidePuzzlePoint slidePuzzlePoint) {
		this.x = slidePuzzlePoint.x;
		this.y = slidePuzzlePoint.y;
	}
	
	public boolean equals(SlidePuzzlePoint slidePuzzlePoint) {
		return this.x == slidePuzzlePoint.x && this.y == slidePuzzlePoint.y;
	}
	
	public void left() {
		x--;
	}
	
	public void right() {
		x++;
	}
	
	public void up() {
		y--;
	}
	
	public void down() {
		y++;
	}
	
	

}
