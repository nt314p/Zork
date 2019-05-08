package com.bayviewglen.zork.item;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bayviewglen.zork.main.FileReader;

public class Preset {
	
	private static HashMap<String, Item> presets;
	
	public static void initialize() {
		presets = new HashMap<String, Item>();
		
		FileReader mapReader = new FileReader("data/presets.json");
		JSONObject obj = new JSONObject(mapReader.getLinesSingle());
		JSONArray jsonPresets = obj.getJSONArray("presets"); // getting array of presets
		
		for (int i = 0; i < jsonPresets.length(); i++) { // iterating through objects
			Item item = Item.loadItem(jsonPresets.getJSONObject(i));
			presets.put(item.getName(), item); // putting item into hashmap
		}
	}
	
	public static Item get(String presetName) {
		return presets.get(presetName);
	}

}
