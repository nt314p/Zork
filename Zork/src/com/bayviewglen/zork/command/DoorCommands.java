package com.bayviewglen.zork.command;

import com.bayviewglen.zork.item.*;

public interface DoorCommands {
	
	public String open();
	public String close();
	public String unlock(String keyCode);
	public String lock();

}
