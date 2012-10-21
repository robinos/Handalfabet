package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
 * The MainActivity class.
 * 
 * @author : Grupp02
 * @version : 2012-10-21, v1.0
 * 
 */
public class MainActivity extends Activity {

    // Audio Focus helper
    private AudioFocusHelper focusHelper;

    TextView nameField;
    private TextView userStatus;
    private ImageView userImg;

    public String name = "";
    private String playerName;
    private Button startGameButton;
    private Button settingsButton;
    private Bitmap img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	// Make sure we're running on Honeycomb or higher to use ActionBar APIs
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    // getActionBar().setDisplayHomeAsUpEnabled( true );
	}

	if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
	    focusHelper = new AudioFocusHelper(this);
	} else {
	    focusHelper = null;
	}

	userImg = (ImageView) findViewById(R.id.userpic);
	userStatus = (TextView) findViewById(R.id.textView1);
	nameField = (TextView) findViewById(R.id.textView2);

	startGameButton = (Button) findViewById(R.id.startaSpel);
	settingsButton = (Button) findViewById(R.id.settings);

	startGameButton.setEnabled(false);
	settingsButton.setEnabled(false);
    }

    @Override
    /**
     * onResume is overriden in order to utterly abandon sound focus if
     * sound has been turned off, or resume sound if on.
     * 
     */
    public void onResume() {
	super.onResume();

	if (SoundPlayer.getSoundEnabled() == false) {
	    if (focusHelper != null) {
		focusHelper.abandonFocus();
	    }
	    SoundPlayer.stop();
	} else {
	    SoundPlayer.resume();
	}
    }

    @Override
    public void onPause() {
	super.onPause(); // Always call the superclass method first

	// Pause sound when paused
	if (SoundPlayer.getSoundEnabled()) {
	    SoundPlayer.pause();
	}
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
	// Save name, user status, and user picture
	savedInstanceState.putString("Name", playerName);
	savedInstanceState.putString("status", userStatus.getText().toString());
	savedInstanceState.putParcelable("picture", img);
	savedInstanceState.putBoolean("game", startGameButton.isEnabled());
	savedInstanceState.putBoolean("settings", settingsButton.isEnabled());

	// Always call the superclass so it can save the view hierarchy state
	super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
	// Always call the superclass so it can restore the view hierarchy
	super.onRestoreInstanceState(savedInstanceState);

	// Restore name, user status, and user picture
	playerName = savedInstanceState.getString("Name");
	name = playerName;
	nameField.setText(playerName);
	userStatus.setText(savedInstanceState.getString("status"));
	img = savedInstanceState.getParcelable("picture");
	userImg.setImageBitmap(img);
	startGameButton.setEnabled(savedInstanceState.getBoolean("game"));
	settingsButton.setEnabled(savedInstanceState.getBoolean("settings"));
    }

    /** Called when the user clicks the New Game button */
    public void newGame(View v) {
	// Starts the level chooser activity
	playButton();
	Intent intent = new Intent("android.intent.action.LEVELCHOOSERACTIVITY");
	intent.putExtra("Name", name);
	intent.putExtra("userImg", img);
	startActivity(intent);
    }

    /** Called when the user clicks the New Player button */
    public void newPlayer(View v) {
	playButton();
	Intent intent = new Intent(this, UserActivity.class);
	startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	if (resultCode == RESULT_OK && requestCode == 1) {
	    name = data.getStringExtra("PlayerName");
	    nameField.setText(name);

	    img = (Bitmap) data.getExtras().getParcelable("userImg");
	    userImg.setImageBitmap(img);

	    userStatus.setText(R.string.logged_in);
	    startGameButton.setEnabled(true);
	    settingsButton.setEnabled(true);
	}
	playerName = name;
    }

    /** Called when the user clicks the settings button (formerly level) */
    public void settings(View v) {
	playButton();
	Intent intent = new Intent("android.intent.action.GAMESETTINGSACTIVITY");
	intent.putExtra("Name", name);
	intent.putExtra("userImg", img);
	intent.putExtra("User", userStatus.getText());
	startActivity(intent);
    }

    /** Called when the user clicks the HighScore button */
    public void highScore(View v) {
	playButton();
	startActivity(new Intent(
		"android.intent.action.DISPLAYHIGHSCOREACTIVITY"));
    }

    /** Called when the user clicks the Instruktioner button */
    public void help(View v) {
	playButton();
	Intent intent = new Intent("android.intent.action.HELP");
	intent.putExtra("Name", name);
	intent.putExtra("userImg", img);
	intent.putExtra("User", userStatus.getText());
	startActivity(intent);
    }

    /**
     * The getAudioFocus method attempts to gain focus for playing audio. If
     * full access can't be gained, transitive access at a quiet volume is
     * attempted. If that can't be granted, false is returned.
     * 
     * @return : true if focus in some form is granted, otherwise false
     */
    private boolean getAudioFocus() {

	if (focusHelper != null) {
	    if (!focusHelper.requestFocus()) {
		if (focusHelper.requestQuietFocus()) {
		    return false;
		} else {
		    return true;
		}
	    } else {
		return true;
	    }
	}

	return false;
    }

    /**
     * playButton plays the button sound
     * 
     * If there is an AudioFocusHelper (api >= 8) use it, otherwise default to
     * SoundPlayer
     */
    public void playButton() {
	if (SoundPlayer.getSoundEnabled()) {
	    if (focusHelper != null) {
		if (getAudioFocus()) {
		    focusHelper.playButton();
		}
	    } else {
		SoundPlayer.playButton(this);
	    }
	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.activity_main, menu);
	return true;
    }

    /**
     * onStop is called when the activity is shut down, usually before being
     * destroyed. We need to stop any media players to properly free up memory.
     * The focus helper should lose focus anyway, but no reason not to tie up
     * loose ends.
     */
    @Override
    public void onStop() {
	// cancel any noises and abandon focus
	SoundPlayer.stop();

	if (focusHelper != null) {
	    focusHelper.abandonFocus();
	}

	// call the super method
	super.onStop();
    }
}
