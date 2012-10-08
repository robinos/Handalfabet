package com.example.android;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "User";
	 // User table name
	public static final String USER_TABLE = "Users";
	
	// UTable Columns names
	private static final String colName = "Username";
	private static final String colPassword = "Password";
	private static final String colHighScore = "HighScore";
	
	static final String USER_VIEW = "UserVeiw";
	
	private static final String DATABASE_CREATE = "CREATE TABLE "+ USER_TABLE +   
								"(" + colName + " VARCHAR(36) PRIMARY KEY,"+ colPassword + 
														" TEXT,"+ colHighScore + " INTEGER )";
	
	public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	//Skappar en tabel med tre kolumer UserName, UserPassword och HighScore
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//Skappar en tabel med tre kolumer UserName, Password och HighScore
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
 
        ContentValues values = new ContentValues();
        values.put(colName, user.getName()); // User Name
        values.put(colPassword, user.getPassword()); // User Password
        values.put(colHighScore, user.getHighScore()); // User Highscore
        
        // Inserting Row
        db.insert(USER_TABLE, null, values);
        db.close(); // Closing database connection
    }
    
    // Getting single contact
    public User getUser(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
     
        Cursor cursor = db.query(USER_TABLE, new String[] { colName,
                colPassword, colHighScore }, colName + "=?",
                new String[] { String.valueOf(userName) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        User user = new User(cursor.getString(0),cursor.getString(1),cursor.getInt(2));
        // return user
        return user;
    }
    
//  Getting All Users 
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + USER_TABLE;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //User user = new User();
         	   User user = new User();
                user.setName(cursor.getString(0));
                //          user.setPassword(cursor.getString(1));
                user.setHighScore(cursor.getInt(2));
                // Adding user to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
     
//         return user list
        return userList;
    }
    
//     Getting All Users name
    public List<String> getAllUsersName() {
       List<String> userList = new ArrayList<String>();
       // Select All Query
       String selectQuery = "SELECT * FROM " + USER_TABLE;
    
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(selectQuery, null);
    
       // looping through all rows and adding to list
       if (cursor.moveToFirst()) {
           do {
               //User user = new User();
        	   String user = cursor.getString(0);
               // Adding user to list
               userList.add(user);
           } while (cursor.moveToNext());
       }
    
       // return user list
       return userList;
   }
    
 // Updating single user
    public int updateUserHighScore(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(colHighScore, user.getHighScore());
        values.put(colName, user.getName());
//        values.put(colPassword, user.getPassword());
 
        // updating row
        return db.update(USER_TABLE, values, colName + " =?",  new String[]{ String.valueOf(user.getName()) });
//        return db.update(USER_TABLE, values, colName + " = ?",
//                new String[] { String.valueOf(user.getName()) });
    }
 
    // Deleting single contact
    public void deleteContact(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE, colName + " = ?",
                new String[] { String.valueOf(user.getName()) });
        db.close();
    }
 
    // Getting contacts Count
    public int getUserCount() {
        String countQuery = "SELECT  * FROM " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }    
    
}
