package com.bayviewglen.zork.map;

import java.util.Iterator;
import java.util.Map.Entry;

public class Coordinate {

	private double x, y, z;

	public Coordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Coordinate(double x, double y, double z, boolean isDouble) { // true if the coordinates are double their
																		// actual val
		if (isDouble) {
			this.x = x / 2;
			this.y = y / 2;
			this.z = z / 2;
		}
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

	public Coordinate(String coords) {
		Coordinate c = Coordinate.readCoords(coords);
		this.x = c.x;
		this.y = c.y;
		this.z = c.z;
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
	
	public char direction(Coordinate target) {
		// only one axis should vary between this coordinate and the target coordinate
		Coordinate absDiff = this.absoluteDifference(target);
		
		Iterator<Entry<Character, Coordinate>> it = Map.DIRECTIONS.entrySet().iterator();
		while (it.hasNext()) {
			java.util.Map.Entry<Character, Coordinate> pair = (java.util.Map.Entry<Character, Coordinate>) it.next();
			char key = (char) pair.getKey();
			Coordinate coord = ((Coordinate) pair.getValue()).normalize();
			if (absDiff.equals(coord)) {
				return key;
			}
		}
		
		return 0;		
	}

	public Coordinate multiply(double factor) {
		return new Coordinate(x * factor, y * factor, z * factor);
	}

	public Coordinate absoluteDifference(Coordinate coords) {
		return new Coordinate(Math.abs(x - coords.x), Math.abs(y - coords.y), Math.abs(z - coords.z));
	}
	
	public Coordinate average(Coordinate other) {
		return new Coordinate((x + other.x) / 2, (y + other.y) / 2, (z + other.z) / 2);
	}
	
	public Coordinate normalize() {
		return new Coordinate(Math.signum(x), Math.signum(y), Math.signum(z));
	}

	public static Coordinate readCoords(String line) {
		line.replaceAll("[ (){]", "");
		String[] coordsString = line.split(","); // split on comma to extract coords
		double[] coords = new double[3];
		for (int i = 0; i < 3; i++)
			coords[i] = Double.parseDouble(coordsString[i]);
		return new Coordinate(coords);
	}
	public String toString() {
		return x + ", " + y + ", " + z;
	}

}
