package chart;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


public class LineChartSample3 extends Application {

    @Override public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        yAxis.setLabel("adfadfasdf");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data
        double d1 =0.0;
        double d2 =200.0;
        double d=0.3; //取点间隔
        for(int i=0;i<1999;i++){
            d1 = d1 + d;
            d2 = 200+70*Math.sin(d1/20);
            series.getData().add(new XYChart.Data(d1, d2));
            i++;
        }

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);

        Node line = lineChart.lookup(".chart-series-line");
//        line.setStyle("-fx-stroke-width:5;");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)     {
        launch(args);
    }
}
