package com.bayviewglen.zork.main;

import java.util.Scanner;

public class SlidePuzzle {
	
	static final char SHUFFLE = '~';
	static final char QUIT = '`';
	static final int GAME_X = 3;
	static final int GAME_Y = 3;

	public static void play(){
		Scanner scanner = new Scanner(System.in);
		boolean isPlaying = true;
		
		while(isPlaying) {
			SlidePuzzleBoard.initializeBoard(GAME_X, GAME_Y);
					//new SlidePuzzleBoard(getGame(scanner, "Game X: "), getGame(scanner, "Game Y: "));
			
			int i;
			for(i = 0; !SlidePuzzleBoard.isSolved(); i++){
				System.out.println(SlidePuzzleBoard.display() + "\n");
				char temp = getTurn(scanner, i);
				
				if(temp == SHUFFLE)
					SlidePuzzleBoard.initializeBoard(GAME_X, GAME_Y);
				else if (temp == QUIT){
					isPlaying = false;
					break;
				}else
					SlidePuzzleBoard.go(temp);
			}
			
			if(isPlaying) {			
				System.out.println(SlidePuzzleBoard.display());
				System.out.println("You won in " + i + " turns!");
				isPlaying = playAgain(scanner);
			}
		}
		System.out.println("Thanks for playing.");
	}

	public static boolean playAgain(Scanner scanner) {
		boolean legit = false;
		
		while(!legit) {
			System.out.print("Play again? (Y/N) ");
			String in = scanner.next();
			char temp = in.charAt(0);
			if(temp == 'y' || temp == 'Y')
				return true;
			else if(temp == 'n' || temp == 'N')
				return false;
			else
				System.out.println(in + " is not a valid response.");
			
		}
		return false;
	}

	public static int getGame(Scanner scanner, String z) {
		boolean legit = false;
		
		while(!legit) {
			System.out.print(z);
			String in = scanner.next();
			try {
				int x = Integer.parseInt(in);
				return x;
			} catch (Exception ex) {
				System.out.println(in + " is not a valid board size. Please pick a number.");
			}
		}
		return 0;
	}

	public static char getTurn(Scanner scanner, int turnNum) {
		boolean legit = false;
		
		while(!legit) {
			System.out.print("Move " + turnNum + ": ");
			String in = scanner.next();
			
			if(in.equalsIgnoreCase("shuffle"))
				return SHUFFLE;
			else if(in.equalsIgnoreCase("quit"))
				return QUIT;
			
			char turn = in.charAt(0);
			if(SlidePuzzleBoard.isValid(turn))
				return turn;
			else
				System.out.println(turn + " is not a valid move.\n");
		}
		return '0';
	}

}
