package chart;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.*;

/**
 * Created by xiongfei.lei on 2017/5/23.
 */
public class ChartTest extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis,yAxis);

        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data
        Random rand = new Random();

        TreeMap<Integer, Integer> data = new TreeMap();

        for(int i = 0; i < 3; i++)
        {
            data.put(rand.nextInt(51), rand.nextInt(51));
        }

        Set set = data.entrySet();
        Iterator i = set.iterator();
        while(i.hasNext())
        {
            Map.Entry me = (Map.Entry)i.next();
            System.out.println(me.getKey() + " - " + me.getValue());
            series.getData().add(new XYChart.Data(me.getKey(), me.getValue()));
        }

        lineChart.getData().add(series);

        Set<Node> node = lineChart.lookupAll(".default-color0.chart-line-symbol.series0.");
        node.forEach((element) -> {
            element.setOnMouseEntered((MouseEvent event1) -> {
                double x = event1.getScreenX();
                double y = event1.getScreenY();
                List keys = new ArrayList(data.keySet());
                System.out.println("over value!");
                if (event1.getSource().toString().contains("data0")) {
                    Tooltip t = new Tooltip(data.get(Integer.parseInt(keys.get(0).toString())).toString());
                    Tooltip.install(element, t);
                } else if (event1.getSource().toString().contains("data1")) {
                    Tooltip t = new Tooltip(data.get(Integer.parseInt(keys.get(1).toString())).toString());
                    Tooltip.install(element, t);
                } else if (event1.getSource().toString().contains("data2")) {
                    Tooltip t = new Tooltip(data.get(Integer.parseInt(keys.get(2).toString())).toString());
                    Tooltip.install(element, t);
                }
            });
        });

        Scene scene  = new Scene(lineChart,800,600);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
