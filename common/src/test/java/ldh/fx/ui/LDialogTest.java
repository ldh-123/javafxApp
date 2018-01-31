package ldh.fx.ui;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ldh.fx.component.LDialog;
import ldh.fx.component.LdhWindow;

/**
 * Created by ldh on 2018/1/31.
 */
public class LDialogTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LDialog lWindow = new LDialog();
        lWindow.initModel(primaryStage, true);
//        lWindow.buildResizable(primaryStage);
        lWindow.setContentPane(new Label("sadfasdfasdfas"));
//        Scene scene = new Scene(lWindow, 1200, 600);
//        primaryStage.setScene(scene);
//        primaryStage.show();
        lWindow.getScene().getStylesheets().add(LdhWindowTest2.class.getResource("/component/LDialog.css").toExternalForm());
        lWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
