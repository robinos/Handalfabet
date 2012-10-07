package com.example.android;

import java.util.List;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class DisplayHighscoreActivity extends Activity {

	private ListView listView;
	private List<User> list;
	private DatabaseHelper db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_highscore);
        
        db = new DatabaseHelper(this);
        
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }      
          
        listView = (ListView) findViewById(R.id.list_highscore);
        list = db.getAllUsers();
        // Create ArrayAdapter using the user list.  
        UserArrayAdapter adapter = new UserArrayAdapter(this, R.layout.show_user_highscore, list);
        adapter.inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        listView.setAdapter(adapter);
    }  

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_display_highscore, menu);
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
