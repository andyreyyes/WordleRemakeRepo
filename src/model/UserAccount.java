package model;

import java.io.Serializable;
import java.util.ArrayList;

public class UserAccount implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
    private String password;
    private int gamesPlayed = 0;
    private int gamesWon = 0;
    private int maxStreak = 0;
    private int curStreak = 0;
    private int[] guessDist = {0,0,0,0,0,0,0};
    private int prevWinGuess = 0;
    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public int[] getGuessDist() {
    	return guessDist;
    }
    public int getLastGameGuess() {
    	return prevWinGuess;
    }
    // Getters and setters for the attributes
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public void addGame(WordleGame game) {
    	if(game.getWin()) {
    		guessDist[game.getGuessAmount()] = guessDist[game.getGuessAmount()] + 1;
        	prevWinGuess = game.getGuessAmount();
        	curStreak++;
        	if(curStreak > maxStreak) {
        		maxStreak = curStreak;
        	}
        	gamesWon++;
        	gamesPlayed++;
    	}
    	else {
    		gamesPlayed++;
    		curStreak = 0;
    	}
  
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
