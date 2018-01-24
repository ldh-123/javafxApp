package ldh.descktop.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChartContent extends VBox implements Initializable{

    @FXML private LineChart graph;

    public ChartContent() {
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/page/ChartContent.fxml"));
            fxmlloader.setRoot(this);
            fxmlloader.setController(this);
            fxmlloader.load();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML public void chartContentClick() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data
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

        graph.getData().add(series);

        Node line = graph.lookup(".chart-series-line");
        line.setStyle("-fx-stroke-width:5;");
    }
}
