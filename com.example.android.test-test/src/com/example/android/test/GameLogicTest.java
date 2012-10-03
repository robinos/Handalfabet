package com.example.android.test;

import com.example.android.*;


import android.test.AndroidTestCase;

/**
 * The GameLogicTest class is a testclass to test the GameLogic for Game.
 * 
 * @author  : Grupp02
 * @version : 2012-10-02, v0.2
 * @License : GPLv3
 *
 */
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
	
	/**
	 *  A simple test for score value
	 */
	public void simpleTestScore() {
		
		assertEquals(gameLogic.getRoundScore(),11);
	}
	/**
	 * Test the tree answersbuttons gets unique strings. 
	 */
	public void testUniqueRandomizedLetters() {
		gameLogic.determineChoices();
		assertFalse(gameLogic.getFirstButtonString().equals(gameLogic.getSecondButtonString()));
		assertFalse(gameLogic.getFirstButtonString().equals(gameLogic.getThirdButtonString()));
		assertFalse(gameLogic.getSecondButtonString().equals(gameLogic.getThirdButtonString()));
	}
	
	/**
	 * Runs the tests method 10 times
	 */
	public void testUniqueRandomizedLetters10times() {
		for(int i = 0; i < 10; i++) {
			testUniqueRandomizedLetters();
		}
	}
}
