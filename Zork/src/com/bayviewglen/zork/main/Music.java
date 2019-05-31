package com.bayviewglen.zork.main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class Music {

	// play the MP3 file to the sound card
	public static void play(String fileName) {
		Thread mplayer = new Thread(new MediaPlayer(fileName));
		mplayer.start();
	}

	static class MediaPlayer implements Runnable {
		String fileName;

		public MediaPlayer(String fileName) {
			this.fileName = fileName;
		}

		public void run() {
			try {
				FileInputStream fis = new FileInputStream(fileName);
				BufferedInputStream bis = new BufferedInputStream(fis);
				javazoom.jl.player.Player player = new javazoom.jl.player.Player(bis);
				player.play();
			} catch (Exception e) {
				System.out.println("Problem playing file " + fileName);
				System.out.println(e);
			}
		}
	}

}
