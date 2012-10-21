package com.example.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
 * The SoundSettings class is used to help access to the sound database for
 * saving sound settings.
 * 
 * @author : Grupp02
 * @version : 2012-10-19, v1.0
 */
public class SoundSettings extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Sound";
    // User table name
    public static final String SOUND_TABLE = "Sound";

    // UTable Columns names
    private static final String colName = "Username";
    private static final String colVolume = "Volume";
    private static final String colSound = "Sound";
    private static final String colVibrations = "Vibrations";

    private final int zero = 0;
    private final int one = 1;

    static final String USER_VIEW = "UserView";

    private static final String DATABASE_CREATE = "CREATE TABLE " + SOUND_TABLE
	    + "(" + colName + " VARCHAR(36) PRIMARY KEY," + colVolume
	    + " INTEGER, " + colSound + " INTEGER, " + colVibrations
	    + " INTEGER)";

    private static final String DATABASE_CREATEe = "CREATE TABLE "
	    + SOUND_TABLE + "(" + colName + " VARCHAR(36) PRIMARY KEY,"
	    + colVolume + " INTEGER, " + colSound + " INTEGER, "
	    + colVibrations + " INTEGER)";

    public SoundSettings(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creates a table with four columns UserName, Volume, Sound, and Vibrations
    @Override
    public void onCreate(SQLiteDatabase db) {
	// TODO Auto-generated method stub
	db.execSQL(DATABASE_CREATE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// TODO Auto-generated method stub
	Log.w(SoundSettings.class.getName(), "Upgrading database from version "
		+ oldVersion + " to " + newVersion
		+ ", which will destroy all old data");
	// Drop older table if existed
	db.execSQL("DROP TABLE IF EXISTS " + SOUND_TABLE);
	onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new entry
    public void addEntry(User user) {
	SQLiteDatabase db = this.getWritableDatabase();
	String name;
	int volume = SoundPlayer.getCurrentVolume();
	int sound = one;
	int vibrations = one;

	if (user == null) {
	    name = "default";
	} else {
	    name = user.getName();
	}

	if (!SoundPlayer.getSoundEnabled()) {
	    sound = zero;
	}
	if (!SoundPlayer.getVibrationEnabled()) {
	    vibrations = zero;
	}

	ContentValues values = new ContentValues();
	values.put(colName, name); // User Name
	values.put(colVolume, volume); // User Volume
	values.put(colSound, sound); // User Sound on/off
	values.put(colVibrations, vibrations); // User Vibrations on/off
	// Inserting Row
	db.insert(SOUND_TABLE, null, values);
	db.close(); // Closing database connection
    }

    // Getting single contact
    public void getEntry(String userName) {
	SQLiteDatabase db = this.getReadableDatabase();

	Cursor cursor = db.query(SOUND_TABLE, new String[] { colName,
		colVolume, colSound, colVibrations }, colName + "=?",
		new String[] { String.valueOf(userName) }, null, null, null,
		null);
	if (cursor != null) {
	    cursor.moveToFirst();
	}

	SoundPlayer.setCurrentVolume((cursor.getInt(cursor
		.getColumnIndex(colVolume))));
	int sound = cursor.getInt(cursor.getColumnIndex(colSound));
	int vibrations = cursor.getInt(cursor.getColumnIndex(colVibrations));

	if (sound == one) {
	    SoundPlayer.setSoundEnabled(true);
	} else {
	    SoundPlayer.setSoundEnabled(false);
	}

	if (vibrations == one) {
	    SoundPlayer.setVibrationEnabled(true);
	} else {
	    SoundPlayer.setVibrationEnabled(false);
	}

	db.close();
    }

    // Updating single entry
    public int updateEntry(User user) {

	SQLiteDatabase db = this.getWritableDatabase();
	String name;
	int volume = SoundPlayer.getCurrentVolume();
	int sound = one;
	int vibrations = one;

	if (user == null) {
	    name = "default";
	} else {
	    name = user.getName();
	}

	if (!SoundPlayer.getSoundEnabled()) {
	    sound = zero;
	}
	if (!SoundPlayer.getVibrationEnabled()) {
	    vibrations = zero;
	}

	ContentValues values = new ContentValues();
	values.put(colVolume, volume); // User Volume
	values.put(colSound, sound); // User Sound on/off
	values.put(colVibrations, vibrations); // User Vibrations on/off
	values.put(colName, user.getName());

	// updating row
	return db.update(SOUND_TABLE, values, colName + " =?",
		new String[] { String.valueOf(name) });
    }

    // Deleting single contact
    public void deleteContact(User user) {
	SQLiteDatabase db = this.getWritableDatabase();
	db.delete(SOUND_TABLE, colName + " = ?",
		new String[] { String.valueOf(user.getName()) });
	db.close();
    }

    // Getting contacts Count
    public int getUserCount() {
	String countQuery = "SELECT  * FROM " + SOUND_TABLE;
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor = db.rawQuery(countQuery, null);
	cursor.close();

	// return count
	return cursor.getCount();
    }

}
