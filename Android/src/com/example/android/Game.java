package com.example.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;

/**
<<<<<<< HEAD
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
 * The Game class
=======
 * The Game class 
>>>>>>> HighScore
 * 
 * @author  : Grupp02
 * @version : 2012-10-08, v0.4
 * @License : GPLv3
 * @Copyright :Copyright© 2012, Grupp02  
 */
public class Game extends Activity {	
	
	 //Number right, total score, and average time for passing on to
	 //game end
	 public final static String NUMCORRECT = "com.example.Android.NUMCORRECT";	
	 public final static String TOTALSCORE = "com.example.Android.TOTALSCORE";	
	 public final static String AVERAGETIME = "com.example.Android.AVERAGETIME";
	
	 //GameLogic object
	 GameLogic gameLogic;
	
	 //Instance Variables
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
	 
	 private TextView userName;
	 private TextView userStatus;

	  
	
	@Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.game );     
        
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
        	 //getActionBar().setDisplayHomeAsUpEnabled( true );
        }     
        
        
        
        //game difficulty, default level 1
        difficulty = getIntent().getIntExtra( MainActivity.DIFFLEVEL, 1 ); 
        //difficulty = 2;
        
        //The game logic object
        gameLogic = new GameLogic( difficulty, this );        
         
        //GUI variables
        timerBar = ( ProgressBar ) findViewById (R.id.timer_bar );

		image1 = ( ImageView ) findViewById( R.id.image_view1 );
		image2 = ( ImageView ) findViewById( R.id.image_view2 );
		image3 = ( ImageView ) findViewById( R.id.image_view3 );		
		nextButton = ( ImageButton ) findViewById( R.id.next_button );        		
		String pic_blank = "blank";
		
		//Have certain pictures blank depending on difficulty level
		if( difficulty == 2 ) image3.setImageResource( picSetter( pic_blank ) ); 
		else if( difficulty == 1 ) {
		    image2.setImageResource( picSetter( pic_blank ) );
		    image3.setImageResource( picSetter( pic_blank ) );
		}
       
        
		userStatus = (TextView)findViewById(R.id.textView1);
		userStatus.setText(R.string.inloggad);
		//Displays the username
		userName = (TextView) findViewById(R.id.textView2);
		userName.setText(getIntent().getStringExtra("Name"));
		
		//Initialize the layout
        setButtonsAndTextView();                    
        
        //Resets for a new round
        nextRound();
        //Changes the picture and button text
        deployTextButtons();    
    }
	 
	 /**
	  * getTimerBAr
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
		 
	        firstOptionButton = ( Button ) findViewById( R.id.first_opt_button );
	        secondOptionButton = ( Button ) findViewById( R.id.second_opt_button );
	        thirdOptionButton = ( Button ) findViewById( R.id.third_opt_button );        
	        
	        totalPoint = ( TextView ) findViewById( R.id.show_total_points );
	        roundPoint = ( TextView ) findViewById( R.id.show_round_points );
	 }
	 
	 /**
	  * Displays the score on the screen
	  */
	 private void scoreCounter() {
		 gameLogic.scoreCounter();
		 totalPoint.setText( Integer.toString( gameLogic.getTotalScore() ) );
		 roundPoint.setText( Integer.toString( gameLogic.getRoundScore() ) );		 
	 }	 
	 
	 /**
	  * Sets the green color on the button the player clicked 
	  * for correct answer and red for wrong answer
	  * 
	  * @param v Clicked answer button
	  */
	 public void markButtonsAfterClicked( View v ) {	
		 
		 //cancels the count down timer
		 gameLogic.getCountDownTimer().cancel();		 
		 
		 int button_id = v.getId();
		 
		 firstOptionButton.setEnabled( false );
		 secondOptionButton.setEnabled( false );
		 thirdOptionButton.setEnabled( false );
		 
		 switch( button_id ) {
		 
		 	case R.id.first_opt_button: 
		 		
		 		if( gameLogic.getCorrectButton() == 1 ) {
					firstOptionButton.setBackgroundColor( android.graphics.Color.GREEN );
					scoreCounter();
		 		}
				else
					firstOptionButton.setBackgroundColor( android.graphics.Color.RED );
		 		break;
		 	
		 	case R.id.second_opt_button:
		 		
		 		if( gameLogic.getCorrectButton() == 2 ) {
					secondOptionButton.setBackgroundColor( android.graphics.Color.GREEN );
					scoreCounter();
 				}
				else
					secondOptionButton.setBackgroundColor( android.graphics.Color.RED );
		 		break;
		 		
		 	case R.id.third_opt_button:
		 		
		 		if( gameLogic.getCorrectButton() == 3 ) {
					thirdOptionButton.setBackgroundColor( android.graphics.Color.GREEN );
					scoreCounter();
 				}
				else
					thirdOptionButton.setBackgroundColor( android.graphics.Color.RED );
 				break;
		 }
		 
		 //Enable the next letter/word button
		 nextButton.setEnabled( true );
	 }
	
	/**
	 * Changes the sign image when nextButton is clicked
	 */
	private void nextRound() {		
		 
		//Disable the next letter/word button
		nextButton.setEnabled( false );
		
		nextButton.setOnClickListener( new OnClickListener() {		
			public void onClick( View arg0 ) {
			    //Reset round points to 0
				roundPoint.setText( Integer.toString( 0 ) );					
				
				//Reset answer button backgrounds
				firstOptionButton.setBackgroundColor( android.graphics.Color.LTGRAY );
				secondOptionButton.setBackgroundColor( android.graphics.Color.LTGRAY );
				thirdOptionButton.setBackgroundColor( android.graphics.Color.LTGRAY );
				
				//Enable answer buttons
				firstOptionButton.setEnabled( true );
				secondOptionButton.setEnabled( true );
				thirdOptionButton.setEnabled( true );

				//Get text for buttons and disable next button
				deployTextButtons();
				nextButton.setEnabled( false );
				
				//Reset the timer
				timerBar.setProgress( 0 );
				gameLogic.resetTimeCount();
				//(re)starts the count down timer				
				gameLogic.getCountDownTimer().start();
				
				//If all game rounds have completed, bring up the end screen
				if( gameLogic.countDownRounds() ) {
				    Intent endIntent = new Intent( "android.intent.action.GAMEEND" );
				    endIntent.putExtra( NUMCORRECT, gameLogic.getNumCorrect() );
				    endIntent.putExtra( TOTALSCORE, gameLogic.getTotalScore() );
				    endIntent.putExtra( AVERAGETIME, gameLogic.getAverageTime() );	
				    endIntent.putExtra("name", getIntent().getStringExtra("Name"));
				    startActivity( endIntent );		
				}				
			} 
		} ); 
	}
	
	/**
	 * Sets random letters to the buttons and
	 * the correct letter for the shown sign.
	 */
	private void deployTextButtons() {
		//Determine sign picture and button text
		gameLogic.determineChoices();		
		
		//Set the picture
		image1.setImageResource( picSetter( gameLogic.getFirstPicture() ) );		
		
		if( difficulty >= 2 ) image2.setImageResource( picSetter( gameLogic.getSecondPicture() ) );
		if( difficulty == 3 ) image3.setImageResource( picSetter( gameLogic.getThirdPicture() ) );
		
		//Set the button text
		firstOptionButton.setText( gameLogic.getFirstButtonString() );
		secondOptionButton.setText( gameLogic.getSecondButtonString() );
		thirdOptionButton.setText( gameLogic.getThirdButtonString() );
	}
	
	/**
	 * Gets the id for the sign image letter/word
	 * 
	 * @param word
	 * @return the id number
	 */ 
	private int picSetter( String word ) {		
		int resource;
		
		if( word.equals( Character.toString( 'ä' ) ) )
			resource = getResources().getIdentifier( "ae", "drawable", "com.example.android" );
	    else if( word.equals( Character.toString( 'å' ) ) ) 
		    resource = getResources().getIdentifier( "ao", "drawable", "com.example.android" );
		else if( word.equals( Character.toString( 'ö' ) ) )
		    resource = getResources().getIdentifier( "oe", "drawable", "com.example.android" );			
		else resource = getResources().getIdentifier( word, "drawable", "com.example.android" );
		
		return resource;		
	}
	
	 @Override
	 public boolean onCreateOptionsMenu( Menu menu ) {
	     getMenuInflater().inflate( R.menu.activity_game, menu );
	     return true;
	 }	 
	    
	 //@Override
	 public boolean onOptionsItemSelected( MenuItem item ) {
	     switch ( item.getItemId() ) {
	          case android.R.id.home:
	              NavUtils.navigateUpFromSameTask( this );
	              return true;
	     }
	     return super.onOptionsItemSelected( item );
	 }		 
	 
}
