package com.example.android;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
  * The CreateNewPlayer class is the Activity where new players are
  * created
  * 
  * @author  : Grupp02
  * @version : 2012-10-14, v0.5
  * @License : GPLv3
  * @Copyright :Copyright© 2012, Grupp02  
  */
public class CreateNewPlayer extends Activity{
	
	//Audio Focus helper
	private AudioFocusHelper focusHelper;	
	
	private static final int REQUEST_CODE = 1;
	private Bitmap bitmap;
	private ImageView imageView;
	private Button takePhotoButton;
	private DatabaseHelper db;
	private SoundSettings soundData;	
	private EditText userName; 
	private Button createPlayerButton;
	
	@Override 	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_player);
	
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
        	 //getActionBar().setDisplayHomeAsUpEnabled( true );
        }     
          
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)
        	focusHelper = new AudioFocusHelper(this);
        else focusHelper = null;        
        
        db = new DatabaseHelper(this); 
        soundData = new SoundSettings(this);
        
        userName = (EditText) findViewById(R.id.username);
        imageView = (ImageView) findViewById(R.id.userImage);
        
        takePhotoButton =(Button) findViewById(R.id.takePic);
        createPlayerButton =(Button) findViewById(R.id.createPlayer);
        
        createPlayerButton.setEnabled(false);
        enableCreatePlayerButton();
        
      //TODO 
        takePhotoButton.setEnabled(false);
	}
	
	 @Override
	 /**
	  * onResume is overriden in order to utterly abandon sound focus if
	  * sound has been turned off, or resume sound if on.
	  * 
	  */
	 public void onResume() {
	 	 super.onResume();
	 	 
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
	 
	/** Called when the user clicks the Create New Player button */
	public void createPlayer(View v){
		playButton();		
		BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
		Bitmap bitmap = drawable.getBitmap();
		User user = new User(userName.getText().toString(), 0,  bitmap, 0, 0);		
		db.addUser(user);
		soundData.addEntry(user);
		finish();
	}
	
	
	/** Called when the user clicks the Choose Image button */
	public void pickImage(View View) {
		playButton();		
	    Intent intent = new Intent();
	    intent.setType("image/*");
	    intent.setAction(Intent.ACTION_GET_CONTENT);
	    intent.addCategory(Intent.CATEGORY_OPENABLE);
	    startActivityForResult(intent, REQUEST_CODE);
	  }

	  @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
	      try {
	        // We need to recyle unused bitmaps
	        if (bitmap != null) {
	          bitmap.recycle();
	        }
	        InputStream stream = getContentResolver().openInputStream(data.getData());
	        bitmap = BitmapFactory.decodeStream(stream);
	        stream.close();
	        imageView.setImageBitmap(bitmap);
	      } catch (FileNotFoundException e) {
	        e.printStackTrace();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    super.onActivityResult(requestCode, resultCode, data);
	  }
	  
	  private void enableCreatePlayerButton(){
			userName.addTextChangedListener(new TextWatcher(){
		        public void afterTextChanged(Editable s) {
		        	
		        }
		        public void beforeTextChanged(CharSequence s, int start, int count, int after){
		        	
		        }
		        public void onTextChanged(CharSequence s, int start, int before, int count){
		        	if(userName.getText().length() > 2){
		        		createPlayerButton.setEnabled(true);
					}else{
						createPlayerButton.setEnabled(false);
					}		
		        }
		    }); 
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
		 */	
	     public void playButton() {
	    	  if(SoundPlayer.getSoundEnabled()) {
		   	      if(focusHelper != null) {
		   		      if(getAudioFocus()) focusHelper.playButton();
		   	      }
		   	      else SoundPlayer.playButton(this);
	    	  }
	     }		
}
