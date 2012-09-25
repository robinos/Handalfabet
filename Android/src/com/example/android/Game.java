package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Game
 * 
 * Note - next letter should be disabled until a right or wrong answer is
 * received (or time runs out).  Right now, clicking the next button
 * several times quickly gives strange behaviour, especially for the timer.
 * 
 * @author : Grupp02
 *
 */
public class Game extends Activity {
	
	
	 ImageView image; 
	 ImageButton button;
	 Button firstOptionButton;
	 String str = randomLetter();
	 ArrayList<String> usedSignList = new ArrayList<String>();
	 private ProgressBar timerBar;
     private CountDownTimer countDownTimer;	 
     private int timeCount = 1;
	 private int timeLimit = 11000; //11000 ms = 11s (needed instead of 10s)
	 private int tickTime = 1000;  //1000 ms = 1s
     private ImageButton nextLetterButton;
	 
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getActionBar().setDisplayHomeAsUpEnabled(true);     
        
       
               
        firstOptionButton = (Button) findViewById(R.id.first_opt_button);
        //final ImageButton secondOptionButton = (ImageButton) findViewById(R.id.second_opt_button);
        //final ImageButton thirdOptionButton = (ImageButton) findViewById(R.id.third_opt_button);        
        timerBar = (ProgressBar) findViewById(R.id.timerBar);
        nextLetterButton = (ImageButton) findViewById(R.id.next_letter_button); 
        
        firstOptionButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {							
			}
		});
        
        //secondOptionButton.setOnClickListener(new View.OnClickListener() {
 		//	public void onClick(View v) {				
 		//	}
 		//});
        
        //thirdOptionButton.setOnClickListener(new View.OnClickListener() {
 		//	public void onClick(View v) {				
 		//	}
 		//});  
        
        
        // Ändrar bild varje gång man klickar på next knappen
        switchPic();
        
        // Ändrar text på knappen
        firstOptionButton.setText(str);
        
       //This is needed to restart the timer on clicking next letter
       nextLetterButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				    timerBar.setProgress(0);				
				    timeCount = 1;
					startTimer();
				}				
		});
        
        startTimer();
         
    }
	
	
	private void switchPic(){
		image = (ImageView) findViewById(R.id.imageView);
		button = (ImageButton) findViewById(R.id.next_letter_button);
		image.setImageResource(picSetter(str));
		
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				str = randomLetter();
				firstOptionButton.setText(str);
				image.setImageResource(picSetter(str));
			} 
		});
	}
	/**
	 * Checks if a sign has already been used previus
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

	
	private String randomLetter()
	{
		Random rng = new Random();
	
		String randomLetter = "abcdefghijklmnopqrestuvwz";
		String name = Character.toString(randomLetter.charAt(rng.nextInt(25)));
		return name;
	}
	private int picSetter(String letter){
		
//		String randomLetter = "abcdefghijklmnopqrestuvwz";
//		String name = Character.toString(randomNumber); // Ändra till 28 när vi lägger till å ä ö
		
		int resource = getResources().getIdentifier(letter, "drawable", "com.example.android");
		return resource;
		
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
	  * startTimer method starts the countdown timer for making a
	  * choice in the game.
	  * We can also later add code for cancel() on a right answer,
	  * giving bonus points for time.
	  */
	 public void startTimer() {
	     countDownTimer = new CountDownTimer(timeLimit,tickTime) {
	    	//a long is required by onTick, timeLeft is not used
	         public void onTick(long timeLeft) 
	         {
	        	 //The progress bar goes from 0 to 100 while timeCount
	        	 //is 1 to 10, so *10 for display
	             timerBar.setProgress(timeCount*10);
	             timeCount++;
	         }
	        	 
	         public void onFinish()
	         {
	             //on finish we can display (Fel!) or whatever we decide
	         }
	     }.start();		 
	 }
}
