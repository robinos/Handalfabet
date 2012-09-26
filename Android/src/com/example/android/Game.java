package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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


import java.util.ArrayList;
import java.util.Random;

public class Game extends Activity {
	
	
	 ImageView image; 
	 ImageButton nextButton;
	 Button firstOptionButton;
	 Button secondOptionButton;
	 Button thirdOptionButton;
	 
	 Random rng = new Random();
	 
	 
	 private String str;
	 private ArrayList<Integer> answerForButtons = new ArrayList<Integer>();
	 ArrayList<String> usedSignList = new ArrayList<String>();
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getActionBar().setDisplayHomeAsUpEnabled(true);     
        
        str = randomLetter(randomNumber());
               
        firstOptionButton = (Button) findViewById(R.id.first_opt_button);
        secondOptionButton = (Button) findViewById(R.id.second_opt_button);
        thirdOptionButton = (Button) findViewById(R.id.third_opt_button);        
        
        
        
        
        firstOptionButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {							
			}
		});
        
        secondOptionButton.setOnClickListener(new View.OnClickListener() {
 			public void onClick(View v) {				
 			}
 		});
        
        thirdOptionButton.setOnClickListener(new View.OnClickListener() {
 			public void onClick(View v) {				
 			}
 		});   
        
       
     
        
        // Ändrar bild varje gång man klickar på next knappen
        switchPic();
        deployTextButtons();
//        // Ändrar text på knappen
//        firstOptionButton.setText(str);
//        
//        nextLetterButton.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//					switchPic();
//				}				
//		});        
         
    }
	
	
	private void switchPic(){
		image = (ImageView) findViewById(R.id.imageView);
		nextButton = (ImageButton) findViewById(R.id.next_letter_button);
		image.setImageResource(picSetter(str));
		
		nextButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
//				str = randomLetter(randomNumber());
//				firstOptionButton.setText(str);
//				image.setImageResource(picSetter(str));
				deployTextButtons();
			} 
		});
	}
	
	private void deployTextButtons(){
		Random rnr = new Random();

		while(true) {
			randomizerLettersForAnswerButtons(randomNumber());
			str = randomLetter(answerForButtons.get(0));
			
			if(putUsedSignsInArray(str)) {
				Log.e(">>>>>>>", str);
				break;
			}
			else
				answerForButtons.clear();
			
		}
		image.setImageResource(picSetter(str));

		int y = rnr.nextInt(3); 
		
		
		//Log.e("Första nr", "y: " + y);
		//Log.e("Första nr", "a.get(y): " + answerForButtons.get(y));
		str = randomLetter(answerForButtons.get(y));
		firstOptionButton.setText(str);
		answerForButtons.remove(y);
		y = rnr.nextInt(2);
		str = randomLetter(answerForButtons.get(y));
		secondOptionButton.setText(str);
		answerForButtons.remove(y);
		str = randomLetter(answerForButtons.get(0));
		thirdOptionButton.setText(str);

		
		//Christers Medot
		answerForButtons.clear();
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

	
	
	private String randomLetter(int nr){
//		Random rng = new Random();
		String randomLetter = "abcdefghijklmnopqrestuvwz";
//		String name = Character.toString(randomLetter.charAt(rng.nextInt(25)));  // Ändra till 28 när vi lägger till å ä ö
		String name = Character.toString(randomLetter.charAt(nr));
		return name;
	}
	
	
	private int picSetter(String letter){		
		int resource = getResources().getIdentifier(letter, "drawable", "com.example.android");
		return resource;		
	}
	
	private int randomNumber(){
		int nr = rng.nextInt(25);
		return nr;
	}
	
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

}
