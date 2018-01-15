package chart.mychart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainAppLauncher extends Application {

    public static void main(String[] args) {
        Application.launch(MainAppLauncher.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            URL file = getClass().getClassLoader().getResource("fxml/MainApp.fxml");
            Parent root = FXMLLoader.load(file);
            stage.setScene(new Scene(root));
            stage.setTitle("JavaFX Graph Example");
            stage.show();
        } catch (Exception e) {
            System.out.print(e);
            e.printStackTrace();
        }
    }
}
