package ldh.descktop.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ldh.descktop.page.FormContent;

public class FormPageTest extends Application {

    public static Stage STAGE = null;

    public void start(Stage primaryStage) throws Exception {
        FormContent loginPage = new FormContent();
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
