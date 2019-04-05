package com.bayviewglen.zork.map;

public class Opening extends Side {
	
	private static final boolean isExit = true;
	
	public Opening() {
		super(isExit);
	}
	
	public String toString() {
		return getClass().getSimpleName();
	}

}
