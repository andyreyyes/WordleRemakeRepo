package view_controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWordleGUI extends Application {

	private BorderPane pane;

	private MenuBar menuBar;
	private Menu home;
	private Menu stats;
	private Menu settings;
	private Menu login;
	
	private WordleGamePane gamePane;

	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		layoutGUI();

		Scene scene = new Scene(pane, 800, 800);
		stage.setScene(scene);
		stage.show();
	}

	private void layoutGUI() {
		pane = new BorderPane();
		menuBar = new MenuBar();
		
		home = new Menu("Home");
		stats = new Menu("Stats");
		settings = new Menu("Settings");
		login = new Menu("Account");
		
		menuBar.getMenus().addAll(home, stats, settings, login);
		pane.setTop(menuBar);
		
		gamePane = new WordleGamePane();
		pane.setCenter(gamePane);
		
	}

}
