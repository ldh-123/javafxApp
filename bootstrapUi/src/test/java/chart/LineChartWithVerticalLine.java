package chart;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Random;

public class LineChartWithVerticalLine extends Application {

    @Override
    public void start(Stage primaryStage) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.getData().add(createSeries());

        Pane chartHolder = new Pane();
        chartHolder.getChildren().add(chart);


        DoubleProperty lineX = new SimpleDoubleProperty();
        Slider slider = new Slider();
        slider.minProperty().bind(xAxis.lowerBoundProperty());
        slider.maxProperty().bind(xAxis.upperBoundProperty());

        slider.setPadding(new Insets(20));

        lineX.bind(slider.valueProperty());

        chartHolder.getChildren().add(createVerticalLine(chart, xAxis, yAxis, chartHolder, lineX));

        BorderPane root = new BorderPane(chartHolder, null, null, slider, null);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Line createVerticalLine(XYChart<Number, Number> chart, NumberAxis xAxis, NumberAxis yAxis, Pane container, ObservableDoubleValue x) {
        Line line = new Line();
        line.startXProperty().bind(Bindings.createDoubleBinding(() -> {
                    double xInAxis = xAxis.getDisplayPosition(x.get());
                    Point2D pointInScene = xAxis.localToScene(xInAxis, 0);
                    double xInContainer = container.sceneToLocal(pointInScene).getX();
                    return xInContainer ;
                },
                x,
                chart.boundsInParentProperty(),
                xAxis.lowerBoundProperty(),
                xAxis.upperBoundProperty()));
        line.endXProperty().bind(line.startXProperty());
        line.startYProperty().bind(Bindings.createDoubleBinding(() -> {
                    double lowerY = yAxis.getDisplayPosition(yAxis.getLowerBound());
                    Point2D pointInScene = yAxis.localToScene(0, lowerY);
                    double yInContainer = container.sceneToLocal(pointInScene).getY();
                    return yInContainer ;
                },
                chart.boundsInParentProperty(),
                yAxis.lowerBoundProperty()));
        line.endYProperty().bind(Bindings.createDoubleBinding(() -> {
                    double upperY = yAxis.getDisplayPosition(yAxis.getUpperBound());
                    Point2D pointInScene = yAxis.localToScene(0, upperY);
                    double yInContainer = container.sceneToLocal(pointInScene).getY();
                    return yInContainer ;
                },
                chart.boundsInParentProperty(),
                yAxis.lowerBoundProperty()));

        line.visibleProperty().bind(
                Bindings.lessThan(x, xAxis.lowerBoundProperty())
                        .and(Bindings.greaterThan(x, xAxis.upperBoundProperty())).not());

        return line ;
    }

    private XYChart.Series<Number, Number> createSeries() {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Data");
        Random rng = new Random();
        for (int i=0; i<=20; i++) {
            series.getData().add(new XYChart.Data<>(i, rng.nextInt(101)));
        }
        return series ;
    }

    public static void main(String[] args) {
        launch(args);
    }
}