package ldh.fx.util;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import ldh.fx.StageUtil;
import ldh.fx.component.DialogModel;
import ldh.fx.component.LDialog;
import ldh.fx.StageUtil;
import ldh.fx.component.DialogModel;
import ldh.fx.component.LdhDialog;
import ldh.fx.component.LxDialog;

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
        LDialog window = new LDialog(StageUtil.STAGE, title, width, height, DialogModel.Normal);
        window.getScene().getStylesheets().add("component/LDialog.css");
        Label label = new Label(info);
        label.setPadding(new Insets(5));
        window.isShowingMinButton(false);
        window.isShowingMaxButton(false);
        window.show();
    }

    public static void modelInfo(String title, String info, double width, double height) {
        LDialog window = new LDialog(StageUtil.STAGE, title, width, height, DialogModel.Application_model);
        window.getScene().getStylesheets().add("/component/LDialog.css");
        Label label = new Label(info);
        label.setPadding(new Insets(5));
        window.setCenter(label);
        window.setPrefSize(width, height);
        window.isShowingMinButton(false);
        window.isShowingMaxButton(false);
//        window.setModel(true);
        window.show();
    }
}

