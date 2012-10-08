

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
 * The User class.
 * 
 * @author  : Grupp02
 * @version : 2012-10-08, v0.4
 * @License : GPLv3
 * @Copyright : Copyright© 2012, Grupp02
 *
 */


package com.example.android;

public class User {
	
	private String userName;
	private String password;
	private int highScore = 0;
	
	public User(){}
	
	public User(String userName){
		this.userName = userName;
	}
	
	public User(String userName, String password){
		this.userName = userName;
		this.password = password;		
	}
	
	public User(String userName, int highScore){
		this.userName = userName;
		this.highScore = highScore;		
	}
	
	public User(String userName, String password, int highScore){
		this.userName = userName;
		this.password = password;	
		this.highScore = highScore;
	}
	
	public String getName(){
		return userName;
	}
	
	public int getHighScore(){
		return highScore;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setName(String userName){
		this.userName = userName;
	}
		
	public void setHighScore(int highScore){
		this.highScore = highScore;
	}

	public void setPassword(String password){
		this.password = password;
	}
}

