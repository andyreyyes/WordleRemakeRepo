
package view_controller;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import model.UserAccount;
import model.WordleGame;

/**
 * This class creates the pane that displays the guesses and the correct and
 * incorrect guesses.
 * 
 * @author Brian Nguyen and Andy Reyes.
 *
 */
public class WordleGamePane extends TilePane {

	WordleGame game = new WordleGame(); // Wordle game
	KeyBoardPane keyboard;
	Square[][] grid = new Square[6][5]; // grid of Buttons
	ArrayList<Character> lettersUsed = new ArrayList<Character>(); // List of characters that have been used
	Boolean win = false; // if player won
	UserAccount user = null;

	int currentRow = 0;
	int currentCol = 0;

	/**
	 * This is the constructor of the class that initializes the pane and makes the
	 * squares.
	 */
	public WordleGamePane() {
		editPane();
		makeSquares();

		System.out.println(game.getTargetWord()); // for testing
	}

	/**
	 * This method gets the current game.
	 * 
	 * @return The current game.
	 */
	public WordleGame getGame() {
		return this.game;
	}

	/**
	 * This method sets the user to the current game.
	 * 
	 * @param newUser An UserAccount that it going to play the current game.
	 */
	public void setUser(UserAccount newUser) {
		user = newUser;
	}

	/**
	 * This method removes the user from the current game.
	 */
	public void removeUser() {
		user = null;
	}

	/**
	 * This method creates the visuals for the pane.
	 */
	private void editPane() {
		this.setStyle("-fx-background-color: white;");
		this.setMaxSize(400, 600);
		this.setVgap(10);
		this.setHgap(10);
		this.setOrientation(Orientation.HORIZONTAL);
		this.setPrefRows(6);
		this.setPrefColumns(5);
		this.setOnKeyPressed(event -> keyPress(event.getText(), String.valueOf(event.getCode())));
	}

	/**
	 * This method updates the buttons on each guess. It also creates the animations
	 * for each square.
	 */
	public void updateRowColors() {
		int delay = 400;
		int startDelay = 400;

		Timeline timeline = null;

		for (int i = 0; i < 5; i++) {
			Button currentSquare = grid[currentRow][i];
			switch (grid[currentRow][i].getStatus()) {
			case "Wrong": // grey if wrong
				timeline = new Timeline(
						new KeyFrame(Duration.millis(startDelay),
								new KeyValue(currentSquare.scaleYProperty(), 0, Interpolator.EASE_BOTH)),

						new KeyFrame(Duration.millis(delay),
								new KeyValue(currentSquare.styleProperty(),
										"-fx-background-color: grey; -fx-text-fill: white", Interpolator.EASE_BOTH)),

						new KeyFrame(Duration.millis(delay += 400),
								new KeyValue(currentSquare.scaleYProperty(), 1, Interpolator.EASE_BOTH))

				);
				timeline.play();
				break;
			case "Present": // gold if present in word
				timeline = new Timeline(
						new KeyFrame(Duration.millis(startDelay),
								new KeyValue(currentSquare.scaleYProperty(), 0, Interpolator.EASE_BOTH)),

						new KeyFrame(Duration.millis(delay),
								new KeyValue(currentSquare.styleProperty(),
										"-fx-background-color: gold; -fx-text-fill: white", Interpolator.EASE_BOTH)),

						new KeyFrame(Duration.millis(delay += 400),
								new KeyValue(currentSquare.scaleYProperty(), 1, Interpolator.EASE_BOTH))

				);
				timeline.play();
				break;
			case "Correct": // green if in correct spot
				timeline = new Timeline(
						new KeyFrame(Duration.millis(startDelay),
								new KeyValue(currentSquare.scaleYProperty(), 0, Interpolator.EASE_BOTH)),

						new KeyFrame(Duration.millis(delay),
								new KeyValue(currentSquare.styleProperty(),
										"-fx-background-color: green; -fx-text-fill: white", Interpolator.EASE_BOTH)),

						new KeyFrame(Duration.millis(delay += 400),
								new KeyValue(currentSquare.scaleYProperty(), 1, Interpolator.EASE_BOTH))

				);
				timeline.play();
				break;
			}
			startDelay += 400;
		}
		if (win) { // disable buttons on win and animate win
			timeline.setOnFinished(event -> animateWin());
			this.setOnKeyPressed(null);
		}
	}

	/**
	 * This method creates the squares that are used to hold the guesses.
	 */
	private void makeSquares() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				Square square = new Square();
				square.setMaxSize(60, 60);
				square.setMinSize(60, 60);
				square.setPrefSize(60, 60);
				grid[i][j] = square;
				this.getChildren().add(square);
				square.setStyle(
						"-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;"
								+ "-fx-border-color: grey;");
				square.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 25));
			}
		}
	}

	/**
	 * This method registers all the keys that are pressed.
	 * 
	 * @param letter  A String that represents the current letter that was typed
	 * @param keyCode A String that represents if either enter or backspace was
	 *                pressed.
	 */
	public void keyPress(String letter, String keyCode) {
		if (win) { // cant type after win
			return;
		} else if (keyCode.equals("ENTER") && currentCol == 5) { // next line and changes current one
			String word = "";
			for (int i = 0; i < 5; i++) {
				word += grid[currentRow][i].getText();
			}
			if (validWord(word)) {
				updateGrid(word);
			} else {
				animateInvalidWord();
			}
		} else if (currentCol == 5 && letter.length() > 0) { // if out of range do nothing
			return;
		} else if (currentCol == 0 && keyCode.equals("BACK_SPACE")) { // if out of range do nothing
			currentCol = 0;
		} else if (keyCode.equals("BACK_SPACE")) { // goes back a space
			currentCol--;
			Button currentButton = grid[currentRow][currentCol];
			currentButton.setText("");
		} else if (letter.equals("")) {
			return;
		} else if (Character.isLetter(letter.charAt(0))) { // changes a character with what key was pressed
			if (currentRow < 6) {
				Button currentButton = grid[currentRow][currentCol];
				currentButton.setText(letter.toUpperCase());
				currentCol++;
				animateButtonClick(currentButton);
			}
		} else {
			return;
		}
	}

	/**
	 * This method creates the shake animation when the word is invalid.
	 */
	private void animateInvalidWord() {
		for (int i = 0; i < grid[currentRow].length; i++) {
			Button button = grid[currentRow][i];

			KeyValue[] keyValues = new KeyValue[] {
					new KeyValue(button.translateXProperty(), 10, Interpolator.EASE_BOTH),
					new KeyValue(button.translateXProperty(), -10, Interpolator.EASE_BOTH),
					new KeyValue(button.translateXProperty(), 10, Interpolator.EASE_BOTH),
					new KeyValue(button.translateXProperty(), -10, Interpolator.EASE_BOTH),
					new KeyValue(button.translateXProperty(), 0, Interpolator.EASE_BOTH) };

			KeyFrame[] keyFrames = new KeyFrame[5];
			for (int j = 0; j < 5; j++) {
				keyFrames[j] = new KeyFrame(Duration.millis((j + 1) * 100), keyValues[j]);
			}

			Timeline timeline = new Timeline(keyFrames);
			timeline.play();
		}
	}

	/**
	 * This method creates the pop effect when typing.
	 * 
	 * @param currentButton The current Button.
	 */
	private void animateButtonClick(Button currentButton) {
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.millis(100),
						new KeyValue(currentButton.scaleXProperty(), 1.2, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(100),
						new KeyValue(currentButton.scaleYProperty(), 1.2, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(105),
						new KeyValue(currentButton.scaleXProperty(), 1, Interpolator.EASE_OUT)),
				new KeyFrame(Duration.millis(105),
						new KeyValue(currentButton.scaleYProperty(), 1, Interpolator.EASE_OUT)));

		timeline.play();
	}

	/**
	 * This method creates the win animation for when the player wins.
	 */
	private void animateWin() {
		int bounceUp = -20;
		int bounceDown = 10;
		int delay = 0;

		for (int i = 0; i < grid[currentRow - 1].length; i++) {
			Button button = grid[currentRow - 1][i];

			KeyValue[] keyValues = new KeyValue[] {
					new KeyValue(button.translateYProperty(), bounceUp, Interpolator.EASE_BOTH),
					new KeyValue(button.translateYProperty(), bounceDown, Interpolator.EASE_BOTH),
					new KeyValue(button.translateYProperty(), 0, Interpolator.EASE_BOTH) };

			KeyFrame[] keyFrames = new KeyFrame[3];
			for (int j = 0; j < 3; j++) {
				keyFrames[j] = new KeyFrame(Duration.millis(delay += 50), keyValues[j]);
			}

			Timeline timeline = new Timeline(keyFrames);
			timeline.play();
		}
	}

	/**
	 * This method is used for updating the GUI after the user creates a guess.
	 * 
	 * @param word A String that is the word the user guessed.
	 */
	private void updateGrid(String word) {
		setStatusOfButtons(word);
		updateRowColors();
		keyboard.setGrid(grid);
		keyboard.setGame(this);
		keyboard.updateKeys();
		currentRow++;
		currentCol = 0;
	}

	/**
	 * This method sets the status of each of the buttons based on the users guess.
	 * 
	 * @param word A String which is the users guess
	 */
	private void setStatusOfButtons(String word) {
		word = word.toLowerCase();
		if (game.processGuess(word)) {
			if (user != null) {
				user.addGame(game);
			}
			win = true;
		}
		HashMap<Character, Integer> lettersCountMap = makeHashMap(game.getTargetWord()); // HashMap that counts the
																							// letters in the word

		for (int i = 0; i < 5; i++) {
			if (word.charAt(i) == game.getTargetWord().charAt(i)) {
				lettersCountMap.put(game.getTargetWord().charAt(i),
						lettersCountMap.get(game.getTargetWord().charAt(i)) - 1);
				grid[currentRow][i].setCorrect();
			} else if (game.getTargetWord().indexOf(word.charAt(i)) != -1 && lettersCountMap.get(word.charAt(i)) > 0)
			// checks if the letter is in the word and is has enough letters to be present
			{
				lettersCountMap.put(word.charAt(i), lettersCountMap.get(word.charAt(i)) - 1);
				grid[currentRow][i].setPresent();
			} else {
				grid[currentRow][i].setWrong();
			}

		}
	}

	/**
	 * This is a helper method that creates a HashMap that counts each letter in the
	 * word. This is then used in order to set the color of each button based on a
	 * guess.
	 * 
	 * @param word A String which is the word the user needs to guess.
	 * @return A HashMap that counts the letters in a string,
	 */
	private HashMap<Character, Integer> makeHashMap(String word) {
		HashMap<Character, Integer> newMap = new HashMap<>();
		for (int i = 0; i < word.length(); i++) {
			if (newMap.containsKey(word.charAt(i))) {

				newMap.put(word.charAt(i), newMap.get(word.charAt(i)) + 1);
			} else {
				newMap.put(word.charAt(i), 1);
			}
		}
		return newMap;
	}

	/**
	 * This method checks if the users guess is a valid word.
	 * 
	 * @param word A String that is the users guess.
	 * @return A Boolean that is true if the word is valid.
	 */
	private boolean validWord(String word) {
		for (int i = 0; i < word.length(); i++) { // loop that adds to lettersUsed
			if (!lettersUsed.contains(word.charAt(i))) {
				lettersUsed.add(word.charAt(i));
			}
		}
		if (!game.getWordList().contains(word.toLowerCase())) {
			return false;
		}
		return true;
	}

	/**
	 * This is a getter method that returns the ArrayList of characters used.
	 * 
	 * @return An ArrayList of the letters used in a guess.
	 */
	public ArrayList<Character> getLettersUsed() {
		return lettersUsed;
	}

	/**
	 * This method returns the current column.
	 * 
	 * @return An Integer that is the current column.
	 */
	public int getColumn() {
		return currentCol;
	}

	/**
	 * This method returns the current row.
	 * 
	 * @return An Integer that is the current row.
	 */
	public int getRow() {
		return currentRow;
	}

	/**
	 * This method returns the grid.
	 * 
	 * @return A 2d Square array that represents the game.
	 */
	public Square[][] getGrid() {
		return grid;
	}

	/**
	 * This method returns a String version of the grid.
	 */
	public String toString() {
		String ret = "";
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				ret += grid[i][j].toString();
			}
			ret += "\n";
		}
		return ret;
	}

	/**
	 * This method sets the keyboard to a KeyBoardPane object.
	 * 
	 * @param pane A KeyBoardPane object that is the current keyboard for the game.
	 */
	public void setKeyboard(KeyBoardPane pane) {
		keyboard = pane;
	}

	/**
	 * This method sets the game to dark mode.
	 */
	public void setDarkMode() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				if (grid[i][j].getStatus().equals("Empty")) {
					grid[i][j].setStyle(
							"-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: black;"
									+ "-fx-border-color:grey;");
					grid[i][j].setTextFill(Color.WHITE);
				}
			}
		}
		this.setStyle("-fx-background-color: black;");

	}

	/**
	 * This method sets the game to light mode.
	 */
	public void setLightMode() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				if (grid[i][j].getStatus().equals("Empty")) {
					grid[i][j].setStyle(
							"-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;"
									+ "-fx-border-color:grey;");
					grid[i][j].setTextFill(Color.BLACK);
				}
			}
		}
		this.setStyle("-fx-background-color: white;");
	}
}

/**
 * This class is used as the squares the show the guesses.
 * 
 * @author Brian Nguyen
 *
 */
class Square extends Button {
	char letter;
	String status;

	/**
	 * This is the constructor of the class that sets the status to empty and the
	 * letter to a period.
	 */
	public Square() {
		letter = '.';
		status = "Empty";
	}

	/**
	 * This method sets the letter to the letter that is passed in. It also updates
	 * the square.
	 * 
	 * @param letter A Character that is a letter thats passed in from a user guess.
	 */
	public void setSpace(char letter) {
		this.letter = letter;
		this.setText(String.valueOf(letter));
	}

	/**
	 * This method returns the String value of the Square.
	 */
	public String toString() {
		return String.valueOf(letter);
	}

	/**
	 * This method sets the status of the square to Wrong.
	 */
	void setWrong() {
		status = "Wrong";
	}

	/**
	 * This method sets the status of the square to Present.
	 */
	void setPresent() {
		status = "Present";
	}

	/**
	 * This method sets the status of the square to Correct.
	 */
	void setCorrect() {
		status = "Correct";
	}

	/**
	 * This method sets the status to what ever is passed in.
	 * 
	 * @param status A String that the user passes in.
	 */
	void setStatus(String status) {
		this.status = status;
	}

	/**
	 * This method returns the status of the Square.
	 * 
	 * @return A String which is the status of the Square.
	 */
	String getStatus() {
		return status;
	}
}
