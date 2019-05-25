package com.bayviewglen.zork.command;

public interface DoorCommands {
	
	public String open();
	public String close();
	public String unlock(String keyCode);
	public String lock();

}
