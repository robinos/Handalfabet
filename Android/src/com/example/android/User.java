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

