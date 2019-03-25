package com.bayviewglen.map;

public abstract class Place {

	private double x;
	private double y;
	private double z;
	
	private boolean isRoom;
	
	public Place(double x, double y, double z, boolean isRoom) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.isRoom = isRoom;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
}
