package page;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ldh.common.ui.node.ChartContent;

import java.io.IOException;

public class HomePageTest extends Application {

    public static Stage STAGE = null;

    public void start(Stage primaryStage) throws Exception {
        Parent root = null;
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/LNavPane.fxml"));
            root = fxmlloader.load();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll("/component/LNavPane.css");
        scene.getStylesheets().addAll("/css/test.css");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
        STAGE = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
