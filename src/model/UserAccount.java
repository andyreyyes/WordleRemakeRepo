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
    private ArrayList<WordleGame> games = new ArrayList<WordleGame>();
    
    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters for the attributes
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public void addGame(WordleGame game) {
    	games.add(game);
    	gamesPlayed += 1;
    	if(game.getWin()) {
    		gamesWon+=1;
    		curStreak +=1;
    		if(curStreak > maxStreak) {
    			maxStreak = curStreak;
    		}
    	}
    	else {
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
