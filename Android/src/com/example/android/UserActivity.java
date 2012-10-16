package com.example.android;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 *   This file is part of Handalfabetet.
 *
 *   Handalfabetet is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Handalfabetet is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Handalfabetet.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * The UserActivity class handles the user login screen.
 * 
 * @author  : Grupp02
 * @version : 2012-10-14, v0.5
 * @License : GPLv3
 * @Copyright : Copyright© 2012, Grupp02
 *
 */
public class UserActivity extends Activity {

	//Audio Focus helper
	private AudioFocusHelper focusHelper;

	private SoundSettings soundData;
	private final static int MAX_VOLUME = 100;	
	
	private Button loginButton; 
	private ListView listView;
	private List<User> list;
	User player;
	private DatabaseHelper db;
	String playerName;
	
	private Bitmap bitImg;
	private TextView userName;
	
	 
	@Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        
        db = new DatabaseHelper(this); 
        soundData = new SoundSettings(this); 
        
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
        	 //getActionBar().setDisplayHomeAsUpEnabled( true );
        }     
          
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)
        	focusHelper = new AudioFocusHelper(this);
        else focusHelper = null;	
        
        
        listView =(ListView)findViewById(R.id.list);
        userName = (TextView) findViewById(R.id.textView1);
        loginButton = (Button) findViewById(R.id.login);
        
        loginButton.setEnabled(false);
        
        playerName = userName.getText().toString();
       
        enableLoginButton();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		 
		list = db.getAllUsersName();
        // Create ArrayAdapter using the user list.  
		UserArrayAdapter adapter = new UserArrayAdapter(this, R.layout.activity_user_array_adapter, list);
        adapter.inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        listView.setAdapter(adapter);
       
        listView.setOnItemClickListener(new OnItemClickListener() {
        	  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		  User user = list.get(position);
           		  userName.setText(user.getName());
        		  bitImg = user.getUserImg();
        	  }
        });
        
//		list = db.getAllUsersName();
//        // Create ArrayAdapter using the user list.  
//        ArrayAdapter<String> userList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
//        listView.setAdapter(userList);
//       
//        listView.setOnItemClickListener(new OnItemClickListener() {
//        	  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        		  userName.setText(list.get(position).toString());
//        		  User user = db.getUser(userName.getText().toString());
//        		  bitImg = user.getUserImg();
//        	  }
//        });
        
         //On resume for sound
	     if(SoundPlayer.getSoundEnabled() == false) {
	    	 if(focusHelper != null) {
	             focusHelper.abandonFocus();
	    	 }
	    	 SoundPlayer.stop();
	     }
	     else SoundPlayer.resume();
	}	
	
	 @Override
	 public void onPause() {
	     super.onPause();  // Always call the superclass method first

	     // Pause sound when paused
        if(SoundPlayer.getSoundEnabled()) SoundPlayer.pause();
	 }	
	
		@Override
		public void onSaveInstanceState(Bundle savedInstanceState) {
		    // Save name, user status, and user picture
		    savedInstanceState.putString("Name", userName.getText().toString());	     
		    savedInstanceState.putParcelable("picture", bitImg);
		    savedInstanceState.putBoolean("login", loginButton.isEnabled());		    
		    
		    // Always call the superclass so it can save the view hierarchy state
		    super.onSaveInstanceState(savedInstanceState);
		} 
		
		@Override
		public void onRestoreInstanceState(Bundle savedInstanceState) {
		    // Always call the superclass so it can restore the view hierarchy
		    super.onRestoreInstanceState(savedInstanceState);
		   
		    // Restore name, user status, and user picture
		    userName.setText(savedInstanceState.getString( "Name" ));
		    bitImg = savedInstanceState.getParcelable("picture");
		    loginButton.setEnabled(savedInstanceState.getBoolean("login"));		    
		}	 
	 
	/** Called when the user clicks the Create New Player button */
	public void createNewPlayer(View v) {
		playButton();		
		startActivity(new Intent("android.intent.action.CREATENEWPLAYER"));
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
	
    private void getSoundSettings() {	   
     	String name = userName.getText().toString();
     	
 	    // Restores data to SoundPlayer     	
     	if(name != null) {
     		if(name != "") soundData.getEntry(name); ;
     	} 		     	    
    }	
	
	/** Scales down User picture */
	private static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

		 final float densityMultiplier = context.getResources().getDisplayMetrics().density;        

		 int h= (int) (newHeight*densityMultiplier);
		 int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

		 photo=Bitmap.createScaledBitmap(photo, w, h, true);

		 return photo;
	}
	
	
	/** Called when the user clicks the Login button */
	public void Login (View v){
		playButton();		
		Bitmap img = scaleDownBitmap(bitImg, 55 ,this);
		getSoundSettings();
		
		Intent intent = getIntent();              
        intent.putExtra("PlayerName", userName.getText().toString()); 
        intent.putExtra("userImg", img );
        setResult(RESULT_OK,intent);
        finish();   
	}		 
	
	/**
	 * The getAudioFocus method attempts to gain focus for playing audio.
	 * If full access can't be gained, transitive access at a quiet volume
	 * is attempted.  If that can't be granted, false is returned.
	 * 
	 * @return : true if focus in some form is granted, otherwise false
	 */
	private boolean getAudioFocus() {
		
		if(focusHelper != null) {
			if(!focusHelper.requestFocus()) {
				if(!focusHelper.requestQuietFocus()) return false;
				else return true;
			}
			else return true;
		}
		
		return false;
	}   
    
    /**
     * playButton plays the button sound
     * 
     * If there is an AudioFocusHelper (api >= 8) use it,
     * otherwise default to SoundPlayer
     */	
     public void playButton() {
    	  if(SoundPlayer.getSoundEnabled()) {
	   	      if(focusHelper != null) {
	   		      if(getAudioFocus()) focusHelper.playButton();
	   	      }
	   	      else SoundPlayer.playButton(this);
    	  }
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
