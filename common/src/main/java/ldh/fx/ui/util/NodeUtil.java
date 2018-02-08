package ldh.fx.ui.util;

import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Window;
import ldh.fx.component.LPopupButton;
import ldh.fx.component.PopupPos;

/**
 * Created by ldh on 2018/1/18.
 */
public class NodeUtil {

    public static Rectangle2D getVisualBound(Node node) {
        Window window = node.getScene().getWindow();
        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        ObservableList<Screen> screens = Screen.getScreensForRectangle(window.getX(), window.getY(), window.getWidth(), window.getHeight());
        if (screens != null && screens.size() > 0) {
            Screen screen = screens.get(0);
            rectangle2D = screen.getVisualBounds();
        }
        return rectangle2D;
    }
}
