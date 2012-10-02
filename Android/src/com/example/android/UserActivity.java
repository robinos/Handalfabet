package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserActivity extends Activity {

	private EditText userName; 
	private Button loginButton;
	Player player;
	
	String playerName;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        
         //Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }	
        
        userName = (EditText) findViewById(R.id.username);
        loginButton = (Button) findViewById(R.id.login);
        
        playerName = userName.getText().toString();
         
        player = new Player(playerName);
        
        loginButton.setEnabled(false);
        
        
     // Enabels Login Button if the Username i 5 letters or larger
        userName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                    //XXX do something
            }
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
                    //XXX do something
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	if(userName.getText().length() > 4){
            		loginButton.setEnabled(true);
            	}else{
            		loginButton.setEnabled(false);
            	}	
            }
        });
	}
	
	
	/** Called when the user clicks the New Game button */
	public void Login (View v){
		Intent intent = getIntent();              
        intent.putExtra("PlayerName", userName.getText().toString());                                       
        setResult(RESULT_OK,intent);
        finish();   
		
//		
//		Intent intent = new Intent(this, MainActivity.class);
//	    EditText userName = (EditText) findViewById(R.id.username);
//	    String message = userName.getText().toString();
//	    intent.putExtra(EXTRA_MESSAGE, message);
//	    startActivity(intent);
		
	}
 
	
	/** Skappar knappen högst upp i menyn */   
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_level_chooser, menu);
        return true;
    }

	/** Går tillbaka till Main Activity när användaren klickar på knappen*/
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
