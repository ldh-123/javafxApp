package ldh.fx.ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.octicons.OctIcon;
import de.jensd.fx.glyphs.weathericons.WeatherIcon;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ldh.fx.component.DialogModel;
import ldh.fx.component.LDialog;
import ldh.fx.component.LIconPane;

/**
 * Created by ldh on 2018/4/18.
 */
public class LIconPaneTest2 extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button = new Button("show");
        button.setOnAction(e->{
            LDialog dialog = new LDialog(primaryStage, "选择图标", 800d, 600d, DialogModel.Application_model);
            LIconPane iconPane = new LIconPane(FontAwesomeIcon.class, MaterialDesignIcon.class, OctIcon.class, WeatherIcon.class);
            iconPane.getSelectedNodeProperty().addListener((b, o, n)->{
                if (n != null) {
                    button.setGraphic(n);
                }
            });
            dialog.setContentPane(iconPane);
            dialog.show();
        });

        Scene scene = new Scene(button, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
