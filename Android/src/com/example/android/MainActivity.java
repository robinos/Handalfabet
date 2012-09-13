package com.example.android;




import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
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
        
        final EditText userNameBar = (EditText) findViewById(R.id.userName);
		final Button loginButton = (Button) findViewById(R.id.loginButton);
        
        
		// set up username textbar		
				userNameBar.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						if (firstClick) {
							userNameBar.setText("");
							firstClick = false;
						}
					} 
				});
		
				// Enabels Button if you type more then 2 letters
				loginButton.addTextChangedListener(new TextWatcher() {
					public void afterTextChanged(Editable arg0) {}
					public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
					public void onTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
						if(loginButton.getText().length() > 2){
							loginButton.setEnabled(true);
						}else{
							loginButton.setEnabled(false);
						}
					}
				});
				
				loginButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						startActivity(new Intent("android.intent.action.HIGHSCORE"));
//						Intent intent = new Intent(getBaseContext(), HighScore.class);
//						startActivityForResult(intent, 0);
					}
				});
				
				
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
