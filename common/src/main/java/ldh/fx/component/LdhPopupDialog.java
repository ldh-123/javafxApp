package ldh.fx.component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.*;
import ldh.fx.StageUtil;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LdhPopupDialog extends LdhResizePopupWindow {

    @FXML private Label title;
    @FXML private HBox headPane;
    @FXML private StackPane contentPane;
    @FXML private Button windowMaxBtn;
    @FXML private Button windowMinBtn;

    private Logger logger = Logger.getLogger(LdhPopupDialog.class.getName());

    private boolean isHide = false;
    private ObjectProperty<EventHandler<ActionEvent>> closeRequestHandler = new SimpleObjectProperty<>();

    private Popup popup = new Popup();

    public LdhPopupDialog(String titleStr, Double width, double height) {
        this(titleStr, width, height, true);
    }

    public LdhPopupDialog(String titleStr, Double width, double height, boolean isResize) {
        loadFxl();
        title.setText(titleStr);

        headPane.setAlignment(Pos.CENTER_LEFT);
        this.setPrefSize(width, height);
//        this.setMinSize(width, height);
        buildMovable(headPane);
        if (isResize) {
            buildResizable(this);
        }

        this.widthProperty().addListener(l->position());
        this.heightProperty().addListener(l->position());
        popup.getContent().add(this);
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
    }

    public void position() {
//        if (dialogStage.isFullScreen()) return;
//        if (isMoved) return;
//        Stage stage = StageUtil.STAGE;
//        if (stage != null) {
//            if (stage.getWidth() > dialogStage.getWidth()) {
//                dialogStage.setX(stage.getX() + (stage.getWidth()-dialogStage.getWidth())/2);
//            }
//            if (stage.getHeight() > dialogStage.getHeight()) {
//                dialogStage.setY(stage.getY() + (stage.getHeight() - dialogStage.getHeight())/2);
//            }
//        }
    }

    public void setIsHide(boolean isHide) {
        this.isHide = isHide;
    }

    public void show() {
//        dialogStage.show();
        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        double x = (rectangle2D.getWidth() - this.getPrefWidth())/2;
        double y = (rectangle2D.getHeight() - this.getPrefHeight())/2;
        this.toFront();
        popup.show(StageUtil.STAGE, 100, 100);
        logger.log(Level.INFO, "dialog x:" + x + ", y:" + y + ", w:" + this.getWidth() + ", h:" + this.getHeight());
    }

    public void setOnCloseRequestHandler(EventHandler<ActionEvent> eventEventHandler) {
        closeRequestHandler.set(eventEventHandler);
    }

    public boolean isShowing() {
        return popup.isShowing();
    }

    @FXML public void min() {
        if (isHide) {
            popup.hide();
        } else {
            popup.hide();
        }
    }

    @FXML public void max() {
//        if (popup.isFullScreen()) {
//            dialogStage.setFullScreen(false);
//            windowMaxBtn.getGraphic().getStyleClass().clear();
//            windowMaxBtn.getGraphic().getStyleClass().add("window-max-icon");
//        } else {
//            dialogStage.setFullScreen(true);
//            windowMaxBtn.getGraphic().getStyleClass().clear();
//            windowMaxBtn.getGraphic().getStyleClass().add("window-restore");
//        }
        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        this.setPrefSize(rectangle2D.getWidth(), rectangle2D.getHeight());
        popup.show(StageUtil.STAGE, 0, 0);
    }

    @FXML public void close() {
        if (closeRequestHandler.get() != null) {
            closeRequestHandler.get().handle(new ActionEvent());
        }
        popup.hide();
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
