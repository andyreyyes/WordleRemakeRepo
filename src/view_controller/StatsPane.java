package view_controller;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.UserAccount;
import model.WordleGame;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * This class handles a stats pane
 */
public class StatsPane extends BorderPane {

	private Label statsLabel = new Label("STATISTICS: ");

	private HBox statsContainer = new HBox();
	private VBox mainContainer = new VBox();

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
	private Label currentStreakLabel = new Label("Current");
	private Label streakLabel1 = new Label("streak");
	private Label streakLabel2 = new Label("streak");
	private Label emptyLabel1 = new Label(" ");
	private Label emptyLabel2 = new Label(" ");


	private Label maxStreakLabel = new Label("Max");
	
	NumberAxis xAxis = new NumberAxis();
	CategoryAxis yAxis = new CategoryAxis();
    BarChart<Number, String> barChart = new BarChart<>(xAxis, yAxis);
    XYChart.Series<Number, String> dataSeries = new XYChart.Series<>();

	
	
	public StatsPane() {
		initializePane();
	}

	private void initializePane() {

		statsLabel.setStyle(
				"-fx-text-fill: black; -fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-font-size: 15px;");

		playedContainer.getChildren().addAll(playedVariable, playedLabel,emptyLabel1);
		winPercentContainer.getChildren().addAll(winPercentVariable, winPercentLabel,emptyLabel2);
		currentStreakContainer.getChildren().addAll(currentStreakVariable, currentStreakLabel,streakLabel2);
		maxStreakContainer.getChildren().addAll(maxStreakvariable, maxStreakLabel,streakLabel1);

		playedContainer.setPadding(new Insets(0, 15, 0, 0));
		winPercentContainer.setPadding(new Insets(0, 15, 0, 0));
		currentStreakContainer.setPadding(new Insets(0, 15, 0, 0));
		
		playedContainer.setAlignment(Pos.CENTER);
		winPercentContainer.setAlignment(Pos.CENTER);
		currentStreakContainer.setAlignment(Pos.CENTER);
		maxStreakContainer.setAlignment(Pos.CENTER);

		playedLabel.setStyle("-fx-font-size: 12.5px");
		winPercentLabel.setStyle("-fx-font-size: 12.5px");
		currentStreakLabel.setStyle("-fx-font-size: 12.5px");
		maxStreakLabel.setStyle("-fx-font-size: 12.5px");
		streakLabel1.setStyle("-fx-font-size: 12.5px");
		streakLabel2.setStyle("-fx-font-size: 12.5px");
		

		playedVariable.setStyle("-fx-font-size: 30px; -fx-font-weight: bold");
		winPercentVariable.setStyle("-fx-font-size: 30px;-fx-font-weight: bold");
		currentStreakVariable.setStyle("-fx-font-size: 30px;-fx-font-weight: bold");
		maxStreakvariable.setStyle("-fx-font-size: 30px;-fx-font-weight: bold");

		statsContainer.setAlignment(Pos.CENTER);
		statsContainer.getChildren().addAll(playedContainer, winPercentContainer, currentStreakContainer,
				maxStreakContainer);

		mainContainer.getChildren().addAll(statsLabel, statsContainer,barChart);
		mainContainer.setAlignment(Pos.CENTER);

        dataSeries.getData().add(new XYChart.Data<>(0, "1"));
        dataSeries.getData().add(new XYChart.Data<>(0, "2"));
        dataSeries.getData().add(new XYChart.Data<>(0, "3"));
        dataSeries.getData().add(new XYChart.Data<>(0, "4"));
        dataSeries.getData().add(new XYChart.Data<>(0, "5"));
        dataSeries.getData().add(new XYChart.Data<>(0, "6"));

        

        barChart.getData().add(dataSeries);
        
		
		
        yAxis.setTickMarkVisible(false);
        xAxis.setTickMarkVisible(false);

        xAxis.setTickLabelsVisible(false);
        xAxis.setMinorTickVisible(false);
        xAxis.setVisible(false);
        barChart.setHorizontalGridLinesVisible(false); 
        barChart.setLegendVisible(false);
        barChart.setVerticalGridLinesVisible(false);
        barChart.setBarGap(2);

        
        // Apply styles directly in Java code
        for (Data<Number, String> data : dataSeries.getData()) {
        	if(data != null) {
        		data.getNode().setStyle("-fx-bar-fill: #787c7e");
            }
        }
        barChart.setMaxHeight(300);
        barChart.setMaxWidth(300);
		this.setCenter(mainContainer);

	}

	public void updateStats(UserAccount user) {
		if (user != null) {
			
	        dataSeries.getData().clear();
			playedVariable.setText("" + user.getGamesPlayed());
			winPercentVariable.setText("" + user.getWinPercentage());
			currentStreakVariable.setText("" + user.getCurStreak());
			maxStreakvariable.setText("" + user.getMaxStreak());
			
			int[] guessDist = user.getGuessDist();
			int prevWin = user.getLastGameGuess();

			for(int i = 1; i < 7; i++) {
			    dataSeries.getData().add(new XYChart.Data<>(guessDist[i], String.valueOf(i)));
			}
			// Apply styles directly in Java code
	        for (Data<Number, String> data : dataSeries.getData()) {
	        	if(data != null) {
	        		if(data.getYValue().equals(String.valueOf(prevWin))) {
	        			data.getNode().setStyle("-fx-bar-fill: #538d4e");
	        		}
	        		else {
	        			data.getNode().setStyle("-fx-bar-fill: #787c7e");
	        		}
	            }
	        }
			xAxis.setUpperBound(user.getGamesPlayed());
		}else {
	        dataSeries.getData().clear();

			playedVariable.setText("0");
			winPercentVariable.setText("0");
			currentStreakVariable.setText("0");
			maxStreakvariable.setText("0");
		}
	}

}
