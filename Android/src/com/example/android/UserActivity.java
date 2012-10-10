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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserActivity extends Activity {
	

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
         
         //Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//          getActionBar().setDisplayHomeAsUpEnabled(true);
        }	
        
        
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
	}
	
	/** Called when the user clicks the Create New Player button */
	public void createNewPlayer(View v){
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
	
	/** Scales down User picture */
	private static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

		 final float densityMultiplier = context.getResources().getDisplayMetrics().density;        

		 int h= (int) (newHeight*densityMultiplier);
		 int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

		 photo=Bitmap.createScaledBitmap(photo, w, h, true);

		 return photo;
	}
	
	
	/** Called when the user clicks the New Game button */
	public void Login (View v){
		Bitmap img = scaleDownBitmap(bitImg, 55 ,this);
		
		Intent intent = getIntent();              
        intent.putExtra("PlayerName", userName.getText().toString()); 
        intent.putExtra("userImg", img );
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
