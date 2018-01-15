package ldh.fx.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ldh.fx.component.PageablePane;

public class PageablePaneTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vbox = new VBox(new PageablePane());
        Scene scene = new Scene(vbox, 1200, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
