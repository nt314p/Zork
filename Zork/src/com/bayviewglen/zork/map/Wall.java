package com.bayviewglen.zork.map;

public class Wall extends Side {
	
	private static final boolean isExit = false;

	public Wall() {
		super(isExit);
	}
	
	public String toString() {
		return getClass().getSimpleName();
	}
	
}
