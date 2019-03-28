package com.bayviewglen.zork;

import java.util.ArrayList;
import com.bayviewglen.item.*;
import com.bayviewglen.map.*;

public class Player{
	
	private static int deaths = 0;
	
	private static Monitor healthMonitor = new Monitor();
	private static Monitor foodMonitor = new Monitor();
	private static Monitor waterMonitor = new Monitor();
	//*****IF THERE ARE TOO MANY OF THESE, COULD MAKE A LIST OF MONITORS
	

	

	/**
	 * die - update death count, reset statistics, reset current room to the checkpoint
	 */
	public static void die() {
		deaths++;
		healthMonitor.reset();
		foodMonitor.reset();
		waterMonitor.reset();
		//currentRoom = currentPhase.getCurrentMap().getCheckpoint();
		//need to reset location
	}

	/**
	 * check if healthMonitor/foodMonitor/waterMonitor is 0
	 * @return if you're dead
	 */
	public static boolean checkDeath() {
		return healthMonitor.isDead() || foodMonitor.isDead() || waterMonitor.isDead();
	}
	
	public static void increase(String monitor, double amount) {
		if(monitor.equals("health"))
			healthMonitor.increase(amount);
		else if(monitor.equals("food"))
			foodMonitor.increase(amount);
		else if(monitor.equals("water"))
			waterMonitor.increase(amount);
		else
			System.out.println(monitor + " is not a monitor.");
	}


	
	
	

	
	
}
