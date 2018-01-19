package ldh.fx.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ldh.fx.StageUtil;
import ldh.fx.component.LdhDialog;
import ldh.fx.component.LdhPopupDialog;

/**
 * Created by ldh on 2018/1/13.
 */
public class LdhPopupDialogTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        StageUtil.STAGE = stage;
        LdhPopupDialog window = new LdhPopupDialog("adas", 400d, 200d, true);
        window.setWindowMin(false);
        window.setWindowMax(false);
//        window.show();
        Button button = new Button("show");
        button.setOnAction(e->window.show());
        Scene scene = new Scene(button, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
