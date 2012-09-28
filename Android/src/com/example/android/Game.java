package com.example.android;

import android.app.Activity;
import android.content.Intent;
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
	
	 private ImageView image; 
	 private ImageButton nextButton;
	 private Button firstOptionButton;
	 private Button secondOptionButton;
	 private Button thirdOptionButton;
	 private TextView totalPoint;
	 private TextView roundPoint;
	 private Random rng = new Random();
	 private ProgressBar timerBar;
     private CountDownTimer countDownTimer;	 
     private int timeCount = 10;
     //timeLimit needs to add extra time at the beginning and end for the bar
	 private int timeLimit = 12000; //12000 ms = 12s (needed instead of 10s)
	 private int tickTime = 1000;  //1000 ms = 1s	 
	 
	 private int score = 0;
	 private String correctSign;
	 private int playRoundCounter = 10; //Antal spelrundor man kan kšra.
	 
	 private String str;
	 private ArrayList<Integer> answerForButtons = new ArrayList<Integer>();
	 private ArrayList<String> usedSignList = new ArrayList<String>();
	 
	 
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getActionBar().setDisplayHomeAsUpEnabled(true);     
        timerBar = (ProgressBar) findViewById(R.id.timer_bar);
        
        str = randomLetter(randomNumber());
        setButtonsAndTextView();
        
        // Ändrar bild varje gång man klickar på next knappen
        switchPic();
        deployTextButtons(); 
        //Initialises and starts the countdown timer
        startTimer();
    }
	 
	 
	 /**
	  * Initiate buttons and textViews
	  */
	 private void setButtonsAndTextView() {
		 
	        firstOptionButton = (Button) findViewById(R.id.first_opt_button);
	        secondOptionButton = (Button) findViewById(R.id.second_opt_button);
	        thirdOptionButton = (Button) findViewById(R.id.third_opt_button);        
	        
	        totalPoint = (TextView) findViewById(R.id.show_total_point);
	        roundPoint = (TextView) findViewById(R.id.show_round_points);
	 }
	 
	 /**
	  * Counts the score and display them on the screen
	  */
	 private void scoreCounter() {
		 //timeCount is the number of seconds left on the counter
		 //when a correct answer was given.  +1 is because timeCount
		 //is actually always 1 less than it should be do to display
		 //issues.
		 int roundScore = 1 + timeCount;
		 score += roundScore;
		 totalPoint.setText(Integer.toString(score));
		 roundPoint.setText(Integer.toString(roundScore));		 
	 }
	 
	 /**
	  * Counts the how many game rounds are left
	  * 
	  * @return true if the game rounds has reach 10 rounds
	  */
	 private boolean countDownRounds () {
		 playRoundCounter--;
		 if(playRoundCounter == 0)
			 return true;
		 else
			 return false;
	 }
	 
	 /**
	  * Sets the green color on the button the player clicked 
	  * for correct answer and red for wrong answer
	  * 
	  * @param v Clicked answerbutton
	  */
	 public void markButtonsAfterClicked(View v) {
		 
		 //cancels the count down timer
		 countDownTimer.cancel();		 
		 
		 int button_id = v.getId();
		 
		 firstOptionButton.setEnabled(false);
		 secondOptionButton.setEnabled(false);
		 thirdOptionButton.setEnabled(false);
		 
		 switch(button_id) {
		 
		 	case R.id.first_opt_button: 
		 		
		 		if(correctSign.equals(firstOptionButton.getText())) {
					firstOptionButton.setBackgroundColor(android.graphics.Color.GREEN);
					scoreCounter();
		 		}
				else
					firstOptionButton.setBackgroundColor(android.graphics.Color.RED);
		 		break;
		 	
		 	case R.id.second_opt_button:
		 		
		 		if(correctSign.equals(secondOptionButton.getText())) {
					secondOptionButton.setBackgroundColor(android.graphics.Color.GREEN);
					scoreCounter();
 				}
				else
					secondOptionButton.setBackgroundColor(android.graphics.Color.RED);
		 		break;
		 		
		 	case R.id.third_opt_button:
		 		
 				if(correctSign.equals(thirdOptionButton.getText())){
					thirdOptionButton.setBackgroundColor(android.graphics.Color.GREEN);
					scoreCounter();
 				}
				else
					thirdOptionButton.setBackgroundColor(android.graphics.Color.RED);
 				break;
		 }
		 
		 nextButton.setEnabled(true);
	 }
	
	/**
	 * Changes the sign image when nextButton is clicked
	 */
	private void switchPic(){
		image = (ImageView) findViewById(R.id.image_view);
		nextButton = (ImageButton) findViewById(R.id.next_letter_button);
		image.setImageResource(picSetter(str));
		
		nextButton.setEnabled(false);
		
		nextButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				firstOptionButton.setBackgroundColor(android.graphics.Color.LTGRAY);

				secondOptionButton.setBackgroundColor(android.graphics.Color.LTGRAY);

				thirdOptionButton.setBackgroundColor(android.graphics.Color.LTGRAY);
				
				firstOptionButton.setEnabled(true);
				secondOptionButton.setEnabled(true);
				thirdOptionButton.setEnabled(true);

				deployTextButtons();
				nextButton.setEnabled(false);
				
				timerBar.setProgress(0);
				timeCount = 10;
				//(re)starts the count down timer				
				countDownTimer.start();
				
				if(countDownRounds())
					startActivity(new Intent("android.intent.action.GAMEEND"));
			} 
		}); 
	}
	
	/**
	 * Sets random letters to the buttons and
	 * the correct letter for the shown sign.
	 */
	private void deployTextButtons(){
		Random rnr = new Random();

		while(true) {
			randomizerLettersForAnswerButtons(randomNumber());
			str = randomLetter(answerForButtons.get(0));
			correctSign = str;
			if(putUsedSignsInArray(str)){
				Log.e(">>>>>>>", str);
				break;
			}
			else
		        answerForButtons.clear();
		}
		
		image.setImageResource(picSetter(str));
		
		int y = rnr.nextInt(3); 

		str = randomLetter(answerForButtons.get(y));
		firstOptionButton.setText(str);
		answerForButtons.remove(y);
		
		y = rnr.nextInt(2);
		str = randomLetter(answerForButtons.get(y));
		secondOptionButton.setText(str);
		answerForButtons.remove(y);
		
		str = randomLetter(answerForButtons.get(0));
		thirdOptionButton.setText(str);

		answerForButtons.clear();
	}
	
	/**
	 * Checks if a sign has already been used previously
	 * 
	 * @param signToCheck
	 * @return A boolean if the words exits or not
	 */
	private boolean putUsedSignsInArray(String signToCheck) {
		
		if(usedSignList.contains(signToCheck))
			return false;
		else{
			usedSignList.add(signToCheck);
			return true;
		}
	}

	
	/**
	 * Randomize letters from the alphabet
	 * 
	 * @param nr a random number
	 * @return a letter
	 */
	private String randomLetter(int nr){
		String randomLetter = "abcdefghijklmnopqrestuvwz";
		String name = Character.toString(randomLetter.charAt(nr));
		return name;
	}
	
	/**
	 * Gets the id for the sign image letter
	 * 
	 * @param letter
	 * @return the id number
	 */
	private int picSetter(String letter){		
		int resource = getResources().getIdentifier(letter, "drawable", "com.example.android");
		return resource;		
	}
	
	/**
	 * Randomizes a number between 0-25 the represent number of letters in the alphabet
	 * @return number
	 */
	private int randomNumber(){
		int nr = rng.nextInt(25);
		return nr;
	}
	
	/**
	 * Randomizes position for letters for the answer buttons
	 * 
	 * @param the position in the alphabet for correct answer
	 */
	private void randomizerLettersForAnswerButtons(int x){
		for(int y = 0; y < 3; y++){
			if(!answerForButtons.contains(x)){
				answerForButtons.add(x);
				x = randomNumber();
			}else{
				x = randomNumber();
				y--;
			}
		}	
	}
	
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	     getMenuInflater().inflate(R.menu.activity_game, menu);
	     return true;
	 }
	 
	    
	 //@Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	          case android.R.id.home:
	              NavUtils.navigateUpFromSameTask(this);
	              return true;
	     }
	     return super.onOptionsItemSelected(item);
	 }	

	 /**
	  * The startTimer method starts the countdown timer for making a
	  * choice in the game.
	  * The variable timeCount is even used to determine bonus points
	  * for quick answers in the scoreCounter() method.
	  */
	 public void startTimer() {
	     countDownTimer = new CountDownTimer(timeLimit,tickTime) {
	    	 private int factor = 10;
	    	 
	    	//a long is required by onTick, timeLeft is not used
	         public void onTick(long timeLeft) 
	         {
	        	 //The progress bar goes from 100 to 0 while timeCount
	        	 //is 10 to 0, so *factor for display
	             timerBar.setProgress(timeCount*factor);
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
