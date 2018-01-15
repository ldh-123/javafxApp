package ldh.common.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ldh.fx.StageUtil;

public class CvsApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageUtil.STAGE = primaryStage;
        Parent node = FXMLLoader.load(JavafxHomeApplication.class.getResource("/fxml/Cvs.fxml"));
        Scene scene = new Scene(node, 1200, 700);
        scene.getStylesheets().add(this.getClass().getResource("/css/Cvs.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("CVS查看器");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
