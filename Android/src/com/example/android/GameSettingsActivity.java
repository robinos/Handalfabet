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
 * The GameSettingsAcitivty class.
 * 
 * @author  : Grupp02
 * @version : 2012-10-14, v0.5
 * @License : GPLv3
 * @Copyright : Copyright© 2012, Grupp02
 *
 */
public class GameSettingsActivity extends Activity {

	//Audio Focus helper
	private AudioFocusHelper focusHelper;	
	
	private Bitmap img;
	private ImageView userImg;	
    private TextView userName;
	private TextView userStatus;	
	
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game_settings );
        
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
        	 //getActionBar().setDisplayHomeAsUpEnabled( true );
        }     
          
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)
        	focusHelper = new AudioFocusHelper(this);
        else focusHelper = null;
        
        // User Image      
        userImg = (ImageView)findViewById(R.id.userpic);
        img = (Bitmap)( getIntent().getExtras().getParcelable("userImg"));
		userImg.setImageBitmap(img);        
        
		userStatus = ( TextView )findViewById( R.id.textView1 );
		userStatus.setText( getIntent().getStringExtra( "User" ) );
		//Displays the username
		userName = ( TextView ) findViewById( R.id.textView2 );
		userName.setText(getIntent().getStringExtra( "Name" ) );         
        
        final Button soundSettingsButton = ( Button ) findViewById( R.id.sound_settings_button ); 
        final Button profileSettingsButton = ( Button ) findViewById( R.id.profile_settings_button );         

        soundSettingsButton.setOnClickListener( new View.OnClickListener() {
			public void onClick( View v ) {
				SoundPlayer.playButton(GameSettingsActivity.this);								
				startActivity( new Intent( "android.intent.action.SOUNDSETTINGSACTIVITY" )
				    .putExtra( "Name", userName.getText().toString() )
				    .putExtra( "userImg", img )
				    .putExtra( "User", userStatus.getText() ) );				
			}
		});        
        
        profileSettingsButton.setOnClickListener( new View.OnClickListener() {
			public void onClick( View v ) {
				SoundPlayer.playButton(GameSettingsActivity.this);				
				startActivity( new Intent( "android.intent.action.PROFILESETTINGSACTIVITY" )
				    .putExtra( "Name", userName.getText().toString() )
				    .putExtra( "userImg", img )
				    .putExtra( "User", userStatus.getText() ) );				
			}
		});         
    }

	 @Override
	 /**
	  * onResume is overriden in order to utterly abandon sound focus if
	  * sound has been turned off, or resume sound if on.
	  * 
	  */
	 public void onResume() {
	 	 super.onResume();
	 	 
	     if(SoundPlayer.getSoundEnabled() == false) {
	    	 if(focusHelper != null) {
	             focusHelper.abandonFocus();
	    	 }
	    	 SoundPlayer.stop();
	     }
	     else SoundPlayer.resume();
	}	
	
	 @Override
	 public void onPause() {
	     super.onPause();  // Always call the superclass method first

	     // Pause sound when paused
        if(SoundPlayer.getSoundEnabled()) SoundPlayer.pause();
	 }    
    
		@Override
		public void onSaveInstanceState(Bundle savedInstanceState) {
		    // Save name, user status, and user picture
		    savedInstanceState.putString("Name", userName.getText().toString());
		    savedInstanceState.putString("status", userStatus.getText().toString());	     
		    savedInstanceState.putParcelable("picture", img);
		    
		    // Always call the superclass so it can save the view hierarchy state
		    super.onSaveInstanceState(savedInstanceState);
		} 
		
		@Override
		public void onRestoreInstanceState(Bundle savedInstanceState) {
		    // Always call the superclass so it can restore the view hierarchy
		    super.onRestoreInstanceState(savedInstanceState);
		   
		    // Restore name, user status, and user picture
		    userName.setText(savedInstanceState.getString( "Name" ));
		    userStatus.setText(savedInstanceState.getString( "status" ));
		    img = savedInstanceState.getParcelable("picture");
		    userImg.setImageBitmap(img);
		}	 
	 
	/**
	 * The getAudioFocus method attempts to gain focus for playing audio.
	 * If full access can't be gained, transitive access at a quiet volume
	 * is attempted.  If that can't be granted, false is returned.
	 * 
	 * @return : true if focus in some form is granted, otherwise false
	 */
	private boolean getAudioFocus() {
		
		if(focusHelper != null) {
			if(!focusHelper.requestFocus()) {
				if(!focusHelper.requestQuietFocus()) return false;
				else return true;
			}
			else return true;
		}
		
		return false;
	}    
    
    /**
     * playButton plays the button sound
     * 
     * If there is an AudioFocusHelper (api >= 8) use it,
     * otherwise default to SoundPlayer
     */	
    public void playButton() {
	  	if(SoundPlayer.getSoundEnabled()) {
		   	if(focusHelper != null) {
		   	    if(getAudioFocus()) focusHelper.playButton();
		   	}
		   	else SoundPlayer.playButton(this);
	  	}
   }
	
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.activity_game_settings, menu );
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

}
