package com.example.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.support.v4.app.NavUtils;

public class GameEnd extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        final Button backButton = (Button) findViewById(R.id.backButton);
        final Button highScoreButton = (Button) findViewById(R.id.highScoreButton);
        final Button gameSettingsButton = (Button) findViewById(R.id.settings_button);        
        final ImageButton nextWordButton = (ImageButton) findViewById(R.id.next_word_button);        
        
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
        
        nextWordButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.GAME")); 				
			}
		});         
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game_end, menu);
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

}
