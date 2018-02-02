package ldh.fx.component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ldh.fx.StageUtil;

import java.io.IOException;

public class LdhDialog extends LxWindow {

    @FXML private Label title;
    @FXML private HBox headPane;
    @FXML private AnchorPane contentPane;
    @FXML private Button windowMaxBtn;
    @FXML private Button windowMinBtn;

    private boolean isHide = false;
    private boolean isFirstShow = true;
    private ObjectProperty<EventHandler<ActionEvent>> closeRequestHandler = new SimpleObjectProperty<>();

    private Stage dialogStage;

    public LdhDialog(String titleStr, Double width, double height) {
        this(titleStr, width, height, true);
    }

    public LdhDialog(String titleStr, Double width, double height, boolean isResize) {
        loadFxl();
        title.setText(titleStr);

        dialogStage = new Stage(StageStyle.TRANSPARENT);
        headPane.setAlignment(Pos.CENTER_LEFT);
        dialogStage.initOwner(StageUtil.STAGE);
        dialogStage.initModality(Modality.NONE);
        Scene scene = new Scene(this, width, height);
        scene.setFill(null);
        dialogStage.setScene(scene);
//        this.setStage(dialogStage);
        buildMovable(headPane);
        if (isResize) {
            buildResizable(this);
        }

        dialogStage.widthProperty().addListener(l->position());
        dialogStage.heightProperty().addListener(l->position());
    }

    public void setWindowMin(boolean isShowing) {
        windowMinBtn.setVisible(isShowing);
    }

    public void setWindowMax(boolean isShowing) {
        windowMaxBtn.setVisible(isShowing);
    }

    public void setContentPane(Node node) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(node);
        AnchorPane.setTopAnchor(node, 0d);
        AnchorPane.setBottomAnchor(node, 0d);
        AnchorPane.setLeftAnchor(node, 0d);
        AnchorPane.setRightAnchor(node, 0d);
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

    public void position() {
        if (dialogStage.isFullScreen()) return;
        if (isMoved) return;
        Stage stage = StageUtil.STAGE;
        if (stage != null) {
            if (stage.getWidth() > dialogStage.getWidth()) {
                dialogStage.setX(stage.getX() + (stage.getWidth()-dialogStage.getWidth())/2);
            }
            if (stage.getHeight() > dialogStage.getHeight()) {
                dialogStage.setY(stage.getY() + (stage.getHeight() - dialogStage.getHeight())/2);
            }
        }
    }

    public void setIsHide(boolean isHide) {
        this.isHide = isHide;
    }

    public void show() {
        if (isFirstShow && !isHide) {
            dialogStage.show();
            isFirstShow = false;
        }
        if (isHide) {
            if (dialogStage.isShowing()) {
                dialogStage.hide();
                return;
            } else {
                dialogStage.show();
            }
        } else {
            if (dialogStage.isIconified()) {
                dialogStage.setIconified(false);
            } else {
                dialogStage.setIconified(true);
            }
        }
    }

    public void setOnCloseRequestHandler(EventHandler<ActionEvent> eventEventHandler) {
        closeRequestHandler.set(eventEventHandler);
    }

    public Stage getNewStage() {
        return this.dialogStage;
    }

    public boolean isShowing() {
        return dialogStage.isShowing();
    }

    @FXML public void min() {
        if (isHide) {
            dialogStage.hide();
        } else {
            dialogStage.setIconified(false);
        }
    }

    @FXML public void max() {
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

    @FXML public void close() {
        if (closeRequestHandler.get() != null) {
            closeRequestHandler.get().handle(new ActionEvent());
        }
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
