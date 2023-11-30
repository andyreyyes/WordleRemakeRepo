package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
/**
 * This class represents the actual game of wordle. It has all the logic behind it.
 * @author Adler Nguyen
 *
 */
public class WordleGame implements Serializable{

	private static final long serialVersionUID = 1L;
	private String targetWord;
	private ArrayList<String> guesses;
	private ArrayList<Character> guessedLetters;
	private int guessAmount;
	private ArrayList<String> wordList;
	private boolean win = false;

	/**
	 * This is the constructor of the class, it initializes the target word, guesses, the guessed letters, and
	 * the guess Amount
	 */
	public WordleGame() {
		targetWord = getRandomWord();
		guesses = new ArrayList<String>();
		guessedLetters = new ArrayList<Character>();
		guessAmount = 0;
	}

	/**
	 * This is another constructor for the class that is used for testing.
	 * @param word A String that is able to set the target word.
	 */
	public WordleGame(String word) {
		targetWord = word;
		guesses = new ArrayList<String>();
		guessedLetters = new ArrayList<Character>();

	}
	/**
	 * This method returns a random word that is used for the target word.
	 * @return A String that is picked randomly using the Wordle word database.
	 */
	public String getRandomWord() {
		wordList = new ArrayList<String>();
		Random random = new Random();
		// add all words to the wordlist
		try (BufferedReader reader = new BufferedReader(new FileReader("words.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				wordList.add(line.strip());
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		// get a random word
		int randomIndex = random.nextInt(wordList.size());
		return wordList.get(randomIndex);
	}
	/**
	 * This is a getter method that returns the target word.
	 * @return A String which is the target word.
	 */
	public String getTargetWord() {
		return targetWord;
	}
	/**
	 * This method processes each guess. It increases the guess amount, adds the guess to the guess list, and check
	 * if the users guess was correct.
	 * @param guess A String that is the users guess.
	 * @return Returns a boolean that is true if the guess is correct.
	 */
	public boolean processGuess(String guess) {
		guessAmount++;
		guesses.add(guess);
		this.addGuessedLetters(guess);
		win = targetWord.equals(guess);
		return targetWord.equals(guess);
	}
	/**
	 * This method returns a boolean that shows whether the user won or not.
	 * @return A boolean that is true if the user wins.
	 */
	public boolean getWin() {
		return this.win;
	}
	/**
	 * This method adds the letters of the user guess to a GuessedLetters list.
	 * @param guess A String which is the users guess.
	 */ 
	private void addGuessedLetters(String guess) {
		for (int i = 0; i < guess.length(); i++) {
            char currentChar = guess.charAt(i);
            if (!guessedLetters.contains(currentChar)) {
                guessedLetters.add(currentChar);
            }
        }
	}
	/**
	 * This method returns the GuessedLetters list.
	 * @return An ArrayList of characters which is the guessed letters.
	 */
	public ArrayList<Character> getGuessedLetters(){
		return guessedLetters;
	}
	/**
	 * This method returns the guesses that the user had.
	 * @return An ArrayList of strings that are the guesses that the use has had.
	 */
	public ArrayList<String> getGuesses(){
		return this.guesses;
	}
	/**
	 * This method is used for testing that prints each of the guesses.
	 */
	public void printGuesses() {
		for(int i = 0; i < 5; i++) {
			// if no word then print a blank 5 space
			if(i >= guesses.size()) {
				System.out.println("_ _ _ _ _");
			}
			else {
				String guess = guesses.get(i);
				for(int y = 0 ; y <= 5; y++) {
					System.out.print(guess.charAt(y) + " ");
				}
				System.out.println();

			}
		}
	}
	/**
	 * This method returns the amount of guesses.
	 * @return An Integer that is the amount of guesses the user has had
	 */
	public int getGuessAmount() {
		return guessAmount;
	}
	/**
	 * This method returns the list of words that are in the Wordle database.
	 * @return An ArrayList of strings.
	 */
	public ArrayList<String> getWordList() {
		return wordList;
	}

}