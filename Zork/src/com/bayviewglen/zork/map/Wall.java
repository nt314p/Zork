package com.bayviewglen.zork.map;

public class Wall extends Side {

	public Wall() {
		super(false);
	}
	
	public boolean checkIsExit() {
		return false;
	}
}
