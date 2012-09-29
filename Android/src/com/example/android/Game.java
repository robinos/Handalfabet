package com.example.android;

import android.app.ActionBar;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Game class
 * 
 * @author  : Grupp02
 * @version : 2012-09-28, v0.3 
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
	 private ImageView image; 
	 private ImageButton nextButton;
	 private Button firstOptionButton;
	 private Button secondOptionButton;
	 private Button thirdOptionButton;
	 private TextView totalPoint;
	 private TextView roundPoint;
	 //private Random rng = new Random();
	 private ProgressBar timerBar;
     private CountDownTimer countDownTimer;	 
     private int timeCount = 10;
     //timeLimit needs to add extra time at the beginning and end for the bar
	 private int timeLimit = 12000; //12000 ms = 12s (needed instead of 10s)
	 private int tickTime = 1000;  //1000 ms = 1s	 
	 private int averageTime = 0;
	 private int numCorrect = 0;
	 
	 private int totalScore = 0;	 
	 
	 @Override
    public void onCreate (Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.game );     
        
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            getActionBar().setDisplayHomeAsUpEnabled( true );
        }         
        
        //default level 1
        int difficulty = getIntent().getIntExtra( MainActivity.DIFFLEVEL, 1 ); 
        //The game logic object
        gameLogic = new GameLogic( difficulty );        
         
        //GUI variables
        timerBar = ( ProgressBar ) findViewById (R.id.timer_bar );
		image = ( ImageView ) findViewById( R.id.image_view );
		nextButton = ( ImageButton ) findViewById( R.id.next_letter_button );        
        
		//Initialize the layout
        setButtonsAndTextView();                      
        
        //Resets for a new round
        nextRound();
        //Changes the picture and button text
        deployTextButtons(); 
        //Initializes and starts the count down timer
        startTimer();    
    }
	 
	 /**
	  * Initiate buttons and textViews
	  */
	 private void setButtonsAndTextView() {
		 
	        firstOptionButton = ( Button ) findViewById( R.id.first_opt_button );
	        secondOptionButton = ( Button ) findViewById( R.id.second_opt_button );
	        thirdOptionButton = ( Button ) findViewById( R.id.third_opt_button );        
	        
	        totalPoint = ( TextView ) findViewById( R.id.show_total_point );
	        roundPoint = ( TextView ) findViewById( R.id.show_round_points );
	 }
	 
	 /**
	  * Counts the score and displays it on the screen
	  */
	 private void scoreCounter() {
		 
		 //timeCount is the number of seconds left on the counter
		 //when a correct answer was given.  +1 is because timeCount
		 //is actually always 1 less than it should be do to display
		 //issues.
		 int roundScore = 1 + timeCount;
		 totalScore += roundScore;
		 averageTime += timeCount;
		 numCorrect++;
		 totalPoint.setText( Integer.toString( totalScore ) );
		 roundPoint.setText( Integer.toString( roundScore ) );		 
	 }
	 
	 /**
	  * Sets the green color on the button the player clicked 
	  * for correct answer and red for wrong answer
	  * 
	  * @param v Clicked answer button
	  */
	 public void markButtonsAfterClicked( View v ) {	
		 
		 //cancels the count down timer
		 countDownTimer.cancel();		 
		 
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
		
	    //Reset round points to 0
		roundPoint.setText( Integer.toString( 0 ) );
		 
		//Disable the next letter/word button
		nextButton.setEnabled( false );
		
		nextButton.setOnClickListener( new OnClickListener() {
			public void onClick( View arg0 ) {
				firstOptionButton.setBackgroundColor( android.graphics.Color.LTGRAY );

				secondOptionButton.setBackgroundColor( android.graphics.Color.LTGRAY );

				thirdOptionButton.setBackgroundColor( android.graphics.Color.LTGRAY );
				
				firstOptionButton.setEnabled( true );
				secondOptionButton.setEnabled( true );
				thirdOptionButton.setEnabled( true );

				deployTextButtons();
				nextButton.setEnabled( false );
				
				timerBar.setProgress( 0 );
				timeCount = 10;
				//(re)starts the count down timer				
				countDownTimer.start();
				
				if( gameLogic.countDownRounds() ) {
					averageTime /= 10;
				    Intent endIntent = new Intent( "android.intent.action.GAMEEND" );
				    endIntent.putExtra( NUMCORRECT, numCorrect );
				    endIntent.putExtra( TOTALSCORE, totalScore );
				    endIntent.putExtra( AVERAGETIME, averageTime );				    
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
		
		image.setImageResource( picSetter( gameLogic.getPicture() ) );
		
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
		int resource = getResources().getIdentifier( word, "drawable", "com.example.android" );
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

	 /**
	  * The startTimer method starts the countdown timer for making a
	  * choice in the game.
	  * The variable timeCount is even used to determine bonus points
	  * for quick answers in the scoreCounter() method.
	  */
	 public void startTimer() {
	     countDownTimer = new CountDownTimer( timeLimit, tickTime ) {
	    	 private int factor = 10;
	    	 
	    	//a long is required by onTick, timeLeft is not used
	         public void onTick( long timeLeft ) 
	         {
	        	 //The progress bar goes from 100 to 0 while timeCount
	        	 //is 10 to 0, so *factor for display
	             timerBar.setProgress( timeCount*factor );
	             timeCount--;
	         }
	        	 
	         public void onFinish()
	         {
	             //on finish bonus points are -1, because timeCount is
	        	 //always 1 point lower than it 'should be' due to display
	        	 //issues
	        	 timeCount = -1;
	         }
	     }.start();		 
	 }	 
	 
}
