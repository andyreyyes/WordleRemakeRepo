package view_controller;

import java.util.ArrayList;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.WordleGame;

public class WordleGamePane extends TilePane {

	WordleGame game = new WordleGame(); // Wordle game

	Square[][] grid = new Square[6][5]; // grid of Buttons

	int currentRow;
	int currentCol;

	ArrayList<Character> lettersUsed = new ArrayList<Character>(); // List of characters that have been used

	Boolean win = false; // if won

	public WordleGamePane() {
		System.out.println(game.getTargetWord());
		this.setMaxSize(400, 600);

		this.setVgap(10);
		this.setHgap(10);

		this.setOrientation(Orientation.HORIZONTAL);
		this.setPrefRows(6);
		this.setPrefColumns(5);

		currentRow = 0;
		currentCol = 0;

		makeSquares();
		this.setOnKeyPressed(event -> keyPress(event.getText(), String.valueOf(event.getCode())));
	}

	// changes the color of the spaces in the row (and disables buttons on win)
	public void updateRow() {
		for (int i = 0; i < 5; i++) {
			switch (grid[currentRow][i].getStatus()) {
			case "Empty":
				break;
			case "Unchecked":
				break;
			case "Wrong":
				grid[currentRow][i].setStyle("-fx-background-color: grey;");
				break;
			case "Present":
				grid[currentRow][i].setStyle("-fx-background-color: gold;");
				break;
			case "Correct":
				grid[currentRow][i].setStyle("-fx-background-color: green;");
				break;
			}
			grid[currentRow][i].setTextFill(Color.WHITE);	
		}
		if (win) {
			this.setOnKeyPressed(null);
		}
	}

	// makes the squares of the grids
	void makeSquares() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				Square square = new Square();
				square.setPrefSize(60, 60);
				grid[i][j] = square;
				this.getChildren().add(square);
				square.setStyle(
						"-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
				square.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 25));
			}
		}
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

	// on a key press
	private void keyPress(String letter, String keyCode) {
		if (keyCode.equals("ENTER") && currentCol == 5) { // next line and changes current one
			String word = "";
			for (int i = 0; i < 5; i++) {
				word += grid[currentRow][i].getText();
			}
			if (validWord(word)) {
				setStatusOfButtons(word);
				updateRow();
				currentRow++;
				currentCol = 0;
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
		}
	}

	// Checks for win, and also changes the status of the spaces
	private void setStatusOfButtons(String word) {
		word = word.toLowerCase();
		if (game.processGuess(word)) {
			win = true;
		}
		for (int i = 0; i < 5; i++) {
			if (word.charAt(i) == game.getTargetWord().charAt(i)) {
				grid[currentRow][i].setStatus("Correct");
			} else if (game.getTargetWord().indexOf(word.charAt(i)) != -1) {
				grid[currentRow][i].setStatus("Present");
			} else {
				grid[currentRow][i].setStatus("Wrong");
			}
		}

	}

	// Checks if word is able to be used
	private boolean validWord(String word) {
		for (int i = 0; i < word.length(); i++) {
			if (!lettersUsed.contains(word.charAt(i))) {
				lettersUsed.add(word.charAt(i));
			}
		}
		return true;
	}

	public ArrayList<Character> getLettersUsed() {
		return lettersUsed;
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

	void setUnchecked() {
		status = "Unchecked";
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
