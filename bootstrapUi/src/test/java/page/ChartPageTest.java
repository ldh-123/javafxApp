package page;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ldh.common.ui.node.ChartContent;
import ldh.common.ui.node.FormContent;

public class ChartPageTest extends Application {

    public static Stage STAGE = null;

    public void start(Stage primaryStage) throws Exception {
        ChartContent loginPage = new ChartContent();
        Scene scene = new Scene(loginPage);
//        scene.getStylesheets().addAll("bootstrapfx.css");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
        STAGE = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
