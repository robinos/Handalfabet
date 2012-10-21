package com.example.android;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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
 * The DatabaseHelper class is used to interact with the User database.
 * 
 * @author : Grupp02
 * @version : 2012-10-21, v1.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "User";
    // User table name
    public static final String USER_TABLE = "Users";

    // UTable Columns names
    private static final String colImage = "image";
    private static final String colName = "Username";
    private static final String colPassword = "Password";
    private static final String colHighScore = "HighScore";
    private static final String colMaxDifficulty = "Difficult";
    private static final String colMaxLetters = "Letters";

    static final String USER_VIEW = "UserView";

    private static final String DATABASE_CREATE = "CREATE TABLE " + USER_TABLE
	    + "(" + colName + " VARCHAR(36) PRIMARY KEY," + colPassword
	    + " TEXT," + colHighScore + " INTEGER, " + colImage + " blob,"
	    + colMaxDifficulty + " INTEGER, " + colMaxLetters + " INTEGER)";

    private final int oneHundred = 100;
    private final int zero = 0;
    private final int two = 2;

    public DatabaseHelper(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Skappar en tabel med sex kolumer UserName, UserPassword,
    // UserHighScore, UserImage, MaxDifficulty och MaxLetters
    @Override
    public void onCreate(SQLiteDatabase db) {
	// TODO Auto-generated method stub
	// Skappar en tabel med tre kolumer UserName, Password och HighScore
	db.execSQL(DATABASE_CREATE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// TODO Auto-generated method stub
	Log.w(DatabaseHelper.class.getName(),
		"Upgrading database from version " + oldVersion + " to "
			+ newVersion + ", which will destroy all old data");
	// Drop older table if existed
	db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
	onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new User
    public void addUser(User user) {
	SQLiteDatabase db = this.getWritableDatabase();

	ByteArrayOutputStream out = new ByteArrayOutputStream();
	user.getUserImg().compress(Bitmap.CompressFormat.PNG, oneHundred, out);

	ContentValues values = new ContentValues();
	values.put(colName, user.getName()); // User Name
	values.put(colPassword, user.getPassword()); // User Password
	values.put(colHighScore, user.getHighScore()); // User Highscore
	values.put(colImage, out.toByteArray()); // User Image
	values.put(colMaxDifficulty, user.getMaxDifficulty()); // User Image
	values.put(colMaxLetters, user.getMaxLetters()); // User Image
	// Inserting Row
	db.insert(USER_TABLE, null, values);
	db.close(); // Closing database connection
    }

    // Getting single user
    public User getUser(String userName) {
	SQLiteDatabase db = this.getReadableDatabase();

	Cursor cursor = db.query(USER_TABLE, new String[] { colName,
		colPassword, colHighScore, colImage, colMaxDifficulty,
		colMaxLetters }, colName + "=?",
		new String[] { String.valueOf(userName) }, null, null, null,
		null);
	if (cursor != null) {
	    cursor.moveToFirst();
	}
	// retriving user image from SQlite
	byte[] blob = cursor.getBlob(cursor.getColumnIndex(colImage));
	Bitmap bmp = BitmapFactory.decodeByteArray(blob, zero, blob.length);
	User user = new User(cursor.getString(zero), cursor.getInt(two), bmp,
		cursor.getInt(cursor.getColumnIndex(colMaxDifficulty)),
		cursor.getInt(cursor.getColumnIndex(colMaxLetters)));

	db.close();
	// return user
	return user;
    }

    // Getting All Users
    public List<User> getAllUsers() {
	List<User> userList = new ArrayList<User>();
	// Select All Query
	// String selectQuery = "SELECT * FROM " + USER_TABLE;

	SQLiteDatabase db = this.getWritableDatabase();
	// Cursor cursor = db.rawQuery(selectQuery, null);

	// Sort list
	Cursor cursor = db
		.query(USER_TABLE,
			new String[] { colName, colPassword, colHighScore,
				colImage, colMaxDifficulty, colMaxLetters },
			null, null, null, null, colHighScore + " DESC");

	// looping through all rows and adding to list
	if (cursor.moveToFirst()) {
	    do {
		User user = new User();
		user.setName(cursor.getString(zero));
		// user.setPassword(cursor.getString(1));
		user.setHighScore(cursor.getInt(two));
		// retriving user image from SQlite
		byte[] blob = cursor.getBlob(cursor.getColumnIndex(colImage));
		Bitmap bmp = BitmapFactory.decodeByteArray(blob, zero,
			blob.length);
		user.setUserImg(bmp);
		user.setMaxDifficulty(cursor.getInt(cursor
			.getColumnIndex(colMaxDifficulty)));
		user.setMaxLetters(cursor.getInt(cursor
			.getColumnIndex(colMaxLetters)));
		// Adding user to list
		userList.add(user);
	    } while (cursor.moveToNext());
	}

	db.close();
	// return user list
	return userList;
    }

    // Getting All Users
    public List<User> getAllUsersName() {
	List<User> userList = new ArrayList<User>();
	// Select All Query
	// String selectQuery = "SELECT * FROM " + USER_TABLE;

	SQLiteDatabase db = this.getWritableDatabase();
	// Cursor cursor = db.rawQuery(selectQuery, null);

	// Sort list
	Cursor cursor = db.query(USER_TABLE, new String[] { colName,
		colPassword, colHighScore, colImage, colMaxDifficulty,
		colMaxLetters }, null, null, null, null, null, null);

	// looping through all rows and adding to list
	if (cursor.moveToFirst()) {
	    do {
		User user = new User();
		user.setName(cursor.getString(zero));
		// user.setPassword(cursor.getString(1));
		user.setHighScore(cursor.getInt(two));
		// retriving user image from SQlite
		byte[] blob = cursor.getBlob(cursor.getColumnIndex(colImage));
		Bitmap bmp = BitmapFactory.decodeByteArray(blob, zero,
			blob.length);
		user.setUserImg(bmp);
		user.setMaxDifficulty(cursor.getInt(cursor
			.getColumnIndex(colMaxDifficulty)));
		user.setMaxLetters(cursor.getInt(cursor
			.getColumnIndex(colMaxLetters)));
		// Adding user to list
		userList.add(user);
	    } while (cursor.moveToNext());
	}

	db.close();
	// return user list
	return userList;
    }

    // Updating single user
    public int updateUserHighScore(User user) {
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();
	values.put(colHighScore, user.getHighScore());
	values.put(colMaxDifficulty, user.getMaxDifficulty());
	values.put(colMaxLetters, user.getMaxLetters());
	values.put(colName, user.getName());
	// values.put(colPassword, user.getPassword());

	// updating row
	return db.update(USER_TABLE, values, colName + " =?",
		new String[] { String.valueOf(user.getName()) });
	// return db.update(USER_TABLE, values, colName + " = ?",
	// new String[] { String.valueOf(user.getName()) });
    }

    // Deleting single user
    public void deleteUser(User user) {
	SQLiteDatabase db = this.getWritableDatabase();
	db.delete(USER_TABLE, colName + " = ?",
		new String[] { String.valueOf(user.getName()) });
	db.close();
    }

    // Getting user Count
    public int getUserCount() {
	String countQuery = "SELECT  * FROM " + USER_TABLE;
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor = db.rawQuery(countQuery, null);
	cursor.close();

	// return count
	return cursor.getCount();
    }

}
