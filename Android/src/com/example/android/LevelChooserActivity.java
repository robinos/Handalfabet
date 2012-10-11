package com.example.android;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

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
 * The LevelChooserActivity class.
 * 
 * @author  : Grupp02
 * @version : 2012-10-08, v0.5
 * @License : GPLv3
 * @Copyright : Copyrightę 2012, Grupp02
 *
 */
public class LevelChooserActivity extends Activity {

	//DifficultyLevel
	private int difficulty;
	public final static String DIFFLEVEL = "com.example.Android.DIFFICULTY";
	
	private Button firstLevelButton;
	private Button secondLevelButton;
	private Button thirdLevelButton;	
	
    private TextView userName;
	private TextView userStatus;
	private Bitmap img;
	private ImageView userImg;
	
	
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_level_chooser );
        
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            //getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        // User Image      
        userImg = (ImageView)findViewById(R.id.userpic);
        img = (Bitmap)( getIntent().getExtras().getParcelable("userImg"));
		userImg.setImageBitmap(img);
               
        
		userStatus = ( TextView )findViewById( R.id.textView1 );
		//Since a user must be logged in at this point, this status does not need
		//retrieved
		userStatus.setText( R.string.logged_in );
		//Displays the username
		userName = ( TextView ) findViewById( R.id.textView2 );
		userName.setText( getIntent().getStringExtra( "Name" ) );   
		
		
        firstLevelButton = ( Button )findViewById( R.id.firstLevelButton );  
        secondLevelButton = ( Button )findViewById( R.id.secondLevelButton ); 
        thirdLevelButton = ( Button )findViewById( R.id.thirdLevelButton );
        
        firstLevelButton.setOnClickListener( new View.OnClickListener() {
			public void onClick( View v ) {
				SoundPlayer.playButton(LevelChooserActivity.this);				
				difficulty = 1;
				chooseDifficulty(v);				
			}
		} );
        
        secondLevelButton.setOnClickListener( new View.OnClickListener() {
			public void onClick( View v ) {
				SoundPlayer.playButton(LevelChooserActivity.this);				
				difficulty = 2;
				chooseDifficulty( v );				
			}
		} ); 
        
        thirdLevelButton.setOnClickListener( new View.OnClickListener() {
			public void onClick( View v ) {
				SoundPlayer.playButton(LevelChooserActivity.this);				
				difficulty = 3;
				chooseDifficulty( v );				
			}
		} );         
    }    
    
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.activity_level_chooser, menu );
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch ( item.getItemId() ) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask( this );
                return true;
        }
        return super.onOptionsItemSelected( item );
    }
    
	/** Called when the user clicks a difficulty button*/
	public void chooseDifficulty( View v ) {
		Intent intent = new Intent("android.intent.action.GAME");
		intent.putExtra( DIFFLEVEL, difficulty );
		intent.putExtra("Name", userName.getText().toString());
		intent.putExtra("userImg", img );
		startActivity(intent);
		
   	 //kills current activity
   	 finish();			
	}     
}
