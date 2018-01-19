package ldh.descktop.test;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ldh.descktop.page.AnimationPane;

/**
 * Created by ldh on 2018/1/12.
 */
public class AnimationPaneTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnimationPane animationPane = new AnimationPane();
        Scene scene = new Scene(animationPane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("demo");
        primaryStage.show();
    }
}
