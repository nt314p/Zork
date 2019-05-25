package com.bayviewglen.zork.main;

public class SlidePuzzlePiece {

	private SlidePuzzlePoint coords;
	private SlidePuzzlePoint endCoords;
	private int num;
	
	public SlidePuzzlePiece(SlidePuzzlePoint coords, SlidePuzzlePoint endCoords, int num) {
		this.coords = coords;
		this.endCoords = endCoords;
		this.num = num;
	}
	
	public int getNum() {
		return num;
	}
	
	public SlidePuzzlePoint getCoords() {
		return coords;
	}
	
	public SlidePuzzlePoint getEndCoords() {
		return endCoords;
	}
	
	public boolean atCorrectPoint() {
		return coords.equals(endCoords);
	}
	
	public boolean isEmpty() {
		return endCoords == null;
	}
}
