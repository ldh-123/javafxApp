package ldh.fx.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ldh.fx.StageUtil;
import ldh.fx.component.DialogModel;
import ldh.fx.component.LDialog;

/**
 * Created by ldh on 2018/1/13.
 */
public class LdhPopupDialogTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        StageUtil.STAGE = stage;
        LDialog window = new LDialog(stage, "adas", 400d, 200d);
//        window.setWindowMin(false);
//        window.setWindowMax(false);
//        window.show();
        Button button = new Button("show");
        button.setOnAction(e->window.show());
        Scene scene = new Scene(button, 1200, 600);
        scene.getStylesheets().add("/component/LDialog.css");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
