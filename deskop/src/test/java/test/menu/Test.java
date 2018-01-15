package test.menu;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ldh.fx.util.DialogUtil;

/**
 * Created by ldh on 2018/1/15.
 */
public class Test extends Application {

//    ContextMenuExample contextMenu = new ContextMenuExample();

    @Override
    public void start(Stage primaryStage) {
        try {

            // Root
            BorderPane root = new BorderPane();
            ContextMenu contextMenu = buildContextMenu();
            root.setOnMouseClicked(e -> contextMenu.show(primaryStage, e.getScreenX(), e.getScreenY()));

            // Scene
            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

            // Stage
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ContextMenu buildContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setAutoHide(true);

        MenuItem fullScreen = new MenuItem("进入全屏");
        MenuItem notFullScreen = new MenuItem("退出全屏");
        MenuItem about = new MenuItem("关于");
        about.setOnAction(e->{
            DialogUtil.info("javafx demo", "javafx desktop demo", 300d, 150d);
        });
        contextMenu.getItems().addAll(fullScreen, notFullScreen, about);
        return contextMenu;
    }

    public static void main(String[] args) {
        launch(args);
    }
}