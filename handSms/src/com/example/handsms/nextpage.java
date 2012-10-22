package com.example.handsms;
import android.telephony.SmsManager;
import android.util.Log;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.*;
import android.widget.ImageView;
import android.graphics.*;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.EditText;

public class nextpage extends Activity {
	
	private ImageButton q,r,s,t,u,v,w,x,y,z,aa,ao,oa,shiftOn;
	private String fromFirstpage ;
	private StringBuilder stb2 = new StringBuilder();
	private boolean shift_on = false;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.more_letter);
	        
	        q = (ImageButton) findViewById(R.id.q);
	        r = (ImageButton) findViewById(R.id.r);
	        s = (ImageButton) findViewById(R.id.s);
	        t = (ImageButton) findViewById(R.id.imageButton1);
	        u = (ImageButton) findViewById(R.id.imageButton2);
	        v = (ImageButton) findViewById(R.id.imageButton3);
	        w = (ImageButton) findViewById(R.id.imageButton4);
	        x = (ImageButton) findViewById(R.id.imageButton5);
	        y = (ImageButton) findViewById(R.id.imageButton6);
	        z = (ImageButton) findViewById(R.id.imageButton7);
	        aa = (ImageButton) findViewById(R.id.imageButton8);
	        ao = (ImageButton) findViewById(R.id.imageButton9);
	        oa = (ImageButton) findViewById(R.id.imageButton10);
	        shiftOn = (ImageButton) findViewById(R.id.shift1);
	        
	        
	        shiftOn.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View arg0) { 
	            	
	            	
	            	
	            	if(shift_on)
	            	{
	            		shift_on=false;
	            		shiftOn.setBackgroundColor(android.graphics.Color.DKGRAY);
	            	}
	            	else
	            	{
	            		shift_on=true;
	            		shiftOn.setBackgroundColor(android.graphics.Color.YELLOW);
	            	}
	            		            	
	            }
	        });
	            
	        
	        
	        q.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View arg0) { 
	            	
	            	Bundle b = getIntent().getExtras();
	            	fromFirstpage = b.getString("key");
	            	  Log.v(" mottagit from first", fromFirstpage);

	            	  Bundle resultBundle=new Bundle();
	            	
	            	if(shift_on){
	            		
	            		resultBundle.putString(SMSActivity.letter,b.getString("key")+"Q");
	            		shift_on=false;
		            	shiftOn.setBackgroundColor(android.graphics.Color.DKGRAY);
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
			            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            	finish();

	            	}
	            	else{
	            		
				            resultBundle.putString(SMSActivity.letter,b.getString("key")+"q");
				            Intent resultIntent = new Intent().putExtras(resultBundle);
				            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
				            Log.v(" To first Screen", b.getString("key")+"q" + ".");
				            finish();
	            	}
	                
	            }
	        });
	      //  Log.v(" to first Screen", "q" + ".");
	        
	        r.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View arg0) { 
	            	
	            	Bundle b = getIntent().getExtras();
	            	
	            	Bundle resultBundle=new Bundle();
	            	if(shift_on){
	            		resultBundle.putString(SMSActivity.letter,b.getString("key")+"R");
	            		shift_on=false;
		            	shiftOn.setBackgroundColor(android.graphics.Color.DKGRAY);
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
			            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            	finish();

	            	}
		            resultBundle.putString(SMSActivity.letter,b.getString("key")+"r");
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
		            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            Log.v(" To first Screen",b.getString("key")+"r" + ".");
		            	 finish();

	            	
	            	
	            }
	        });
	        
	        s.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View arg0) {
	            	
	            	Bundle b = getIntent().getExtras();	            	
	            	Bundle resultBundle=new Bundle();
	            	if(shift_on){
	            		resultBundle.putString(SMSActivity.letter,b.getString("key")+"S");
	            		shift_on=false;
		            	shiftOn.setBackgroundColor(android.graphics.Color.DKGRAY);
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
			            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            	finish();

	            	}
		            resultBundle.putString(SMSActivity.letter,b.getString("key")+"s");
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
		            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            Log.v(" To first Screen",b.getString("key")+"s" + ".");
		            	 finish();
	            }
	        });
	        
	        t.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View arg0) {
	            	
	            	Bundle b = getIntent().getExtras();	            	
	            	Bundle resultBundle=new Bundle();
	            	if(shift_on){
	            		resultBundle.putString(SMSActivity.letter,b.getString("key")+"T");
	            		shift_on=false;
		            	shiftOn.setBackgroundColor(android.graphics.Color.DKGRAY);
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
			            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            	finish();

	            	}
		            resultBundle.putString(SMSActivity.letter,b.getString("key")+"t");
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
		            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            Log.v(" To first Screen",b.getString("key")+"t" + ".");
		            	 finish();
	            }
	        });
	        
	        u.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View arg0) {
	            	
	            	Bundle b = getIntent().getExtras();	            	
	            	Bundle resultBundle=new Bundle();
	            	if(shift_on){
	            		resultBundle.putString(SMSActivity.letter,b.getString("key")+"U");
	            		shift_on=false;
		            	shiftOn.setBackgroundColor(android.graphics.Color.DKGRAY);
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
			            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            	finish();

	            	}
		            resultBundle.putString(SMSActivity.letter,b.getString("key")+"u");
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
		            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            Log.v(" To first Screen",b.getString("key")+"u" + ".");
		            	 finish();
	            }
	        });
	        
	        v.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View arg0) {
	            	
	            	Bundle b = getIntent().getExtras();	            	
	            	Bundle resultBundle=new Bundle();
	            	if(shift_on){
	            		resultBundle.putString(SMSActivity.letter,b.getString("key")+"V");
	            		shift_on=false;
		            	shiftOn.setBackgroundColor(android.graphics.Color.DKGRAY);
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
			            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            	finish();

	            	}
		            resultBundle.putString(SMSActivity.letter,b.getString("key")+"v");
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
		            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            Log.v(" To first Screen",b.getString("key")+"v" + ".");
		            	 finish();
	            }
	        });
	        
	        w.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View arg0) {
	            	
	            	Bundle b = getIntent().getExtras();	            	
	            	Bundle resultBundle=new Bundle();
	            	if(shift_on){
	            		resultBundle.putString(SMSActivity.letter,b.getString("key")+"W");
	            		shift_on=false;
		            	shiftOn.setBackgroundColor(android.graphics.Color.DKGRAY);
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
			            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            	finish();

	            	}
		            resultBundle.putString(SMSActivity.letter,b.getString("key")+"w");
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
		            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            Log.v(" To first Screen",b.getString("key")+"w" + ".");
		            	 finish();
	            }
	        });
	        
	        x.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View arg0) {
	            	
	            	Bundle b = getIntent().getExtras();	            	
	            	Bundle resultBundle=new Bundle();
	            	if(shift_on){
	            		resultBundle.putString(SMSActivity.letter,b.getString("key")+"X");
	            		shift_on=false;
		            	shiftOn.setBackgroundColor(android.graphics.Color.DKGRAY);
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
			            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            	finish();

	            	}
		            resultBundle.putString(SMSActivity.letter,b.getString("key")+"x");
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
		            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            Log.v(" To first Screen",b.getString("key")+"x" + ".");
		            	 finish();
	            }
	        });
	        
	        y.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View arg0) {
	            	
	            	Bundle b = getIntent().getExtras();	            	
	            	Bundle resultBundle=new Bundle();
	            	if(shift_on){
	            		resultBundle.putString(SMSActivity.letter,b.getString("key")+"Y");
	            		shift_on=false;
		            	shiftOn.setBackgroundColor(android.graphics.Color.DKGRAY);
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
			            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            	finish();

	            	}
		            resultBundle.putString(SMSActivity.letter,b.getString("key")+"y");
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
		            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            Log.v(" To first Screen",b.getString("key")+"y" + ".");
		            	 finish();
	            }
	        });
	        
	        z.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View arg0) {
	            	
	            	Bundle b = getIntent().getExtras();	            	
	            	Bundle resultBundle=new Bundle();
	            	if(shift_on){
	            		resultBundle.putString(SMSActivity.letter,b.getString("key")+"Z");
	            		shift_on=false;
		            	shiftOn.setBackgroundColor(android.graphics.Color.DKGRAY);
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
			            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            	finish();

	            	}
		            resultBundle.putString(SMSActivity.letter,b.getString("key")+"z");
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
		            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            Log.v(" To first Screen",b.getString("key")+"z" + ".");
		            	 finish();
	            }
	        });
	        
	        aa.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View arg0) {
	            	
	            	Bundle b = getIntent().getExtras();	            	
	            	Bundle resultBundle=new Bundle();
	            	if(shift_on){
	            		resultBundle.putString(SMSActivity.letter,b.getString("key")+"Ä");
	            		shift_on=false;
		            	shiftOn.setBackgroundColor(android.graphics.Color.DKGRAY);
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
			            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            	finish();

	            	}
		            resultBundle.putString(SMSActivity.letter,b.getString("key")+"ä");
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
		            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            Log.v(" To first Screen",b.getString("key")+"ä" + ".");
		            	 finish();
	            }
	        });
	        
	        ao.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View arg0) {
	            	
	            	Bundle b = getIntent().getExtras();	            	
	            	Bundle resultBundle=new Bundle();
	            	if(shift_on){
	            		resultBundle.putString(SMSActivity.letter,b.getString("key")+"Å");
	            		shift_on=false;
		            	shiftOn.setBackgroundColor(android.graphics.Color.DKGRAY);
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
			            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            	finish();

	            	}
		            resultBundle.putString(SMSActivity.letter,b.getString("key")+"å");
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
		            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            Log.v(" To first Screen",b.getString("key")+"å" + ".");
		            	 finish();
	            }
	        });
	        
	        oa.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View arg0) {
	            	
	            	Bundle b = getIntent().getExtras();	            	
	            	Bundle resultBundle=new Bundle();
	            	if(shift_on){
	            		resultBundle.putString(SMSActivity.letter,b.getString("key")+"Ö");
	            		shift_on=false;
		            	shiftOn.setBackgroundColor(android.graphics.Color.DKGRAY);
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
			            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            	finish();

	            	}
		            resultBundle.putString(SMSActivity.letter,b.getString("key")+"ö");
		            	Intent resultIntent = new Intent().putExtras(resultBundle);
		            setResult(SMSActivity.START_ACTIVITY_BUTTON_CODE, resultIntent);
		            Log.v(" To first Screen",b.getString("key")+"ö" + ".");
		            	 finish();
	            }
	        });
	        
            Bundle bundle = new Bundle();
            bundle.putString("key", stb2.toString());
            
            
            	        
	 }
	 
	 
}
