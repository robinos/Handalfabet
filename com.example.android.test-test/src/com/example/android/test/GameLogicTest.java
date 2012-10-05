package com.example.android.test;

import com.example.android.*;


import android.test.AndroidTestCase;

/**
 *     This file is part of Handalfabetet.
 *
 *   Handalfabetet is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Handalfabetet is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Handalfabetet.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * The GameLogicTest class is a testclass to test the GameLogic for Game.
 * 
 * @author  : Grupp02
 * @version : 2012-10-02, v0.2
 * @License : GPLv3
 * @Copyright :Copyright© 2012, Grupp02
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
