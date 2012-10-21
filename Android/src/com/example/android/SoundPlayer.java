package com.example.android;

import java.io.IOException;
import java.util.HashSet;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;

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
 * The sources and licenses of the sound files used are:
 * 
 * The following sounds have this source and license:
 * 
 * mp3_button.mp3       = Button Sound 16
 * mp3_clockticking.mp3 = Clock Ticking Sound Effect 4
 * mp3_right.mp3        = Button Sound 37
 * mp3_timeout.mp3      = Button Sound 4 
 * mp3_wrong.mps        = Beep 10
 * 
 * http://http://www.soundjay.com/button-sounds-1.html
 * 
 * Copyright © 2012 SoundJay.com
 * Sound Effects
 * You are allowed to use the sounds free of charge and royalty free in your
 * projects (such as films, videos, games, presentations, animations, stage
 * plays, radio plays, audio books, apps) be it for commercial or non-commercial
 * purposes.
 * But you are NOT allowed to
 * - post the sounds (as sound effects or ringtones) on any website for others
 *   to download, copy or use
 * - use them as a raw material to create sound effects or ringtones that you
 *   will sell, distribute or offer for downloading
 * - sell, re-sell, license or re-license the sounds (as individual sound effects
 *   or as a sound effects library) to anyone else
 * - claim the sounds as yours
 * - link directly to individual sound files
 * - distribute the sounds in apps or computer programs that are clearly sound
 *   related in nature (such as sound machine, sound effect generator, ringtone
 *   maker, funny sounds app, sound therapy app, etc.) or in apps or computer
 *   programs that use the sounds as the program's sound resource library for
 *   other people's use (such as animation creator, digital book creator, song
 *   maker software, etc.). If you are developing such computer programs, contact
 *   us for licensing options.
 * If you use the sound effects, please consider giving us a credit and linking back
 * to us but it's not required.
 * 
 * 
 * The following sound has this source and license:
 * 
 * mp3_applause.mp3     = Small Crowd Applause by Yannick Lemieux
 * 
 * http://soundbible.com/royalty-free-sounds-8.html 
 * 
 * Attribution 3.0 License 
 * Creative Commons Attribution 3.0
 * Creative Commons Attribution 3.0 is one of many CC Audio types. Creative
 * Commons Attribution 3.0 put simply means you can use this audio but you
 * must attribute your work to this person. If the audio is from Mike Koenig
 * then you need to give him credit somewhere. That somewhere could be in the
 * credits, the cd cover, or a link to the sounds page from your site.
 */

/**
 * SoundPlayer is based on PlaySound code from
 * http://blog.endpoint.com/2011/03/api-gaps-android-mediaplayer-example.html
 * Though it has been altered. It is a class filled with primarily static
 * variables and methods that can be easily be called by all the activities in
 * handalfabet, or through AudioFocusHelper. It is built to handle many
 * simulataneous media players for each separate desired sound, and to hold all
 * information about enabled sound and volume. It also houses the vibrator, used
 * to make vibrations, along with information about enabled vibration. The
 * methods for different sounds are meant to house all such code in one place.
 * 
 * @author : Grupp02
 * @version : 2012-10-19, v1.0
 */
public class SoundPlayer extends Service {
    // The set of media players
    private static HashSet<MediaPlayer> mpSet = new HashSet<MediaPlayer>();
    // The debug flag for error messages
    private static boolean debug = false;

    // The path to package resources
    private final static String packagePath = "android.resource://com.example.android/";

    // volume/sound and vibration related variables
    private static float volumeRight = 1.0f;
    private static float volumeLeft = 1.0f;
    private static int currentVolume = 50;
    private static boolean soundEnabled = true;
    private static boolean vibrationEnabled = true;

    // Modified from example from http://android.konreu.com/developer-how-to/
    // vibration-examples-for-android-phone-development/
    // Values representing time in milliseconds
    private final static int right_buzz = 200; // A short pulse
    private final static int wrong_buzz = 500; // A medium pulse
    private final static int timeout_buzz = 1000; // A long pulse
    private final static int short_pause = 200; // A short pause

    // The pulse sequence for a right answer
    private final static long[] right = { 0, // Start immediately
	    right_buzz, short_pause, right_buzz };

    // The pulse sequence for applause (congratulations)
    private final static long[] applause = {
	    0, // Start immediately
	    right_buzz, short_pause, right_buzz, short_pause, right_buzz,
	    short_pause, right_buzz };

    /**
     * The play method plays a sound given the context and resource ID
     * 
     * @param context
     *            : the context for the sound to be played in (the calling
     *            Activity generally)
     * @param resID
     *            : the resource ID of the sound file
     * 
     * @return : the MediaPlayer created to play the new sound, or null
     */
    public static MediaPlayer play(Context context, int resId) {

	// If sound is enabled
	if (soundEnabled) {

	    try {

		MediaPlayer mp = new MediaPlayer();
		// Set up an audio stream of type STREAM_RING. This allows
		// the user to also use the phones own volume to raise and
		// lower volume levels.
		mp.setDataSource(context, Uri.parse(packagePath + resId));
		mp.setAudioStreamType(AudioManager.STREAM_RING);
		mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
		    // Should be with @Override, but oddly won't accept it
		    public void onCompletion(MediaPlayer mp) {
			mpSet.remove(mp);
			mp.stop();
			mp.release();
		    }
		});

		// Prepare the media player for use
		mp.prepare();

		// Add the media player to the set of active media players,
		// set the volume to the current level, and start the media
		// player
		mpSet.add(mp);
		mp.setVolume(volumeLeft, volumeRight);
		mp.start();

		// The media player is returned (useful for AudioFocusHelper)
		return mp;

	    } catch (IOException e) {
		if (debug) {
		    System.err.println("Error reading mp3 file.");
		}
		return null;
	    }
	} else { // Otherwise the sound is not enabled, return null
	    return null;
	}
    }

    /**
     * The stop method stops all ongoing sounds (and media players) and releases
     * their memory.
     */
    public static void stop() {
	// For the set of media players, stop and release all
	for (MediaPlayer mp : mpSet) {
	    if (mp != null) {
		mp.stop();
		mp.release();
	    }
	}
	// Empty the media player set
	mpSet.clear();
    }

    /**
     * The pause method pauses all ongoing sounds.
     */
    public static void pause() {
	// For the set of media players, pause all
	for (MediaPlayer mp : mpSet) {
	    if (mp != null) {
		mp.pause();
	    } else {
		// Don't keep null MediaPlayers in the set
		mpSet.remove(mp);
	    }
	}
    }

    /**
     * The resume method resumes all ongoing sounds.
     */
    public static void resume() {
	// For the set of media players, restart all
	for (MediaPlayer mp : mpSet) {
	    if (mp != null) {
		mp.start();
	    } else {
		// Don't keep null MediaPlayers in the set
		mpSet.remove(mp);
	    }
	}
    }

    /**
     * The lowerVolume method lowers the volume of all ongoing sounds. It is
     * synchronized to avoid concurrency problems, though none should be
     * possible.
     * 
     * @param volumeLeft
     *            : the left side volume as a float value
     * @param volumeRight
     *            : the right side volume as a float value
     */
    public synchronized static void setVolume(float volumeLeft,
	    float volumeRight) {
	// The Sound Player's left and right volume levels are altered.
	// This will affect all newly created sounds.
	SoundPlayer.volumeLeft = volumeLeft;
	SoundPlayer.volumeRight = volumeRight;

	// For every media player in the set, the current volume is altered.
	for (MediaPlayer mp : mpSet) {
	    if (mp != null) {
		mp.setVolume(volumeLeft, volumeRight);
	    } else {
		// Don't keep null MediaPlayers in the set
		mpSet.remove(mp);
	    }
	}
    }

    /**
     * The getVolumeLeft method returns the left volume value of the sound
     * player (used to create new sounds).
     * 
     * @return : Sound Player's left volume as a float value
     */
    public static float getVolumeLeft() {
	return volumeLeft;
    }

    /**
     * The getVolumeRight method returns the right volume value of the sound
     * player (used to create new sounds).
     * 
     * @return : Sound Player's right volume as a float value
     */
    public static float getVolumeRight() {
	return volumeRight;
    }

    /**
     * The buzz method vibrates the device according to predetermined pattern to
     * represent certain game events.
     * 
     * @param context
     *            : the context for the vibration to be played in (the calling
     *            Activity generally)
     * @param type
     *            : A string with a value describing which buzz sequence to play
     */
    public static void buzz(Context context, String type) {
	// If vibration is enabled
	if (vibrationEnabled) {
	    // Create the vibrator
	    Vibrator vibrator = (Vibrator) context
		    .getSystemService(Context.VIBRATOR_SERVICE);

	    // There are four valid buzz sequences defined,
	    // right, wrong, applause, and timeout.
	    // The play and then end.
	    if (type.equals("right")) {
		vibrator.vibrate(right, -1);
	    } else if (type.equals("wrong")) {
		vibrator.vibrate(wrong_buzz);
	    } else if (type.equals("applause")) {
		vibrator.vibrate(applause, -1);
	    } else if (type.equals("timeout")) {
		vibrator.vibrate(timeout_buzz);
	    } else {
		if (debug) {
		    System.err.println("Invalid buzz type!");
		}
	    }
	}
    }

    /**
     * playTimeout plays the timeout sound in the given context.
     * 
     * @param context
     *            : the context for the sound to be played in (the calling
     *            Activity generally)
     */
    public static void playTimeout(Context context) {
	// If sound is enabled play the timeout sound
	if (soundEnabled) {
	    play(context, R.raw.mp3_timeout);
	}
    }

    /**
     * playButton plays the button sound in the given context.
     * 
     * @param context
     *            : the context for the sound to be played in (the calling
     *            Activity generally)
     */
    public static void playButton(Context context) {
	// If sound is enabled play the button sound
	if (soundEnabled) {
	    play(context, R.raw.mp3_button);
	}
    }

    /**
     * playTicking plays the ticking sound in the given context.
     * 
     * @param context
     *            : the context for the sound to be played in (the calling
     *            Activity generally)
     */
    public static void playTicking(Context context) {
	// If sound is enabled play the ticking sound
	if (soundEnabled) {
	    play(context, R.raw.mp3_clockticking);
	}
    }

    /**
     * playApplause plays the applause sound and vibration applause sequence in
     * the given context.
     * 
     * @param context
     *            : the context for the sound to be played in (the calling
     *            Activity generally)
     */
    public static void playApplause(Context context) {
	// If vibration is enabled play the applause buzzing sequence
	if (vibrationEnabled) {
	    SoundPlayer.buzz(context, "applause");
	}
	// If sound is enabled play the applause sound
	if (soundEnabled) {
	    play(context, R.raw.mp3_applause);
	}
    }

    /**
     * playRightChoice plays the right choice sound and vibration right answer
     * sequence in the given context.
     * 
     * @param context
     *            : the context for the sound to be played in (the calling
     *            Activity generally)
     */
    public static void playRightChoice(Context context) {
	// If vibration is enabled play the right answer buzzing sequence
	if (vibrationEnabled) {
	    buzz(context, "right");
	}
	// If sound is enabled play the right answer sound
	if (soundEnabled) {
	    play(context, R.raw.mp3_right);
	}
    }

    /**
     * playWrongChoice plays the right choice sound and vibration right answer
     * sequence in the given context.
     * 
     * @param context
     *            : the context for the sound to be played in (the calling
     *            Activity generally)
     */
    public static void playWrongChoice(Context context) {
	// If vibration is enabled play the wrong answer buzzing sequence
	if (vibrationEnabled) {
	    buzz(context, "wrong");
	}
	// If sound is enabled play the wrong answer sound
	if (soundEnabled) {
	    play(context, R.raw.mp3_wrong);
	}
    }

    /**
     * Returns if the sound is currently enabled. It is synchronized to avoid
     * concurrency problems, though none should be possible.
     * 
     * @return : true if sound is enabled, otherwise false
     */
    public synchronized static boolean getSoundEnabled() {
	if (soundEnabled) {
	    return true;
	}
	return false;
    }

    /**
     * Sets if the sound is currently enabled. It is synchronized to avoid
     * concurrency problems, though none should be possible.
     * 
     * @param enabled
     *            : true to enable sound, false to disable it
     */
    public synchronized static void setSoundEnabled(boolean enabled) {
	soundEnabled = enabled;
    }

    /**
     * Returns if vibrations are currently enabled. It is synchronized to avoid
     * concurrency problems, though none should be possible.
     * 
     * @return : true if vibrations are enabled, otherwise false
     */
    public synchronized static boolean getVibrationEnabled() {
	if (vibrationEnabled) {
	    return true;
	}
	return false;
    }

    /**
     * Sets if vibrations are currently enabled. It is synchronized to avoid
     * concurrency problems, though none should be possible.
     * 
     * @param enabled
     *            : true to enable vibrations, false to disable it
     */
    public synchronized static void setVibrationEnabled(boolean enabled) {
	vibrationEnabled = enabled;
    }

    /**
     * Returns an integer representation of current volume. Sets if vibrations
     * are currently enabled. It is synchronized to avoid concurrency problems,
     * though none should be possible.
     * 
     * @return : an integer representing the current volume as a single value
     */
    public synchronized static int getCurrentVolume() {
	return currentVolume;
    }

    /**
     * Sets the currentVolume variable. It is synchronized to avoid concurrency
     * problems, though none should be possible.
     * 
     * @param volume
     *            : an integer representation of volume
     */
    public synchronized static void setCurrentVolume(int volume) {
	currentVolume = volume;
    }

    @Override
    /**
     * The onDestroy methods overrides onDestroy from Service, making sure that
     * MediaPlayer objects properly release their memory when the SoundPlayer
     * is destroyed.
     * I don't think this is required since Static calls only are made to
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
