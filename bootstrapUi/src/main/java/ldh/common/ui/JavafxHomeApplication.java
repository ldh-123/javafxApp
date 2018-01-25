package ldh.common.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import ldh.fx.StageUtil;
import ldh.fx.ui.util.RegionUtil;
import org.controlsfx.control.GridView;

public class JavafxHomeApplication extends Application {

    public void start(Stage primaryStage) throws Exception {
        StageUtil.STAGE = primaryStage;
        Region node = FXMLLoader.load(JavafxHomeApplication.class.getResource("/fxml/Home.fxml"));
        Scene scene = new Scene(node, 1200, 700);
        RegionUtil.sizeRegionWhenSceneChange(node, scene);
        scene.getStylesheets().add(this.getClass().getResource("/css/Common.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
