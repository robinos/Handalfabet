package com.example.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
 * The SoundSettingActivity class is the activity where sound settings and
 * vibration settings may be altered.
 * 
 * @author : Grupp02
 * @version : 2012-10-19, v1.0
 * 
 */
public class SoundSettingsActivity extends Activity implements
	SeekBar.OnSeekBarChangeListener {

    // Audio Focus helper
    private AudioFocusHelper focusHelper;

    // SoundSettings database
    private SoundSettings soundData;
    private DatabaseHelper db;

    private Bitmap img;
    private ImageView userImg;
    private TextView userName;
    private TextView userStatus;
    private SeekBar volumeBar;
    private ToggleButton soundButton;
    private ToggleButton vibrationButton;

    private int soundVolume = 100;
    private final static int MAX_VOLUME = 100;

    private final int zero = 0;
    private final int one = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_sound_settings);

	// Make sure we're running on Honeycomb or higher to use ActionBar APIs
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    // getActionBar().setDisplayHomeAsUpEnabled( true );
	}

	if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
	    focusHelper = new AudioFocusHelper(this);
	} else {
	    focusHelper = null;
	}

	soundData = new SoundSettings(this);
	db = new DatabaseHelper(this);

	// User Image
	userImg = (ImageView) findViewById(R.id.userpic);
	img = (Bitmap) (getIntent().getExtras().getParcelable("userImg"));
	userImg.setImageBitmap(img);

	userStatus = (TextView) findViewById(R.id.textView1);
	userStatus.setText(getIntent().getStringExtra("User"));
	// Displays the username
	userName = (TextView) findViewById(R.id.textView2);
	userName.setText(getIntent().getStringExtra("Name"));

	// Activate the volume bar
	volumeBar = (SeekBar) findViewById(R.id.volume_bar);
	volumeBar.setOnSeekBarChangeListener(this);

	soundButton = (ToggleButton) findViewById(R.id.sound_toggle_button);
	vibrationButton = (ToggleButton) findViewById(R.id.vibration_toggle_button);

	if (savedInstanceState == null) {
	    volumeBar.setProgress(soundVolume);
	    soundButton.setChecked(true);
	    vibrationButton.setChecked(true);
	}

	getSettings();

	soundButton.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		float volume = (float) (one - (Math.log(MAX_VOLUME
			- soundVolume) / Math.log(MAX_VOLUME)));
		playButton();
		if (soundButton.isChecked()) {
		    SoundPlayer.setSoundEnabled(true);
		    SoundPlayer.setVolume(volume, volume);
		} else {
		    SoundPlayer.setSoundEnabled(false);
		}
	    }
	});

	vibrationButton.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		playButton();
		if (vibrationButton.isChecked()) {
		    SoundPlayer.setVibrationEnabled(true);
		} else {
		    SoundPlayer.setVibrationEnabled(false);
		}
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
	savedInstanceState.putString("Name", userName.getText().toString());
	savedInstanceState.putString("status", userStatus.getText().toString());
	savedInstanceState.putParcelable("picture", img);

	// Save the user's current game state
	savedInstanceState.putInt("volume", soundVolume);
	savedInstanceState.putBoolean("sound", soundButton.isChecked());
	savedInstanceState.putBoolean("vibration", vibrationButton.isChecked());

	updateSettings();

	// Always call the superclass so it can save the view hierarchy state
	super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
	// Always call the superclass so it can restore the view hierarchy
	super.onRestoreInstanceState(savedInstanceState);

	// Restore name, user status, and user picture
	userName.setText(savedInstanceState.getString("Name"));
	userStatus.setText(savedInstanceState.getString("status"));
	img = savedInstanceState.getParcelable("picture");
	userImg.setImageBitmap(img);

	soundVolume = savedInstanceState.getInt("volume");
	soundButton.setChecked(savedInstanceState.getBoolean("sound"));
	vibrationButton.setChecked(savedInstanceState.getBoolean("vibration"));

	volumeBar.setProgress(soundVolume);
    }

    /**
     * Update the database with user sound settings
     */
    private void updateSettings() {
	String name = userName.getText().toString();
	User user;

	if (name != null) {
	    if (name != "") {
		user = db.getUser(userName.getText().toString());
	    } else {
		user = null;
	    }
	} else {
	    user = null;
	}

	// Save the current sound settings is a user is logged in
	if (user != null) {
	    soundData.updateEntry(user); // defaults to "default" user if null
	}
    }

    /**
     * Retrieve user sound settings
     */
    private void getSettings() {
	String name = userName.getText().toString();

	// Restores data to SoundPlayer
	if (name != null) {
	    if (name != "") {
		soundData.getEntry(name);
	    }
	}

	// Restore visual appearance
	if (!SoundPlayer.getSoundEnabled()) {
	    soundButton.setChecked(false);
	} else {
	    soundButton.setChecked(true);
	}

	if (!SoundPlayer.getVibrationEnabled()) {
	    vibrationButton.setChecked(false);
	} else {
	    vibrationButton.setChecked(true);
	}

	soundVolume = SoundPlayer.getCurrentVolume();
	volumeBar.setProgress(soundVolume);

	// Restore volume setting
	float volume = (float) (one - (Math.log(MAX_VOLUME - soundVolume) / Math
		.log(MAX_VOLUME)));
	SoundPlayer.setVolume(volume, volume);
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
		if (!focusHelper.requestQuietFocus()) {
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
	getMenuInflater().inflate(R.menu.activity_sound_settings, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case android.R.id.home:
	    NavUtils.navigateUpFromSameTask(this);
	    return true;
	}
	return super.onOptionsItemSelected(item);
    }

    /**
     * Change the visual progress of the bar.
     */
    public void onProgressChanged(SeekBar seekBar, int progress,
	    boolean fromTouch) {
	// On change
	soundVolume = progress;
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
	// Tracking on
    }

    /**
     * Handle changing volume.
     */
    public void onStopTrackingTouch(SeekBar seekBar) {
	// Tracking off
	soundVolume = seekBar.getProgress();
	SoundPlayer.setCurrentVolume(soundVolume);

	Context context = getApplicationContext();
	CharSequence text = Float.toString(soundVolume);
	int duration = Toast.LENGTH_SHORT;

	Toast toast = Toast.makeText(context, text, duration);
	toast.show();

	float volume = (float) (one - (Math.log(MAX_VOLUME - soundVolume) / Math
		.log(MAX_VOLUME)));
	SoundPlayer.setVolume(volume, volume);
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

	// Update user settings
	updateSettings();

	// call the super method
	super.onStop();
    }

    /**
     * onKeyDown overrides onKeyDown and allows code to be executed when the
     * back button is pushed in the simulator / on the mobile phone Since
     * pushing "back" won't necessarily call the destroy method as far as I
     * understand it.
     * 
     * @param keyCode
     *            : code of the key pressed
     * @param event
     *            : the event for the key pressed
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == zero) {

	    // continue backwards (kills current activity calling onDestroy)
	    finish();

	    return true;
	}

	return super.onKeyDown(keyCode, event);
    }
}
