package com.example.android;

import java.util.HashSet;

import android.content.Context;
import android.media.MediaPlayer;

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
