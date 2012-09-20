package com.example.android;





import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private boolean firstClick = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final EditText usernameBar = (EditText) findViewById(R.id.username);
		final Button loginButton = (Button) findViewById(R.id.loginButton);
		
		// set up username textbar		
		usernameBar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (firstClick) {
					usernameBar.setText("");
					firstClick = false;
				}
			} 
		});
		
		loginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.GAME")); 
//				Intent intent = new Intent(getBaseContext(), Game.class);
//				startActivityForResult(intent, 0);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    /*
     * Called when the user clicks the levelchooser button
     */
    public void goLevelChooser(View view) {
    	
    	Intent intent = new Intent(this, LevelChooserActivity.class);
    	startActivity(intent);
    	
    }
    
    /*
     * Called when the user clicks the highscore button
     */
    public void goTheHighscore(View view) {
    	
    	Intent intent = new Intent(this, DisplayHighscoreActivity.class);
    	startActivity(intent);
    }
}
