package com.bayviewglen.zork.command;

import com.bayviewglen.zork.item.Key;

public interface Unlockable {
	public boolean unlock(Key key);
}
