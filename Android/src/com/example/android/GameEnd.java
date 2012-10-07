package com.example.android;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class GameEnd extends Activity {
	
	private DatabaseHelper db;
	
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game_end );
        
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            getActionBar().setDisplayHomeAsUpEnabled( true );
        }         
             
        db = new DatabaseHelper(this);
        
        final ImageButton newGameButton = ( ImageButton ) findViewById( R.id.new_game_button );                
        final ImageButton highScoreButton = ( ImageButton ) findViewById( R.id.high_scores_button ); 
        final ImageButton mainMenuButton = ( ImageButton ) findViewById( R.id.main_menu_button );
        
        final TextView userStatus = (TextView)findViewById(R.id.textView1);
        final TextView userName = (TextView)findViewById(R.id.textView2);
        
        int numCorrect = getIntent().getIntExtra( Game.NUMCORRECT, 0 );         
        int totalScore = getIntent().getIntExtra( Game.TOTALSCORE, 0 ); 
        int averageTime = getIntent().getIntExtra( Game.AVERAGETIME, 0 );    
        
        
        //Displays the userName
        userStatus.setText(R.string.inloggad);
		userName.setText(getIntent().getStringExtra("name"));
		
		User user = db.getUser(userName.getText().toString());
		  
		// Update HighScore 
		if(user.getHighScore() < totalScore){
			user.setHighScore(totalScore);
			db.updateUserHighScore(user);
		}

        newGameButton.setOnClickListener( new View.OnClickListener() {
			public void onClick( View v ) {
				startActivity( new Intent( "android.intent.action.GAME" ) ); 				
			}
		} ); 
        
        highScoreButton.setOnClickListener( new View.OnClickListener() {
			public void onClick( View v ) {
				startActivity( new Intent( "android.intent.action.DISPLAYHIGHSCOREACTIVITY" ) ); 				
			}
		}); 
        
        //mainMenuButton.setOnClickListener( new View.OnClickListener() {
		//	public void onClick( View v ) {
		//		startActivity( new Intent( "android.intent.action.MAIN" ) );  			
		//	}
		//});         
        
        TextView totalPoints = ( TextView ) findViewById( R.id.show_total_point );        
		totalPoints.setText( "  " + Integer.toString( totalScore ) );        

        TextView correctView = ( TextView ) findViewById( R.id.show_answers );        
        correctView.setText( Integer.toString( numCorrect ) + "  " );
	
        TextView averageView = ( TextView ) findViewById( R.id.show_average );        
        averageView.setText( "  " + Integer.toString( averageTime ) );		        
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.activity_game_end, menu );
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch ( item.getItemId() ) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask( this );
                return true;
        }
        return super.onOptionsItemSelected( item );
    }

}
