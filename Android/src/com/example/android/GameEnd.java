package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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
 * The GameEnd class is the display screen once the game has ended.
 * 
 * @author : Grupp02
 * @version : 2012-10-21, v1.0
 * 
 */
public class GameEnd extends Activity {

    // Audio Focus helper
    private AudioFocusHelper focusHelper;

    private DatabaseHelper db;
    private int difficulty;
    private int numLetters;

    private Bitmap img;
    private ImageView userImg;
    private TextView userStatus;
    private TextView userName;

    private int numCorrect;
    private int totalScore;
    private int averageTime;
    private TextView highView;

    private final int zero = 0;
    private final int difficultyOne = 1;
    private final int oneLetter = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_game_end);

	// Make sure we're running on Honeycomb or higher to use ActionBar APIs
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    // getActionBar().setDisplayHomeAsUpEnabled( true );
	}

	if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
	    focusHelper = new AudioFocusHelper(this);
	} else {
	    focusHelper = null;
	}

	difficulty = getIntent().getIntExtra("Difficulty", difficultyOne);
	numLetters = getIntent().getIntExtra("Letters", oneLetter);

	db = new DatabaseHelper(this);

	final ImageButton newGameButton = (ImageButton) findViewById(R.id.new_game_button);
	final ImageButton highScoreButton = (ImageButton) findViewById(R.id.high_scores_button);
	final ImageButton mainMenuButton = (ImageButton) findViewById(R.id.main_menu_button);

	highView = (TextView) findViewById(R.id.high_view);
	userStatus = (TextView) findViewById(R.id.textView1);
	userName = (TextView) findViewById(R.id.textView2);

	numCorrect = getIntent().getIntExtra("NumCorrect", zero);
	totalScore = getIntent().getIntExtra("TotalScore", zero);
	averageTime = getIntent().getIntExtra("AverageTime", zero);

	// User Image
	userImg = (ImageView) findViewById(R.id.userpic);
	img = (Bitmap) (getIntent().getExtras().getParcelable("userImg"));
	userImg.setImageBitmap(img);

	// Displays the userName
	userStatus.setText(R.string.logged_in);
	userName.setText(getIntent().getStringExtra("name"));

	User user = db.getUser(userName.getText().toString());

	// Update HighScore
	if (user.getHighScore() < totalScore) {
	    user.setHighScore(totalScore);
	    user.setMaxDifficulty(difficulty);
	    user.setMaxLetters(numLetters);
	    db.updateUserHighScore(user);

	    // Display congratulations to user
	    highView.setText(R.string.high_view);

	    if (focusHelper != null) {
		if (getAudioFocus()) {
		    focusHelper.playApplause();
		}
	    } else {
		SoundPlayer.playApplause(this);
	    }
	} else {
	    // Display no new high score to user
	    highView.setText(R.string.low_view);
	}

	/**
	 * The New Game buttons starts a new game with previous settings
	 */
	newGameButton.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		playButton();
		startActivity(new Intent("android.intent.action.GAME")
			.putExtra("Difficulty", difficulty)
			.putExtra("Letters", numLetters)
			.putExtra("userImg", img)
			.putExtra("Name", userName.getText().toString()));

		// kills current activity
		finish();
	    }
	});

	/**
	 * The high score button displays the high scores
	 */
	highScoreButton.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		playButton();
		startActivity(new Intent(
			"android.intent.action.DISPLAYHIGHSCOREACTIVITY"));
	    }
	});

	/**
	 * The menu menu button returns to the main menu
	 */
	mainMenuButton.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		playButton();
		// kills current activity
		finish();
	    }
	});

	TextView totalPoints = (TextView) findViewById(R.id.show_total_point);
	totalPoints.setText("  " + Integer.toString(totalScore));

	TextView correctView = (TextView) findViewById(R.id.show_answers);
	correctView.setText(Integer.toString(numCorrect) + "  ");

	TextView averageView = (TextView) findViewById(R.id.show_average);
	averageView.setText("  " + Integer.toString(averageTime));
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

    /**
     * Saving in case of interruption or layout change
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
	// Save name, user status, and user picture
	savedInstanceState.putString("Name", userName.getText().toString());
	savedInstanceState.putString("status", userStatus.getText().toString());
	savedInstanceState.putParcelable("picture", img);
	savedInstanceState.putInt("difficulty", difficulty);
	savedInstanceState.putInt("letters", numLetters);
	savedInstanceState.putInt("NumScore", numCorrect);
	savedInstanceState.putInt("TotalScore", totalScore);
	savedInstanceState.putInt("AverageTime", averageTime);
	savedInstanceState.putString("High", highView.getText().toString());

	// Always call the superclass so it can save the view hierarchy state
	super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Restoring after interruption or layout change
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
	// Always call the superclass so it can restore the view hierarchy
	super.onRestoreInstanceState(savedInstanceState);

	// Restore name, user status, and user picture
	userName.setText(savedInstanceState.getString("Name"));
	userStatus.setText(savedInstanceState.getString("status"));
	img = savedInstanceState.getParcelable("picture");
	userImg.setImageBitmap(img);
	difficulty = savedInstanceState.getInt("difficulty");
	numLetters = savedInstanceState.getInt("letters");
	numCorrect = savedInstanceState.getInt("NumScore");
	totalScore = savedInstanceState.getInt("TotalScore");
	averageTime = savedInstanceState.getInt("AverageTime");
	highView.setText(savedInstanceState.getString("High"));
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
	getMenuInflater().inflate(R.menu.activity_game_end, menu);
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
     * /** onStop is called when the activity is shut down, usually before being
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
