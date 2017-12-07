package ldh.common.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.controlsfx.control.GridView;

/**
 * Created by xiongfei.lei on 2017/11/14.
 */
public class JavafxHomeApplication extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent node = FXMLLoader.load(JavafxHomeApplication.class.getResource("/fxml/Home.fxml"));
        Scene scene = new Scene(node, 1200, 700);
        scene.getStylesheets().add(this.getClass().getResource("/css/Common.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        BorderPane bp = new BorderPane();


    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Created by xiongfei.lei on 2017/11/14.
     */
    public static class JavafxMainApplication extends Application {

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
}
