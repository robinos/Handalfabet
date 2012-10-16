package com.example.android;

import java.util.HashSet;
import java.io.IOException;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;

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
  * SoundPlayer is based on PlaySound code from
  * http://blog.endpoint.com/2011/03/api-gaps-android-mediaplayer-example.html
  * Though it has been altered.
  * 
  * @author  : Grupp02
  * @version : 2012-10-14, v0.5
  * @License : GPLv3
  * @Copyright :Copyright© 2012, Grupp02  
  */
 public class SoundPlayer extends Service
 {
    private static HashSet<MediaPlayer> mpSet = new HashSet<MediaPlayer>();
    private static boolean debug = false;	   
    
    //The path to package resources
    private final static String packagePath = "android.resource://com.example.android/";
    private static float volumeRight = 1.0f;
    private static float volumeLeft = 1.0f;
    private static int currentVolume = 50;    
    private static boolean soundEnabled = true;
    private static boolean vibrationEnabled = true;
    
  	 //Modified from example from http://android.konreu.com/developer-how-to/
  	 //vibration-examples-for-android-phone-development/
     //Values representing time in milliseconds
  	 private final static int right_buzz = 200;      // A short pulse
  	 private final static int wrong_buzz = 500;     // A medium pulse
  	 private final static int timeout_buzz = 1000;     // A long pulse  	 
  	 private final static int short_pause = 200;    // A short pause
  	 
  	 //The pulse sequence for a right answer
  	 private final static long[] right = {
  	         0,  // Start immediately
  	         right_buzz, short_pause, right_buzz };	    
    
  	 //The pulse sequence for applause (congratulations)
  	 private final static long[] applause = {
  	         0,  // Start immediately
  	         right_buzz, short_pause, right_buzz, short_pause,
  	         right_buzz, short_pause, right_buzz };	  	 
  	 
    /**
     * The play method plays a sound given the context, and resource ID
     * 
     * @param context : the context for the sound to be played in
     *     (the calling Activity generally)
     * @param resID   : the resource ID of the sound file   
     */	


    public static MediaPlayer play(Context context, int resId) {
    	if(soundEnabled) {
	        try {
	            MediaPlayer mp = new MediaPlayer();
	            mp.setDataSource(context, Uri.parse(packagePath + resId));
	            mp.setAudioStreamType(AudioManager.STREAM_RING);
	            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
	            	//Should be with @Override, but oddly won't accept it
	                public void onCompletion(MediaPlayer mp) {
	                    mpSet.remove(mp);
	                    mp.stop();
	                    mp.release();
	                }
	            });
	            
	            mp.prepare();           
	            
	            mpSet.add(mp);
	            mp.setVolume(volumeLeft,volumeRight);
	            mp.start();
	            
	            return mp;
	        } catch (IOException e) {
	            if (debug) System.err.println("Error reading mp3 file.");
	            return null;            
	        }
    	}
    	else return null;
    }       
    
    /**
     * The stop method stops all ongoing sounds and releases their memory
     */
    public static void stop() {
        for (MediaPlayer mp : mpSet) {
            if (mp != null) {
                mp.stop();
                mp.release();
            }
        }
        mpSet.clear();
    }
    
    /**
     * The pause method pauses all ongoing sounds
     */
    public static void pause() {
        for (MediaPlayer mp : mpSet) {
            if (mp != null) {
                mp.pause();               
            }
            else {
            	//Don't keep null MediaPlayers in the set
            	mpSet.remove(mp);
            }
        }
    }    
    
    /**
     * The resume method resumes all ongoing sounds
     */
    public static void resume() {
        for (MediaPlayer mp : mpSet) {
            if (mp != null) {
                mp.start();
            }
            else {
            	//Don't keep null MediaPlayers in the set
            	mpSet.remove(mp);
            }            
        }
    }     

    /**
     * The lowerVolume method lowers the volume of all ongoing sounds
     */
    public static void setVolume(float volumeLeft, float volumeRight) {
    	SoundPlayer.volumeLeft = volumeLeft;
    	SoundPlayer.volumeRight = volumeRight;
        for (MediaPlayer mp : mpSet) {
            if (mp != null) {
                mp.setVolume(volumeLeft,volumeRight);
            }
            else {
            	//Don't keep null MediaPlayers in the set
            	mpSet.remove(mp);
            }            
        }
    }    
    
    
    /**
     * The buzz method vibrates the device according to predetermined pattern
     * to represent certain game events.
     * 
     * @param context : the context for the vibration to be played in
     *     (the calling Activity generally)
     * @param type    : A string with a value describing which buzz sequence
     *     to play 
     */
    public static void buzz(Context context, String type) {
    	if(vibrationEnabled) {
	    	Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	    	
	    	if( type.equals ("right") ) {
	    		vibrator.vibrate( right, -1 );
	    	}
	    	else if( type.equals( "wrong" ) ) {
	    		vibrator.vibrate( wrong_buzz );
	    	}
	    	else if( type.equals( "applause" ) ) {
	    		vibrator.vibrate( applause, -1 );
	    	}
	    	else if( type.equals( "timeout" ) ) {
	    		vibrator.vibrate( timeout_buzz );
	    	}
	    	else System.err.println( "Invalid buzz type!" );
    	}
    }
    
    /**
     * The getVolumeLeft method
     * 
     * @return
     */
    public static float getVolumeLeft() {
    	return volumeLeft;
    }
    
    /**
     * The getVolumeRight method
     * 
     * @return
     */
    public static float getVolumeRight() {
    	return volumeRight;
    }    
    
	/**
	 * playTimeout plays the timeout sound in the given context
	 * 
	 * @param context
	 */
	public static void playTimeout(Context context) {	
		if(soundEnabled) play(context, R.raw.mp3_timeout);
	}	

	/**
	 * playButton plays the button sound in the given context
	 * 
	 * @param context
	 */	
	public static void playButton(Context context) {		
		if(soundEnabled) play(context, R.raw.mp3_button);		
	}

	/**
	 * playTicking plays the ticking sound in the given context
	 * 
	 * @param context
	 */	
	public static void playTicking(Context context) {		
		if(soundEnabled) play(context, R.raw.mp3_clockticking);				
	}	
	
	/**
	 * playApplause plays the applause sound in the given context
	 * 
	 * @param context
	 */	
	public static void playApplause(Context context) {
		if(vibrationEnabled) SoundPlayer.buzz( context, "applause" );
		if(soundEnabled) play(context, R.raw.mp3_applause);			
	}    
    
	public static void playRightChoice(Context context) {
		//Use the right answer pattern (short vibration, pause, short vibration)
		if(vibrationEnabled) buzz( context, "right" );		
        //Play the right answer sound			
		if(soundEnabled) play(context, R.raw.mp3_right);			
	}

	/**
	 * playWrongChoice
	 */
	public static void playWrongChoice(Context context) {
		//Use the medium length buzz for a wrong answer
		if(vibrationEnabled) buzz( context, "wrong" );
        //Play the wrong answer sound			
		if(soundEnabled) play(context, R.raw.mp3_wrong);		
	}	
	
	/**
	 * Returns if the sound is currently enabled.
	 * 
	 * @return
	 */
	public static boolean getSoundEnabled() {
		if(soundEnabled) return true;
		return false;
	}
	
	/**
	 * Sets if the sound is currently enabled.
	 * 
	 * @param enabled : true if sound enabled, otherwise false
	 */
	public static void setSoundEnabled(boolean enabled) {
		soundEnabled = enabled;
	}	
	
	/**
	 * Returns if vibrations are currently enabled.
	 * 
	 * @return
	 */
	public static boolean getVibrationEnabled() {
		if(vibrationEnabled) return true;
		return false;
	}
	
	/**
	 * Sets if vibrations are currently enabled.
	 * 
	 * @param enabled : true if sound enabled, otherwise false
	 */
	public static void setVibrationEnabled(boolean enabled) {
		vibrationEnabled = enabled;
	}
	
	/**
	 * Returns an integer representation of current volume.
	 * 
	 * @return
	 */
	public static int getCurrentVolume() {
		return currentVolume;
	}
	
	/**
	 * Sets a currentVolume variable.
	 * 
	 * @param volume : an integer representation of volume
	 */
	public static void setCurrentVolume(int volume) {
		currentVolume = volume;
	}		
	
	@Override
	/**
	 * The onDestroy methods overrides onDestroy from Service, making sure that
	 * MediaPlayer objects properly release their memory when the SoundPlayer
	 * is destroyed.
	 * I don't know if it is required since Static calls only are made to
	 * SoundPlayer, but better safe than sorry.
	 */
	public void onDestroy() {
	    stop();
		super.onDestroy();	    
	}

	@Override
	/**
	 * Required override of abstract class for Service inheritance
	 * 
	 * @param intent
	 * @return
	 */
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}	
}
