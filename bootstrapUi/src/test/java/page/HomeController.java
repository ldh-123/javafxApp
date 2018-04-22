package page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Popup;
import ldh.fx.component.LNavPane;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ldh123 on 2018/4/22.
 */
public class HomeController {
    @FXML private LNavPane leftPane;
    @FXML private Button tonggle;

    public void tongleLeftPane(ActionEvent event) {
        System.out.println(tonggle.getGraphic().getClass());
        leftPane.tongleLeftPane();
    }

    public void showDashboard(ActionEvent event) {
        System.out.println("sfafdafdasd");
    }
}
