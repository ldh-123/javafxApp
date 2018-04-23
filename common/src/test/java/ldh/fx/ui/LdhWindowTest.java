package ldh.fx.ui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ldh.fx.component.DialogModel;
import ldh.fx.ui.util.PageUtil;

/**
 * Created by ldh on 2018/1/30.
 */
public class LdhWindowTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ldh.fx.component.LWindow lWindow = new ldh.fx.component.LWindow();
        lWindow.initDialogModel(primaryStage, DialogModel.Application);
//        lWindow.buildResizable(primaryStage);
        lWindow.setContentPane(new Label("sadfasdfasdfas"));
//        Scene scene = new Scene(lWindow, 1200, 600);
//        primaryStage.setScene(scene);
//        primaryStage.show();
        lWindow.getScene().getStylesheets().add(LdhWindowTest.class.getResource("/component/LDialog.css").toExternalForm());
        lWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
