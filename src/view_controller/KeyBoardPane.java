package view_controller;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class KeyBoardPane extends GridPane {

	// arraylist of all keys on the keyboard
	private ArrayList<Button> keyList = new ArrayList<>();

	// VBox to hold the key rows
	private VBox keyColumn = new VBox();

	// All 3 rows of keys on the keyboard
	private HBox keyRow1 = new HBox();
	private HBox keyRow2 = new HBox();
	private HBox keyRow3 = new HBox();

	// All individual keys
	// 1st row
	private Button qKey = new Button("Q");
	private Button wKey = new Button("W");
	private Button eKey = new Button("E");
	private Button rKey = new Button("R");
	private Button tKey = new Button("T");
	private Button yKey = new Button("Y");
	private Button uKey = new Button("U");
	private Button iKey = new Button("I");
	private Button oKey = new Button("O");
	private Button pKey = new Button("P");


	// 2nd Row
	private Button aKey = new Button("A");
	private Button sKey = new Button("S");
	private Button dKey = new Button("D");
	private Button fKey = new Button("F");
	private Button gKey = new Button("G");
	private Button hKey = new Button("H");
	private Button jKey = new Button("J");
	private Button kKey = new Button("K");
	private Button lKey = new Button("L");


	// 3rd Row
	private Button enterKey = new Button("ENTER");
	private Button zKey = new Button("Z");
	private Button xKey = new Button("X");
	private Button cKey = new Button("C");
	private Button vKey = new Button("V");
	private Button bKey = new Button("B");
	private Button nKey = new Button("N");
	private Button mKey = new Button("M");
	private Button backKey = new Button("<--");

	// WordGamePane to use to update keys and keep focus in the right pane
	private WordleGamePane game;

	private Square[][] squareList;

	public KeyBoardPane() {
		initialize();
	}

	private void initialize() {
		// Constructs the keyboard
		this.setMinWidth(365);
		this.setMaxWidth(365);
		this.setMinHeight(150);
		this.setMaxHeight(150);

		keyRow1.setPadding(new Insets(2, 2, 2, 2));
		keyRow2.setPadding(new Insets(2, 2, 2, 2));
		keyRow3.setPadding(new Insets(2, 2, 2, 2));
		keyRow1.setSpacing(4);
		keyRow2.setSpacing(4);
		keyRow3.setSpacing(4);


		// Set style of each key to a white button
		// 1st row of keys
		qKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		wKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		eKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		rKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		tKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		yKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		uKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		iKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		oKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		pKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");


		// 2nd row of keys
		aKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		sKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		dKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		fKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		gKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		hKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		jKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		kKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		lKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");

		// 3rd row of keys
		enterKey.setStyle(
				"-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		zKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		xKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		cKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		vKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		bKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		nKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		mKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");
		backKey.setStyle(
				"-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: white;");

		keyRow1.getChildren().addAll(qKey, wKey, eKey, rKey, tKey, yKey, uKey, iKey, oKey, pKey);
		keyRow2.getChildren().addAll(aKey, sKey, dKey, fKey, gKey, hKey, jKey, kKey, lKey);
		keyRow3.getChildren().addAll(enterKey, zKey, xKey, cKey, vKey, bKey, nKey, mKey, backKey);

		keyColumn.getChildren().addAll(keyRow1, keyRow2, keyRow3);

		keyRow1.setAlignment(Pos.CENTER);
		keyRow2.setAlignment(Pos.CENTER);
		keyRow3.setAlignment(Pos.CENTER);

		keyColumn.setAlignment(Pos.CENTER);

		this.add(keyColumn, 0, 0);

		createList();


		registerHandlers();
	}

	private void registerHandlers() {
		// First row of keys event handlers
		qKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("q", "N/A");
		});
		wKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("w", "N/A");
		});
		eKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("e", "N/A");
		});
		rKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("r", "N/A");
		});
		tKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("t", "N/A");
		});
		yKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("y", "N/A");
		});
		uKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("u", "N/A");
		});
		iKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("i", "N/A");
		});
		oKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("o", "N/A");
		});
		pKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("p", "N/A");
		});

		// Second row of keys
		aKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("a", "N/A");
		});
		sKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("s", "N/A");
		});
		dKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("d", "N/A");
		});
		fKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("f", "N/A");
		});
		gKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("g", "N/A");
		});
		hKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("h", "N/A");
		});
		jKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("j", "N/A");
		});
		kKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("k", "N/A");
		});
		lKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("l", "N/A");
		});

		enterKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress(" ", "ENTER");

		});
		zKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("z", "N/A");
		});
		xKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("x", "N/A");
		});
		cKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("c", "N/A");
		});
		vKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("v", "N/A");
		});
		bKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("b", "N/A");
		});
		nKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("n", "N/A");
		});
		mKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("m", "N/A");
		});
		backKey.setOnAction((event) -> {
			game.requestFocus();
			game.keyPress("", "BACK_SPACE");
		});

	}

	public void setGame(WordleGamePane newGame) {
		// Sets game to a WordleGamePane object
		game = newGame;
	}

	public void setGrid(Square[][] grid) {
		squareList = grid;
	}

	public void updateKeys() {
		// Updates the keyboard to gray keys if the letters have been used
		// Goes through each key and each letter used in a guess and
		// Updates the right key accordingly
		String letter;
		for (int i = 0; i < 5; i++) {
			switch (squareList[game.getRow()][i].getStatus()) {
			case "Wrong": // grey if wrong
				letter = squareList[game.getRow()][i].getText();
				for (Button keys : keyList) {
					if ((keys.getText().equals(letter)) && !keys.getStyle().contains("-fx-background-color: green")
							&& !keys.getStyle().contains("-fx-background-color: gold")) {
						keys.setStyle(
								"-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: gray;");
					}
				}
				break;
			case "Present": // gold if present in word
				letter = squareList[game.getRow()][i].getText();
				for (Button keys : keyList) {
					if ((keys.getText().equals(letter)) && !keys.getStyle().contains("-fx-background-color: green")) {
						keys.setStyle(
								"-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: gold;");
					}
				}
				break;
			case "Correct": // green if in correct spot
				letter = squareList[game.getRow()][i].getText();
				for (Button keys : keyList) {
					if ((keys.getText().equals(letter))) {
						keys.setStyle(
								"-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: green;");
					}
				}
				break;
			}
		}
	}

	public void createList() {
		// Creates an arraylist of the key buttons
		// Indexes:
		// 0 = q, 1 = w, 2 = e, 3 = r, 4 = t, 5 = y, 6 = u, 7 = i, 8 = o, 9 = p
		// 10 = a, 11 = s, 12 = d, 13 = f, 14 = g, 15 = h, 16 = j, 17 = j, 18 = k,
		// 19 = l, 20 = z, 21 = x, 22 = c, 23 = v, 24 = b, 25 = n, 26 = m

		// 1st row of keys
		keyList.add(qKey);
		keyList.add(wKey);
		keyList.add(eKey);
		keyList.add(rKey);
		keyList.add(tKey);
		keyList.add(yKey);
		keyList.add(uKey);
		keyList.add(iKey);
		keyList.add(oKey);
		keyList.add(pKey);

		// 2nd row of keys
		keyList.add(aKey);
		keyList.add(sKey);
		keyList.add(dKey);
		keyList.add(fKey);
		keyList.add(gKey);
		keyList.add(hKey);
		keyList.add(jKey);
		keyList.add(kKey);
		keyList.add(lKey);

		// 3rd row of keys
		keyList.add(zKey);
		keyList.add(xKey);
		keyList.add(cKey);
		keyList.add(vKey);
		keyList.add(bKey);
		keyList.add(nKey);
		keyList.add(mKey);
	}

	public void setDarkMode() {
		this.setStyle("-fx-background-color: black;");
		
		backKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: grey;");
		backKey.setTextFill(Color.WHITE);
		
		enterKey.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: grey;");
		enterKey.setTextFill(Color.WHITE);
		
		for (Button key: keyList) {
			key.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: grey;");
			key.setTextFill(Color.WHITE);
			
		}
		
	}

}
