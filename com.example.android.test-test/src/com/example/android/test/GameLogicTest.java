package com.example.android.test;

import com.example.android.*;


import android.test.AndroidTestCase;


public class GameLogicTest extends AndroidTestCase {
	
	GameLogic gameLogic;
	Game game;
	

	protected void setUp() throws Exception {
		game = new Game();
		gameLogic = new GameLogic(1,game);
		gameLogic.scoreCounter();
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void simpleTestScore() {
		
		assertEquals(gameLogic.getRoundScore(),11);
	}
}
