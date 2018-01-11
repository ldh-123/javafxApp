package ldh.fx.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ldh.fx.StageUtil;

import java.io.IOException;

public class LdhDialog extends LdhResizeWindow {

    @FXML private Label title;
    @FXML private HBox headPane;
    @FXML private StackPane contentPane;
    @FXML private Button windowMaxBtn;

    private Stage dialogStage;

    public LdhDialog(String titleStr, Double width, double height) {
        loadFxl();
        title.setText(titleStr);

        dialogStage = new Stage(StageStyle.UNDECORATED);
        headPane.setAlignment(Pos.CENTER_LEFT);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(this, width, height));
        this.setStage(dialogStage);
        buildMovable(headPane);
        buildResizable(this);
    }

    public void setContentPane(Node node) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(node);
    }

    public void setModel(boolean isModel) {
        if (isModel) {
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(StageUtil.STAGE);
        } else {
            dialogStage.initModality(Modality.NONE);
            dialogStage.initOwner(StageUtil.STAGE);
        }
    }

    public  void show() {
        dialogStage.centerOnScreen();
        dialogStage.show();
    }

    @FXML public void minBtn() {
        dialogStage.setIconified(true);
    }

    @FXML public void maxBtn() {
        if (dialogStage.isFullScreen()) {
            dialogStage.setFullScreen(false);
            windowMaxBtn.getGraphic().getStyleClass().clear();
            windowMaxBtn.getGraphic().getStyleClass().add("window-max-icon");
        } else {
            dialogStage.setFullScreen(true);
            windowMaxBtn.getGraphic().getStyleClass().clear();
            windowMaxBtn.getGraphic().getStyleClass().add("window-restore");
        }
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
