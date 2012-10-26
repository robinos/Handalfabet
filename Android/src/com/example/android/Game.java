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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
 * The Game Activity houses all GUI for the main game.
 * 
 * @author : Grupp02
 * @version : 2012-10-21, v1.0
 */
public class Game extends Activity {

    // Audio Focus helper
    private AudioFocusHelper focusHelper;

    // GameLogic object
    GameLogic gameLogic;

    // Instance Variables
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageButton nextButton;
    private Button firstOptionButton;
    private Button secondOptionButton;
    private Button thirdOptionButton;
    private TextView totalPoint;
    private TextView roundPoint;
    private ProgressBar timerBar;
    private int difficulty;
    private int numLetters;

    private TextView userName;
    private TextView userStatus;

    private Bitmap img;
    private ImageView userImg;
    private int answerID = 0;
    
    private final int maxProgress = 100;
    private final int difficultyOne = 1;
    private final int oneLetter = 1;
    private final int twoLetters = 2;
    private final int threeLetters = 3;
    private final int firstButton = 1;
    private final int secondButton = 2;
    private final int thirdButton = 3;
    private final int zero = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.game);

	// Make sure we're running on Honeycomb or higher to use ActionBar APIs
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    // getActionBar().setDisplayHomeAsUpEnabled( true );
	}

	if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
	    focusHelper = new AudioFocusHelper(this);
	} else {
	    focusHelper = null;
	}

	// The text prompt view
	TextView questionView = (TextView) findViewById(R.id.question_view);

	// GUI variables
	timerBar = (ProgressBar) findViewById(R.id.timer_bar);

	image1 = (ImageView) findViewById(R.id.image_view1);
	image2 = (ImageView) findViewById(R.id.image_view2);
	image3 = (ImageView) findViewById(R.id.image_view3);
	nextButton = (ImageButton) findViewById(R.id.next_button);
	String pic_blank = "blank";

	// User Image
	userImg = (ImageView) findViewById(R.id.userpic);	
	userStatus = (TextView) findViewById(R.id.textView1);
	// Displays the username
	userName = (TextView) findViewById(R.id.textView2);
	
	// Initialize the layout
	setButtonsAndTextView();	
	
    // Check whether we're recreating a previously destroyed instance
    if (savedInstanceState != null) {	
    	
    	//basic settings
    	difficulty = savedInstanceState.getInt( "difficulty" );
    	numLetters = savedInstanceState.getInt( "letters" );
  
    	// The game logic object
    	gameLogic = new GameLogic(difficulty, numLetters, this);     	
    	
    	//score
    	int round = savedInstanceState.getInt( "roundScore" );
    	int total = savedInstanceState.getInt( "totalScore" );   	
  
    	gameLogic.setRoundScore(round);
    	roundPoint.setText(Integer.toString( round ));
    	gameLogic.setTotalScore(round);    	
    	totalPoint.setText(Integer.toString( total ));   	
    	
    	// Restore name, user status, and user picture
    	userName.setText(savedInstanceState.getString("Name"));
    	userStatus.setText(savedInstanceState.getString("status"));
    	img = savedInstanceState.getParcelable("picture");
    	userImg.setImageBitmap(img);    	   	
    	
    	//statistics
    	gameLogic.setTotalTime(savedInstanceState.getInt( "totalTime" ));	
    	gameLogic.setNumCorrect(savedInstanceState.getInt( "numCorrect" ));    	
    	
    	//Buttons and correct sign
    	String firstButtonString = savedInstanceState.getString( "firstButtonString" );
    	gameLogic.setFirstButtonString( firstButtonString );
    	firstOptionButton.setText(firstButtonString);
    	String secondButtonString = savedInstanceState.getString( "secondButtonString" );
    	gameLogic.setSecondButtonString( secondButtonString ); 
    	secondOptionButton.setText(secondButtonString);    	
    	String thirdButtonString = savedInstanceState.getString( "thirdButtonString" );
    	gameLogic.setThirdButtonString( thirdButtonString );  
    	thirdOptionButton.setText(thirdButtonString);    	
    	gameLogic.setCorrectChoice(savedInstanceState.getString( "correctSign" ));
    	
    	//Pictures
    	String firstPicture = savedInstanceState.getString( "firstPicture" );
    	gameLogic.setFirstPicture( firstPicture );
    	image1.setImageResource(picSetter(firstPicture));    	
    	String secondPicture = savedInstanceState.getString( "secondPicture" );
    	gameLogic.setSecondPicture( secondPicture );
    	image2.setImageResource(picSetter(secondPicture)); 
    	String thirdPicture = savedInstanceState.getString( "thirdPicture" );
    	gameLogic.setThirdPicture( thirdPicture );
    	image3.setImageResource(picSetter(thirdPicture));     	  	   	
    }
    else {
    	// game difficulty, default level 1
    	difficulty = getIntent().getIntExtra("Difficulty", difficultyOne);
    	numLetters = getIntent().getIntExtra("Letters", oneLetter);    	
    	
    	// User Image
    	img = (Bitmap) (getIntent().getExtras().getParcelable("userImg"));
    	userImg.setImageBitmap(img);	
    	userStatus.setText(R.string.logged_in);
    	// Displays the username
    	userName.setText(getIntent().getStringExtra("Name"));	    	
    	
    	// The game logic object
    	gameLogic = new GameLogic(difficulty, numLetters, this);
    	
    	// Changes the picture and button text
    	deployTextButtons();

    	// Start the ticking noise
    	playTicking();    	
    }
       
	// Sets up the next button
	nextRound();    
    
	// Depending on one letter, or several letter words, alter the
	// question view text and the button appearance
	if (numLetters > oneLetter) {
	    questionView.setText(R.string.word_view);
	    nextButton.setBackgroundResource(R.drawable.next_word);
	} else {
	    questionView.setText(R.string.letter_view);
	    nextButton.setBackgroundResource(R.drawable.next_letter);
	}

	// Have certain pictures blank depending on difficulty level
	if (numLetters == 2) {
	    image3.setImageResource(picSetter(pic_blank));
	} else if (numLetters == 1) {
	    image2.setImageResource(picSetter(pic_blank));
	    image3.setImageResource(picSetter(pic_blank));
	}
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

    gameLogic.restartTimer();
	
	// Sadly this is going to give FULL time again. Have to try to fix this
	// restarts the count down timer
	// gameLogic.getCountDownTimer().start();
    }

    @Override
    public void onPause() {
	super.onPause(); // Always call the superclass method first

	// Pause sound when paused
	if (SoundPlayer.getSoundEnabled()) {
	    SoundPlayer.pause();
	}

	// cancels the count down timer, this runs on startup though, so not
	// good
    // gameLogic.getCountDownTimer().cancel();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save name, user status, and user picture
		savedInstanceState.putString("Name", userName.getText().toString());
		savedInstanceState.putString("status", userStatus.getText().toString());
		savedInstanceState.putParcelable("picture", img);
		//basic settings
		savedInstanceState.putInt( "difficulty", difficulty );
		savedInstanceState.putInt( "letters", numLetters );
		//score
		savedInstanceState.putInt( "roundScore", gameLogic.getRoundScore() );
		savedInstanceState.putInt( "totalScore", gameLogic.getTotalScore() );
		//time
		savedInstanceState.putInt( "timeCount", gameLogic.getTimeCount() );
		savedInstanceState.putInt( "timeLimit", gameLogic.getTimeLimit() );
		//Buttons and correct sign
		savedInstanceState.putString( "firstButtonString", gameLogic.getFirstButtonString() );
		savedInstanceState.putString( "secondButtonString", gameLogic.getSecondButtonString() );
		savedInstanceState.putString( "thirdButtonString", gameLogic.getThirdButtonString() );	
		savedInstanceState.putString( "correctSign", gameLogic.getCorrectChoice() );	
		//Pictures
		savedInstanceState.putString( "firstPicture", gameLogic.getFirstPicture() );
		savedInstanceState.putString( "secondPicture", gameLogic.getSecondPicture() );
		savedInstanceState.putString( "thirdPicture", gameLogic.getThirdPicture() );
		//Answer ID
		savedInstanceState.putInt( "answer", answerID );	
		//statistics
		savedInstanceState.putInt( "totalTime", gameLogic.getTotalTime() );	
		savedInstanceState.putInt( "numCorrect", gameLogic.getNumCorrect() );	
		
		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Always call the superclass so it can restore the view hierarchy
		super.onRestoreInstanceState(savedInstanceState);
		
		//If the save is a valid one, restore the game
		if(getValidInstance()) {
			//time
			int timeCount = savedInstanceState.getInt( "timeCount" );
			gameLogic.setTimeCount(timeCount);
			int tickTime = 1000;			
			
			//Answer ID
			answerID = savedInstanceState.getInt( "answer" );			
			
			//If the timer was on it's way down and an answer wasn't given,
			//the timer should start where it left off
			if(timeCount < 10 && timeCount > 0 && answerID == 0) {				
			    int timeLimit = savedInstanceState.getInt( "timeLimit" );
			    gameLogic.setTimeLimit(timeLimit-(timeCount*tickTime));
			    gameLogic.restartTimer();
			    
			    // Start the ticking noise
			    //playTicking();				    
			}
			//If an answer was given, restore that state
			else if(answerID != 0){
				gameLogic.getCountDownTimer().cancel();				
				
				firstOptionButton.setEnabled(false);
				secondOptionButton.setEnabled(false);
				thirdOptionButton.setEnabled(false);
			
				switch (answerID) {
			
				case R.id.first_opt_button:
			
				    if (gameLogic.getCorrectButton() == firstButton) {
					firstOptionButton
						.setBackgroundColor(android.graphics.Color.GREEN);
				    } else {
					firstOptionButton
						.setBackgroundColor(android.graphics.Color.RED);
				    }
				    break;
			
				case R.id.second_opt_button:
			
				    if (gameLogic.getCorrectButton() == secondButton) {
					secondOptionButton
						.setBackgroundColor(android.graphics.Color.GREEN);
				    } else {
					secondOptionButton
						.setBackgroundColor(android.graphics.Color.RED);
				    }
				    break;
			
				case R.id.third_opt_button:
			
				    if (gameLogic.getCorrectButton() == thirdButton) {
					thirdOptionButton
						.setBackgroundColor(android.graphics.Color.GREEN);
				    } else {
					thirdOptionButton
						.setBackgroundColor(android.graphics.Color.RED);
				    }
				    break;
				}
			
				// Enable the next letter/word button
				nextButton.setEnabled(true);
			}
			//If time stopped make sure the countdown timer has stopped
			else if(timeCount <= 0){
				gameLogic.getCountDownTimer().cancel();
			}
			
			timerBar.setProgress(timeCount*tickTime);
		}
		//Otherwise create a new game round
		else { 
		    // game difficulty, default level 1
		    difficulty = getIntent().getIntExtra("Difficulty", difficultyOne);
		    numLetters = getIntent().getIntExtra("Letters", oneLetter);    	
		    	
		    gameLogic.setDiffLevel(difficulty);
		    gameLogic.setNumLetters(numLetters);
		    
		    // User Image
		    img = (Bitmap) (getIntent().getExtras().getParcelable("userImg"));
		    userImg.setImageBitmap(img);	
		    userStatus.setText(R.string.logged_in);
		    // Displays the username
		    userName.setText(getIntent().getStringExtra("Name"));	    	
		   	
		    // Changes the picture and button text
		    deployTextButtons();
		
		    // Start the ticking noise
		    playTicking();	    
		}	
    }

    /**
     * getValidInstance
     */
    private boolean getValidInstance() {
    	
    	if(firstOptionButton.getText().toString() != null &&
    	   firstOptionButton.getText().toString() != "" &&
    	   secondOptionButton.getText().toString() != null &&
    	   secondOptionButton.getText().toString() != "" &&
    	   thirdOptionButton.getText().toString() != null &&
    	   thirdOptionButton.getText().toString() != "" &&
    	   image1.getDrawable() != null) {
    	    
    		if(difficulty == 3) {
    	    	if(image3.getDrawable() != null &&
    	    	   image2.getDrawable() != null	&&
    	    	   firstOptionButton.getText().toString().length() == 3) {
    	    	    return true;
    	    	}
    		}
    		else if(difficulty == 2) {
    	    	if(image2.getDrawable() != null	&&
    	    	   firstOptionButton.getText().toString().length() == 2) {
    	    	    return true;
    	    	}
    		}
    		else { //difficulty 1
    	    	if(firstOptionButton.getText().toString().length() == 1) {
    	    	    return true;
    	    	}    			
    		}
    	}
    	
    	return false;
    }
    
    /**
     * getTimerBar
     * 
     * @return : returns the ProgressBar object timerBar
     */
    public ProgressBar getTimerBar() {
	return timerBar;
    }

    /**
     * Initiate buttons and textViews
     */
    private void setButtonsAndTextView() {

	firstOptionButton = (Button) findViewById(R.id.first_opt_button);
	secondOptionButton = (Button) findViewById(R.id.second_opt_button);
	thirdOptionButton = (Button) findViewById(R.id.third_opt_button);

	totalPoint = (TextView) findViewById(R.id.show_total_points);
	roundPoint = (TextView) findViewById(R.id.show_round_points);
    }

    /**
     * Displays the score on the screen
     */
    private void scoreCounter() {
	gameLogic.scoreCounter();
	totalPoint.setText(Integer.toString(gameLogic.getTotalScore()));
	roundPoint.setText(Integer.toString(gameLogic.getRoundScore()));
    }

    /**
     * Sets the green color on the button the player clicked for correct answer
     * and red for wrong answer
     * 
     * @param v
     *            Clicked answer button
     */
    public void markButtonsAfterClicked(View v) {

		// stop the clock ticking sound, and play the button sound
		SoundPlayer.stop();
		playButton();
	
		// cancels the count down timer
		gameLogic.getCountDownTimer().cancel();
	
		int button_id = v.getId();
	    answerID = button_id;
		
	    //Set the answer and activate the next button
        setAnswer(button_id);
    }

    /**
     * setAnswer sets the answer and activate the next button
     */
    private void setAnswer(int button_id) {
    	
		firstOptionButton.setEnabled(false);
		secondOptionButton.setEnabled(false);
		thirdOptionButton.setEnabled(false);
	
		switch (button_id) {
	
		case R.id.first_opt_button:
	
		    if (gameLogic.getCorrectButton() == firstButton) {
			firstOptionButton
				.setBackgroundColor(android.graphics.Color.GREEN);
			scoreCounter();
			playRightChoice();
		    } else {
			firstOptionButton
				.setBackgroundColor(android.graphics.Color.RED);
			playWrongChoice();
		    }
		    break;
	
		case R.id.second_opt_button:
	
		    if (gameLogic.getCorrectButton() == secondButton) {
			secondOptionButton
				.setBackgroundColor(android.graphics.Color.GREEN);
			scoreCounter();
			playRightChoice();
		    } else {
			secondOptionButton
				.setBackgroundColor(android.graphics.Color.RED);
			playWrongChoice();
		    }
		    break;
	
		case R.id.third_opt_button:
	
		    if (gameLogic.getCorrectButton() == thirdButton) {
			thirdOptionButton
				.setBackgroundColor(android.graphics.Color.GREEN);
			scoreCounter();
			playRightChoice();
		    } else {
			thirdOptionButton
				.setBackgroundColor(android.graphics.Color.RED);
			playWrongChoice();
		    }
		    break;
		}
	
		// Enable the next letter/word button
		nextButton.setEnabled(true);   	
    }
    
    /**
     * playRightChoice
     * 
     * Unlike playButton, this also plays a vibration, so it is called as long
     * as sound or vibrations are enabled.
     */
    public void playRightChoice() {
	if (SoundPlayer.getSoundEnabled() || SoundPlayer.getVibrationEnabled()) {
	    if (focusHelper != null) {
		if (getAudioFocus()) {
		    focusHelper.playRightChoice();
		}
	    } else {
		SoundPlayer.playRightChoice(this);
	    }
	}
    }

    /**
     * playWrongChoice
     * 
     * Unlike playButton, this also plays a vibration, so it is called as long
     * as sound or vibrations are enabled.
     */
    public void playWrongChoice() {
	if (SoundPlayer.getSoundEnabled() || SoundPlayer.getVibrationEnabled()) {
	    if (focusHelper != null) {
		if (getAudioFocus()) {
		    focusHelper.playWrongChoice();
		}
	    } else {
		SoundPlayer.playWrongChoice(this);
	    }
	}
    }

    /**
     * Changes the sign image when nextButton is clicked
     */
    private void nextRound() {

	// Disable the next letter/word button
	nextButton.setEnabled(false);

	nextButton.setOnClickListener(new OnClickListener() {
	    public void onClick(View arg0) {
	    //Reset the answer variable
	    answerID = 0;	    	
		// play the button sound
		playButton();

		// If all game rounds have completed, bring up the end screen
		if (gameLogic.countDownRounds()) {
		    // kills current activity
		    finish();

		    Intent endIntent = new Intent(
			    "android.intent.action.GAMEEND");
		    endIntent.putExtra("NumCorrect", gameLogic.getNumCorrect());
		    endIntent.putExtra("TotalScore", gameLogic.getTotalScore());
		    endIntent.putExtra("AverageTime",
			    gameLogic.getAverageTime());
		    endIntent.putExtra("Difficulty", difficulty);
		    endIntent.putExtra("Letters", numLetters);
		    endIntent.putExtra("name",
			    getIntent().getStringExtra("Name"));
		    endIntent.putExtra("userImg", img);
		    startActivity(endIntent);
		} else {
		    // start clock ticking
		    playTicking();

		    // Reset round points to 0
		    roundPoint.setText(Integer.toString(zero));

		    // Reset answer button backgrounds
		    firstOptionButton
			    .setBackgroundColor(android.graphics.Color.LTGRAY);
		    secondOptionButton
			    .setBackgroundColor(android.graphics.Color.LTGRAY);
		    thirdOptionButton
			    .setBackgroundColor(android.graphics.Color.LTGRAY);

		    // Enable answer buttons
		    firstOptionButton.setEnabled(true);
		    secondOptionButton.setEnabled(true);
		    thirdOptionButton.setEnabled(true);

		    // Get text for buttons and disable next button
		    deployTextButtons();
		    nextButton.setEnabled(false);

		    // Reset the timer
		    timerBar.setProgress(maxProgress);
		    gameLogic.resetTimeCount();
		    // (re)starts the count down timer
		    //gameLogic.getCountDownTimer().start();
		}
	    }
	});
    }

    /**
     * Sets random letters to the buttons and the correct letter for the shown
     * sign.
     */
    private void deployTextButtons() {
	// Determine sign picture and button text
	gameLogic.determineChoices();

	// Set the picture
	image1.setImageResource(picSetter(gameLogic.getFirstPicture()));

	if (numLetters >= twoLetters) {
	    image2.setImageResource(picSetter(gameLogic.getSecondPicture()));
	}
	if (numLetters == threeLetters) {
	    image3.setImageResource(picSetter(gameLogic.getThirdPicture()));
	}

	// Set the button text
	firstOptionButton.setText(gameLogic.getFirstButtonString());
	secondOptionButton.setText(gameLogic.getSecondButtonString());
	thirdOptionButton.setText(gameLogic.getThirdButtonString());
    }

    /**
     * Gets the id for the sign image letter/word
     * 
     * @param word
     * @return the id number
     */
    private int picSetter(String word) {
	int resource;

	if (word.equals(Character.toString('ä'))) {
	    resource = getResources().getIdentifier("ae", "drawable",
		    "com.example.android");
	} else if (word.equals(Character.toString('å'))) {
	    resource = getResources().getIdentifier("ao", "drawable",
		    "com.example.android");
	} else if (word.equals(Character.toString('ö'))) {
	    resource = getResources().getIdentifier("oe", "drawable",
		    "com.example.android");
	} else {
	    resource = getResources().getIdentifier(word, "drawable",
		    "com.example.android");
	}

	return resource;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.activity_game, menu);
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
     * playTicking plays the ticking sound
     * 
     * If there is an AudioFocusHelper (api >= 8) use it, otherwise default to
     * SoundPlayer
     */
    public void playTicking() {
	if (SoundPlayer.getSoundEnabled()) {
	    if (focusHelper != null) {
		if (getAudioFocus()) {
		    focusHelper.playTicking();
		}
	    } else {
		SoundPlayer.playTicking(this);
	    }
	}
    }

    /**
     * onStop is called when the activity is shut down, usually before being
     * destroyed. We need to stop the timer and media players to properly free
     * up memory. The focus helper should lose focus anyway, but no reason not
     * to tie up loose ends.
     */
    @Override
    public void onStop() {
	// cancels the count down timer
	gameLogic.getCountDownTimer().cancel();

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

		//This code cannot be in onStop in Game because
		//it also kills the applause noise as GameEnd begins		
	    // cancel any noises and abandon focus
	    SoundPlayer.stop();
		if (focusHelper != null) {
		    focusHelper.abandonFocus();
		}		
		
	    // continue backwards (kills current activity)
	    finish();

	    return true;
	}

	return super.onKeyDown(keyCode, event);
    }
}
