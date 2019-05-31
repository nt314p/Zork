package com.bayviewglen.zork.main;

import com.bayviewglen.zork.item.Preset;
import com.bayviewglen.zork.map.Coordinate;
import com.bayviewglen.zork.map.Door;
import com.bayviewglen.zork.map.Map;
import com.bayviewglen.zork.map.Maps;
import com.bayviewglen.zork.map.Opening;
import com.bayviewglen.zork.map.Place;
import com.bayviewglen.zork.map.Room;
import com.bayviewglen.zork.map.Wall;

import processing.core.PApplet;

public class MapDisplay extends PApplet {

	private static Map map;
	private static int mapW;
	private static int mapH;
	private static int blockW = 100;
	private static int blockH = 70;
	private static double maxZ;
	private static double currZ;

//	public static void main(String[] args) {
//		Inventory.initialize();
//		Preset.initialize();
//		Maps.initialize();
//		display(Maps.getMap("TESTING TESTER"));
//	}

	public static void display(String mapName) {
		map = Maps.getMap(mapName);
		mapW = map.getMap().length; // x value
		mapH = map.getMap()[0].length; // y value
		PApplet.runSketch(new String[] { "MapDisplay" }, new MapDisplay());
	}

	public void settings() {
		fullScreen();
		smooth(8);
	}

	public void setup() {
		fill(0, 0, 0);
		currZ = 1;
		maxZ = map.getMap()[0][0].length;
	}

	public void draw() {
		background(0, 0, 0);
		drawLayer(currZ);
	}

	public void keyReleased() {
		if (key == TAB) {
			currZ += 1;
			if (maxZ == currZ) {
				currZ = 0;
			}
		}
	}

	public void drawLayer(double layer) {
		for (int i = 0; i < mapW; i++) {
			for (int j = 0; j < mapH; j++) {
				Coordinate c = new Coordinate(i, j, layer, true);
				Place p = map.getPlace(c);
				String t = "";
				try {
					t = p.getName();
					t += "\n" + c.toString();
				} catch (Exception e) {

				}
				if (p instanceof Room) {
					fill(100, 100, 255); // room
				} else if (p instanceof Door) {
					fill(255, 255, 0);
				} else if (p instanceof Opening) {
					fill(0, 255, 0);
				} else if (p instanceof Wall) {
					fill(255, 200, 0);
				} else {
					fill(255, 255, 255);
				}
				rect(i * blockW, j * blockH, blockW, blockH);
				textSize(13);
				textAlign(CENTER, CENTER);
				fill(0);
				text(t, i * blockW, j * blockH, blockW, blockH);
			}
		}
	}
}
