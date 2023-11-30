package view_controller;

import java.text.DecimalFormat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.UserAccount;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
/**
 * This class creates the statsPane that is used in the MainWordleGUI.
 * 
 * @author Adler Nguyen
 *
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

	/**
	 * This method is the constructor of the class and calls the initializePane.
	 */
	public StatsPane() {
		initializePane();
	}

	/**
	 * This method initializes all of the different components used in the class.
	 */
	private void initializePane() {

		statsLabel.setStyle(
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

		mainContainer.getChildren().addAll(statsLabel, statsContainer, barChart);
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
		this.setCenter(mainContainer);

	}

	/**
	 * This method updates the stats of the current user
	 * 
	 * @param user An UserAccount object that is the current user of the game.
	 */
	public void updateStats(UserAccount user) {

		if (user != null) {

			dataSeries.getData().clear();
			
			playedVariable.setText("" + user.getGamesPlayed());
			DecimalFormat df = new DecimalFormat("#.#");
			winPercentVariable.setText("" + (df.format(user.getWinPercentage() * 100)));
			currentStreakVariable.setText("" + user.getCurStreak());
			maxStreakvariable.setText("" + user.getMaxStreak());

			int[] guessDist = user.getGuessDist();
			int prevWin = user.getLastGameGuess();

			for (int i = 1; i < 7; i++) {
				System.out.println(i +":"+ guessDist[i]);
				dataSeries.getData().add(createData(guessDist[i],String.valueOf(i),prevWin));
			}
			xAxis.setUpperBound(user.getGamesPlayed());
		} else {
			dataSeries.getData().clear();

			playedVariable.setText("0");
			winPercentVariable.setText("0");
			currentStreakVariable.setText("0");
			maxStreakvariable.setText("0");
		}
	}
	/**
 	* Creates a customized XYChart.Data instance with the given x and y values.
 	* The node of the data includes a label with the x value.
 	* If the y value matches the previous win, the bar color is set to green; otherwise, it's set to gray.
	 *
 	* @param x       The x-axis value.
 	* @param y       The y-axis value.
	 * @param prevWin The value indicating the previous win.
 	* @return A customized XYChart.Data instance.
 	*/
	private XYChart.Data createData(Number x, String y,int prevWin) {
		XYChart.Data data =  new XYChart.Data(x, y);
		 
        String text = x.toString();
 
        StackPane node = new StackPane();
        Label label = new Label(text);
        Group group = new Group(label);
        StackPane.setAlignment(group, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(group, new Insets(5, 5, 5, 5));
        node.getChildren().add(group);
        data.setNode(node);
        if (data.getYValue().equals(String.valueOf(prevWin))) {
			data.getNode().setStyle("-fx-bar-fill: #538d4e");
		} else {
			data.getNode().setStyle("-fx-bar-fill: #787c7e");
		}
 
        return data;
		
	}

	/**
	 * This method sets the StatsPane to dark mode.
	 */
	public void setDarkMode() {
		barChart.lookup(".chart-plot-background").setStyle("-fx-background-color: black;");

		this.setStyle("-fx-background-color: black;");
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

	/**
	 * This method sets the StatsPane to light mode.
	 */
	public void setLightMode() {
		this.setStyle("-fx-background-color: white;");

		barChart.lookup(".chart-plot-background").setStyle("-fx-background-color: white;");

		statsLabel.setStyle(
				"-fx-text-fill: black; -fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-font-size: 15px;");
		playedLabel.setStyle("-fx-font-size: 12.5px; -fx-text-fill: black;");
		winPercentLabel.setStyle("-fx-font-size: 12.5px; -fx-text-fill: black;");
		currentStreakLabel.setStyle("-fx-font-size: 12.5px; -fx-text-fill: black;");
		maxStreakLabel.setStyle("-fx-font-size: 12.5px; -fx-text-fill: black;");
		streakLabel1.setStyle("-fx-font-size: 12.5px; -fx-text-fill: black;");
		streakLabel2.setStyle("-fx-font-size: 12.5px; -fx-text-fill: black;");

		playedVariable.setStyle("-fx-text-fill: black");
		winPercentVariable.setStyle("-fx-text-fill: black");
		currentStreakVariable.setStyle("-fx-text-fill: black");
		maxStreakvariable.setStyle("-fx-text-fill: black");

		xAxis.setStyle("-fx-text-fill: black");

		playedVariable.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: black;");
		winPercentVariable.setStyle("-fx-font-size: 30px;-fx-font-weight: bold; -fx-text-fill: black;");
		currentStreakVariable.setStyle("-fx-font-size: 30px;-fx-font-weight: bold; -fx-text-fill: black;");
		maxStreakvariable.setStyle("-fx-font-size: 30px;-fx-font-weight: bold; -fx-text-fill: black;");

	}

}
