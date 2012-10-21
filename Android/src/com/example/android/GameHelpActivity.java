package com.example.android;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

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
 * The GameHelpActicity class handles the Game Help screen. Initial attempts
 * were made to read strings from file, and have a separate class, GameHelp.
 * However, it was deemed both faster (considering time constraints) and easier
 * to simply use xml and drawable references. These are easily altered resources
 * when adding new content. Basically GameHelpActivity acts as a simple reader
 * with picture and text, explaining in x pages about handalfabet and how to
 * play. The previous and next buttons allow for navigation.
 * 
 * @author : Grupp02
 * @version : 2012-10-21, v1.0
 */
public class GameHelpActivity extends Activity {

    // Audio Focus helper
    private AudioFocusHelper focusHelper;

    private Bitmap img;
    private ImageView userImg;
    private TextView userName;
    private TextView userStatus;

    private final int zero = 0;

    private int page = 0;
    private final int firstPage = 0;
    private final int secondPage = 1;
    private final int thirdPage = 2;
    private final int fourthPage = 3;
    private final int fifthPage = 4;
    private final int sixthPage = 5;
    private final int seventhPage = 6;
    private final int eighthPage = 7;
    private final int ninthPage = 8;
    private final int tenthPage = 9;
    private final int eleventhPage = 10;
    private final int twelvthPage = 11;
    private final int thirteenthPage = 12;
    private final int fourteenthPage = 13;
    private final int fifteenthPage = 14;
    private final int lastPage = 15;

    private Button previousButton;
    private Button nextButton;
    private ImageView displayPicture;
    private TextView displayText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_game_help);

	// Make sure we're running on Honeycomb or higher to use ActionBar APIs
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    // getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
	    focusHelper = new AudioFocusHelper(this);
	} else {
	    focusHelper = null;
	}

	// User Image
	userImg = (ImageView) findViewById(R.id.userpic);
	img = (Bitmap) (getIntent().getExtras().getParcelable("userImg"));
	userImg.setImageBitmap(img);

	userStatus = (TextView) findViewById(R.id.textView1);
	userStatus.setText(getIntent().getStringExtra("User"));
	// Displays the username
	userName = (TextView) findViewById(R.id.textView2);
	userName.setText(getIntent().getStringExtra("Name"));

	previousButton = (Button) findViewById(R.id.previous_page_button);
	nextButton = (Button) findViewById(R.id.next_page_button);
	displayPicture = (ImageView) findViewById(R.id.helpPictureView);
	displayText = (TextView) findViewById(R.id.helpTextView);

	if (page == firstPage) {
	    previousButton.setEnabled(false);
	    nextButton.setEnabled(true);
	    displayText.setText(R.string.first_help_page);
	    displayPicture
		    .setImageResource(R.drawable.new_player_button_screen);
	}

	if (page == lastPage) {
	    previousButton.setEnabled(true);
	    nextButton.setEnabled(false);
	}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.activity_game_help, menu);
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

    @Override
    /**
     * onResume is overridden in order to utterly abandon sound focus if
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
	savedInstanceState.putInt("page", page);

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
	page = savedInstanceState.getInt("page");

	if (page == firstPage) {
	    previousButton.setEnabled(false);
	} else {
	    previousButton.setEnabled(true);
	}

	if (page == lastPage) {
	    nextButton.setEnabled(false);
	} else {
	    nextButton.setEnabled(true);
	}
    }

    /** Called when the user clicks the Previous button */
    public void previousPage(View v) {
	playButton();

	switch (page) {
	case secondPage:
	    page = firstPage;
	    displayText.setText(R.string.first_help_page);
	    displayPicture
		    .setImageResource(R.drawable.new_player_button_screen);
	    break;
	case thirdPage:
	    page = secondPage;
	    displayText.setText(R.string.second_help_page);
	    displayPicture.setImageResource(R.drawable.login_player_screen);
	    break;
	case fourthPage:
	    page = thirdPage;
	    displayText.setText(R.string.third_help_page);
	    displayPicture.setImageResource(R.drawable.create_name_screen);
	    break;
	case fifthPage:
	    page = fourthPage;
	    displayText.setText(R.string.fourth_help_page);
	    displayPicture.setImageResource(R.drawable.choose_picture_screen);
	    break;
	case sixthPage:
	    page = fifthPage;
	    displayText.setText(R.string.fifth_help_page);
	    displayPicture
		    .setImageResource(R.drawable.create_new_player_screen);
	    break;
	case seventhPage:
	    page = sixthPage;
	    displayText.setText(R.string.sixth_help_page);
	    displayPicture
		    .setImageResource(R.drawable.login_player_ready_screen);
	    break;
	case eighthPage:
	    page = seventhPage;
	    displayText.setText(R.string.seventh_help_page);
	    displayPicture.setImageResource(R.drawable.new_game_button_screen);
	    break;
	case ninthPage:
	    page = eighthPage;
	    displayText.setText(R.string.eighth_help_page);
	    displayPicture.setImageResource(R.drawable.level_chooser_screen);
	    break;
	case tenthPage:
	    page = ninthPage;
	    displayText.setText(R.string.ninth_help_page);
	    displayPicture.setImageResource(R.drawable.game_screen);
	    break;
	case eleventhPage:
	    page = tenthPage;
	    displayText.setText(R.string.tenth_help_page);
	    displayPicture.setImageResource(R.drawable.game_right_screen);
	    break;
	case twelvthPage:
	    page = eleventhPage;
	    displayText.setText(R.string.eleventh_help_page);
	    displayPicture.setImageResource(R.drawable.game_wrong_screen);
	    break;
	case thirteenthPage:
	    page = twelvthPage;
	    displayText.setText(R.string.twelvth_help_page);
	    displayPicture.setImageResource(R.drawable.game_timeout_screen);
	    break;
	case fourteenthPage:
	    page = thirteenthPage;
	    displayText.setText(R.string.thirteenth_help_page);
	    displayPicture.setImageResource(R.drawable.three_letters_screen);
	    break;
	case fifteenthPage:
	    page = fourteenthPage;
	    displayText.setText(R.string.fourteenth_help_page);
	    displayPicture.setImageResource(R.drawable.game_end_screen);
	    break;
	case lastPage:
	    page = fifteenthPage;
	    displayText.setText(R.string.fifteenth_help_page);
	    displayPicture.setImageResource(R.drawable.high_score_screen);
	    break;
	}

	if (page == firstPage) {
	    previousButton.setEnabled(false);
	    nextButton.setEnabled(true);
	} else {
	    previousButton.setEnabled(true);
	}
    }

    /** Called when the user clicks the Next button */
    public void nextPage(View v) {
	playButton();

	switch (page) {

	case firstPage:
	    page = secondPage;
	    previousButton.setEnabled(true);
	    displayText.setText(R.string.second_help_page);
	    displayPicture.setImageResource(R.drawable.login_player_screen);
	    break;
	case secondPage:
	    page = thirdPage;
	    displayText.setText(R.string.third_help_page);
	    displayPicture.setImageResource(R.drawable.create_name_screen);
	    break;
	case thirdPage:
	    page = fourthPage;
	    displayText.setText(R.string.fourth_help_page);
	    displayPicture.setImageResource(R.drawable.choose_picture_screen);
	    break;
	case fourthPage:
	    page = fifthPage;
	    displayText.setText(R.string.fifth_help_page);
	    displayPicture
		    .setImageResource(R.drawable.create_new_player_screen);
	    break;
	case fifthPage:
	    page = sixthPage;
	    displayText.setText(R.string.sixth_help_page);
	    displayPicture
		    .setImageResource(R.drawable.login_player_ready_screen);
	    break;
	case sixthPage:
	    page = seventhPage;
	    displayText.setText(R.string.seventh_help_page);
	    displayPicture.setImageResource(R.drawable.new_game_button_screen);
	    break;
	case seventhPage:
	    page = eighthPage;
	    displayText.setText(R.string.eighth_help_page);
	    displayPicture.setImageResource(R.drawable.level_chooser_screen);
	    break;
	case eighthPage:
	    page = ninthPage;
	    displayText.setText(R.string.ninth_help_page);
	    displayPicture.setImageResource(R.drawable.game_screen);
	    break;
	case ninthPage:
	    page = tenthPage;
	    displayText.setText(R.string.tenth_help_page);
	    displayPicture.setImageResource(R.drawable.game_right_screen);
	    break;
	case tenthPage:
	    page = eleventhPage;
	    displayText.setText(R.string.eleventh_help_page);
	    displayPicture.setImageResource(R.drawable.game_wrong_screen);
	    break;
	case eleventhPage:
	    page = twelvthPage;
	    displayText.setText(R.string.twelvth_help_page);
	    displayPicture.setImageResource(R.drawable.game_timeout_screen);
	    break;
	case twelvthPage:
	    page = thirteenthPage;
	    displayText.setText(R.string.thirteenth_help_page);
	    displayPicture.setImageResource(R.drawable.three_letters_screen);
	    break;
	case thirteenthPage:
	    page = fourteenthPage;
	    displayText.setText(R.string.fourteenth_help_page);
	    displayPicture.setImageResource(R.drawable.game_end_screen);
	    break;
	case fourteenthPage:
	    page = fifteenthPage;
	    displayText.setText(R.string.fifteenth_help_page);
	    displayPicture.setImageResource(R.drawable.high_score_screen);
	    break;
	case fifteenthPage:
	    page = lastPage;
	    nextButton.setEnabled(false);
	    displayText.setText(R.string.sixteenth_help_page);
	    displayPicture.setImageResource(R.drawable.sound_settings_screen);
	    break;
	}
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
