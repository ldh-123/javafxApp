package ldh.fx.util;

import javafx.scene.control.Alert;

/**
 * Created by ldh on 2017/2/26.
 */
public class DialogUtil {

    public static void show(Alert.AlertType type, String title, String info) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(info);
        alert.setContentText(null);
        alert.showAndWait();
    }
}

