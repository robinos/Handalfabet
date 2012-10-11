package com.example.android;

import java.util.HashSet;

import android.content.Context;
import android.media.MediaPlayer;
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
  * 
  * @author  : Grupp02
  * @version : 2012-10-10, v0.5
  * @License : GPLv3
  * @Copyright :Copyright© 2012, Grupp02  
  */
 public class SoundPlayer
 {
    private static HashSet<MediaPlayer> mpSet = new HashSet<MediaPlayer>();
    	
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
    public static void play(Context context, int resId) {
        MediaPlayer mp = MediaPlayer.create(context, resId);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mpSet.remove(mp);
                mp.stop();
                mp.release();
            }
        });
        mpSet.add(mp);
        mp.start();
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
     * The buzz method vibrates the device according to predetermined pattern
     * to represent certain game events.
     * 
     * @param context : the context for the vibration to be played in
     *     (the calling Activity generally)
     * @param type    : A string with a value describing which buzz sequence
     *     to play 
     */
    public static void buzz(Context context, String type) { 	 
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
    
	/**
	 * playTimeout plays the timeout sound in the given context
	 * 
	 * @param context
	 */
	public static void playTimeout(Context context) {	
		play(context, R.raw.mp3_timeout);		
	}	

	/**
	 * playButton plays the button sound in the given context
	 * 
	 * @param context
	 */	
	public static void playButton(Context context) {		
		play(context, R.raw.mp3_button);		
	}

	/**
	 * playTicking plays the ticking sound in the given context
	 * 
	 * @param context
	 */	
	public static void playTicking(Context context) {		
		play(context, R.raw.mp3_clockticking);		
	}	
	
	/**
	 * playAplause plays the aplause sound in the given context
	 * 
	 * @param context
	 */	
	public static void playApplause(Context context) {		
		play(context, R.raw.mp3_applause);		
	}	
}
