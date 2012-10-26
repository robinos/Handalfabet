package com.example.android;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import android.os.CountDownTimer;
import android.util.Log;

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
 * The GameLogic class is meant to hold all game logic for Game.
 * 
 * @author : Grupp02
 * @version : 2012-10-19, v1.0
 */
public class GameLogic {
    // A variable for debugging purposes
    private boolean debug;

    // The Game
    private Game game;

    // Difficulty Level
    private int diffLevel;
    private int numLetters;

    // Number of allowed rounds
    private int playRoundCounter;
    private final int maxRounds = 10;

    // The count down timer and time variables
    private CountDownTimer countDownTimer;
    private int timeCount = 0;
    // maxCount is the maximum value of time count, which is 10s
    private final int maxCountLevel1 = 10;
    private final int maxCountLevel2 = 5;
    private final int maxCountLevel3 = 2;
    private int timeLimit;
    private final int tickTime = 1000; // 1000 ms = 1s

    // timeLimit needs to add extra time at the beginning and end for the bar
    private final int maxTimeLimit1 = 12000; // 12000 ms = 12s (needed instead
					     // of 10s)
    private final int maxTimeLimit2 = 7000;
    private final int maxTimeLimit3 = 4000;

    // statistic variables
    private int totalTime;
    private int numCorrect;

    // score variables
    private int totalScore;
    private int roundScore;

    // Random generator
    Random randomGen;

    // file reading variables
    private Scanner fileScanner;
    private final String level2 = "level2.txt";
    private final String level3 = "level3.txt";

    // Answer string logic variables
    private String correctSign;
    private ArrayList<Integer> answerForButtons;
    private ArrayList<String> usedSignList;

    // List of letter or word strings
    private ArrayList<String> wordList;

    // Default button and picture strings
    private String firstButtonString = "";
    private String secondButtonString = "";
    private String thirdButtonString = "";
    private String first_picture = "blank";
    private String second_picture = "blank";
    private String third_picture = "blank";
    
    // private final int minus_one = -1;
    private final int zero = 0;
    private final int one = 1;
    private final int two = 2;
    private final int three = 3;
    private final int difficultyOne = 1;
    private final int difficultyTwo = 2;
    private final int difficultyThree = 3;
    private final int oneLetter = 1;
    private final int twoLetters = 2;
    private final int threeLetters = 3;
    private final int factorTwo = 2;
    private final int factorFive = 5;
    private final int factorTen = 10;
    private final int factorTwenty = 20;
    private final int factorFifty = 50;

    /**
     * The GameLogic Constructor
     * 
     * @param diffLevel
     *            : The difficulty level of the game
     * @param game
     *            : The GameActivity object
     */
    public GameLogic(int diffLevel, int numLetters, Game game) {
	// debugging mode
	debug = false;

	// Number of rounds one may play
	playRoundCounter = maxRounds;

	// Initialize start scores and statistics to 0
	totalTime = zero;
	numCorrect = zero;
	totalScore = zero;
	roundScore = zero;

	// Initialize arrays
	answerForButtons = new ArrayList<Integer>();
	usedSignList = new ArrayList<String>();

	// Initialize lists of letter or word strings
	wordList = new ArrayList<String>();

	// number of letters between 1 and 3 (Default 1)
	// if( numLetters > 0 && numLetters < 4 ) {
	if (numLetters >= oneLetter && numLetters <= threeLetters) {
	    this.numLetters = numLetters;
	} else {
	    numLetters = oneLetter;

	    if (debug) {
		System.err.println("Error choosing letter number.");
	    }
	}

	// number of letters between 1 and 3 (Default 1)
	// if( diffLevel > 0 && diffLevel < 4 ) {
	if (diffLevel >= difficultyOne && diffLevel <= difficultyThree) {
	    this.diffLevel = diffLevel;
	} else {
	    diffLevel = difficultyOne;

	    if (debug) {
		System.err.println("Error choosing difficulty.");
	    }
	}

	// Max seconds in a round (this decrements down to 0 under a round)
	if (diffLevel == difficultyTwo) {
	    timeCount = maxCountLevel2;
	    timeLimit = maxTimeLimit2;
	} else if (diffLevel == difficultyThree) {
	    timeCount = maxCountLevel3;
	    timeLimit = maxTimeLimit3;
	} else { // difficulty 1
	    timeCount = maxCountLevel1;
	    timeLimit = maxTimeLimit1;
	}

	// The Game object (for updating the timer bar)
	this.game = game;

	// Initialize random generator
	randomGen = new Random();

	// Sets the list of words or letters
	setWordList();

	// Initializes and starts the count down timer
	startTimer();
    }

    /**
     * Sets the wordList array depending on difficulty level.
     */
    private void setWordList() {

	String alphabet = "a b c d e f g h i j k l m n o p q r e s t u v w z å ä ö";

	// test/default case 60 words
	String[] secondList = new String[] { "av", "ax", "be", "bo", "by",
		"de", "du", "då", "dö", "ed", "ej", "ek", "el", "en", "er",
		"få", "ge", "gå", "ha", "hy", "hö", "in", "is", "ja", "ju",
		"ko", "kö", "le", "lä", "må", "ni", "nu", "ny", "nå", "oj",
		"om", "på", "ro", "rå", "sa", "se", "sy", "så", "ta", "te",
		"tå", "ur", "ut", "uv", "vi", "vy", "yr", "ål", "år", "år",
		"åt", "än", "är", "öl", "öm" };

	// test/default case 120 words
	String[] thirdList = new String[] { "all", "apa", "att", "bad", "bar",
		"bil", "bio", "blå", "bok", "bra", "båt", "bör", "dag", "dal",
		"den", "det", "dig", "din", "dom", "dra", "dyr", "döv", "eld",
		"ett", "far", "fel", "fly", "fot", "fru", "får", "för", "gav",
		"get", "god", "gud", "gul", "gås", "haj", "han", "hat", "hav",
		"hem", "het", "hit", "hos", "hot", "hop", "hud", "hur", "hus",
		"hår", "här", "hög", "jag", "jul", "kam", "kex", "klä", "knä",
		"kul", "kyl", "kär", "köa", "kök", "köp", "kör", "led", "lek",
		"liv", "lov", "lår", "lån", "lök", "lön", "man", "med", "men",
		"mer", "mod", "mor", "mus", "myt", "mål", "ned", "nej", "när",
		"näs", "och", "ont", "oro", "oss", "ost", "par", "ren", "rum",
		"råd", "rök", "ser", "sig", "sin", "sko", "små", "son", "syn",
		"tal", "toa", "tog", "tom", "tre", "tur", "två", "ugn", "ung",
		"vem", "vid", "åka", "ägg", "öga", "öka", "öst" };

	// Initialize word list based on difficulty level
	if (numLetters == threeLetters) {
	    // Read in word list from file
	    try {
		readFile(level3);
	    } catch (IOException ie) {
		if (debug) {
		    System.err.println("IOException : Opening level3 file");
		}
		{
		    Collections.addAll(wordList, thirdList); // default
		}
	    }

	    // if something else went wrong,
	    // like an empty file, also use the default list
	    if (wordList.size() <= zero) {
		if (debug) {
		    System.err.println("Error : Empty level3 file");
		}
		Collections.addAll(wordList, thirdList); // default
	    }
	} else if (numLetters == twoLetters) {
	    // Read in word list from file
	    try {
		readFile(level2);
	    } catch (IOException ie) {
		if (debug) {
		    System.err.println("IOException : Opening level2 file");
		}
		{
		    Collections.addAll(wordList, secondList); // default
		}
	    }

	    // if something else went wrong, like an empty file,
	    // also use the default list
	    if (wordList.size() <= zero) {
		if (debug) {
		    System.err.println("Error : Empty level2 file");
		}
		Collections.addAll(wordList, secondList); // default
	    }
	} else { // difficulty level 1
	    String[] stringArray = alphabet.split(" ");
	    Collections.addAll(wordList, stringArray);
	}
    }

    /**
     * The readFile method reads from a text file, expecting a single word per
     * line. Each word is read into the ArrayList wordList, which is an array
     * list of strings.
     * 
     * @param filename
     *            : a String representing the filename to be read from
     */
    private void readFile(String filename) throws IOException {

	// initialize the file stream scanner
	File listFile = new File(filename);
	fileScanner = new Scanner(listFile);

	// Read the file into wordList.
	// The file is a simple text file. One word per line.
	while (fileScanner.hasNextLine()) {
	    String word = fileScanner.nextLine();
	    wordList.add(word);
	}
    }

    /**
     * Counts the current score.
     */
    public void scoreCounter() {

	int score;
	int time;

	// timeCount is the number of seconds left on the counter
	// when a correct answer was given. +1 is because timeCount
	// is actually always at least 1 less than it should be due to display
	// issues.
	if (diffLevel == difficultyThree) {
	    time = one + maxCountLevel3 - timeCount; // 2 seconds to answer
	    score = (one + timeCount) * factorFive * diffLevel;
	} else if (diffLevel == difficultyTwo) {
	    time = one + maxCountLevel2 - timeCount; // 5 seconds to answer
	    score = (one + timeCount) * factorTwo * diffLevel;
	} else {
	    time = one + maxCountLevel1 - timeCount; // 10 seconds to answer
	    score = (one + timeCount); // - time taken
	}

	//Bugfix for score when time has run out
	if(timeCount <= zero) {
		roundScore = zero;
	} else if (score > zero) {
	    roundScore = score;
	} else {
	    roundScore = zero;
	}

	if (time > zero) {
	    totalTime += time;
	}

	totalScore += roundScore;
	numCorrect++;
    }

    /**
     * The getTotalScore method returns the total score.
     * 
     * @return : an integer representing the total score
     */
    public int getTotalScore() {
	return totalScore;
    }

    /**
     * Sets the total score after a restore. 
     */
    public void setTotalScore(int score) {
    	this.totalScore = score;
    }    
    
    /**
     * The getRoundScore method returns the round score.
     * 
     * @return : an integer representing the round score
     */
    public int getRoundScore() {
	return roundScore;
    }
    
    /**
     * Sets the round score after a restore. 
     */
    public void setRoundScore(int score) {
    	this.roundScore = score;
    }
    
    /**
     * The clearRoundScore method clears (sets to 0) the round score.
     */
    public void clearRoundScore() {
	roundScore = zero;
    }

    /**
     * Sets the difficulty level after a restore. 
     */
    public void setDiffLevel(int diff) {
    	this.diffLevel = diff;
    }     
    
    /**
     * Sets the number of letters after a restore. 
     */
    public void setNumLetters(int letters) {
    	this.numLetters = letters;
    }    
    
    /**
     * The getCountDownTimer method returns the count down timer. object
     * 
     * @return : the CountDownTimer object
     */
    public CountDownTimer getCountDownTimer() {
	return countDownTimer;
    }

    /**
     * Decrements game rounds and determines how many game rounds are left
     * 
     * @return : true if the game rounds has reached 10 rounds
     */
    public boolean countDownRounds() {
	playRoundCounter--;
	if (playRoundCounter == zero) {
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * Gets the round counter before a save. 
     */
    public int getRoundCounter() {
    	return playRoundCounter;
    } 
    
    /**
     * Sets the round counter after a restore. 
     */
    public void setRoundCounter(int counter) {
    	this.playRoundCounter = counter;
    }     
    
    /**
     * Resets time count (time left) and time limit (max time) to max.
     * Time limit is important to reset is cases when the application
     * has been interrupted and needed to continue.  This also requires
     * making a 'new' timer with the new time limit.
     * 
     * @param max
     *            : an integer representing max time left
     */
    public void resetTimeCount() {
    countDownTimer.cancel();    	
	if (diffLevel == difficultyThree) {
	    timeCount = maxCountLevel3;
	    timeLimit = maxTimeLimit3;	    
	} else if (diffLevel == difficultyTwo) {
	    timeCount = maxCountLevel2;
	    timeLimit = maxTimeLimit2;	    
	} else {
	    timeCount = maxCountLevel1;
	    timeLimit = maxTimeLimit1;	    
	}
    countDownTimer.start();	
    }

    /**
     * The getAverageTime method returns the average answering time at the end
     * of 10 rounds.
     * 
     * @return : an integer representing the round score
     */
    public int getAverageTime() {
	if (diffLevel == difficultyThree) {
	    return (totalTime / numCorrect);
	} else if (diffLevel == difficultyTwo) {
	    return (totalTime / numCorrect);
	} else {
	    return (totalTime / numCorrect);
	}
    }

    /**
     * Gets the total time before a save. 
     */
    public int getTotalTime() {
    	return totalTime;
    } 
    
    /**
     * Sets the total time after a restore. 
     */
    public void setTotalTime(int total) {
    	this.totalTime = total;
    }    
    
    /**
     * The getNumCorrect method returns the number of correct answers after 10
     * rounds.
     * 
     * @return : an integer representing the round score
     */
    public int getNumCorrect() {
	return numCorrect;
    }

    /**
     * Sets the number correct after a restore. 
     */
    public void setNumCorrect(int correct) {
    	this.numCorrect = correct;
    }    
    
    /**
     * Returns the number (1, 2, or 3) of the button with the correct answer.
     * 
     * @return : an integer value of 1, 2, or 3 representing a correct button
     */
    public int getCorrectButton() {
	if (correctSign.equals(firstButtonString)) {
	    return one;
	} else if (correctSign.equals(secondButtonString)) {
	    return two;
	} else if (correctSign.equals(thirdButtonString)) {
	    return three;
	} else {
	    if (debug) {
		System.err.println("Invalid correct sign.");
	    }
	    return one;
	}
    }

    /**
     * Returns the random letter(s) for button 1
     * 
     * return : the random letter(s) for button 1 as a String
     */
    public String getFirstButtonString() {
	return firstButtonString;
    }

    /**
     * Sets the first button string after a restore. 
     */
    public void setFirstButtonString(String buttonWord) {
    	this.firstButtonString = buttonWord;
    }    
    
    /**
     * Returns the random letter(s) for button 2
     * 
     * return : the random letter(s) for button 2 as a String
     */
    public String getSecondButtonString() {
	return secondButtonString;
    }

    /**
     * Sets the second button string after a restore. 
     */
    public void setSecondButtonString(String buttonWord) {
    	this.secondButtonString = buttonWord;
    }    
    
    /**
     * Returns the random letter(s) for button 3
     * 
     * return : the random letter(s) for button 3 as a String
     */
    public String getThirdButtonString() {
	return thirdButtonString;
    }

    /**
     * Sets the third button string after a restore. 
     */
    public void setThirdButtonString(String buttonWord) {
    	this.thirdButtonString = buttonWord;
    }     
    
    /**
     * Returns the picture string for the first hand sign
     * 
     * return : the String representing the hand sign
     */
    public String getFirstPicture() {
	return first_picture;
    }

    /**
     * Sets the first picture string after a restore. 
     */
    public void setFirstPicture(String picture) {
    	this.first_picture = picture;
    }    
    
    /**
     * Returns the picture string for the second hand sign
     * 
     * return : the String representing the hand sign
     */
    public String getSecondPicture() {
	return second_picture;
    }

    /**
     * Sets the second picture string after a restore. 
     */
    public void setSecondPicture(String picture) {
    	this.second_picture = picture;
    }    
    
    /**
     * Returns the picture string for the third hand sign
     * 
     * return : the String representing the hand sign
     */
    public String getThirdPicture() {
	return third_picture;
    }

    /**
     * Sets the third picture string after a restore. 
     */
    public void setThirdPicture(String picture) {
    	this.third_picture = picture;
    }    

    /**
     * Gets the correct choice string before a save. 
     */
    public String getCorrectChoice() {
    	return correctSign;
    }    
    
    /**
     * Sets the correct choice string after a restore. 
     */
    public void setCorrectChoice(String choice) {
    	this.correctSign = choice;
    } 
    
    /**
     * Returns random letters/words for the buttons and the correct letter/word
     * for the shown sign.
     */
    public void determineChoices() {
	// Re-initialize the random generator, to increase randomness
	// randomGen = new Random();
	String str;

	while (true) {
	    // Randomize 3 different words/letters by position number,
	    // one of which is the right answer (into answerForButtons)
	    randomizeWordsForAnswerButtons(randomNumber());
	    // The first number in answerForButtons is the correct one
	    str = randomWord(answerForButtons.get(zero));
	    correctSign = str;
	    
	    if (putUsedSignsInArray(str)) {
		Log.e(">>>>>>>", str);
		break;
	    } else {
		answerForButtons.clear();
	    }
	}

	if (numLetters == threeLetters) {
	    first_picture = Character.toString(str.charAt(zero));
	    second_picture = Character.toString(str.charAt(one));
	    third_picture = Character.toString(str.charAt(two));
	} else if (numLetters == twoLetters) {
	    first_picture = Character.toString(str.charAt(zero));
	    second_picture = Character.toString(str.charAt(one));
	} else {
	    first_picture = str;
	}

	int y = randomGen.nextInt(three);

	str = randomWord(answerForButtons.get(y));
	firstButtonString = str;
	answerForButtons.remove(y);

	y = randomGen.nextInt(two);
	str = randomWord(answerForButtons.get(y));
	secondButtonString = str;
	answerForButtons.remove(y);

	str = randomWord(answerForButtons.get(zero));
	thirdButtonString = str;

	answerForButtons.clear();
    }

    /**
     * Checks if a sign has already been used previously
     * 
     * @param signToCheck
     * @return A boolean if the words exits or not
     */
    private boolean putUsedSignsInArray(String signToCheck) {

	if (usedSignList.contains(signToCheck)) {
	    return false;
	} else {
	    usedSignList.add(signToCheck);
	    return true;
	}
    }

    /**
     * Randomize a letter from the alphabet or a word from a word list Level 1 -
     * randomize a letter based on alphabet position
     * 
     * @param position
     *            : the position of a letter in the alphabet or a word in the
     *            word list
     * @return : a letter or word as a String
     */
    public String randomWord(int position) {
	// if(diffLevel > 1) {
	// }
	// else {
	// For Level 1 - a letter only
	String randomLetter;
	String name;

	// Choose a letter from the alphabet based on the given position
	if (debug) {
	    randomLetter = "abcdefghijklmnopqrestuvwz";
	} else {
	    randomLetter = wordList.get(position);
	}

	if (debug) {
	    name = Character.toString(randomLetter.charAt(position));
	} else {
	    name = randomLetter;
	}

	// Debug help
	if (name == null) {
	    System.err.println("No letter at given position.");
	    name = "err";
	}
	// }

	return name;
    }

    /**
     * Randomizes a number between 0-25 the represent number of letters in the
     * alphabet
     * 
     * @return number
     */
    public int randomNumber() {
	int position = randomGen.nextInt(wordList.size() - 1);
	return position;
    }

    /**
     * Randomizes position for letters/words for the answer buttons
     * 
     * @param the
     *            position in the alphabet for correct answer
     */
    private void randomizeWordsForAnswerButtons(int x) {
	for (int y = zero; y < three; y++) {
	    if (!answerForButtons.contains(x)) {
		answerForButtons.add(x);
		x = randomNumber();
	    } else {
		x = randomNumber();
		y--;
	    }
	}
    }

    /**
     * Gets the time count before a save.
     */
    public int getTimeCount() {
    	return timeCount;
    }
    
    /**
     * Sets the time count after a restore. 
     */
    public void setTimeCount(int count) {
    	this.timeCount = count;
    }    
    
    /**
     * Gets the time limit before a save.
     */    
    public int getTimeLimit() {
    	return timeLimit;
    } 
    
    /**
     * Sets the time limit after a restore.
     */
    public void setTimeLimit(int limit) {
    	this.timeLimit = limit;
    }
    
    /**
     * The restartTimer method restarts the count down timer
     * when after the application has been interrupted.
     */
    public void restartTimer() {
    	countDownTimer.cancel();
    	timeLimit = timeLimit - timeCount*tickTime;
    	countDownTimer.start();
    }
    
    
    /**
     * The startTimer method starts the count down timer for making a choice in
     * the game. The variable timeCount is even used to determine bonus points
     * for quick answers in the scoreCounter() method.
     */
    public void startTimer() {
	countDownTimer = new CountDownTimer(timeLimit, tickTime) {
	    private int factor;

	    // a long is required by onTick, timeLeft is not used
	    @Override
	    public void onTick(long timeLeft) {

		if (diffLevel == difficultyTwo) {
		    factor = factorTwenty;
		} else if (diffLevel == difficultyThree) {
		    factor = factorFifty;
		} else {
		    factor = factorTen; // Diff 1
		}

		// The progress bar goes from 100 to 0 while timeCount
		// is 10 to 0, so *factor for display
		game.getTimerBar().setProgress(timeCount * factor);

		// Stops the timeCount from going beyond zero time.
		if (timeCount > zero) {
		    timeCount--;
		}
	    }

	    @Override
	    public void onFinish() {
		// on finish bonus points are -1, because timeCount is
		// always 1 point lower than it 'should be' due to display
		// issues
		SoundPlayer.stop();
		SoundPlayer.playTimeout(game);
		SoundPlayer.buzz(game, "timeout");
		timeCount = zero;
	    }
	}.start();
    }
}
