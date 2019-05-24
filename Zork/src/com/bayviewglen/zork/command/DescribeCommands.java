package com.bayviewglen.zork.command;

public interface DescribeCommands {
	
	public String inventory();
	public String look();
	public String commands();
	public String help();
	
	public String getAllOf(String type);

}
