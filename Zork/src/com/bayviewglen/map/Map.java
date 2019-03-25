package com.bayviewglen.map;

public class Map {
	
	//very important *** everything is doubled into the 2D array - a place's x-value is double in the array
	
	private Place[][][] map;
	
	public Map(double x, double y, double z) {
		map = new Place[(int)(x*2)][(int)(y*2)][(int)(z*2)];
	}
	
	public void set(double x, double y, double z, Place p) {
		map[(int)(x*2)][(int)(y*2)][(int)(z*2)] = p;
	}
	
	public Place get(double x, double y, double z) {
		return map[(int)(x*2)][(int)(y*2)][(int)(z*2)];
	}
	
	public boolean checkMapBuildingErrors() {
		for(int i = 0; i<map.length; i++) {
			for(int j = 0; j<map[i].length; j++) {
				for(int k = 0; k<map[j].length; k++) {
					
					if(i%2==0 && j%2==0 && k%2==0) {
						if(!map[i][j][k].isRoom())
							return false;
					} else
						if(map[i][j][k].isRoom())
							return false;
				}
			}
		}
		return true;
	}
	
}
