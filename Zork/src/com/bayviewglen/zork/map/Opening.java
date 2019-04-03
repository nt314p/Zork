package com.bayviewglen.zork.map;

public class Opening extends Side {
	
	public Opening() {
		super(true);
	}
	
	public boolean checkIsExit() {
		return true;
	}


}
