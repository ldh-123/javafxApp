package ldh.descktop.util;

import javafx.scene.Node;
import javafx.scene.layout.Region;
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
        double anchorX = node.getScene().getWindow().getX() + node.localToScene(0, 0).getX() + node.getScene().getX() - 1;
        return anchorX;
    }

    public static Double anchorY(Region node) {
        double anchorY = node.getScene().getWindow().getY() + node.localToScene(0, 0).getY() + node.getScene().getY();
        return anchorY;
    }
}
