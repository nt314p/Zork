package com.bayviewglen.zork.map;
import org.json.*;
import com.bayviewglen.zork.main.*;

public class JSONReaderTest {
	
	public static void main(String[] args) {
		FileReader mapReader = new FileReader("data/jasontest.json");
		String[] lines = mapReader.getLines();
		String mastaboi = "";
		
		for (String s : lines) {
			mastaboi += s + "\n";
		}
		
		JSONObject obj = new JSONObject(mastaboi);
		String name = obj.getString("name");
		System.out.println(name);

		JSONArray arr = obj.getJSONArray("places");
		for (int i = 0; i < arr.length(); i++)
		{
			String room = arr.getJSONObject(i).getString("name");
			System.out.println(room);
		}
	}
}
