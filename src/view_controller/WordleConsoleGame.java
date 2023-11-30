package view_controller;

import java.util.Scanner;

import model.WordleGame;

/**
 * This class is used to create a console game version of wordle.
 * 
 * @author Andy Reyes
 *
 */
public class WordleConsoleGame {
	/**
	 * This is the main method of this class that calls the method to play the
	 * console game
	 * 
	 * @param args A String array.
	 */
	public static void main(String[] args) {
		playConsoleGame();
		// TODO Auto-generated method stub

	}

	/**
	 * This method creates the Wordle console game.
	 */
	public static void playConsoleGame() {
		WordleGame game = new WordleGame();
		System.out.println(game.getTargetWord());
		while (true) {

			if (game.getGuessAmount() == 6) {
				System.out.println("Ran out of guesses. Game over!");
				break;
			}
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter a guess that is 5 letters long");
			String input = sc.nextLine();
			if (game.processGuess(input)) {
				System.out.println("You guessed correct");
				break;
			} else {
				for (int i = 0; i < 5; i++) {
					if (input.charAt(i) == game.getTargetWord().charAt(i)) {
						System.out.print(input.charAt(i) + " ");

					} else if (game.getTargetWord().indexOf(input.charAt(i)) != -1) {
						System.out.print("* ");

					} else {
						System.out.print("_ ");
					}
				}
				System.out.println();
				System.out.println("Guesses left:" + (6 - game.getGuessAmount()));
			}
		}
		System.out.println("The correct word was " + game.getTargetWord());
	}

}
