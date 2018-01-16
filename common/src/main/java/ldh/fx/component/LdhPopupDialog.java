package ldh.fx.component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
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

    private Logger logger = Logger.getLogger(LdhPopupDialog.class.getName());

    @FXML private Label title;
    @FXML private HBox headPane;
    @FXML private StackPane contentPane;
    @FXML private Button windowMaxBtn;
    @FXML private Button windowMinBtn;

    private double windowWidth, windowHeight;

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

//        this.widthProperty().addListener(l->position());
//        this.heightProperty().addListener(l->position());
        popup.getContent().add(this);
        layoutX = 100d; layoutY = 100d;
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
        if (layoutX > 0) return;
        Stage stage = StageUtil.STAGE;
        if (stage != null) {
            if (stage.getWidth() > this.getWidth()) {
                this.getScene().getWindow().setX(stage.getX() + (stage.getWidth()-this.getWidth())/2);
            }
            if (stage.getHeight() > this.getHeight()) {
                this.getScene().getWindow().setX(stage.getY() + (stage.getHeight() - this.getHeight())/2);
            }
        } else {
            this.getScene().getWindow().centerOnScreen();
        }
        layoutX = this.getScene().getWindow().getX();
        layoutY = this.getScene().getWindow().getY();
    }

    public void show() {
        popup.hide();
        popup.show(StageUtil.STAGE, layoutX, layoutY);
        System.out.println("show: x:" + layoutX + ", y:" + layoutY);
    }

    public void setOnCloseRequestHandler(EventHandler<ActionEvent> eventEventHandler) {
        closeRequestHandler.set(eventEventHandler);
    }

    public boolean isShowing() {
        return popup.isShowing();
    }

    @FXML public void min() {
        layoutX = this.getScene().getWindow().getX();
        layoutY = this.getScene().getWindow().getY();
        popup.hide();
    }

    @FXML public void max() {
        Window window = this.getScene().getWindow();
        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        ObservableList<Screen> screens = Screen.getScreensForRectangle(window.getX(), window.getY(), window.getWidth(), window.getHeight());
        if (screens != null && screens.size() > 0) {
            Screen screen = screens.get(0);
            rectangle2D = screen.getVisualBounds();
        }
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
