package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Game extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        
        final Button backButton = (Button) findViewById(R.id.backButton);
        final Button highScoreButton = (Button) findViewById(R.id.highScoreButton);
        
        backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
        
        highScoreButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.HIGHSCORE")); 
//				Intent intent = new Intent(getBaseContext(), Game.class);
//				startActivityForResult(intent, 0);
			}
		});
        
    }

}
