package view_controller;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.UserAccount;

public class MainWordleGUI extends Application {

	private BorderPane pane;
	
	private LoginAndCreatePane loginView;
	
	private boolean isDarkMode = false;

	private MenuBar menuBar;
	private Menu home;
	private MenuItem stats;
	private Menu settings;
	private Menu more;
	
	private Label title = new Label("		  Briandle");
	private VBox titleBox;

	private MenuItem login;
	private MenuItem game;
	private MenuItem darkMode;
	private MenuItem lightMode;
	private MenuItem newGame;
	
	private WordleGamePane gamePane;
	
	private KeyBoardPane keyboardPane;
	
	private VBox mainGameLayout;

	private StatsPane statsView;

	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		layoutGUI();
		registerListeners();
		Scene scene = new Scene(pane, 800, 800);
		stage.setScene(scene);
		loginView.setupCloseAction(stage);
		stage.show();

	}

	private void layoutGUI() {
		loginView = new LoginAndCreatePane();
		statsView = new StatsPane();
		pane = new BorderPane();
		menuBar = new MenuBar();
		
		pane.setStyle("-fx-background-color: white;");
		
		home = new Menu("Home");
		game = new MenuItem("Game");
		login = new MenuItem("Login");
		home.getItems().addAll(login, game);
		
		more = new Menu("More");
		
		settings = new Menu("Settings");
		
		darkMode = new MenuItem("Dark Mode");
		lightMode = new MenuItem("Light Mode");
		
		newGame = new MenuItem("New Game");
		
		settings.getItems().addAll(darkMode, lightMode, newGame);
		
		stats = new MenuItem("Stats");
		more.getItems().addAll(settings,stats);
		
		menuBar.getMenus().addAll(home, more);
		pane.setTop(menuBar);
		
		
		gamePane = new WordleGamePane();
		 
		keyboardPane = new KeyBoardPane();
		keyboardPane.setGame(gamePane);
		
		titleBox = new VBox();
		
		title.setFont(new Font("Times New Roman", 60));
		
		titleBox.getChildren().add(title);
		
		mainGameLayout = new VBox();
		
		mainGameLayout.getChildren().add(titleBox);
		
		mainGameLayout.getChildren().addAll(gamePane, keyboardPane);
		
		mainGameLayout.setAlignment(Pos.CENTER);
		mainGameLayout.setSpacing(35);
		
		keyboardPane.setGame(gamePane);
		gamePane.setKeyboard(keyboardPane);
		pane.setCenter(mainGameLayout);
	}
	
	
	private void registerListeners() {
		login.setOnAction((arg0) -> {
			pane.setCenter(loginView);
		});
		stats.setOnAction((arg0) ->{
			UserAccount user = loginView.getUser();
			pane.setCenter(statsView);
			statsView.updateStats(user);
		});
		game.setOnAction((arg0) -> {
			UserAccount user = loginView.getUser();
			if(user != null) {
				gamePane.setUser(user);
			}
			else {
				gamePane.removeUser();
			}
			keyboardPane.setGame(gamePane);
			gamePane.setKeyboard(keyboardPane);
			pane.setCenter(mainGameLayout);
		});
		newGame.setOnAction((arg0) -> {
			UserAccount user = loginView.getUser();
			if(user != null) {
				user.addGame(gamePane.getGame());
			}
			
			gamePane = new WordleGamePane();
			keyboardPane = new KeyBoardPane();
			
			if (isDarkMode) {
				gamePane.setDarkMode();
				keyboardPane.setDarkMode();
				pane.setStyle("-fx-background-color: black;");
			}
			
			keyboardPane.setGame(gamePane);
			gamePane.setKeyboard(keyboardPane);
			
			mainGameLayout = new VBox();
			mainGameLayout.getChildren().add(titleBox);
			mainGameLayout.getChildren().addAll(gamePane, keyboardPane);
			
			mainGameLayout.setAlignment(Pos.CENTER);
			mainGameLayout.setSpacing(35);
			
			keyboardPane.setGame(gamePane);
			gamePane.setKeyboard(keyboardPane);
			pane.setCenter(mainGameLayout);
		});
		darkMode.setOnAction((event) -> {
			isDarkMode = true;
			pane.setStyle("-fx-background-color: black;");
			title.setTextFill(Color.WHITE);
			gamePane.setDarkMode();
			
			keyboardPane.setDarkMode();
		});
		lightMode.setOnAction((event) -> {
			isDarkMode = false;
			pane.setStyle("-fx-background-color: white;");
			title.setTextFill(Color.BLACK);
			gamePane.setLightMode();
			keyboardPane.setLightMode();
			
		});
	}
	
	

}
