package ldh.fx.ui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ldh.fx.component.LHiddenPane;
import ldh.fx.ui.util.PageUtil;
import ldh.fx.ui.util.RegionUtil;

/**
 * Created by ldh on 2018/1/29.
 */
public class FormPaneTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = PageUtil.load("/fxml/FormPane1.fxml");
        Scene scene = new Scene(parent, 1200, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
