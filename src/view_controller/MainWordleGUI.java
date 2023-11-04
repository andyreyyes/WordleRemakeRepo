package view_controller;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWordleGUI extends Application {

	private BorderPane pane;
	
	private LoginAndCreatePane loginView;

	private MenuBar menuBar;
	private Menu home;
	private Menu stats;
	private Menu settings;

	private MenuItem login;
	private MenuItem game;
	
	private WordleGamePane gamePane;

	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		layoutGUI();
		registerListeners();
		Scene scene = new Scene(pane, 800, 800);
		stage.setScene(scene);
		stage.show();

	}

	private void layoutGUI() {
		loginView = new LoginAndCreatePane();
		pane = new BorderPane();
		menuBar = new MenuBar();
		
		home = new Menu("Home");
		game = new MenuItem("Game");
		login = new MenuItem("Login");
		home.getItems().addAll(login, game);
		
		stats = new Menu("Stats");
		settings = new Menu("Settings");
		
		menuBar.getMenus().addAll(home, stats, settings);
		pane.setTop(menuBar);
		
		gamePane = new WordleGamePane();
		pane.setCenter(gamePane);
		
	}
	
	
	private void registerListeners() {
		login.setOnAction((arg0) -> {
			pane.setCenter(loginView);
		});
		game.setOnAction((arg0) -> {
			pane.setCenter(gamePane);
		});
	}
	
	

}
