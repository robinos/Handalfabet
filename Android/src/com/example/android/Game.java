package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Game extends Activity {
	
	
	 ImageView image; 
	 ImageButton button;
	 Button firstOptionButton;
	 String str = randomLetter();
	 ArrayList<String> usedSignList = new ArrayList<String>();	 
	 
	 
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getActionBar().setDisplayHomeAsUpEnabled(true);     
        
       
               
        firstOptionButton = (Button) findViewById(R.id.first_opt_button);
        final ImageButton secondOptionButton = (ImageButton) findViewById(R.id.second_opt_button);
        final ImageButton thirdOptionButton = (ImageButton) findViewById(R.id.third_opt_button);        
//        final ImageButton nextLetterButton = (ImageButton) findViewById(R.id.next_letter_button);
        
        
        
        
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
        
        // Ändrar text på knappen
        firstOptionButton.setText(str);
        
//        nextLetterButton.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//					switchPic();
//				}				
//		});        
         
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

}
