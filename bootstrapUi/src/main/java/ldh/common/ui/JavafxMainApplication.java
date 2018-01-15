package ldh.common.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.controlsfx.control.GridView;


public class JavafxMainApplication extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent node = FXMLLoader.load(JavafxMainApplication.class.getResource("/fxml/Main.fxml"));
        Scene scene = new Scene(node, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
        ScrollPane s = new ScrollPane();
        s.getStyleClass().add("active");
        TilePane flowPane = new TilePane();
        flowPane.setPrefColumns(4);
        GridView gv = new GridView();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
