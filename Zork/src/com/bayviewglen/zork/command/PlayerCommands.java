package com.bayviewglen.zork.command;

import com.bayviewglen.zork.item.*;
import com.bayviewglen.zork.map.*;

public interface PlayerCommands {
	
	public String eat(Food food);
	public String drink(Food water);
	public String heal(Health health);
	
	public String quit();
	public String restart();
	public String die();
	
	public String north();
	public String south();
	public String east();
	public String west();
	public String up();
	public String down();
	public String move(char dir);
	
	public String enter(Door d);
	public String enter(Room r);
	
	public String drop(Item i);
	public String pickUp(Item i);
	public String give(Character c, Item i);
	public String take(Character c, Item i);

}