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
package com.example.android.test;

import com.example.android.*;


import android.test.AndroidTestCase;
import android.util.Log;

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
	
	GameLogic gameLogic1;
	GameLogic gameLogic2;
	GameLogic gameLogic3;
	Game game;
	

	protected void setUp() throws Exception {
		game = new Game();
		gameLogic1 = new GameLogic(1,game);
		gameLogic2 = new GameLogic(2,game);
		gameLogic3 = new GameLogic(3,game);
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 *  A simple test for score value
	 *  Difficult 1
	 */
	public void simpleTestScore1() {
		gameLogic1.scoreCounter();
		assertEquals(gameLogic1.getRoundScore(), gameLogic1.getTotalScore() );   
	}
	/**
	 *  A simple test for score value
	 *  Difficult 2
	 */
	public void simpleTestScore2() {
		gameLogic2.scoreCounter();
		assertEquals(gameLogic2.getRoundScore(), gameLogic2.getTotalScore() );   
	}
	/**
	 *  A simple test for score value
	 *  Difficult 3
	 */
	public void simpleTestScore3() {
		gameLogic1.scoreCounter();
		assertEquals(gameLogic3.getRoundScore(), gameLogic3.getTotalScore() );   
	}
	
	/**
	 * Simulates a test for score after 10 rounds
	 */
	public void simpleTestScore10times() {
		for(int i = 0; i < 10; i++) {
			simpleTestScore1();
		}
	}
	
	
	/**
	 * Tests if the tree answersbuttons gets unique strings.
	 * Difficult 1 
	 */
	public void testUniqueRandomizedLetters() {
		
		gameLogic1.determineChoices();
		assertFalse(gameLogic1.getFirstButtonString()
							 .equals(
				    gameLogic1.getSecondButtonString()));
		
		assertFalse(gameLogic1.getFirstButtonString()
							 .equals(
					gameLogic1.getThirdButtonString()));
		
		assertFalse(gameLogic1.getSecondButtonString()
							 .equals(
					gameLogic1.getThirdButtonString()));
		
		
		Log.e("",gameLogic1.getFirstButtonString());
		Log.e("",gameLogic1.getSecondButtonString());
		Log.e("",gameLogic1.getThirdButtonString());
		
		
	}
	
	/**
	 * Difficult 1
	 */
	public void testIfAnswersButtonsContainsTheCorrextAnswer1() {
		
		gameLogic1.determineChoices();
		
		Log.e("","<<<"+	gameLogic1.getFirstPicture());
		Log.e("","<<<"+	gameLogic1.getFirstButtonString());
		
		assertTrue(	gameLogic1.getFirstButtonString().equals(gameLogic1.getFirstPicture())
					||
					gameLogic1.getSecondButtonString().equals(gameLogic1.getFirstPicture()) 
					||
					gameLogic1.getThirdButtonString().equals(gameLogic1.getFirstPicture()) 
					);
		
		
	}
	
	/**
	 * Runs the tests method 10 times
	 * This tests has the O(n^3) so this limits the test to 10 rounds
	 */
	public void testUniqueRandomizedLetters10times() {
		for(int i = 0; i < 10; i++) {
			testUniqueRandomizedLetters();
		}
	}
	
	/**
	 * Tests if the tree answersbuttons gets unique strings. 
	 * Difficult 2
	 */
	public void testUniqueRandomizedLetters2() {
		
		gameLogic2.determineChoices();
		assertFalse(gameLogic2.getFirstButtonString()
							 .equals(
				    gameLogic2.getSecondButtonString()));
		
		assertFalse(gameLogic2.getFirstButtonString()
							 .equals(
					gameLogic2.getThirdButtonString()));
		
		assertFalse(gameLogic2.getSecondButtonString()
							 .equals(
					gameLogic2.getThirdButtonString()));
		
		
		Log.e("",gameLogic2.getFirstButtonString());
		Log.e("",gameLogic2.getSecondButtonString());
		Log.e("",gameLogic2.getThirdButtonString());
		
		
	}
	
	/**
	 * Tests if the tree answersbuttons gets unique strings. 
	 * Difficult 3 
	 */
	public void testUniqueRandomizedLetters3() {
		
		gameLogic3.determineChoices();
		assertFalse(gameLogic3.getFirstButtonString()
							 .equals(
				    gameLogic3.getSecondButtonString()));
		
		assertFalse(gameLogic3.getFirstButtonString()
							 .equals(
					gameLogic3.getThirdButtonString()));
		
		assertFalse(gameLogic3.getSecondButtonString()
							 .equals(
					gameLogic3.getThirdButtonString()));
		
		
		Log.e("",gameLogic3.getFirstButtonString());
		Log.e("",gameLogic3.getSecondButtonString());
		Log.e("",gameLogic3.getThirdButtonString());
		
		
	}
	/**
	 * Difficult 2
	 */
	public void testIfAnswersButtonsContainsTheCorrextAnswer2() {
		
		gameLogic2.determineChoices();
		
		Log.e("","<<<"+	gameLogic2.getFirstPicture() 
				      + gameLogic2.getSecondPicture());

		Log.e("","<<<"+	gameLogic2.getFirstButtonString());
		Log.e("","<<<"+	gameLogic2.getSecondButtonString());
		Log.e("","<<<"+	gameLogic2.getThirdButtonString());
		
		assertTrue(	gameLogic2.getFirstButtonString().equals(
					 		gameLogic2.getFirstPicture()
					 		+gameLogic2.getSecondPicture())
					||
					gameLogic2.getSecondButtonString().equals(
							 gameLogic2.getFirstPicture()
							 +gameLogic2.getSecondPicture()) 
					||
					gameLogic2.getThirdButtonString().equals(
							 gameLogic2.getFirstPicture()
							 +gameLogic2.getSecondPicture()) 
					);
		
		
	}
	
	/**
	 * Difficult 3
	 */
	public void testIfAnswersButtonsContainsTheCorrextAnswer3() {
		
		gameLogic3.determineChoices();
		
		Log.e("","<<<"+	gameLogic3.getFirstPicture()
					  +	gameLogic3.getSecondPicture()
					  + gameLogic3.getThirdPicture());
		
		Log.e("","<<<"+	gameLogic3.getFirstButtonString());
		Log.e("","<<<"+	gameLogic3.getSecondButtonString());
		Log.e("","<<<"+	gameLogic3.getThirdButtonString());
		
		assertTrue(	gameLogic3.getFirstButtonString().equals(
							gameLogic3.getFirstPicture()
							+gameLogic3.getSecondPicture()
							+gameLogic3.getThirdPicture())
					||
					gameLogic3.getSecondButtonString().equals(
							gameLogic3.getFirstPicture()
							+gameLogic3.getSecondPicture()
							+gameLogic3.getThirdPicture()) 
					||
					gameLogic3.getThirdButtonString().equals(
							gameLogic3.getFirstPicture()
							+gameLogic3.getSecondPicture()
							+gameLogic3.getThirdPicture()) 
					);
		
		
	}

}
