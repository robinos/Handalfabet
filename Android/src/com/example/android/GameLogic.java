package com.example.android;

import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

/**
 * The GameLogic class is meant to hold all game logic for Game.
 * 
 * @author  : Grupp02
 * @version : 2012-09-28, v0.3
 *
 */
public class GameLogic
{
	 //A variable for debugging purposes
 	 private boolean debug;
 	 	
 	 //Difficulty Level
 	 private int diffLevel;	
	 
	 //Number of allowed rounds
	 private int playRoundCounter; 
	 
	 //Random generator
	 Random randomGen;
	 
	 //Answer string logic variables
	 private String correctSign;	 
	 private ArrayList<Integer> answerForButtons;
	 private ArrayList<String> usedSignList;
	 
	 //List of letter or word strings	 
	 private ArrayList<String> wordList;	
	 
	 //Default button and picture strings
	 private String firstButtonString = "";
	 private String secondButtonString = "";
	 private String thirdButtonString = "";	
	 private String picture = "";		 
	 
	/**
	 * The GameLogic Constructor
	 */
	public GameLogic(int diffLevel) {
		//debugging mode
		debug = true;		
	    
	    playRoundCounter = 10;  //Number of rounds one may play
	    
	    //Initialize arrays
	    answerForButtons = new ArrayList<Integer>();
	    usedSignList = new ArrayList<String>();
	    
	    //Initialize lists of letter or word strings	    
	    wordList = new ArrayList<String>();	    
	    
		//Difficulty Level between 1 and 3 (Default 1)
		if(diffLevel > 0 && diffLevel < 4)
		    this.diffLevel = diffLevel;
		else {
			diffLevel = 1;
			
			if(debug) {
				System.err.println("Error choosing difficulty.");
			}
		}	   
	    
	    //Initalize random generator
	    randomGen = new Random();
	    
	    //Sets the list of words or letters
	    setWordList();    
	}
	
	/**
	 * Sets the wordList array depending on difficulty level.
	 */
	private void setWordList() {
		
		//Initialize word list based on difficulty level
	    if(diffLevel == 3) {
	    	
	    }
	    else if (diffLevel == 2) {
	    	
	    }
	    else { //difficulty level 1
	    	String alphabet = "a b c d e f g h i j k l m n o p q r e s t u v w z å ä ö";
	    	String[] stringArray = alphabet.split(" ");
	    	Collections.addAll(wordList, stringArray);
	    }
	}	 
	 
	 /**
	  * Counts the how many game rounds are left
	  * 
	  * @return : true if the game rounds has reached 10 rounds
	  */
	 public boolean countDownRounds () {
		 playRoundCounter--;
		 if(playRoundCounter == 0)
			 return true;
		 else
			 return false;
	 }	
	
	/**
	 * Returns the number (1, 2, or 3) of the button with the
	 * correct answer.
	 * 
	 * @return : an integer value of 1, 2, or 3 representing a
	 *    correct button
	 */
	public int getCorrectButton() {
		if(correctSign.equals(firstButtonString))
			return 1;
		else if(correctSign.equals(secondButtonString))
			return 2;		
		else if(correctSign.equals(secondButtonString))
			return 3;		
		else {
			if( debug ) {
			    System.err.println("Invalid correct sign.");
			}
			return 1;
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
	 * Returns the random letter(s) for button 2
	 * 
	 * return : the random letter(s) for button 2 as a String
	 */
	public String getSecondButtonString() {
	    return secondButtonString;	
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
	 * Returns the picture string for the hand sign
	 * 
	 * return : the String representing the hand sign
	 */
	public String getPicture() {
	    return picture;	
	}	
	
	/**
	 * Returns random letters/words for the buttons and
	 * the correct letter/word for the shown sign.
	 */
	public void determineChoices(){
		//Re-initialise the random generator, to increase randomness
		//randomGen = new Random();
        String str;
		
		while(true) {			
			//Randomize 3 different words/letters by position number,
			//one of which is the right answer (into answerForButtons)
			randomizeLettersForAnswerButtons(randomNumber());
			//The first number in answerForButtons is the correct one
			str = randomWord(answerForButtons.get(0));
			correctSign = str;
			
			if( putUsedSignsInArray(str) ){
				Log.e(">>>>>>>", str);
				break;
			}
			else
		        answerForButtons.clear();
		}
		
		picture = str;
		
		int y = randomGen.nextInt(3); 

		str = randomWord(answerForButtons.get(y));
		firstButtonString = str;
		answerForButtons.remove(y);
		
		y = randomGen.nextInt(2);
		str = randomWord(answerForButtons.get(y));
		secondButtonString = str;
		answerForButtons.remove(y);
		
		str = randomWord(answerForButtons.get(0));
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
		
		if(usedSignList.contains(signToCheck))
			return false;
		else{
			usedSignList.add(signToCheck);
			return true;
		}
	}

	
	/**
	 * Randomize a letter from the alphabet or a word from a word list
	 * Level 1 - randomize a letter based on alphabet position 
	 * 
	 * @param position : the position of a letter in the alphabet or a
	 *     word in the word list
	 * @return : a letter or word as a String
	 */
	public String randomWord(int position){
		//if(diffLevel > 1) {
		//}	
		//else {
		    //For Level 1 - a letter only
		    String randomLetter;
		
		    //Choose a letter from the alphabet based on the given position
		    if(debug) randomLetter = "abcdefghijklmnopqrestuvwz";
		    else randomLetter = wordList.get(position);
		    		
		    String name = Character.toString(randomLetter.charAt(position));
		
		    //Debug help
		    if(name == null) {
			    System.err.println("No letter at given position.");
			    name = "err";
		    }
		//}
		
		return name;
	}
	
	/**
	 * Randomizes a number between 0-25 the represent number of letters in the alphabet
	 * @return number
	 */
	public int randomNumber(){
		int number = randomGen.nextInt(25);
		return number;
	}
	
	/**
	 * Randomizes position for letters for the answer buttons
	 * 
	 * @param the position in the alphabet for correct answer
	 */
	private void randomizeLettersForAnswerButtons(int x){
		for(int y = 0; y < 3; y++){
			if(!answerForButtons.contains(x)){
				answerForButtons.add(x);
				x = randomNumber();
			}else{
				x = randomNumber();
				y--;
			}
		}	
	}		 	
	
}
