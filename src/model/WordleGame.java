package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class WordleGame {

	private String targetWord;
	private ArrayList<String> guesses;
	private ArrayList<Character> guessedLetters;
	
	public WordleGame() {
		targetWord = getRandomWord();
		guesses = new ArrayList<String>();
		guessedLetters = new ArrayList<Character>();
	}

	/*
	 * constructor for testing, allows to set target word
	 */
	public WordleGame(String word) {
		targetWord = word;
		guesses = new ArrayList<String>();
		guessedLetters = new ArrayList<Character>();

	}

	public String getRandomWord() {
		ArrayList<String> wordList = new ArrayList<String>();
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

	public String getTargetWord() {
		return targetWord;
	}

	public boolean processGuess(String guess) {
		guesses.add(guess);
		this.addGuessedLetters(guess);
		return targetWord.equals(guess);
	}
	private void addGuessedLetters(String guess) {
		for (int i = 0; i < guess.length(); i++) {
            char currentChar = guess.charAt(i);
            if (!guessedLetters.contains(currentChar)) {
                guessedLetters.add(currentChar);
            }
        }
	}
	public ArrayList<Character> getGuessedLetters(){
		return guessedLetters;
	}

	public ArrayList<String> getGuesses(){
		return this.guesses;
	}
	public void printGuesses() {
		for(int i = 0; i < 5; i++) {
			// if no word then print a blank 5 space
			if(i >= guesses.size()) {
				System.out.println("_ _ _ _ _");
			}
			else {
				String guess = guesses.get(i);
				for(int y = 0 ; y < 5; y++) {
					System.out.print(guess.charAt(y) + " ");
				}
				System.out.println();

			}
		}
	}

}