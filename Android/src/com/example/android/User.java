package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class User extends Activity {

	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        getActionBar().setDisplayHomeAsUpEnabled(true);	
	
	}

	
	/** Skappar knappen h�gst upp i menyn */   
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_level_chooser, menu);
        return true;
    }

	/** G�r tillbaka till Main Activity n�r anv�ndaren klickar p� knappen*/
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
