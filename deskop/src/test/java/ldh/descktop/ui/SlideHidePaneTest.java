package ldh.descktop.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.HiddenSidesPane;

/**
 * Created by ldh on 2018/1/16.
 */
public class SlideHidePaneTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        HiddenSidesPane pane = new HiddenSidesPane();
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new Label("top"));
        borderPane.setBottom(new Label("bottom"));
        borderPane.setCenter(new Label("center"));
        borderPane.setLeft(new Label("left"));
        pane.setContent(borderPane);

        BorderPane borderPane2 = new BorderPane();
        borderPane2.setTop(new Label("top2"));
        borderPane2.setBottom(new Label("bottom2"));
        borderPane2.setCenter(new Label("center"));
        borderPane2.setLeft(new Label("left2"));
        pane.setTop(borderPane2);

        Scene scene = new Scene(pane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("demo");
        primaryStage.show();
    }

}
