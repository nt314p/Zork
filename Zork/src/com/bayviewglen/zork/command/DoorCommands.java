package com.bayviewglen.zork.command;

import com.bayviewglen.zork.item.Key;

public interface DoorCommands {
	
	public String open();
	public String close();
	public String unlock(Key k);
	public String lock();

}
