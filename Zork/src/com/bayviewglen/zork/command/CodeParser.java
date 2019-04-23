package com.bayviewglen.zork.command;

import java.util.ArrayList;

public class CodeParser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static Object parse(String line) {
		
		ArrayList<Object> objs = new ArrayList<Object>();
		
		String[] names = line.split(".");
		for(String s : names) {
			try {
				objs.add(Class.forName(s));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		return null;
	}
}