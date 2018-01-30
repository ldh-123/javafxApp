package ldh.fx.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ldh.fx.component.LdhWindow;

/**
 * Created by ldh on 2018/1/30.
 */
public class LdhWindowTest2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LdhWindow lWindow = new LdhWindow(primaryStage, false);
        lWindow.buildResizable(lWindow);
        lWindow.setPrefSize(600, 400);
        lWindow.setContentPane(new Label("sadfasdfasdfas"));
        lWindow.setStyle("-fx-background-color: grey");
        Button button = new Button("show popup");
        button.setOnAction(e-> lWindow.show());
        Scene scene = new Scene(button, 1200, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
