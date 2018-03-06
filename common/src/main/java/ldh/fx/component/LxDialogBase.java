package ldh.fx.component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
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

    public void initDialogModel(Stage stage, DialogModel dialogModel) {
        super.initDialogModel(stage, dialogModel);
        if (!isModel()) {
            popup.showingProperty().addListener((l, o, n)->{
                if (n) {
                    if (layoutX < 0.1 || layoutY < 0.1) {
                        if (parentStage != null) {
                            Window window = parentStage.getScene().getWindow();
                            Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
                            ObservableList<Screen> screens = Screen.getScreensForRectangle(window.getX(), window.getY(), window.getWidth(), window.getHeight());
                            if (screens != null && screens.size() > 0) {
                                Screen screen = screens.get(0);
                                rectangle2D = screen.getVisualBounds();
                            }
                            layoutX = rectangle2D.getMinX() + (rectangle2D.getWidth() - this.getWidth())/2;
                            layoutY = rectangle2D.getMinY() + (rectangle2D.getHeight() - this.getHeight())/2;
                            popup.show(parentStage, layoutX, layoutY);
                        }
                    }
                }
            });
            this.widthProperty().addListener((l, o, n)->changeSize());
            this.heightProperty().addListener((l, o, n)->changeSize());
            this.isShowingMinButton(true);
        }
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setContentPane(Node node) {
        contentPane.setContent(node);
    }

    public boolean isShowing() {
        if (isModel()) {
            return dialogStage.isShowing();
        }
        return popup.isShowing();
    }

    public void show() {
        if (isModel()) {
            if (isFirstShow) {
                dialogStage.show();
                isFirstShow = false;
                return;
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
            popup.show(parentStage, layoutX, layoutY);
        }
    }

    public void isShowingMaxButton(boolean isShowing) {
        windowMaxBtn.setVisible(isShowing);
    }

    public void isShowingMinButton(boolean isShowing) {
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
        layoutX = rectangle2D.getMinX() + this.getScene().getWindow().getX();
        layoutY = rectangle2D.getMinY() + this.getScene().getWindow().getY();
        popup.hide();
    }

    @FXML public void max() {
        if (isModel()) {
            if (dialogStage.isFullScreen()) {
                dialogStage.setFullScreen(false);
                windowMaxBtn.getGraphic().getStyleClass().clear();
                windowMaxBtn.getGraphic().getStyleClass().add("window-max-graphic");
            } else {
                dialogStage.setFullScreen(true);
                windowMaxBtn.getGraphic().getStyleClass().clear();
                windowMaxBtn.getGraphic().getStyleClass().add("window-restore-graphic");
            }
            return;
        }
        Window window = this.getScene().getWindow();
        Rectangle2D rectangle2D = NodeUtil.getVisualBound(this);
        if (Math.abs(window.getWidth() - rectangle2D.getWidth()) < 0.001) {
            this.setPrefSize(windowWidth, windowHeight);
            popup.hide();
            popup.show(parentStage, layoutX, layoutY);
        } else {
            windowWidth = window.getWidth();
            windowHeight = window.getHeight();
            layoutX = window.getX();
            layoutY = window.getY();
            this.setPrefSize(rectangle2D.getWidth(), rectangle2D.getHeight());

            popup.hide();
            popup.show(parentStage, rectangle2D.getMinX(), rectangle2D.getMinY());
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

    public void setMovable() {
        buildMovable(headPane);
    }

    private void changeSize() {
        Node node = contentPane.getContent();
        if (node == null) return;
        if (node instanceof Region) {
            Region region = (Region) node;
            region.setPrefHeight(this.getScene().getWindow().getHeight()-32);
            region.setPrefWidth(this.getScene().getWindow().getWidth()-2);
        }
    }
}
