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

import com.example.android.UserActivity;

import android.test.ActivityInstrumentationTestCase2;


/**
 * The UserActivity class is a testclass
 * 
 * @author  : Grupp02
 * @version : 2012-10-02, v0.2
 * @License : GPLv3
 * @Copyright :Copyright© 2012, Grupp02
 *
 *	not yet finished more to come
 *
 */
public class UserActivityTest extends
		ActivityInstrumentationTestCase2<UserActivity> {

	UserActivity useract;

	public UserActivityTest(String name) {
		super("com.example.com",UserActivity.class);
		setName(name);
	}


	protected void setUp() throws Exception {
		super.setUp();
		useract = getActivity();
	}


	protected void tearDown() throws Exception {
		super.tearDown();
		getActivity().finish();
	}

	/**
	 * Tests if Activity is created
	 */
	public void testViewCreated() {
		assertNotNull(getActivity());
	}
}
