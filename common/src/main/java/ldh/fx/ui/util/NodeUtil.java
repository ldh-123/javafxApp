package ldh.fx.ui.util;

import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Window;
import ldh.fx.component.LPopupButton;

/**
 * Created by ldh on 2018/1/18.
 */
public class NodeUtil {

    public static Double anchorY(Region node, LPopupButton.PopupPos popupPos) {
        double anchorY = node.getScene().getWindow().getY() + node.localToScene(0, 0).getY() + node.getScene().getY();
        if (popupPos == LPopupButton.PopupPos.down_east) {
            anchorY = anchorY + node.getHeight();
        } else if (popupPos == LPopupButton.PopupPos.down_west) {
            anchorY = anchorY + node.getHeight();
        } else if (popupPos == LPopupButton.PopupPos.up_east) {
            anchorY = anchorY - node.getHeight();
        } else if (popupPos == LPopupButton.PopupPos.up_west) {
            anchorY = anchorY - node.getHeight();
        }
        return anchorY;
    }

    public static Double anchorX(Region node, LPopupButton.PopupPos popupPos) {
        double anchorX = node.getScene().getWindow().getX() + node.localToScene(0, 0).getX() + node.getScene().getX() - 1;
        if (popupPos == LPopupButton.PopupPos.down_east) {
            anchorX = anchorX + node.getHeight();
        } else if (popupPos == LPopupButton.PopupPos.down_west) {
            anchorX = anchorX + node.getHeight();
        } else if (popupPos == LPopupButton.PopupPos.up_east) {
            anchorX = anchorX - node.getWidth();
        } else if (popupPos == LPopupButton.PopupPos.up_west) {
            anchorX = anchorX - node.getWidth();
        }
        return anchorX;
    }

    public static Double anchorX(Region node) {
        double anchorX = node.getScene().getWindow().getX() + node.localToScene(0, 0).getX() + node.getScene().getX();
        return anchorX;
    }

    public static Double anchorY(Region node) {
        double anchorY = node.getScene().getWindow().getY() + node.localToScene(0, 0).getY() + node.getScene().getY();
        return anchorY;
    }

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
