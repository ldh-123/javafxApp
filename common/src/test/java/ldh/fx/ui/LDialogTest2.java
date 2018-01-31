package ldh.fx.ui;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ldh.fx.component.LDialog;
import ldh.fx.ui.util.RegionUtil;

/**
 * Created by ldh on 2018/1/31.
 */
public class LDialogTest2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LDialog lWindow = new LDialog();
        lWindow.initModel(primaryStage, false);
//        lWindow.buildResizable(primaryStage);
        lWindow.setPrefSize(600, 400);
        lWindow.setContentPane(RegionUtil.createButton("drag", new MaterialDesignIconView(), "sw-bugle-graphic"));
//        lWindow.setStyle("-fx-background-color: grey");
        Button button = new Button("show popup");
        button.setOnAction(e-> lWindow.show());
        Scene scene = new Scene(button, 1200, 600);
        scene.getStylesheets().add(LdhWindowTest2.class.getResource("/component/LDialog.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
