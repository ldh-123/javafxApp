package ldh.fx.component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import ldh.fx.StageUtil;
import ldh.fx.ui.util.NodeUtil;

/**
 * Created by ldh on 2018/2/3.
 */
public class LxDialogBase extends LxWindow {

    @FXML private Button windowMaxBtn;
    @FXML private Button windowMinBtn;
    @FXML private ScrollPane contentPane;
    @FXML private Label titleLabel;
    @FXML private HBox headPane;

    private double windowWidth, windowHeight;
    private ObjectProperty<EventHandler<ActionEvent>> closeRequestHandler = new SimpleObjectProperty<>();
    private boolean isFirstShow = true;

    public LxDialogBase() {
        loadFx("/component/LxDialog.fxml");
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setContentPane(Node node) {
        contentPane.setContent(node);
//        contentPane.getChildren().add(node);
    }

    public void show() {
        if (isModel()) {
            if (isFirstShow) {
                dialogStage.show();
                isFirstShow = false;
            }
            if (dialogStage.isIconified()) {
                dialogStage.setIconified(false);
            } else {
                dialogStage.setIconified(true);
            }
        } else {
            if (popup.isShowing()) {
                popup.hide();
                return;
            }
            popup.show(parentStage);
        }
    }

    public void isShowingMaxBtn(boolean isShowing) {
        windowMaxBtn.setVisible(isShowing);
    }

    public void isShowingMinBtn(boolean isShowing) {
        windowMinBtn.setVisible(isShowing);
    }

    public void setOnCloseRequestHandler(EventHandler<ActionEvent> eventEventHandler) {
        closeRequestHandler.set(eventEventHandler);
    }

    @FXML
    public void min() {
        if (isModel()) {
            dialogStage.setIconified(false);
            return;
        }
        Rectangle2D rectangle2D = NodeUtil.getVisualBound(this);
        layoutX = rectangle2D.getMinX();
        layoutY = rectangle2D.getMinY();
        popup.hide();
    }

    @FXML public void max() {
        if (isModel()) {
            if (dialogStage.isFullScreen()) {
                dialogStage.setFullScreen(false);
                windowMaxBtn.getGraphic().getStyleClass().clear();
                windowMaxBtn.getGraphic().getStyleClass().add("window-max-icon");
            } else {
                dialogStage.setFullScreen(true);
                windowMaxBtn.getGraphic().getStyleClass().clear();
                windowMaxBtn.getGraphic().getStyleClass().add("window-restore");
            }
            return;
        }
        Window window = this.getScene().getWindow();
        Rectangle2D rectangle2D = NodeUtil.getVisualBound(this);
        if (Math.abs(window.getWidth() - rectangle2D.getWidth()) < 0.001) {
            this.setPrefSize(windowWidth, windowHeight);
            popup.hide();
            popup.show(StageUtil.STAGE, layoutX, layoutY);
        } else {
            windowWidth = window.getWidth();
            windowHeight = window.getHeight();
            layoutX = window.getX();
            layoutY = window.getY();
            this.setPrefSize(rectangle2D.getWidth(), rectangle2D.getHeight());
            popup.show(StageUtil.STAGE, rectangle2D.getMinX(), rectangle2D.getMinY());
        }
    }

    @FXML public void close() {
        if (closeRequestHandler.get() != null) {
            closeRequestHandler.get().handle(new ActionEvent());
        }
        if (isModel()) {
            dialogStage.close();
            return;
        }
        popup.hide();
    }

    public  void setMovable() {
        buildMovable(headPane);
    }
}
