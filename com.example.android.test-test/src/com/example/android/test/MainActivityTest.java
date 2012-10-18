package com.example.android.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.example.android.MainActivity;

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
 * The MainActivityTest is a testclass.
 * It's now mostly a template for more future test to come.
 *  
 * @author  : Grupp02
 * @version : 2012-10-19, v1.0
 */
public class MainActivityTest extends 
						ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mainAct;
	private Button startGameButton;
	
	public MainActivityTest(String name) {
		super("com.example.com",MainActivity.class);
		setName(name);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		mainAct = getActivity();
		startGameButton = (Button) mainAct.findViewById(
							com.example.android.R.id.new_game_button);
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
		getActivity().finish();
	}
	/**
	 * Tests if MainActivity is created
	 */
	public void testViewCreated() {
		assertNotNull(getActivity());
	}

}
