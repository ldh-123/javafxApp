package control;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Puhui on 2016/9/25.
 */
public class MenuBarTest extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        ToolBar toolBar = new ToolBar();
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem mi1 = new MenuItem("Open");
        menu.getItems().add(mi1);
        menuBar.getMenus().addAll(menu);
        toolBar.getItems().add(menuBar);
        borderPane.setTop(toolBar);
//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Socket Chat : Client version 0.3");
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResource("img/music.jpg").toString()));
        Scene mainScene = new Scene(borderPane, 850, 620);
        primaryStage.setResizable(true);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
