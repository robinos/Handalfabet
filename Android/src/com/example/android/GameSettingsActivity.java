package com.example.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.NavUtils;

public class GameSettingsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        final Button soundSettingsButton = (Button) findViewById(R.id.sound_settings_button); 
        final Button profileSettingsButton = (Button) findViewById(R.id.profile_settings_button);         

        soundSettingsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.SOUNDSETTINGSACTIVITY")); 				
			}
		});        
        
        profileSettingsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.PROFILESETTINGSACTIVITY")); 				
			}
		});         
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game_settings, menu);
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
