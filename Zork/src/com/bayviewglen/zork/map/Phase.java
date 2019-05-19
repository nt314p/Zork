package com.bayviewglen.zork.map;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bayviewglen.zork.main.FileReader;
import com.bayviewglen.zork.main.Inventory;

public class Phase {

	private static HashMap<String, Phase> phases;

	private String phaseName;
	private ArrayList<Map> maps;
	private Location checkpoint;
	private Location endpoint;

	public Phase(String phaseName, ArrayList<Map> maps, Location checkpoint, Location endpoint) {
		this.phaseName = phaseName;
		this.maps = maps;
		this.checkpoint = checkpoint;
		this.endpoint = endpoint;
	}

	public static void initialize() {
		phases = new HashMap<String, Phase>();
		
		File dir = new File("data/phases/");
		File[] dirList = dir.listFiles();
		if (dirList != null) {
			for (File f : dirList) {
				loadPhase(f.getAbsolutePath());
			}
		}
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

	public static Phase getPhase(String name) {
		return phases.get(name);
	}

	private static void loadPhase(String filePath) {
		FileReader reader = new FileReader(filePath);

		JSONObject jObj = new JSONObject(reader.getLinesSingle());
		
		ArrayList<Map> arrMaps = new ArrayList<Map>(); // creating arraylist of maps
		
		String name = jObj.getString("name"); 
		
		// getting checkpoints and endpoints
		Location check = Location.loadLocation(jObj.getJSONObject("checkpoint"));
		Location end = Location.loadLocation(jObj.getJSONObject("endpoint"));

		JSONArray jMaps = jObj.getJSONArray("maps");
		for (int i = 0; i < jMaps.length(); i++) { // iterating through maps
			arrMaps.add(Map.getMap(jMaps.getString(i))); // adding the map to the arraylist
		}
		
		phases.put(name, new Phase(name, arrMaps, check, end));
	}

}
