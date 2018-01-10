package chart.polygonChart;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by xiongfei.lei on 2017/9/29.
 */
public class ChartTest extends Application {
    @Override
    public void start(Stage primaryStage) {
        String categories[] = { "  Category A", "Category B", "Category C", "Category D", "Category E", "Category F"};
        PolygonChart chart = new PolygonChart(485, 400, 4, categories, 0, 10);

        double values[] = { 5, 2, 8, 6, 9, 7 };
        chart.setValues(values);

        Pane pane = new Pane(chart);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
