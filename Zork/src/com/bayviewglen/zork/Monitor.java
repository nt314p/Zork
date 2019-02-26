package com.bayviewglen.zork;

public class Monitor {
	//measures, monitors, adjusts player health, water, food
	
	final static double MONITOR_MAX = 1;
	final static double LOW_LIMIT = 0.2;
	final static double CRITICAL_LIMIT = 0.1;
	
	double meter;
	
	public Monitor() {
		meter = MONITOR_MAX;
	}
	
	public Monitor(double monitorStart) {
		meter = monitorStart;
	}
	
	/**
	 *
	 * @return the player's statistic meter - how much food, health, water, etc.
	 */
	public double get() {
		return meter;
	}
	
	/**
	 * sets the meter TO a new amount
	 * @param meter the new meter
	 */
	public void set(double meter) {
		this.meter = meter;
		trimToSize();
	}
	
	/**
	 * reduce the meter BY a certain amount
	 * @param decreaseBy the amount to reduce by
	 */
	public void decrease(double decreaseBy) {
		meter -= decreaseBy;
	}
	
	
	/**
	 * increase the meter BY a certain amount
	 * @param increaseBy the amount to increase by
	 */
	public void increase(double increaseBy) {
		meter += increaseBy;
		trimToSize();
	}
	
	/**
	 * 
	 * @return if the monitor is low (low, critical, dead)
	 */
	public boolean isLow() {
		return meter<=LOW_LIMIT;
	}
	
	/**
	 * 
	 * @return if the monitor is critically low (low, critical, dead)
	 */
	public boolean isCritical() {
		return meter<=CRITICAL_LIMIT;
	}
	
	/**
	 * 
	 * @return if the monitor is dead (low, critical, dead)
	 */
	public boolean isDead() {
		return meter <= 0;
	}
	
	/**
	 * set the monitor to original
	 */
	public void reset() {
		meter = MONITOR_MAX;
	}
	
	/**
	 * make sure meter doesn't exceed the limit - if so, make it equal the limit
	 */
	public void trimToSize() {
		if(meter>MONITOR_MAX)
			meter = MONITOR_MAX;
	}
	
	
	

}
