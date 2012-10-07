package com.example.android;





import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
public class MainActivity extends Activity {
	
	private boolean firstClick = true;
	private int difficulty = 1;
	//DifficultyLevel
	public final static String DIFFLEVEL = "com.example.Android.DIFFICULTY";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
    }
    
//    /** Called when the user clicks the Login button */
//	public void login(View view){			
//		Intent intent = new Intent(this, User.class);
//	    EditText userName = (EditText) findViewById(R.id.username);
//	    String message = userName.getText().toString();
//	    intent.putExtra(EXTRA_MESSAGE, message);
//	    startActivity(intent);
//	}
	
	/** Called when the user clicks the New Game button */
	public void newGame (View v){
		startActivity(new Intent("android.intent.action.GAME").putExtra(DIFFLEVEL, difficulty));	
	}
	
	/** Called when the user clicks the New Player button */
	public void newPlayer(View v){
		startActivity(new Intent("android.intent.action.USER"));		
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
