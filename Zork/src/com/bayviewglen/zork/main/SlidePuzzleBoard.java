package com.bayviewglen.zork.main;

public class SlidePuzzleBoard {
	
	private static final char UP = 'w';
	private static final char DOWN = 's';
	private static final char LEFT = 'a';
	private static final char RIGHT = 'd';
	private static final int NUM_DIRS = 4;

	private static int boardX;
	private static int boardY;
	private static SlidePuzzlePiece[][] board;
	private static SlidePuzzlePiece empty;
	
	public static void initializeBoard(int boardX, int boardY) {
		SlidePuzzleBoard.boardX = boardX;
		SlidePuzzleBoard.boardY = boardY;
		board = new SlidePuzzlePiece[boardY][boardX];
		
		int x = 0;
		for(int i = 0; i<boardY; i++) {
			for(int j = 0; j<boardX; j++) {
				SlidePuzzlePiece slidePuzzlePiece = new SlidePuzzlePiece(new SlidePuzzlePoint(j, i), new SlidePuzzlePoint(j, i), x);
				set(slidePuzzlePiece);
				x++;
			}
		}
		
		empty = new SlidePuzzlePiece(new SlidePuzzlePoint(boardX - 1, boardY - 1), null, -1);
		set(empty);
		
		for(int i = 0; i<100000; i++) {
			int random = (int)(Math.random() * NUM_DIRS);
			if(random == 0)
				go(UP);
			else if(random == 1)
				go(DOWN);
			else if(random == 2)
				go(LEFT);
			else if(random == 3)
				go(RIGHT);
		}
	}
	
	public static SlidePuzzlePiece[][] getBoard(){
		return board;
	}
	
	public static boolean isValid(char dir) {
		SlidePuzzlePoint loc = empty.getCoords();
		if(dir == UP)
			return loc.getY() < boardY - 1;
		else if (dir == DOWN)
			return loc.getY() > 0;
		else if (dir == LEFT)
			return loc.getX() < boardX - 1;
		else if (dir == RIGHT) 
			return loc.getX() > 0;

		return false;
	}
	
	public static void go(char dir) {
		if(dir == UP) {
			if(isValid(UP)) {
				SlidePuzzlePiece temp = get(empty, DOWN);
				temp.getCoords().up();
				set(temp);
				empty.getCoords().down();
				set(empty);
			}
		} else if (dir == DOWN) {
			if(isValid(DOWN)) {
				SlidePuzzlePiece temp = get(empty, UP);
				temp.getCoords().down();
				set(temp);
				empty.getCoords().up();
				set(empty);
			}
		} else if (dir == LEFT) {
			if(isValid(LEFT)) {
				SlidePuzzlePiece temp = get(empty, RIGHT);
				temp.getCoords().left();
				set(temp);
				empty.getCoords().right();
				set(empty);
			}
		} else {
			if(isValid(RIGHT)) {
				SlidePuzzlePiece temp = get(empty, LEFT);
				temp.getCoords().right();
				set(temp);
				empty.getCoords().left();
				set(empty);
			}
		}
	}
	
	public static String display() {
		String str = "";
		for(int i = 0; i<boardY; i++) {
			for(int j = 0; j<boardX; j++) {
				String out = board[i][j].getNum() + 1 + "";
				if(out.equals("0"))
					out = "  ";
				else if(out.length() == 1)
					out = "0" + out;
				str += out + " ";	
			}
			str += "\n";
		}
		return str;
	}
	
	public static boolean isSolved() {
		
		for(int i = 0; i<boardY; i++) {
			for(int j = 0; j<boardX; j++) {
				if(board[i][j].isEmpty())
					continue;
				else if(!board[i][j].atCorrectPoint())
					return false;
			}
		}
		return true;
	}
	

	
	public static void set(SlidePuzzlePiece slidePuzzlePiece) {
		board[slidePuzzlePiece.getCoords().getY()][slidePuzzlePiece.getCoords().getX()] = slidePuzzlePiece;
	}
	
	public static SlidePuzzlePiece get(SlidePuzzlePiece slidePuzzlePiece, char dir) {
		SlidePuzzlePoint temp = slidePuzzlePiece.getCoords();
		try {
			switch (dir) {
			case UP: return board[temp.getY()-1][temp.getX()];
			case DOWN: return board[temp.getY()+1][temp.getX()];
			case LEFT: return board[temp.getY()][temp.getX()-1];
			default: return board[temp.getY()][temp.getX()+1];
			}
		} catch (Exception ex) {
			return null;
		}
	}

}
