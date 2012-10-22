package com.example.handteckenhelp;
import java.util.Timer;
import java.util.TimerTask;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
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
public class anim extends Activity{
	
	
	
	        
	        
	        private Button stop;
	    	private TextView l;
	    	private ImageView m;
	    	public int currentimageindex=0;
	    	Timer timer;
	    	TimerTask task;
	    	
	    	 private int[] imagId = {
	 	            R.drawable.a, R.drawable.b, R.drawable.c,
	 	            R.drawable.d, R.drawable.e, R.drawable.f,
	 	            R.drawable.g, R.drawable.h, R.drawable.i,
	 	            R.drawable.j, R.drawable.k, R.drawable.l,
	 	            R.drawable.m, R.drawable.n, R.drawable.o,
	 	            R.drawable.p, R.drawable.q, R.drawable.r,
	 	            R.drawable.s, R.drawable.t, R.drawable.u,
	 	            R.drawable.v, R.drawable.w, R.drawable.x,
	 	            R.drawable.y, R.drawable.z, R.drawable.za,
	 	            R.drawable.zb, R.drawable.zc
	 	        };
	 	    private String [] letters ={"A","B","C",
	 	    							"D","E","F",
	 	    							"G","H","I",
	 	    							"J","K","L",
	 	    							"M","N","O",
	 	    							"P","Q","R",
	 	    							"S","T","U",
	 	    							"V","W","X",
	 	    							"Y","Z","Ä",
	 	    							"Å","Ö"};


	    	    @Override
	    	    public void onCreate(Bundle savedInstanceState) {
	    	        super.onCreate(savedInstanceState);
	    	        setContentView(R.layout.anim);
	               	
	    	        
	        	l = (TextView) findViewById(R.id.letters);
	            m = (ImageView) findViewById(R.id.imagechange);
	            
	            changeimage();
	            stop= (Button) findViewById(R.id.stop);
	            
	            
	            stop.setOnClickListener(new View.OnClickListener() 
	            {
	                public void onClick(View v) 
	                
	                { 
	                	currentimageindex=0;
	                	finish();
	                    	
	                }
	                
	            });   
	            
	        }
	        
	        
	        
	        
	        private void changeimage()
	        {
	        	final Handler mHandler = new Handler();
	        	 
	            // Create runnable for posting
	            final Runnable mUpdateResults = new Runnable() {
	                public void run() {
	     
	                    AnimateandSlideShow();
	     
	                }
	            };
	     
	            int delay = 1000; // delay for 1 sec.
	     
	            int period = 8000; // repeat every 4 sec.
	     
	            Timer timer = new Timer();
	     
	            timer.scheduleAtFixedRate(new TimerTask() {
	     
	            public void run() {
	     
	                 mHandler.post(mUpdateResults);
	     
	            }
	     
	            }, delay, period);
	     
	        }
	        
	        /**
	         * Helper method to start the animation on the splash screen
	         */
	        private void AnimateandSlideShow() {
	     
	           
	            m.setImageResource(imagId[currentimageindex%imagId.length]);
	            l.setText(letters[currentimageindex%imagId.length]);
	            currentimageindex++;
	     
	            Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
	     
	              m.startAnimation(rotateimage);
	              l.startAnimation(rotateimage);
	              
	     
	        }
	        
	        
	 

}
