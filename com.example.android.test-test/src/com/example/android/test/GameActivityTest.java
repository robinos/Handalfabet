
package com.example.android.test;

import android.test.ActivityInstrumentationTestCase2;

import com.example.android.Game;

/**
 * 	 Copyright© 2012, Grupp02
 * 
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
 * The GameActivityTest class is a testclass.
 * It's now only a template for future test to come.
 * 
 * @author  : Grupp02
 * @version : 2012-10-19, v1.0
 */
public class GameActivityTest extends 
				ActivityInstrumentationTestCase2<Game> {

	private Game game;
	
	public GameActivityTest() {		//String name
		super("com.example.com",Game.class);
		//setName(name);
	}

	
	protected void setUp() throws Exception {
		super.setUp();
		//game = getActivity();
	}

	
	protected void tearDown() throws Exception {
		super.tearDown();
		//getActivity().finish();
	}
	
	/**
	 * Not really a test.
	 */
	public void testViewCreated() {
		assertTrue(true);
	}

}
