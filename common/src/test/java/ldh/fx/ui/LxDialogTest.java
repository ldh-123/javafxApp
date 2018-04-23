package ldh.fx.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ldh.fx.StageUtil;
import ldh.fx.component.DialogModel;
import ldh.fx.component.LdhDialog;
import ldh.fx.component.LxDialog;

/**
 * Created by ldh on 2018/1/13.
 */
public class LxDialogTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        StageUtil.STAGE = stage;

//        window.setResizable();
//        window.isShowingMaxBtn(false);
//        window.isShowingMinBtn(false);

//        window.show();
        Button b = new Button("show");
        b.setOnAction(e->{
            LxDialog window = new LxDialog(stage, "demo", DialogModel.Application, 400d, 200d);
            window.isShowingMinButton(false);
            Label label = new Label("error");
            label.setStyle("-fx-padding: 10");
            window.setContentPane(label);
            window.getScene().getStylesheets().add("/component/LxDialog.css");
            window.setMovable();
            window.show();
        });
        Scene scene = new Scene(b, 200, 300);
        scene.getStylesheets().add("/component/LxDialog.css");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
