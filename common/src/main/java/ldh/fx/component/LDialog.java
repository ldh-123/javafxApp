package ldh.fx.component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
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
public class LDialog extends LdhWindow implements Initializable {

    @FXML private HBox headPane;

    private double lastX = 0.0d;
    private double lastY = 0.0d;
    private double lastWidth = 0.0d;
    private double lastHeight = 0.0d;

    private boolean isMax = false;

    public LDialog() {
        super();
        loadFx("/component/LDialog.fxml");
    }

    public void buildMovable() {
        buildMovable(headPane);
    }

    @FXML
    public void titleDoubleClick(MouseEvent evt) {
        if (evt.getClickCount() != 2) return;
        maximize(evt);
    }

    @FXML
    public void close(MouseEvent evt) {
        ((Label)evt.getSource()).getScene().getWindow().hide();
    }


    @FXML
    public void maximize(MouseEvent evt) {
        if (isModel()) {
            stageMax(evt);
        } else {
            popupMax();
        }
    }

    @FXML
    public void minimize(MouseEvent evt) {
        if (isModel()) {
            Stage stage = (Stage)((Label)evt.getSource()).getScene().getWindow();
            stage.setIconified(true);
        } else {
            popup.hide();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildMovable();
    }

    private void stageMax(MouseEvent evt) {
        Window w = this.getScene().getWindow();
        double currentX = w.getX();
        double currentY = w.getY();
        double currentWidth = w.getWidth();
        double currentHeight = w.getHeight();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        if( currentX != bounds.getMinX() &&
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
        } else {
            w.setX(lastX);
            w.setY(lastY);
            w.setWidth(lastWidth);
            w.setHeight(lastHeight);

        }
        evt.consume();  // don't bubble up to title bar
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
