package chart.core;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Test extends Application{
	@Override
	public void start(Stage primaryStage) {
		ObservableList<SemiCircleChart.Data> dataList = FXCollections.observableArrayList(
				new SemiCircleChart.Data("data1", 1, Color.RED),
				new SemiCircleChart.Data("data2", 2, Color.GREEN),
				new SemiCircleChart.Data("data3", 3, Color.BLUE));

		SemiCircleChart chart = new SemiCircleChart(dataList, 500, 500, 500,100,2);

		Pane pane = new Pane(chart);
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
