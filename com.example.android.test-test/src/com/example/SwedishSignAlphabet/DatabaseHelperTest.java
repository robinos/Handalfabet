package com.example.SwedishSignAlphabet;


import android.content.Context;
import android.graphics.Bitmap;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.SwedishSignAlphabet.DatabaseHelper;
import com.example.SwedishSignAlphabet.User;




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
 * The DatabaseHelperTest class is a testclass to test the DatabaseHelper .
 * 
 * @author  : Grupp02
 * @version : 2012-10-02, v0.2
 *
 */



public class DatabaseHelperTest extends AndroidTestCase {
	
	User user;
	DatabaseHelper db;
	Context context = null;

	public DatabaseHelperTest(){
		super();
	}
	
	 protected void setUp() throws Exception {
	        super.setUp();
	        db = new DatabaseHelper(getContext());
	    }
	 
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * Test if Database Exists.
	 */
	public void testIfDatabaseExists(){
		assertNotNull(db);
	}
	
	
	/**
	 * Test add new player to Database.
	 */
	public void testAddNewUser() throws Exception{
		db.addUser(user);
		assertEquals(user.getName(), db.getUser(user.getName()));   
	}
	
	
	/**
	 * Test player highscore.
	 */
	public void testHighscore() throws Exception{
		User usr = db.getUser(user.getName());
		int highScore = usr.getHighScore();
		assertEquals(user.getHighScore(), highScore);   
	}
	
	/**
	 * Test change highscore .
	 */
	public void testChangeHighscore() throws Exception{
		user.setHighScore(10);
		db.updateUserHighScore(user);
		
		User usr = db.getUser(user.getName());
		int highScore = usr.getHighScore();
		assertEquals(user.getHighScore(), highScore);
	}
	
	/**
	 * Test add 100 players in database.
	 */
	public void testAdd100PlayersInDatabase() throws Exception{
		int player;
		Bitmap img = null;
		for(player = 0; player < 100; player++){
			User nUser = new User(Integer.toString(player), 0, img, 1, 1);
			db.addUser(nUser);
		}
		assertEquals(0, db.getUserCount());
	}
	
	/**
	 * Test delete 100 players in database.
	 */
	public void testDelete100PlayersFromDatabase() throws Exception{
		testAdd100PlayersInDatabase();
		int player;
		for(player = 0; player < 100; player++){
			User nUser = new User();
			nUser.setName(Integer.toString(player));
			db.deleteUser(nUser);
		}
		assertEquals(0, db.getUserCount());
		Log.e(">>>>>>>>>>>>>>>", Integer.toString(db.getUserCount()));
	}
}

