package view_controller;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import model.WordleGame;

public class WordleGamePane extends TilePane {

	WordleGame game = new WordleGame();

	Square[][] grid = new Square[6][5];

	int currentRow;
	int currentCol;

	public WordleGamePane() {

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

	public void updateRow() {
		for (int i = 0; i < 5; i++) {
			switch (grid[currentRow][i].getStatus()) {
			case "Empty":
				break;
			case "Unchecked":
				break;
			case "Wrong":
				grid[currentRow][i].setStyle("-fx-background-color: red;");
				break;
			case "Present":
				grid[currentRow][i].setStyle("-fx-background-color: yellow;");
				break;
			case "Correct":
				grid[currentRow][i].setStyle("-fx-background-color: green;");
				break;

			}
		}

	}

	void makeSquares() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				Square square = new Square();
				square.setPrefSize(60, 60);
				grid[i][j] = square;
				this.getChildren().add(square);
				square.setStyle(
						"-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
			}
		}
	}

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

	private void keyPress(String letter, String keyCode) {
		if (keyCode.equals("ENTER") && currentCol == 5) {
			String word = "";
			for (int i = 0; i < 5; i++) {
				word += grid[currentRow][i].getText();
			}
			if (validWord(word)) {
				setStatusOfButtons();
				updateRow();
				currentRow++;
				currentCol = 0;
			}
		} else if (currentCol == 5 && letter.length() > 0) {
			return;
		} else if (currentCol == 0 && keyCode.equals("BACK_SPACE")) {
			currentCol = 0;
		} else if (keyCode.equals("BACK_SPACE")) {
			currentCol--;
			Button currentButton = grid[currentRow][currentCol];
			currentButton.setText("");
		} else if (Character.isLetter(letter.charAt(0))) {
			Button currentButton = grid[currentRow][currentCol];
			currentButton.setText(letter.toUpperCase());
			currentCol++;
		}
	}

	private void setStatusOfButtons() {
		// TODO Auto-generated method stub

	}

	private boolean validWord(String word) {
		// TODO Auto-generated method stub
		return true;
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

	String getStatus() {
		return status;
	}
}
