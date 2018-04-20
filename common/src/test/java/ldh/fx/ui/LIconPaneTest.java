package ldh.fx.ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.octicons.OctIcon;
import de.jensd.fx.glyphs.weathericons.WeatherIcon;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ldh.fx.component.LIconPane;

/**
 * Created by ldh on 2018/4/18.
 */
public class LIconPaneTest extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        LIconPane iconPane = new LIconPane(FontAwesomeIcon.class, MaterialDesignIcon.class, OctIcon.class, WeatherIcon.class);
        Scene scene = new Scene(iconPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
