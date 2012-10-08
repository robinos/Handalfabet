
/**
 *   This file is part of Handalfabetet.
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
 * The MainActivity class.
 * 
 * @author  : Grupp02
 * @version : 2012-10-08, v0.4
 * @License : GPLv3
 * @Copyright : Copyright© 2012, Grupp02
 *
 */
package com.example.android;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private int difficulty = 3;
	//DifficultyLevel
	public final static String DIFFLEVEL = "com.example.Android.DIFFICULTY";
	
	TextView nameField;
	private String feriz = "peci";
	private TextView userStatus;
	
	public String name;
	private String playerName;
	private Button startGameButton;
	private Button newPlayerButton;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);   
        
     // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
        	name = savedInstanceState.getString(playerName);
        	nameField.setText(name);
        	userStatus.setText(R.string.inloggad);
        }
        
        
        userStatus = (TextView)findViewById(R.id.textView1);
        nameField = (TextView) findViewById( R.id.textView2 );  
        startGameButton = (Button)findViewById(R.id.startaSpel);
        
        newPlayerButton = (Button)findViewById(R.id.bytspelare);
        startGameButton.setEnabled(false); 
    } 
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    // Save the user's current game state
	    savedInstanceState.putString(playerName, feriz);
	    
	    // Always call the superclass so it can save the view hierarchy state
	    super.onSaveInstanceState(savedInstanceState);
	} 
	
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	    // Always call the superclass so it can restore the view hierarchy
	    super.onRestoreInstanceState(savedInstanceState);
	   
	    // Restore state members from saved instance
	    name = savedInstanceState.getString(playerName);
	}
//	@Override
//	public void onResume() {
//		super.onResume();
//		nameField.setText(playerName);
//	}
//		userStatus.setText(R.string.inloggad);
//		nameField.setText(playerName);
//		Log.e( ">>>>>>>onP", playerName);
//		
//		nameField.setText(playerName);
//		nameField.setText(name);
//		userStatus.setText(R.string.inloggad);
//		Log.e( ">>>>>>>", "OnResum har används" );
		
//		userStatus.setText(R.string.inloggad);
//		//TODO
//		String str2 =  nameField.getText().toString();
//		String str = userStatus.getText().toString();
//		
//		Log.e( ">>>>>>>", str2); 
//		Log.e( ">>>>>>>", str);
//	} 

//	protected void onPause(){
//		playerName = nameField.getText().toString();
//		Log.e( ">>>>>>>onP", playerName);
//        super.onPause();
//        playerName = name;
//        nameField.setText(playerName);
//        Log.e( ">>>>>>>", name);
        
//	}
 
	
	/** Called when the user clicks the New Game button */
	public void newGame (View v){
		startActivity(new Intent("android.intent.action.GAME").putExtra(DIFFLEVEL, difficulty)
				.putExtra("Name", name));	
	}
	
	/** Called when the user clicks the New Player button */
	public void newPlayer(View v){
		Intent intent = new Intent(this, UserActivity.class);
	    startActivityForResult(intent, 1);		
	}
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);
//		 userStatus = (TextView)findViewById(R.id.textView1);
		 if(resultCode==RESULT_OK && requestCode==1){
			 name = data.getStringExtra("PlayerName");
			 nameField.setText(name);
		 }
		 userStatus.setText(R.string.inloggad);
		 
		 startGameButton.setEnabled(true);
	     playerName = name;
	 }
	
	 /** Called when the user clicks the Level button */
	public void level(View v){
		startActivity(new Intent("android.intent.action.LEVELCHOOSERACTIVITY"));
	}													
	
	/** Called when the user clicks the HighScore button */	
	public void highScore(View v){
		startActivity(new Intent("android.intent.action.DISPLAYHIGHSCOREACTIVITY")); 
	}
	
	 /** Called when the user clicks the Instruktioner button */
	public void help(View v){
		startActivity(new Intent("android.intent.action.HELP"));
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
