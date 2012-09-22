package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Game extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        
        final Button backButton = (Button) findViewById(R.id.backButton);
        final Button highScoreButton = (Button) findViewById(R.id.highScoreButton);
        final ImageButton firstOptionButton = (ImageButton) findViewById(R.id.first_opt_button);
        
        TextView loginView = new TextView(this);
        loginView.setTextSize(20);
        loginView.setText(R.string.login_view);
        
        backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
        
        highScoreButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.DISPLAYHIGHSCOREACTIVITY")); 
//				Intent intent = new Intent(getBaseContext(), Game.class);
//				startActivityForResult(intent, 0);
			}
		});       
        
        firstOptionButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			}
		});        
        
    }

}
