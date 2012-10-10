package com.example.android;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class CreateNewPlayer extends Activity{
	
	private static final int REQUEST_CODE = 1;
	private Bitmap bitmap;
	private ImageView imageView;
	private Button takePhotoButton;
	private DatabaseHelper db;
	private EditText userName; 
	private Button createPlayerButton;
	
	@Override 	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_player);
	
        db = new DatabaseHelper(this); 
        
        userName = (EditText) findViewById(R.id.username);
        imageView = (ImageView) findViewById(R.id.userImage);
        
        takePhotoButton =(Button) findViewById(R.id.takePic);
        createPlayerButton =(Button) findViewById(R.id.createPlayer);
        
        createPlayerButton.setEnabled(false);
        enableCreatePlayerButton();
        
      //TODO 
        takePhotoButton.setEnabled(false);
	}
	
	/** Called when the user clicks the Create New Player button */
	public void createPlayer(View v){
		BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
		Bitmap bitmap = drawable.getBitmap();
		db.addUser(new User(userName.getText().toString(), 0,  bitmap));
		finish();
	}
	
	
	/** Called when the user clicks the Choose Image button */
	public void pickImage(View View) {
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
}
