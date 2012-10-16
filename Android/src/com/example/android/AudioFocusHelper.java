package com.example.android;

import java.util.HashSet;

import android.content.Context;
import android.media.AudioManager;
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
 * The AudioFocusHelper class puts all audio focus related functionality in
 * a separate class that is not loaded if the running system cannot handle
 * Api 8 or higher (Froyo, 2.2).
 * 
  * @author  : Grupp02
  * @version : 2012-10-14, v0.5
  * @License : GPLv3
  * @Copyright :Copyright© 2012, Grupp02  
  */
public class AudioFocusHelper implements AudioManager.OnAudioFocusChangeListener
{
	
    AudioManager mAudioManager; 
    //The hash set for sounds specifically related to the given context
    private static HashSet<MediaPlayer> mpSet = new HashSet<MediaPlayer>();  
    //The context associated with this Audio Focus Helper
    Context context;  
    
    // other fields here, you'll probably hold a reference to an interface
    // that you can use to communicate the focus changes to your Service

    public AudioFocusHelper(Context context) {
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        this.context = context;
    }

    public boolean requestFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
            mAudioManager.requestAudioFocus(null, AudioManager.STREAM_RING,
                AudioManager.AUDIOFOCUS_GAIN);
    }

    public boolean requestQuietFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
        	mAudioManager.requestAudioFocus(null, AudioManager.STREAM_RING,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK); 
    }    
    
    public boolean abandonFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
            mAudioManager.abandonAudioFocus(this);
    }

    //@Override
    public void onAudioFocusChange(int focusChange) {
    	
		if(SoundPlayer.getSoundEnabled()) {	
	        for(MediaPlayer mp : mpSet) {
		        switch (focusChange) {
			        case AudioManager.AUDIOFOCUS_GAIN:
			            if (mp == null) {
			            	//It has already been played and destroyed by SoundPlayer
			            	mpSet.remove(mp);
			            }
			            else if (!mp.isPlaying())
			            {
			                mp.start();
			                mp.setVolume(SoundPlayer.getVolumeLeft(), SoundPlayer.getVolumeRight());
			            }
			            break;
			
			        case AudioManager.AUDIOFOCUS_LOSS:
			            // Lost focus for an unbounded amount of time: stop playback and release media player
			            if (mp.isPlaying()) mp.stop();
			            mp.release();
			            mp = null;
			            break;
			
			        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
			            // Lost focus for a short time, but we have to stop
			            // playback. We don't release the media player because playback
			            // is likely to resume
			            if (mp.isPlaying()) mp.pause();
			            break;
			
			        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
			            // Lost focus for a short time, but it's ok to keep playing
			            // at an attenuated level
			            if (mp.isPlaying()) mp.setVolume(0.1f, 0.1f);
			            break;
		        }
	        }
		}
    }


	/**
	 * playTimeout plays the timeout sound in the given context
	 */
	public void playTimeout() {	
		if(SoundPlayer.getSoundEnabled()) {
			MediaPlayer mp = SoundPlayer.play(context, R.raw.mp3_timeout);
			mpSet.add(mp);
		}
	}	

	/**
	 * playButton plays the button sound in the given context
	 */	
	public void playButton() {		
		if(SoundPlayer.getSoundEnabled()) {		
			MediaPlayer mp = SoundPlayer.play(context, R.raw.mp3_button);	
			mpSet.add(mp);	
		}
	}

	/**
	 * playTicking plays the ticking sound in the given context
	 */	
	public void playTicking() {	
		if(SoundPlayer.getSoundEnabled()) {			
			MediaPlayer mp = SoundPlayer.play(context, R.raw.mp3_clockticking);		
			mpSet.add(mp);		
		}
	}	
	
	/**
	 * playApplause plays the applause sound in the given context
	 */	
	public void playApplause() {
		if (SoundPlayer.getVibrationEnabled()) SoundPlayer.buzz( context, "applause" );		
		if (SoundPlayer.getSoundEnabled()) {	
			MediaPlayer mp = SoundPlayer.play(context, R.raw.mp3_applause);	
			mpSet.add(mp);		
		}
	}
	
	/**
	 * playRightChoice
	 */
	public void playRightChoice() {
		//Use the right answer pattern (short vibration, pause, short vibration)
		if (SoundPlayer.getVibrationEnabled()) SoundPlayer.buzz( context, "right" );		
        //Play the right answer sound		
		if(SoundPlayer.getSoundEnabled()) {	
			MediaPlayer mp = SoundPlayer.play(context, R.raw.mp3_right);
			mpSet.add(mp);			
		}
	}

	/**
	 * playWrongChoice
	 */
	public void playWrongChoice() {
		//Use the medium length buzz for a wrong answer
		if (SoundPlayer.getVibrationEnabled()) SoundPlayer.buzz( context, "wrong" );
        //Play the wrong answer sound	
		if(SoundPlayer.getSoundEnabled()) {			
		    MediaPlayer mp = SoundPlayer.play(context, R.raw.mp3_wrong);
		    mpSet.add(mp);			
		}
	}	
}
