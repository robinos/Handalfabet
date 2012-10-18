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
 * The UserTest class is a testclass to test the User clas .
 * 
 * @author  : Grupp02
 * @version : 2012-10-02, v0.5
 * @License : GPLv3
 * @Copyright :Copyright© 2012, Grupp02
 *
 */


package com.example.android.test;

import android.test.AndroidTestCase;
import com.example.android.User;

/**
 * The UserTest class is a testclass to test the class User.
 * 
 * @author  : Grupp02
 * @version : 2012-10-19, v1.0
 *
 */
public class UserTest extends AndroidTestCase {

	private User user;
	
	public UserTest() {
		super();
	}

	protected void setUp() throws Exception {
		
		user = new User();
		
		super.setUp();
	}


	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * Test the the getter and the setter methods.
	 */
	public void testGetName() {
		user.setName("Baby99");
		String testUser = "Baby99";
		assertEquals(testUser, user.getName());
	}
	
	/**
	 * Test the swedish letters in a setname.
	 */
	public void testSwedishLetters() {
		user.setName("Åsa Ämö");
		assertEquals("Åsa Ämö", user.getName());
	}
	
	/**
	 * Test if the name can accept nonletters.
	 */
	public void testNonLettersSetName() {
		user.setName("LOL?*%&");
		assertEquals("LOL?*%&", user.getName());
		
	}
}
