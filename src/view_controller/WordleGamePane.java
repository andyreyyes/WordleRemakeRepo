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
import model.WordleGame;

public class WordleGamePane extends TilePane {

	WordleGame game = new WordleGame(); // Wordle game
	KeyBoardPane keyboard;
	Square[][] grid = new Square[6][5]; // grid of Buttons
	ArrayList<Character> lettersUsed = new ArrayList<Character>(); // List of characters that have been used
	Boolean win = false; // if player won

	int currentRow = 0;
	int currentCol = 0;

	public WordleGamePane() {
		editPane();
		makeSquares();

		System.out.println(game.getTargetWord()); // for testing
	}

	private void editPane() {
		this.setMaxSize(400, 600);
		this.setVgap(10);
		this.setHgap(10);
		this.setOrientation(Orientation.HORIZONTAL);
		this.setPrefRows(6);
		this.setPrefColumns(5);
		this.setOnKeyPressed(event -> keyPress(event.getText(), String.valueOf(event.getCode())));
	}

	// changes the color of the spaces in the row (and disables buttons on win)
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

	// makes the squares of the grids
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
						"-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
				square.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 26));
			}
		}
	}

	// on a key press
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
			Button currentButton = grid[currentRow][currentCol];
			currentButton.setText(letter.toUpperCase());
			currentCol++;
			animateButtonClick(currentButton);
		} else {
			return;
		}
	}

	private void animateInvalidWord() {
		Button button1 = grid[currentRow][0];
		Button button2 = grid[currentRow][1];
		Button button3 = grid[currentRow][2];
		Button button4 = grid[currentRow][3];
		Button button5 = grid[currentRow][4];
		
		Timeline timeline = new Timeline(

				new KeyFrame(Duration.millis(100),
						new KeyValue(button1.translateXProperty(), 10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(200),
						new KeyValue(button1.translateXProperty(), -10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(300),
						new KeyValue(button1.translateXProperty(), 10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(400),
						new KeyValue(button1.translateXProperty(), -10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(500),
						new KeyValue(button1.translateXProperty(), 0, Interpolator.EASE_BOTH)),
				
				new KeyFrame(Duration.millis(100),
						new KeyValue(button2.translateXProperty(), 10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(200),
						new KeyValue(button2.translateXProperty(), -10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(300),
						new KeyValue(button2.translateXProperty(), 10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(400),
						new KeyValue(button2.translateXProperty(), -10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(500),
						new KeyValue(button2.translateXProperty(), 0, Interpolator.EASE_BOTH)),
				
				new KeyFrame(Duration.millis(100),
						new KeyValue(button3.translateXProperty(), 10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(200),
						new KeyValue(button3.translateXProperty(), -10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(300),
						new KeyValue(button3.translateXProperty(), 10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(400),
						new KeyValue(button3.translateXProperty(), -10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(500),
						new KeyValue(button3.translateXProperty(), 0, Interpolator.EASE_BOTH)),
				
				new KeyFrame(Duration.millis(100),
						new KeyValue(button4.translateXProperty(), 10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(200),
						new KeyValue(button4.translateXProperty(), -10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(300),
						new KeyValue(button4.translateXProperty(), 10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(400),
						new KeyValue(button4.translateXProperty(), -10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(500),
						new KeyValue(button4.translateXProperty(), 0, Interpolator.EASE_BOTH)),
				
				new KeyFrame(Duration.millis(100),
						new KeyValue(button5.translateXProperty(), 10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(200),
						new KeyValue(button5.translateXProperty(), -10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(300),
						new KeyValue(button5.translateXProperty(), 10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(400),
						new KeyValue(button5.translateXProperty(), -10, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(500),
						new KeyValue(button5.translateXProperty(), 0, Interpolator.EASE_BOTH))
				

		);

		timeline.play();

	}

	// animates pop effect on buttons when pressed
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

	// every button will bounce up, then down, then back to its normal position on a
	// win
	private void animateWin() {
		Button button1 = grid[currentRow - 1][0];
		Button button2 = grid[currentRow - 1][1];
		Button button3 = grid[currentRow - 1][2];
		Button button4 = grid[currentRow - 1][3];
		Button button5 = grid[currentRow - 1][4];

		int bounceUp = -20;
		int bounceDown = 10;

		int delay = 0;

		Timeline timeline = new Timeline(

				new KeyFrame(Duration.millis(delay += 50),
						new KeyValue(button1.translateYProperty(), bounceUp, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(delay += 75),
						new KeyValue(button1.translateYProperty(), bounceDown, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(delay += 100),
						new KeyValue(button1.translateYProperty(), 0, Interpolator.EASE_BOTH)),

				new KeyFrame(Duration.millis(delay += 50),
						new KeyValue(button2.translateYProperty(), bounceUp, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(delay += 75),
						new KeyValue(button2.translateYProperty(), bounceDown, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(delay += 100),
						new KeyValue(button2.translateYProperty(), 0, Interpolator.EASE_BOTH)),

				new KeyFrame(Duration.millis(delay += 50),
						new KeyValue(button3.translateYProperty(), bounceUp, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(delay += 75),
						new KeyValue(button3.translateYProperty(), bounceDown, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(delay += 100),
						new KeyValue(button3.translateYProperty(), 0, Interpolator.EASE_BOTH)),

				new KeyFrame(Duration.millis(delay += 50),
						new KeyValue(button4.translateYProperty(), bounceUp, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(delay += 75),
						new KeyValue(button4.translateYProperty(), bounceDown, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(delay += 100),
						new KeyValue(button4.translateYProperty(), 0, Interpolator.EASE_BOTH)),

				new KeyFrame(Duration.millis(delay += 50),
						new KeyValue(button5.translateYProperty(), bounceUp, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(delay += 75),
						new KeyValue(button5.translateYProperty(), bounceDown, Interpolator.EASE_BOTH)),
				new KeyFrame(Duration.millis(delay += 100),
						new KeyValue(button5.translateYProperty(), 0, Interpolator.EASE_BOTH))

		);

		timeline.play();
	}

	// updates buttons and current row and column
	private void updateGrid(String word) {
		setStatusOfButtons(word);
		updateRowColors();
		keyboard.setGrid(grid);
		keyboard.setGame(this);
		keyboard.updateKeys();
		currentRow++;
		currentCol = 0;
	}

	// Checks for win, and also changes the status of the spaces
	private void setStatusOfButtons(String word) {
		word = word.toLowerCase();
		if (game.processGuess(word)) {
			win = true;
		}
		HashMap<Character, Integer> lettersCountMap = makeHashMap(game.getTargetWord());

		for (int i = 0; i < 5; i++) {
			if (word.charAt(i) == game.getTargetWord().charAt(i)) {
				lettersCountMap.put(game.getTargetWord().charAt(i),
						lettersCountMap.get(game.getTargetWord().charAt(i)) - 1);
			} else if (game.getTargetWord().indexOf(word.charAt(i)) != -1) {
				lettersCountMap.put(word.charAt(i), lettersCountMap.get(word.charAt(i)) - 1);
			}
		}
		for (int i = 0; i < 5; i++) {
			if (word.charAt(i) == game.getTargetWord().charAt(i)) { // if char is in word and in the right spot
				grid[currentRow][i].setCorrect();

			} else if (!game.getTargetWord().contains(word.substring(i, i + 1))) { // if char is completely not in the
																					// word
				grid[currentRow][i].setWrong();
			} else if (game.getTargetWord().indexOf(word.charAt(i)) != -1 && lettersCountMap.get(word.charAt(i)) >= 0) {
				// if chat is in the word but not at the right location.
				grid[currentRow][i].setPresent();
			} else {
				grid[currentRow][i].setWrong();
			}
		}

	}

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

	// Checks if word is able to be used
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

	public ArrayList<Character> getLettersUsed() {
		return lettersUsed;
	}

	public int getColumn() {
		return currentCol;
	}

	public int getRow() {
		return currentRow;
	}

	public Square[][] getGrid() {
		return grid;
	}

	// Prints the grid for testing
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

	// Sets the keyboard to a KeyBoardPane object
	public void setKeyboard(KeyBoardPane pane) {
		keyboard = pane;
	}
}

class Square extends Button {
	char letter;
	String status;

	public Square() {
		letter = '.';
		status = "Empty";
	}

	public void setSpace(char letter) {
		this.letter = letter;
		this.setText(String.valueOf(letter));
	}

	public String toString() {
		return String.valueOf(letter);
	}

	void setWrong() {
		status = "Wrong";
	}

	void setPresent() {
		status = "Present";
	}

	void setCorrect() {
		status = "Correct";
	}

	void setStatus(String status) {
		this.status = status;
	}

	String getStatus() {
		return status;
	}
}
