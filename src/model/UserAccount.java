package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class creates the UserAccount that is used to create account for the
 * wordle game.
 * 
 * @author Adler Nguyen
 *
 */
public class UserAccount implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private int gamesPlayed = 0;
	private int gamesWon = 0;
	private int maxStreak = 0;
	private int curStreak = 0;
	private int[] guessDist = { 0, 0, 0, 0, 0, 0, 0 };
	private int prevWinGuess = 0;

	/**
	 * This method is the constructor if the class and sets the username and
	 * password.
	 * 
	 * @param username A String that is the username.
	 * @param password A String that is the password.
	 */
	public UserAccount(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * This is a getter method that gets the guess distribution.
	 * 
	 * @return An array of Integers that represent the guess distribution.
	 */
	public int[] getGuessDist() {
		return guessDist;
	}

	/**
	 * This is a getter method that returns the amount of wins.
	 * 
	 * @return An integer that is the amount fo wins.
	 */
	public int getLastGameGuess() {
		return prevWinGuess;
	}

	/**
	 * This is a getter method that returns the username.
	 * 
	 * @return A String which is the username of the current user.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * This is a getter method that returns the password.
	 * 
	 * @return A String which is the password of the current user.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * This method registers each game.
	 * 
	 * @param game A WordleGame objec which is the current game.
	 */
	public void addGame(WordleGame game) {
		if (game.getWin()) {
			guessDist[game.getGuessAmount()] = guessDist[game.getGuessAmount()] + 1;
			prevWinGuess = game.getGuessAmount();
			curStreak++;
			if (curStreak > maxStreak) {
				maxStreak = curStreak;
			}
			gamesWon++;
			gamesPlayed++;
		} else {
			gamesPlayed++;
			curStreak = 0;
		}

	}

	/**
	 * This method is used to see if the account is valid.
	 * 
	 * @param pwAttempt A String which is the password that the user types in.
	 * @return A Boolean that is true if the login attempt is correct.
	 */
	public boolean attemptLogin(String pwAttempt) {
		return password == pwAttempt;
	}

	/**
	 * This is a getter method that returns the amount of game the user has played.
	 * 
	 * @return An integer that represents the amount of games that were played.
	 */
	public int getGamesPlayed() {
		return this.gamesPlayed;
	}

	/**
	 * This is a getter method that returns the win percentage of the user.
	 * 
	 * @return A Double that is the win percentage of the user.
	 */
	public double getWinPercentage() {
		if (this.gamesPlayed == 0) {
			return 0.0;
		}
		return this.gamesWon / this.gamesPlayed;
	}

	/**
	 * This is a getter method that returns the max win streak.
	 * 
	 * @return An integer that is the max win streak of the current user.
	 */
	public int getMaxStreak() {
		return this.maxStreak;
	}

	/**
	 * This is a getter method that returns the current win streak.
	 * 
	 * @return An Integer that is the current win streak of the current user.
	 */
	public int getCurStreak() {
		return this.curStreak;
	}
}
