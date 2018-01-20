package ldh.fx.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import ldh.fx.component.LdhDialog;

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

    public static void info(String title, String info, double width, double height) {
        LdhDialog window = new LdhDialog(title, width, height, false);
        window.setContentPane(new Label(info));
        window.setWindowMin(false);
        window.setWindowMax(false);
        window.show();
    }

    public static void modelInfo(String title, String info, double width, double height) {
        LdhDialog window = new LdhDialog(title, width, height, false);
        window.setContentPane(new Label(info));
        window.setWindowMin(false);
        window.setWindowMax(false);
        window.setModel(true);
        window.show();
    }
}

