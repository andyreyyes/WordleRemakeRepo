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
		login = new MenuItem("Account");
		home.getItems().add(login);
		stats = new Menu("Stats");
		settings = new Menu("Settings");
		
		menuBar.getMenus().addAll(home, stats, settings);
		pane.setTop(menuBar);
		
	}
	
	private void setViewTo(Node newView) {
		pane.setCenter(null);
		pane.setCenter((BorderPane)  newView);
	}
	
	private void registerListeners() {
		login.setOnAction((arg0) -> {
			pane.setCenter(loginView);
			
			
		});
	}
	
	

}
