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
import org.controlsfx.control.GridView;

public class JavafxHomeApplication extends Application {

    public void start(Stage primaryStage) throws Exception {
        StageUtil.STAGE = primaryStage;
        Region node = FXMLLoader.load(JavafxHomeApplication.class.getResource("/fxml/Home.fxml"));
        Scene scene = new Scene(node, 1200, 700);
        scene.widthProperty().addListener((l, o, n)->size(node, scene));
        scene.heightProperty().addListener((l, o, n)->size(node, scene));
        scene.getStylesheets().add(this.getClass().getResource("/css/Common.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void size(Region region, Scene scene) {
        region.setPrefWidth(scene.getWidth());
        region.setPrefHeight(scene.getHeight());
//        region.resize(scene.getWidth(), scene.getHeight());
        region.requestLayout();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
