package ldh.fx.component;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import ldh.fx.ui.util.NodeUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ldh on 2018/1/31.
 */
public class LDialogBase extends LWindow implements Initializable {

    @FXML private HBox headPane;
    @FXML private Label titleLabel;
    @FXML private Button minButton;
    @FXML private Button maxButton;
    @FXML private Button closeButton;

    private double lastX = 0.0d;
    private double lastY = 0.0d;
    private double lastWidth = 0.0d;
    private double lastHeight = 0.0d;

    private boolean isMax = false;
    private ObjectProperty<EventHandler<ActionEvent>> closeRequestHandler = new SimpleObjectProperty<>();

    public LDialogBase() {
        super();
        loadFx("/component/LDialog.fxml");
    }

    public void initDialogModel(Stage stage, DialogModel dialogModel) {
        super.initDialogModel(stage, dialogModel);
        if(isModel() && dialogModel == DialogModel.Application_model) {
            isShowingMinButton(false);
            this.addDropShadow();
            this.getScene().getStylesheets().add("/component/LDialog.css");
        }
    }

    public void setOnCloseRequestHandler(EventHandler<ActionEvent> eventEventHandler) {
        closeRequestHandler.set(eventEventHandler);
    }

    public void buildMovable() {
        buildMovable(headPane);
    }

    public boolean isShowing() {
        if (isModel()) {
           return newStage.isShowing();
        } else {
            return popup.isShowing();
        }
    }

    public void close() {
        this.getScene().getWindow().hide();
    }

    public void min() {
        close();
    }

    public void isShowingMaxButton(boolean isShowingMaxButton) {
        maxButton.setVisible(isShowingMaxButton);
    }

    public void isShowingMinButton(boolean isShowingMinButton) {
        minButton.setVisible(isShowingMinButton);
    }

    @FXML
    public void headDoubleClick(MouseEvent evt) {
        if (evt.getClickCount() != 2) return;
        if (isModel() && isMinProperty.get()) {
            newStage.setIconified(true);
            isMinProperty.set(false);
        } else {
            maximize(new ActionEvent());
        }
        evt.consume();
    }

    @FXML
    public void close(ActionEvent evt) {
        if (closeRequestHandler.get() != null) {
            closeRequestHandler.get().handle(new ActionEvent());
        }
        ((Button)evt.getSource()).getScene().getWindow().hide();
    }


    @FXML
    public void maximize(ActionEvent evt) {
        if (isModel()) {
            stageMax(evt);
        } else {
            popupMax();
        }
    }

    @FXML
    public void minimize(ActionEvent evt) {
        if (isModel()) {
            Stage stage = (Stage)((Button)evt.getSource()).getScene().getWindow();
            stage.setIconified(true);
            isMinProperty.set(true);
        } else {
            popup.hide();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildMovable();
    }

    private void stageMax(ActionEvent evt) {
        Window w = this.getScene().getWindow();
        double currentX = w.getX();
        double currentY = w.getY();
        double currentWidth = w.getWidth();
        double currentHeight = w.getHeight();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        if(currentX != bounds.getMinX() &&
                currentY != bounds.getMinY() &&
                currentWidth != bounds.getWidth() &&
                currentHeight != bounds.getHeight() ) {  // if not maximized

            w.setX(bounds.getMinX());
            w.setY(bounds.getMinY());
            w.setWidth(bounds.getWidth());
            w.setHeight(bounds.getHeight());

            lastX = currentX;  // save old dimensions
            lastY = currentY;
            lastWidth = currentWidth;
            lastHeight = currentHeight;
            isMax = false;
        } else {
            isMax = true;
            w.setX(lastX);
            w.setY(lastY);
            w.setWidth(lastWidth);
            w.setHeight(lastHeight);

        }
        evt.consume();  // don't bubble up to title bar
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    private void popupMax() {
        Window window = this.getScene().getWindow();
        Rectangle2D rectangle2D = NodeUtil.getVisualBound(this);
        if (isMax) {
            this.setPrefSize(lastWidth, lastHeight);
            popup.hide();
            popup.show(parentStage, lastX, lastY);
//            this.setEffect(new DropShadow());
            isMax = false;
        } else {
            lastWidth = window.getWidth();
            lastHeight = window.getHeight();
            lastX = window.getX();
            lastY = window.getY();
            this.setPrefSize(rectangle2D.getWidth(), rectangle2D.getHeight());
            popup.show(parentStage, rectangle2D.getMinX(), rectangle2D.getMinY());
            isMax = true;
//            this.setEffect(null);
        }
    }
}
