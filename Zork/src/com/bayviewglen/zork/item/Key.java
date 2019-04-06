package com.bayviewglen.zork.item;

import java.util.ArrayList;
import java.util.HashMap;

public class Key extends Item{
	String code;
	
	public Key(String name, double weight, HashMap<String, String> descriptions, String code) {
		super(name, weight, descriptions);
		this.code = code;
	}
	
	
	public Key(Key key) {
		super(key.getName(), key.getWeight(), key.getDescriptions());
		this.code = key.code;
	}
	
	public Key(Item item, String code) {
		super(item);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String toString() {
		String str = super.toString();
		str += "\nCode: " + code;

		return str;
	}
	
	public boolean equals(Key key) {
		return this.code.equals(key.code);
	}
	

}
