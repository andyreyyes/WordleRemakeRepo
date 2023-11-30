package view_controller;

import java.awt.event.KeyEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import model.UserAccount;


public class StatsWinPopup {

	private static VBox window = new VBox();

	private static Label statsLabel = new Label("STATISTICS: ");
	private static Label closeLabel = new Label("Click on window or press a button to close");

	private static HBox statsContainer = new HBox();
	private static VBox mainContainer = new VBox();

	private static VBox playedContainer = new VBox();
	private static VBox winPercentContainer = new VBox();
	private static VBox currentStreakContainer = new VBox();
	private static VBox maxStreakContainer = new VBox();

	private static Label playedVariable = new Label("0");
	private static Label winPercentVariable = new Label("0");
	private static Label currentStreakVariable = new Label("0");
	private static Label maxStreakvariable = new Label("0");

	private static Label playedLabel = new Label("Played");
	private static Label winPercentLabel = new Label("Win %");
	private static Label currentStreakLabel = new Label("Current");
	private static Label streakLabel1 = new Label("streak");
	private static Label streakLabel2 = new Label("streak");
	private static Label emptyLabel1 = new Label(" ");
	private static Label emptyLabel2 = new Label(" ");


	private static Label maxStreakLabel = new Label("Max");

	private static boolean set = false;

	static NumberAxis xAxis = new NumberAxis();
	static CategoryAxis yAxis = new CategoryAxis();
	static BarChart<Number, String> barChart = new BarChart<>(xAxis, yAxis);
	static XYChart.Series<Number, String> dataSeries = new XYChart.Series<>();

	static Stage popup = new Stage();
	
	static Scene scene;

	public static void initialize() {
		if (!set) {
			set = true;
			initializePane();
			scene = new Scene(window, 350, 375);

			popup.setResizable(false);

			popup.setScene(scene);
			popup.initModality(Modality.APPLICATION_MODAL);
		}
	}

	public static void popUp() {
		registerListeners();
		popup.show();
	}

	private static void initializePane() {
		statsLabel.setStyle(
				"-fx-text-fill: black; -fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-font-size: 15px;");
		closeLabel.setStyle(
				"-fx-text-fill: black; -fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-font-size: 15px;");

		playedContainer.getChildren().addAll(playedVariable, playedLabel, emptyLabel1);
		winPercentContainer.getChildren().addAll(winPercentVariable, winPercentLabel, emptyLabel2);
		currentStreakContainer.getChildren().addAll(currentStreakVariable, currentStreakLabel, streakLabel2);
		maxStreakContainer.getChildren().addAll(maxStreakvariable, maxStreakLabel, streakLabel1);

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

		mainContainer.getChildren().addAll(statsLabel, statsContainer, barChart, closeLabel);
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
			if (data != null) {
				data.getNode().setStyle("-fx-bar-fill: #787c7e");
			}
		}
		barChart.setMaxHeight(300);
		barChart.setMaxWidth(300);
		if (window.getChildren().isEmpty()) {
			window.getChildren().add(mainContainer);
		} else {
			window.getChildren().clear();
			window.getChildren().add(mainContainer);
		}

	}

	public static void updateStats(UserAccount user) {


		if (user != null) {
			
			statsLabel.setText("STATISTICS: ");
			dataSeries.getData().clear();
			playedVariable.setText("" + user.getGamesPlayed());
			winPercentVariable.setText("" + user.getWinPercentage());
			currentStreakVariable.setText("" + user.getCurStreak());
			maxStreakvariable.setText("" + user.getMaxStreak());

			int[] guessDist = user.getGuessDist();
			int prevWin = user.getLastGameGuess();

			for (int i = 1; i < 7; i++) {
				dataSeries.getData().add(new XYChart.Data<>(guessDist[i], String.valueOf(i)));
			}
			// Apply styles directly in Java code
			for (Data<Number, String> data : dataSeries.getData()) {
				if (data != null) {
					if (data.getYValue().equals(String.valueOf(prevWin))) {
						data.getNode().setStyle("-fx-bar-fill: #538d4e");
					} else {
						data.getNode().setStyle("-fx-bar-fill: #787c7e");
					}
				}
			}
			xAxis.setUpperBound(user.getGamesPlayed());
		} else {
			statsLabel.setText("Create an account or login to see your stats");
			dataSeries.getData().clear();
			playedVariable.setText("0");
			winPercentVariable.setText("0");
			currentStreakVariable.setText("0");
			maxStreakvariable.setText("0");
		}
	}
	
	public static void registerListeners() {
		
		scene.setOnMousePressed((event) -> {
			popup.close();
		});
		
		scene.setOnKeyPressed((event) -> {
			popup.close();
		});
	}

	public static void setDarkMode() {
		barChart.lookup(".chart-plot-background").setStyle("-fx-background-color: black;");

		window.setStyle("-fx-background-color: black;");
		statsLabel.setStyle(
				"-fx-text-fill: white; -fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-font-size: 15px;");
		playedLabel.setStyle("-fx-font-size: 12.5px; -fx-text-fill: white;");
		winPercentLabel.setStyle("-fx-font-size: 12.5px; -fx-text-fill: white;");
		currentStreakLabel.setStyle("-fx-font-size: 12.5px; -fx-text-fill: white;");
		maxStreakLabel.setStyle("-fx-font-size: 12.5px; -fx-text-fill: white;");
		streakLabel1.setStyle("-fx-font-size: 12.5px; -fx-text-fill: white;");
		streakLabel2.setStyle("-fx-font-size: 12.5px; -fx-text-fill: white;");

		playedVariable.setStyle("-fx-text-fill: white");
		winPercentVariable.setStyle("-fx-text-fill: white");
		currentStreakVariable.setStyle("-fx-text-fill: white");
		maxStreakvariable.setStyle("-fx-text-fill: white");

		xAxis.setStyle("-fx-text-fill: white");

		playedVariable.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: white;");
		winPercentVariable.setStyle("-fx-font-size: 30px;-fx-font-weight: bold; -fx-text-fill: white;");
		currentStreakVariable.setStyle("-fx-font-size: 30px;-fx-font-weight: bold; -fx-text-fill: white;");
		maxStreakvariable.setStyle("-fx-font-size: 30px;-fx-font-weight: bold; -fx-text-fill: white;");

		
		
	}

	public static void setLightMode() {
		// 
		
	}
}
