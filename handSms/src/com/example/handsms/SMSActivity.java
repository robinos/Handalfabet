package com.example.handsms;

//next page button http://cutcaster.com/photo/100678079-Glossy-directional-arrow-buttons/
//shift button: http://www.fedbybirds.com/2008/07/time_goes_up.html

import android.telephony.SmsManager;
import android.util.Log;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
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

import android.app.Activity;
import android.view.Menu;

public class SMSActivity extends Activity {
	
	private EditText message,phone_nr;
	static final int START_ACTIVITY_BUTTON_CODE=0;
	static final String str = " ";
	private ArrayList<String> editTextList = new ArrayList<String>();
	private ImageButton next_page,shift,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p;
	private Button space,send;
	static final  String letter = "";
	private String letter2, messagetext, phoneNr;
	private int shiftOn = 0;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        
        phone_nr = (EditText) findViewById(R.id.editText1);
        
        a = (ImageButton) findViewById(R.id.imageView1);
        b = (ImageButton) findViewById(R.id.imageButton1);
        c= (ImageButton) findViewById(R.id.imageButton2);
        d = (ImageButton) findViewById(R.id.imageButton3);
        e = (ImageButton) findViewById(R.id.imageButton4);
        f = (ImageButton) findViewById(R.id.imageButton5);
        g = (ImageButton) findViewById(R.id.imageButton6);
        h = (ImageButton) findViewById(R.id.imageButton7);
        i= (ImageButton) findViewById(R.id.imageButton8);
        j= (ImageButton) findViewById(R.id.imageButton9);
        k= (ImageButton) findViewById(R.id.imageButton10);
        l= (ImageButton) findViewById(R.id.imageButton11);
        m= (ImageButton) findViewById(R.id.imageButton12);
        n= (ImageButton) findViewById(R.id.imageButton13);
        o= (ImageButton) findViewById(R.id.imageButton14);
        p= (ImageButton) findViewById(R.id.imageButton15);
        space= (Button) findViewById(R.id.space);
        send= (Button) findViewById(R.id.send);
        shift= (ImageButton) findViewById(R.id.shift);
         
        
        shift.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {                
            	shiftOn = 1;
            	shift.setBackgroundColor(android.graphics.Color.YELLOW);
            }
        });    
        


        a.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	
            	if(shiftOn == 1){
            		
            		editTextList.add("A");
               	 StringBuilder stringBuilder = new StringBuilder();
               	for (String editText : editTextList) {
                       stringBuilder.append(editText);
                   }
               	 
               	if(!(letter.equals(null)))
                       message.append(letter+"A");
               	else
               		message.setText(stringBuilder); 
            		
            		shiftOn = 0;
                	shift.setBackgroundColor(android.graphics.Color.DKGRAY);
            	}
            
            	else{	
            		editTextList.add("a");
            		StringBuilder stringBuilder = new StringBuilder();
            		for (String editText : editTextList) {
            			stringBuilder.append(editText);
            		}
            	 
            		if(!(letter.equals(null)))
            			message.append(letter+"a");
            		else
            			message.setText(stringBuilder); 
            	}
            }
        });

        
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	
            	if(shiftOn == 1){
            		
            		editTextList.add("B");
               	 StringBuilder stringBuilder = new StringBuilder();
               	for (String editText : editTextList) {
                       stringBuilder.append(editText);
                   }
               	 
               	if(!(letter.equals(null)))
                       message.append(letter+"B");
               	else
               		message.setText(stringBuilder); 
            		
            		shiftOn = 0;
                	shift.setBackgroundColor(android.graphics.Color.DKGRAY);
            	}
            	
            	else{
            	
            		editTextList.add("b");
            		StringBuilder stringBuilder = new StringBuilder();
            		for (String editText : editTextList) {
            			stringBuilder.append(editText);
            		}
            	 
            		if(!(letter.equals(null)))
            			message.append(letter+"b");
            		else
            			message.setText(stringBuilder); 
            		}
            }
        });
        
        c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	
            	
            	if(shiftOn == 1){
            		
            		editTextList.add("C");
               	 StringBuilder stringBuilder = new StringBuilder();
               	for (String editText : editTextList) {
                       stringBuilder.append(editText);
                   }
               	 
               	if(!(letter.equals(null)))
                       message.append(letter+"C");
               	else
               		message.setText(stringBuilder); 
            		
            		shiftOn = 0;
                	shift.setBackgroundColor(android.graphics.Color.DKGRAY);
            	}
            	
            	
            	else{
            	
            	editTextList.add("c");
            	 StringBuilder stringBuilder = new StringBuilder();
            	for (String editText : editTextList) {
                    stringBuilder.append(editText);
                }
            	 
            	if(!(letter.equals(null)))
                    message.append(letter+"c");
            	else
            		message.setText(stringBuilder);
            	}
            	
            	
            }
        });
        
        d.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	
            		if(shiftOn == 1){
            			
            			editTextList.add("D");
            			StringBuilder stringBuilder = new StringBuilder();
            			for (String editText : editTextList) {
            				stringBuilder.append(editText);
            			}
               	 
            			if(!(letter.equals(null)))
            				message.append(letter+"D");
            			else
            				message.setText(stringBuilder); 
            		
            			shiftOn = 0;
            			shift.setBackgroundColor(android.graphics.Color.DKGRAY);
            		}
            		
            		else{
            	
            			editTextList.add("d");
            			StringBuilder stringBuilder = new StringBuilder();
            			for (String editText : editTextList) {
            				stringBuilder.append(editText);
            			}
            			if(!(letter.equals(null)))
            				message.append(letter+"d");
            			else
            				message.setText(stringBuilder);
            			Log.v("letter is : ", letter);
            		}
            	
            }
        });
        
        e.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	
            	if(shiftOn == 1){
        			
        			editTextList.add("E");
        			StringBuilder stringBuilder = new StringBuilder();
        			for (String editText : editTextList) {
        				stringBuilder.append(editText);
        			}
           	 
        			if(!(letter.equals(null)))
        				message.append(letter+"E");
        			else
        				message.setText(stringBuilder); 
        		
        			shiftOn = 0;
        			shift.setBackgroundColor(android.graphics.Color.DKGRAY);
        		}
        		
        		else{
            	
        			editTextList.add("e");
        			StringBuilder stringBuilder = new StringBuilder();
        			for (String editText : editTextList) {
        				stringBuilder.append(editText);
        			}
        			
        			if(!(letter.equals(null)))
        				message.append(letter+"e");
        			else
        				message.setText(stringBuilder); 
        			}
           	 }
        });
        
        f.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	
            	if(shiftOn == 1){
        			
        			editTextList.add("F");
        			StringBuilder stringBuilder = new StringBuilder();
        			for (String editText : editTextList) {
        				stringBuilder.append(editText);
        			}
           	 
        			if(!(letter.equals(null)))
        				message.append(letter+"F");
        			else
        				message.setText(stringBuilder); 
        		
        			shiftOn = 0;
        			shift.setBackgroundColor(android.graphics.Color.DKGRAY);
        		}
        		
        		else{
            	
	            	editTextList.add("f");
	            	 StringBuilder stringBuilder = new StringBuilder();
	            	for (String editText : editTextList) {
	                    stringBuilder.append(editText);
	                }
	            	 
	            	if(!(letter.equals(null)))
	                    message.append(letter+"f");
	            	else
	            		message.setText(stringBuilder); 
        			}
            }
        });
        
        g.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	if(shiftOn == 1){
        			
        			editTextList.add("D" +
        					"G");
        			StringBuilder stringBuilder = new StringBuilder();
        			for (String editText : editTextList) {
        				stringBuilder.append(editText);
        			}
           	 
        			if(!(letter.equals(null)))
        				message.append(letter+"G");
        			else
        				message.setText(stringBuilder); 
        		
        			shiftOn = 0;
        			shift.setBackgroundColor(android.graphics.Color.DKGRAY);
        		}
        		
        		else{
            	
	            	editTextList.add("g");
	            	 StringBuilder stringBuilder = new StringBuilder();
	            	for (String editText : editTextList) {
	                    stringBuilder.append(editText);
	                }
	            	 
	            	if(!(letter.equals(null)))
	                    message.append(letter+"g");
	            	else
	            		message.setText(stringBuilder); 
        			}
            }
        });
        
        h.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	if(shiftOn == 1){
        			
        			editTextList.add("H");
        			StringBuilder stringBuilder = new StringBuilder();
        			for (String editText : editTextList) {
        				stringBuilder.append(editText);
        			}
           	 
        			if(!(letter.equals(null)))
        				message.append(letter+"H");
        			else
        				message.setText(stringBuilder); 
        		
        			shiftOn = 0;
        			shift.setBackgroundColor(android.graphics.Color.DKGRAY);
        		}
        		
        		else{
            	
	            	editTextList.add("h");
	            	 StringBuilder stringBuilder = new StringBuilder();
	            	for (String editText : editTextList) {
	                    stringBuilder.append(editText);
	                }
	            	 
	            	if(!(letter.equals(null)))
	                    message.append(letter+"h");
	            	else
	            		message.setText(stringBuilder); 
	        		}
            }
        });
        
        i.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	if(shiftOn == 1){
        			
        			editTextList.add("I");
        			StringBuilder stringBuilder = new StringBuilder();
        			for (String editText : editTextList) {
        				stringBuilder.append(editText);
        			}
           	 
        			if(!(letter.equals(null)))
        				message.append(letter+"I");
        			else
        				message.setText(stringBuilder); 
        		
        			shiftOn = 0;
        			shift.setBackgroundColor(android.graphics.Color.DKGRAY);
        		}
        		
        		else{
	            	
	            	editTextList.add("i");
	            	 StringBuilder stringBuilder = new StringBuilder();
	            	for (String editText : editTextList) {
	                    stringBuilder.append(editText);
	                }
	            	 
	            	if(!(letter.equals(null)))
	                    message.append(letter+"i");
	            	else
	            		message.setText(stringBuilder); 
	        		}
            }
        });
        
        j.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	if(shiftOn == 1){
        			
        			editTextList.add("J");
        			StringBuilder stringBuilder = new StringBuilder();
        			for (String editText : editTextList) {
        				stringBuilder.append(editText);
        			}
           	 
        			if(!(letter.equals(null)))
        				message.append(letter+"J");
        			else
        				message.setText(stringBuilder); 
        		
        			shiftOn = 0;
        			shift.setBackgroundColor(android.graphics.Color.DKGRAY);
        		}
        		
        		else{
            	
	            	editTextList.add("j");
	            	 StringBuilder stringBuilder = new StringBuilder();
	            	for (String editText : editTextList) {
	                    stringBuilder.append(editText);
	                }
	            	 
	            	if(!(letter.equals(null)))
	                    message.append(letter+"j");
	            	else
	            		message.setText(stringBuilder);
	        		}
            }
        });
        
        
        k.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	if(shiftOn == 1){
        			
        			editTextList.add("K");
        			StringBuilder stringBuilder = new StringBuilder();
        			for (String editText : editTextList) {
        				stringBuilder.append(editText);
        			}
           	 
        			if(!(letter.equals(null)))
        				message.append(letter+"K");
        			else
        				message.setText(stringBuilder); 
        		
        			shiftOn = 0;
        			shift.setBackgroundColor(android.graphics.Color.DKGRAY);
        		}
        		
        		else{
            	
	            	editTextList.add("k");
	            	 StringBuilder stringBuilder = new StringBuilder();
	            	for (String editText : editTextList) {
	                    stringBuilder.append(editText);
	                }
	            	 
	            	if(!(letter.equals(null)))
	                    message.append(letter+"k");
	            	else
	            		message.setText(stringBuilder);
	        		}
            }
        });
        
        l.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	
            	if(shiftOn == 1){
        			
        			editTextList.add("I");
        			StringBuilder stringBuilder = new StringBuilder();
        			for (String editText : editTextList) {
        				stringBuilder.append(editText);
        			}
           	 
        			if(!(letter.equals(null)))
        				message.append(letter+"I");
        			else
        				message.setText(stringBuilder); 
        		
        			shiftOn = 0;
        			shift.setBackgroundColor(android.graphics.Color.DKGRAY);
        		}
        		
        		else{
            	
	            	editTextList.add("l");
	            	 StringBuilder stringBuilder = new StringBuilder();
	            	for (String editText : editTextList) {
	                    stringBuilder.append(editText);
	                }
	            	 
	            	if(!(letter.equals(null)))
	                    message.append(letter+"l");
	            	else
	            		message.setText(stringBuilder); 
	        		}
            }
        });
        
        m.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	
            	if(shiftOn == 1){
        			
        			editTextList.add("M");
        			StringBuilder stringBuilder = new StringBuilder();
        			for (String editText : editTextList) {
        				stringBuilder.append(editText);
        			}
           	 
        			if(!(letter.equals(null)))
        				message.append(letter+"M");
        			else
        				message.setText(stringBuilder); 
        		
        			shiftOn = 0;
        			shift.setBackgroundColor(android.graphics.Color.DKGRAY);
        		}
        		
        		else{
            	
	            	editTextList.add("m");
	            	 StringBuilder stringBuilder = new StringBuilder();
	            	for (String editText : editTextList) {
	                    stringBuilder.append(editText);
	                }
	            	 
	            	if(!(letter.equals(null)))
	                    message.append(letter+"m");
	            	else
	            		message.setText(stringBuilder); 
	        		}
            }
        });
        
        n.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	
            	if(shiftOn == 1){
        			
        			editTextList.add("N");
        			StringBuilder stringBuilder = new StringBuilder();
        			for (String editText : editTextList) {
        				stringBuilder.append(editText);
        			}
           	 
        			if(!(letter.equals(null)))
        				message.append(letter+"N");
        			else
        				message.setText(stringBuilder); 
        		
        			shiftOn = 0;
        			shift.setBackgroundColor(android.graphics.Color.DKGRAY);
        		}
        		
        		else{
            	
	            	editTextList.add("n");
	            	 StringBuilder stringBuilder = new StringBuilder();
	            	for (String editText : editTextList) {
	                    stringBuilder.append(editText);
	                }
	            	 
	            	if(!(letter.equals(null)))
	                    message.append(letter+"n");
	            	else
	            		message.setText(stringBuilder); 
	        		}
            }
        });
        
        o.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	
            	if(shiftOn == 1){
        			
        			editTextList.add("O");
        			StringBuilder stringBuilder = new StringBuilder();
        			for (String editText : editTextList) {
        				stringBuilder.append(editText);
        			}
           	 
        			if(!(letter.equals(null)))
        				message.append(letter+"O");
        			else
        				message.setText(stringBuilder); 
        		
        			shiftOn = 0;
        			shift.setBackgroundColor(android.graphics.Color.DKGRAY);
        		}
        		
        		else{
            	
	            	editTextList.add("o");
	            	 StringBuilder stringBuilder = new StringBuilder();
	            	for (String editText : editTextList) {
	                    stringBuilder.append(editText);
	                }
	            	 
	            	if(!(letter.equals(null)))
	                    message.append(letter+"o");
	            	else
	            		message.setText(stringBuilder); 
	        		}
            }
        });
        
        p.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	
            	if(shiftOn == 1){
        			
        			editTextList.add("P");
        			StringBuilder stringBuilder = new StringBuilder();
        			for (String editText : editTextList) {
        				stringBuilder.append(editText);
        			}
           	 
        			if(!(letter.equals(null)))
        				message.append(letter+"P");
        			else
        				message.setText(stringBuilder); 
        		
        			shiftOn = 0;
        			shift.setBackgroundColor(android.graphics.Color.DKGRAY);
        		}
        		
        		else{
            	
	            	editTextList.add("p");
	            	 StringBuilder stringBuilder = new StringBuilder();
	            	for (String editText : editTextList) {
	                    stringBuilder.append(editText);
	                }
	            	 
	            	if(!(letter.equals(null)))
	                    message.append(letter+"p");
	            	else
	            		message.setText(stringBuilder); 
	        		}
            }
        });
        
        space.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { 
            	editTextList.add(str);
            	 StringBuilder stringBuilder = new StringBuilder();
            	for (String editText : editTextList) {
                    stringBuilder.append(editText);
                }
            	 
            	if(!(letter.equals(null)))
                    message.append(letter+str);
            	else
            		message.setText(stringBuilder); 
            }
        });
        
        
        message = (EditText) findViewById(R.id.textMessage);
        
        next_page = (ImageButton) findViewById(R.id.arrow);
        next_page.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {    
            	Context context = getApplicationContext();
            	 Intent intent=new Intent(context, nextpage.class);
            	 Bundle currentWord=new Bundle();
            	 currentWord.putString("key", message.getText().toString());
            	 message.setText("");
                 intent.putExtras(currentWord);   
            	startActivityForResult(intent, START_ACTIVITY_BUTTON_CODE);
                Log.v("TO---> Second Screen", currentWord.toString());

            	
            }
          
        });
        
        send.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {                
                 phoneNr = phone_nr.getText().toString();
                 messagetext = message.getText().toString();                 
                if (phoneNr.length()>0 && messagetext.length()>0)                
                    sendSMS(phoneNr, messagetext);                
                else
                    Toast.makeText(getBaseContext(), 
                        "Please enter both phone number and message.", 
                        Toast.LENGTH_SHORT).show();
            }
        });     
    }
    
    
 
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	 Bundle resultBundle = data.getExtras();
    	 letter2 = resultBundle.getString(letter);
    	Context context = getApplicationContext();
    	Log.v("from  Second Screen", letter2);
    	message.append(letter2);
    	
    	 
    }
    
    
    private void sendSMS(String phoneNumber, String message)
    {        
        PendingIntent pi = PendingIntent.getActivity(this, 0,
            new Intent(this, SMSActivity.class), 0); 
        try{
        	
        
        		SmsManager sms = SmsManager.getDefault();
        			sms.sendTextMessage(phoneNr, null, messagetext, null, null);
        			Toast.makeText(getApplicationContext(), "SMS Sent!",
        					Toast.LENGTH_LONG).show();
        		} catch (Exception e) {
        				Toast.makeText(getApplicationContext(),
        						"SMS faild, please try again later!",
        						Toast.LENGTH_LONG).show();
        				e.printStackTrace();
        		}
    }    

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sms, menu);
        return true;
    }
}
