package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;

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
	 private ProgressBar timerBar;	 
	 
	 @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.game );     
        
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            getActionBar().setDisplayHomeAsUpEnabled( true );
        }         
        
        //game difficulty, default level 1
        int difficulty = getIntent().getIntExtra( MainActivity.DIFFLEVEL, 1 ); 
        //The game logic object
        gameLogic = new GameLogic( difficulty, this );        
         
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
	        
	        totalPoint = ( TextView ) findViewById( R.id.show_total_point );
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
		image.setImageResource( picSetter( gameLogic.getPicture() ) );
		
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
	 
}
