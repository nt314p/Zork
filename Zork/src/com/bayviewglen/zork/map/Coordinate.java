package com.bayviewglen.zork.map;

public class Coordinate {

	private double x, y, z;

	public Coordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Coordinate() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Coordinate(double[] coords) {
		this.x = coords[0];
		this.y = coords[1];
		this.z = coords[2];
	}

	public double[] toArray() {
		return new double[] { x, y, z };
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public boolean equals(Coordinate coords) {
		return (x == coords.x) && (y == coords.y) && (z == coords.z);
	}
	
	public Coordinate add(Coordinate c) { 
		return new Coordinate(x + c.x, y + c.y, z + c.z);
	}
	
	public Coordinate multiply(double factor) {
		return new Coordinate(x * factor, y * factor, z * factor);
	}
	
	public static Coordinate readCoords (String line) {
		line.replaceAll("[ (){]", "");
		String[] coordsString = line.split(","); // split on comma to extract coords
		double[] coords = new double[3];
		for (int i = 0; i < 3; i++)
			coords[i] = Double.parseDouble(coordsString[i]);
		return new Coordinate(coords);
	}

}
