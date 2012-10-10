package com.example.android;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class UserActivity extends Activity {
	
	private Button loginButton;
	private EditText userName; 
	private ListView listView;
	private List<String> list;
	User player;
	private DatabaseHelper db;
	String playerName;
	

	
	@Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        
        db = new DatabaseHelper(this);
         
         //Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //getActionBar().setDisplayHomeAsUpEnabled(true);
        }	
           
        userName = (EditText) findViewById(R.id.username);
        loginButton = (Button) findViewById(R.id.login);
        listView = (ListView) findViewById(android.R.id.list);
        
        loginButton.setEnabled(false);
        
        playerName = userName.getText().toString();
       
        enableLoginButton();
             
        // lägger till spelare i databasen 
        addUsers();    
       
        list = db.getAllUsersName();
        // Create ArrayAdapter using the user list.  
        ArrayAdapter<String> userList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(userList);
       
        listView.setOnItemClickListener(new OnItemClickListener() {        	
        	  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {        		  
        		  userName.setText(list.get(position).toString());
        	  }
        }); 
	}
	 
	private void addUsers(){

        // Inserting Users
        Log.d("Insert: ", "Inserting ..");
//        db.addUser(new User("Feriz", 52));
//        db.addUser(new User("Christer", 23));
//        db.addUser(new User("Robin", 26));
//        db.addUser(new User("Anas", 12));
//        db.addUser(new User("Henrik", 0));
//         Reading all Users
        Log.d("Reading: ", "Reading all Users..");
        List<User> User = db.getAllUsers();       
 
        for (User cn : User) {
            String log = "Name: "+cn.getName() +" ,HighScore: " + cn.getHighScore();
                // Writing Contacts to log
        Log.d("Name: ", log);
        
        }
	}
	
	
	private void enableLoginButton(){
		userName.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){
	        	
	        }
	        public void onTextChanged(CharSequence s, int start, int before, int count){
	        	if(userName.getText().length() > 2){
					loginButton.setEnabled(true);
				}else{
					loginButton.setEnabled(false);
				}		
	        }
	    }); 
	}
	
	/** Called when the user clicks the New Game button */
	public void Login (View v){
		//Play the button sound
		SoundPlayer.playButton(UserActivity.this);		
		// If user does´t exist create new User
		if(!list.contains(userName.getText().toString())){
			db.addUser(new User(userName.getText().toString(), 0));
		}
		Intent intent = getIntent();              
        intent.putExtra("PlayerName", userName.getText().toString());                                       
        setResult(RESULT_OK,intent);
        finish();   
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
