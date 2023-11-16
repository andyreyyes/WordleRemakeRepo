package view_controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.UserAccount;
import javafx.scene.layout.HBox;
/**
 * This class handles a stats pane
 */
public class StatsPane extends VBox {
	
	private Label statsLabel = new Label("Statistics: ");
	
	private HBox statsContainer = new HBox();
	
	private VBox playedContainer = new VBox();
	private VBox winPercentContainer = new VBox();
	private VBox currentStreakContainer = new VBox();
	private VBox maxStreakContainer = new VBox();
	
	private Label playedVariable = new Label("0");
	private Label winPercentVariable = new Label("0");
	private Label currentStreakVariable = new Label("0");
	private Label maxStreakvariable = new Label("0");
	
	private Label playedLabel = new Label("Played");
	private Label winPercentLabel = new Label("Win %");
	private Label currentStreakLabel = new Label("Current Streak");
	private Label maxStreakLabel = new Label("Max Streak");


	public StatsPane() {
		initializePane();
	}

	private void initializePane() {
		
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setSpacing(10);
		
		playedContainer.getChildren().addAll(playedVariable,playedLabel);
		winPercentContainer.getChildren().addAll(winPercentVariable,winPercentLabel);
		currentStreakContainer.getChildren().addAll(currentStreakVariable,currentStreakLabel);
		maxStreakContainer.getChildren().addAll(maxStreakvariable,maxStreakLabel);
		statsContainer.setAlignment(Pos.CENTER);
		statsContainer.getChildren().addAll(playedContainer,winPercentContainer,currentStreakContainer,maxStreakContainer);
		this.getChildren().addAll(statsLabel,statsContainer);
		this.setAlignment(Pos.CENTER);

	}
	public void updateStats(UserAccount user) {
		if(user != null) {
		playedVariable.setText("" +user.getGamesPlayed());
		winPercentVariable.setText(""+user.getWinPercentage());
		currentStreakVariable.setAccessibleText(""+user.getCurStreak());
		maxStreakvariable.setText(""+user.getMaxStreak());
		}
	}

}
