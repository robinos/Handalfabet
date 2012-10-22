package com.example.handteckenhelp;

//http://www.aktivitetsbanken.se/aktivitetsbanken/images/1/14/Handalfabet.JPG

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;


public class HelpActivity extends Activity {
	
	private Button start;
	private TextView t,l;
	private ImageView m;
	public int currentimageindex=0;
	Timer timer;
	TimerTask task;
	
	
	
	 
	   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        
        start = (Button) findViewById(R.id.start);
        
        
        start.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {       
            	
            	Context context = getApplicationContext();
            	Intent intent=new Intent(context, anim.class);
            	startActivity(intent);
                	
            }
            
        });   
        
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_help, menu);
        return true;
    }
}
