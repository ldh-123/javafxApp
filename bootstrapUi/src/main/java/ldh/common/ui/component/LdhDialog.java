package ldh.common.ui.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LdhDialog extends LdhWindow {

    @FXML private Label title;
    @FXML private HBox headPane;
    @FXML private ScrollPane contentPane;

    private Stage dialogStage;

    public LdhDialog(String titleStr, Double width, double height) {
        loadFxl();
        title.setText(titleStr);
        buildMovable(headPane);
        headPane.setAlignment(Pos.CENTER_LEFT);
        dialogStage = new Stage(StageStyle.UNDECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(this, width, height));
    }

    public void setModel(boolean isModel) {
        if (isModel) {
            dialogStage.initModality(Modality.APPLICATION_MODAL);
        } else {
            dialogStage.initModality(Modality.WINDOW_MODAL);
        }
    }

    public  void show() {
        dialogStage.show();
    }

    @FXML public void minBtn() {

    }

    @FXML public void maxBtn() {

    }

    @FXML public void closeBtn() {
        dialogStage.close();
    }

    private void loadFxl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/component/LdhDialog.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
