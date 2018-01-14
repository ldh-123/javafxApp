package ldh.fx.ui;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ldh.fx.component.LdhDialog;
import ldh.fx.component.LdhWindow;

/**
 * Created by ldh on 2018/1/13.
 */
public class LdhDialogTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        LdhDialog window = new LdhDialog("adas", 400d, 200d, false);
        window.setWindowMin(false);
        window.setWindowMax(false);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
