package com.bayviewglen.zork.main;

import com.bayviewglen.zork.item.Preset;
import com.bayviewglen.zork.map.Coordinate;
import com.bayviewglen.zork.map.Map;

import processing.core.PApplet;

public class MapDisplay extends PApplet {

	private static Map map;
	private static int mapW;
	private static int mapH;
	private static int blockW = 40;
	private static int blockH = 40;

	public static void main(String[] args) {
		Preset.initialize();
		display(Map.loadMap("data/minesweeper2.json"));
	}

	public static void display(Map m) {
		map = m;
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
	}

	public void draw() {
		background(0, 0, 0);
		drawLayer(1);
		
	}

	public void drawLayer(double layer) {
    	for (int i = 0; i < mapW; i++) {
    		for (int j = 0; j < mapH; j++) {
    			String t = "";
    			if (i % 2 == 1 && j % 2 == 1) {
    				fill(0, 0, 255); // room
    				t = map.getPlace(new Coordinate(i, j, layer, true)).getName();
    			} else if (i % 2 != j % 2) {
    				fill(0, 255, 0); // side
    			} else {
    				fill(255, 255, 255); // empty
    			}
    			rect(i * blockW, j * blockH, blockW, blockH);
    			textSize(13);
    			textAlign(CENTER, CENTER);
    			fill(255);
    			text(t, i * blockW, j * blockH, blockW, blockH);
    		}
    	}
    }
}
