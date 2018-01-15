package chart;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Objects;

public class LineChartSample2 extends Application {

    @Override public void start(Stage stage) {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");

        final LineChartWithMarkers<Number,Number> lineChart = new LineChartWithMarkers<Number,Number>(xAxis,yAxis);

        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");

        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));

        lineChart.getData().add(series);

        XYChart.Data<Number, Number> horizontalMarker = new XYChart.Data<>(0, 25);
        lineChart.addHorizontalValueMarker(horizontalMarker);

        XYChart.Data<Number, Number> verticalMarker = new XYChart.Data<>(10, 0);
        lineChart.addVerticalValueMarker(verticalMarker);

        Slider horizontalMarkerSlider = new Slider(yAxis.getLowerBound(), yAxis.getUpperBound(), 0);
        horizontalMarkerSlider.setOrientation(Orientation.VERTICAL);
        horizontalMarkerSlider.setShowTickLabels(true);
        horizontalMarkerSlider.valueProperty().bindBidirectional(horizontalMarker.YValueProperty());
        horizontalMarkerSlider.minProperty().bind(yAxis.lowerBoundProperty());
        horizontalMarkerSlider.maxProperty().bind(yAxis.upperBoundProperty());

        Slider verticalMarkerSlider = new Slider(xAxis.getLowerBound(), xAxis.getUpperBound(), 0);
        verticalMarkerSlider.setOrientation(Orientation.HORIZONTAL);
        verticalMarkerSlider.setShowTickLabels(true);
        verticalMarkerSlider.valueProperty().bindBidirectional(verticalMarker.XValueProperty());
        verticalMarkerSlider.minProperty().bind(xAxis.lowerBoundProperty());
        verticalMarkerSlider.maxProperty().bind(xAxis.upperBoundProperty());

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter( lineChart);
        borderPane.setTop(verticalMarkerSlider);
        borderPane.setRight(horizontalMarkerSlider);

        Scene scene  = new Scene(borderPane,800,600);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class LineChartWithMarkers<X,Y> extends LineChart {

        private ObservableList<Data<X, Y>> horizontalMarkers;
        private ObservableList<Data<X, Y>> verticalMarkers;

        public LineChartWithMarkers(Axis<X> xAxis, Axis<Y> yAxis) {
            super(xAxis, yAxis);
            horizontalMarkers = FXCollections.observableArrayList(data -> new ObjectProperty[] {data.YValueProperty()});
            horizontalMarkers.addListener((InvalidationListener) observable -> layoutPlotChildren());
            verticalMarkers = FXCollections.observableArrayList(data -> new ObjectProperty[] {data.XValueProperty()});
            verticalMarkers.addListener((InvalidationListener)observable -> layoutPlotChildren());
        }

        public void addHorizontalValueMarker(Data<X, Y> marker) {
            Objects.requireNonNull(marker, "the marker must not be null");
            if (horizontalMarkers.contains(marker)) return;
            Line line = new Line();
            marker.setNode(line );
            getPlotChildren().add(line);
            horizontalMarkers.add(marker);
        }

        public void removeHorizontalValueMarker(Data<X, Y> marker) {
            Objects.requireNonNull(marker, "the marker must not be null");
            if (marker.getNode() != null) {
                getPlotChildren().remove(marker.getNode());
                marker.setNode(null);
            }
            horizontalMarkers.remove(marker);
        }

        public void addVerticalValueMarker(Data<X, Y> marker) {
            Objects.requireNonNull(marker, "the marker must not be null");
            if (verticalMarkers.contains(marker)) return;
            Line line = new Line();
            marker.setNode(line );
            getPlotChildren().add(line);
            verticalMarkers.add(marker);
        }

        public void removeVerticalValueMarker(Data<X, Y> marker) {
            Objects.requireNonNull(marker, "the marker must not be null");
            if (marker.getNode() != null) {
                getPlotChildren().remove(marker.getNode());
                marker.setNode(null);
            }
            verticalMarkers.remove(marker);
        }


        @Override
        protected void layoutPlotChildren() {
            super.layoutPlotChildren();
            for (Data<X, Y> horizontalMarker : horizontalMarkers) {
                Line line = (Line) horizontalMarker.getNode();
                line.setStartX(0);
                line.setEndX(getBoundsInLocal().getWidth());
                line.setStartY(getYAxis().getDisplayPosition(horizontalMarker.getYValue()) + 0.5); // 0.5 for crispness
                line.setEndY(line.getStartY());
                line.toFront();
            }
            for (Data<X, Y> verticalMarker : verticalMarkers) {
                Line line = (Line) verticalMarker.getNode();
                line.setStartX(getXAxis().getDisplayPosition(verticalMarker.getXValue()) + 0.5);  // 0.5 for crispness
                line.setEndX(line.getStartX());
                line.setStartY(0d);
                line.setEndY(getBoundsInLocal().getHeight());
                line.toFront();
            }
        }

    }
}
