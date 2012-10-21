package com.example.android;

import java.util.HashSet;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * 	 Copyright© 2012, Grupp02
 * 
 *     This file is part of Handalfabetet.
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
 * The AudioFocusHelper class puts all audio focus related functionality in a
 * separate class that is not loaded if the running system cannot handle Api 8
 * or higher (Froyo, 2.2). It is used as a middle-man between the context and
 * the SoundPlayer. It is based upon code from the Android training page
 * http://developer.android.com/training/managing-audio/audio-focus.html
 * 
 * For course and licensing information on the sounds used, see SoundPlayer.
 * 
 * @author : Grupp02
 * @version : 2012-10-21, v1.0
 */
public class AudioFocusHelper extends Service implements
	AudioManager.OnAudioFocusChangeListener {

    // The audio manager
    AudioManager mAudioManager;
    // The hash set for sounds specifically related to the given context
    private static HashSet<MediaPlayer> mpSet = new HashSet<MediaPlayer>();
    // The context associated with this Audio Focus Helper
    Context context;

    /**
     * The constructor for AudioFocusHelper
     * 
     * @param context
     *            : the context (activity) the audio focus helper is associated
     *            with
     */
    public AudioFocusHelper(Context context) {
	mAudioManager = (AudioManager) context
		.getSystemService(Context.AUDIO_SERVICE);
	this.context = context;
    }

    /**
     * The requestFocus method is used when a sound is about to be played, to
     * ensure that the context has sound focus. The RING stream is used since
     * then the devices own hardware volume control will also function.
     * 
     * @return : true if focus was granted, otherwise false
     */
    public boolean requestFocus() {
	return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == mAudioManager
		.requestAudioFocus(null, AudioManager.STREAM_RING,
			AudioManager.AUDIOFOCUS_GAIN);
    }

    /**
     * The requestQuietFocus method is used most often when requestFocus, has
     * failed. It tries again to ensure that the context has sound focus by
     * lowering standards. It attempts to gain transient focus that allows quiet
     * use. The onAudioFocusChange will notice this change, and also lower the
     * play volume considerably.
     * 
     * @return : true if focus was granted, otherwise false
     */
    public boolean requestQuietFocus() {
	return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == mAudioManager
		.requestAudioFocus(null, AudioManager.STREAM_RING,
			AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
    }

    /**
     * This is called when a context needs to abandon all audio focus, to allow
     * other media to take over.
     * 
     * @return : true if focus was abandonned, otherwise false
     */
    public boolean abandonFocus() {
	return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == mAudioManager
		.abandonAudioFocus(this);
    }

    /**
     * The onAudioFocusChange method detects audio focus change (such as a phone
     * call starting or music being played) and changes audio behaviour as a
     * result.
     * 
     * @param focusChange
     *            : an integer representing the type of detected focus change
     */
    // @Override
    public void onAudioFocusChange(int focusChange) {

	// If sound is enabled
	if (SoundPlayer.getSoundEnabled()) {
	    // For each media player active for this context
	    for (MediaPlayer mp : mpSet) {
		// Check the form of focus change
		switch (focusChange) {
		// Full focus was regained, continue playing
		case AudioManager.AUDIOFOCUS_GAIN:
		    if (mp == null) {
			// It has already been played and destroyed by
			// SoundPlayer
			mpSet.remove(mp);
		    }
		    // If the media player is not playing, restart it at
		    // currently set
		    // volume
		    else if (!mp.isPlaying()) {
			mp.start();
			mp.setVolume(SoundPlayer.getVolumeLeft(),
				SoundPlayer.getVolumeRight());
		    }
		    break;

		// Audio focus was lost, stop all audio and release memory
		case AudioManager.AUDIOFOCUS_LOSS:
		    // Lost focus for an unbounded amount of time:
		    // stop playback and release media player
		    if (mp.isPlaying()) {
			mp.stop();
		    }
		    mp.release();
		    mp = null;
		    break;

		// Transient focus, pause all audio
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
		    // Lost focus for a short time, but we have to stop
		    // playback. We don't release the media player because
		    // playback
		    // is likely to resume
		    if (mp.isPlaying()) {
			mp.pause();
		    }
		    break;

		// Transient focus that allows quiet play volume
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
		    // Lost focus for a short time, but it's ok to keep playing
		    // at an attenuated level
		    if (mp.isPlaying()) {
			mp.setVolume(0.1f, 0.1f);
		    }
		    break;
		}
	    }
	}
    }

    /**
     * playTimeout plays the timeout sound in the given context.
     */
    public void playTimeout() {
	// If sound is enabled play the timeout sound
	if (SoundPlayer.getSoundEnabled()) {
	    MediaPlayer mp = SoundPlayer.play(context, R.raw.mp3_timeout);
	    mpSet.add(mp);
	}
    }

    /**
     * playButton plays the button sound in the given context-
     */
    public void playButton() {
	// If sound is enabled play the button sound
	if (SoundPlayer.getSoundEnabled()) {
	    MediaPlayer mp = SoundPlayer.play(context, R.raw.mp3_button);
	    mpSet.add(mp);
	}
    }

    /**
     * playTicking plays the ticking sound in the given context.
     */
    public void playTicking() {
	// If sound is enabled play the ticking sound
	if (SoundPlayer.getSoundEnabled()) {
	    MediaPlayer mp = SoundPlayer.play(context, R.raw.mp3_clockticking);
	    mpSet.add(mp);
	}
    }

    /**
     * playApplause plays the applause sound and vibration applause sequence in
     * the given context.
     */
    public void playApplause() {
	// If vibration is enabled play the applause buzzing sequence
	if (SoundPlayer.getVibrationEnabled()) {
	    SoundPlayer.buzz(context, "applause");
	}
	// If sound is enabled play the applause sound
	if (SoundPlayer.getSoundEnabled()) {
	    MediaPlayer mp = SoundPlayer.play(context, R.raw.mp3_applause);
	    mpSet.add(mp);
	}
    }

    /**
     * playRightChoice plays the right choice sound and vibration right answer
     * sequence in the given context.
     */
    public void playRightChoice() {
	// If vibration is enabled play the right answer buzzing sequence
	if (SoundPlayer.getVibrationEnabled()) {
	    // Use the right answer pattern (short vibration, pause, short
	    // vibration)
	    SoundPlayer.buzz(context, "right");
	}
	// Play the right answer sound
	if (SoundPlayer.getSoundEnabled()) {
	    MediaPlayer mp = SoundPlayer.play(context, R.raw.mp3_right);
	    mpSet.add(mp);
	}
    }

    /**
     * playWrongChoice plays the right choice sound and vibration right answer
     * sequence in the given context.
     */
    public void playWrongChoice() {
	// If vibration is enabled play the wrong answer buzzing sequence
	if (SoundPlayer.getVibrationEnabled()) {
	    // Use the medium length buzz for a wrong answer
	    SoundPlayer.buzz(context, "wrong");
	}
	// Play the wrong answer sound
	if (SoundPlayer.getSoundEnabled()) {
	    MediaPlayer mp = SoundPlayer.play(context, R.raw.mp3_wrong);
	    mpSet.add(mp);
	}
    }

    @Override
    /**
     * The onDestroy methods overrides onDestroy from Service, making sure that
     * MediaPlayer objects properly release their memory when the AudioFocusHelper
     * is destroyed.
     */
    public void onDestroy() {
	// For the set of media players, stop and release all
	for (MediaPlayer mp : mpSet) {
	    if (mp != null) {
		mp.stop();
		mp.release();
	    }
	}
	// Empty the media player set
	mpSet.clear();

	SoundPlayer.stop();
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
