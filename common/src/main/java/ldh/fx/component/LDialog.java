package ldh.fx.component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import ldh.fx.ui.window.LxWindow;

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
        Node n = (Node)evt.getSource();
        Window w = n.getScene().getWindow();
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

    @FXML
    public void minimize(MouseEvent evt) {
        Stage stage = (Stage)((Label)evt.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildMovable();
    }
}
