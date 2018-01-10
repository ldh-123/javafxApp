package chart;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by xiongfei.lei on 2017/5/19.
 */
public class PieChartSample extends Application {

    @Override public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Imported Fruits");
        stage.setWidth(500);
        stage.setHeight(500);

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Oranges1", 25),
                        new PieChart.Data("Plums1", 10),
                        new PieChart.Data("Pears1", 22),
                        new PieChart.Data("Oranges2", 25),
                        new PieChart.Data("Plums2", 10),
                        new PieChart.Data("Pears2", 22),
                        new PieChart.Data("Apples", 30));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Imported Fruits");

        final Label caption = new Label("");
        caption.setTextFill(Color.BLANCHEDALMOND);
        caption.setStyle("-fx-font: 24 arial;");

        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf(data.getPieValue()) + "%");
                        }
                    });
        }

        ((Group) scene.getRoot()).getChildren().addAll(chart, caption);
        stage.setScene(scene);
        stage.show();


        try {
            Thread.sleep(1000);
            SnapshotParameters parameters = new SnapshotParameters();
            WritableImage wi = new WritableImage(500, 500);
            WritableImage snapshot = chart.snapshot(new SnapshotParameters(), wi);

            File output = new File("f:/snapshot" + new Date().getTime() + ".png");
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);

            Thread.sleep(100);
        } catch (Exception ex) {
            Logger.getLogger(PieChartSample.class.getName()).log(Level.SEVERE, null, ex);
        }
//        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
