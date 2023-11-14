package model;

import java.io.Serializable;

public class UserAccount implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
    private String password;
    private WordleGame currentGame;
    private int gamesPlayed = 0;
    private int gamesWon = 0;
    private int maxStreak = 0;
    private int curStreak = 0;
    
    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.currentGame = null;
    }

    // Getters and setters for the attributes
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setCurrentGame(WordleGame game) {
    	this.currentGame = game;
    }
    public boolean attemptLogin(String pwAttempt) {
    	return password == pwAttempt;
    }
    public int getGamesPlayed() {
    	return this.gamesPlayed;
    }
    public double getWinPercentage() {
    	if(this.gamesPlayed == 0) {
    		return 0.0;
    	}
    	return this.gamesWon/this.gamesPlayed;
    }
    public int getMaxStreak() {
    	return this.maxStreak;
    }
    public int getCurStreak() {
    	return this.curStreak;
    }
}
