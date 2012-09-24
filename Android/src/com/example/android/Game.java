package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Game extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getActionBar().setDisplayHomeAsUpEnabled(true);        
        
        final Button backButton = (Button) findViewById(R.id.backButton);
        final Button highScoreButton = (Button) findViewById(R.id.highScoreButton);
        final Button gameSettingsButton = (Button) findViewById(R.id.settings_button);         
        final ImageButton firstOptionButton = (ImageButton) findViewById(R.id.first_opt_button);
        final ImageButton secondOptionButton = (ImageButton) findViewById(R.id.second_opt_button);
        final ImageButton thirdOptionButton = (ImageButton) findViewById(R.id.third_opt_button);        
        final ImageButton nextLetterButton = (ImageButton) findViewById(R.id.next_letter_button);
        
        backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
        
        highScoreButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.DISPLAYHIGHSCOREACTIVITY")); 
			}
		});       
        
        gameSettingsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.GAMESETTINGSACTIVITY")); 
			}
		});        
        
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
        
        nextLetterButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.GAMEEND")); 				
			}
		});        
        
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
