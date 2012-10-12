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

import com.example.android.CreateNewPlayer;

import android.test.ActivityInstrumentationTestCase2;

/**
 * The CreateNewPlayerActivityTest class is a testclass
 * 
 * @author  : Grupp02
 * @version : 2012-10-02, v0.2
 * @License : GPLv3
 * @Copyright :Copyright© 2012, Grupp02
 *
 *	not yet finished
 */
public class CreateNewPlayerActivityTest extends 
					ActivityInstrumentationTestCase2<CreateNewPlayer> 
{

	CreateNewPlayer createnewplayer;
	
	public CreateNewPlayerActivityTest(String name) {
		super("com.example.com",CreateNewPlayer.class);
		setName(name);
		// TODO Auto-generated constructor stub
	}
	
	public void setUp() throws Exception {
		super.setUp();
		createnewplayer = getActivity();
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
		getActivity().finish();
	}
	
	/**
	 * Tests if Activity is created
	 */
	public void testViewCreated() {
		assertNotNull(createnewplayer);
	}
}
