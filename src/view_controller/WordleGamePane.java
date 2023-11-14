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
		for (int i = 0; i < 5; i++) {
			switch (grid[currentRow][i].getStatus()) {
			case "Wrong": // grey if wrong
				grid[currentRow][i].setStyle("-fx-background-color: grey;");
				break;
			case "Present": // gold if present in word
				grid[currentRow][i].setStyle("-fx-background-color: gold;");
				break;
			case "Correct": // green if in correct spot
				grid[currentRow][i].setStyle("-fx-background-color: green;");
				break;
			}
			grid[currentRow][i].setTextFill(Color.WHITE);
		}
		if (win) { // disable buttons on win
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
				square.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 25));
			}
		}
	}

	// on a key press
	public void keyPress(String letter, String keyCode) {
		System.out.println(keyCode);
		if (win) { // cant type after win
			return;
		} else if (keyCode.equals("ENTER") && currentCol == 5) { // next line and changes current one
			String word = "";
			for (int i = 0; i < 5; i++) {
				word += grid[currentRow][i].getText();
			}
			if (validWord(word)) {
				updateGrid(word);
			}
		} else if (currentCol == 5 && letter.length() > 0) { // if out of range do nothing
			return;
		} else if (currentCol == 0 && keyCode.equals("BACK_SPACE")) { // if out of range do nothing
			currentCol = 0;
		} else if (keyCode.equals("BACK_SPACE")) { // goes back a space
			currentCol--;
			Button currentButton = grid[currentRow][currentCol];
			currentButton.setText("");
		} else if (Character.isLetter(letter.charAt(0))) { // changes a character with what key was pressed
			Button currentButton = grid[currentRow][currentCol];
			currentButton.setText(letter.toUpperCase());
			currentCol++;
			animateButtonClick(currentButton);
		} else {
			return;
		}
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
				lettersCountMap.put(word.charAt(i),
						lettersCountMap.get(word.charAt(i)) - 1);
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
