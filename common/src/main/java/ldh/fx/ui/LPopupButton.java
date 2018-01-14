package ldh.fx.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;

/**
 * Created by ldh on 2018/1/11.
 */
public class LPopupButton extends Button {

    public static double popupHeight = 200;
    public static double popupWidth = 300;

    private ObjectProperty<Node> popupNodeProperty = new SimpleObjectProperty<>();
    private Popup popup = new Popup();
    private PopupPos popupPos;
    private StackPane popupContentPane = new StackPane();

    public LPopupButton(PopupPos popupPos) {
        this.getStylesheets().add(LPopupButton.class.getResource("/ldh.fx.css/LPopupButton.css").toExternalForm());
        this.popupPos = popupPos;
        initEvent();
    }

    public LPopupButton(String text, PopupPos popupPos) {
        super(text);
        this.getStylesheets().add(LPopupButton.class.getResource("/ldh.fx.css/LPopupButton.css").toExternalForm());
        this.popupPos = popupPos;
        initEvent();
    }

    public LPopupButton(String text, Region graphic, PopupPos popupPos) {
        super(text, graphic);
        this.getStylesheets().add(LPopupButton.class.getResource("/ldh.fx.css/LPopupButton.css").toExternalForm());
        this.popupPos = popupPos;
        initEvent();
    }

    public void setPopupContentPane(Region popupNode) {
        popupNodeProperty.set(popupNode);
    }

    public Node getPopupNode() {
        return popupNodeProperty.get();
    }

    public ObjectProperty<Node> popupNodeProperty() {
        return popupNodeProperty;
    }

    private void initEvent() {
        popupContentPane.setAlignment(Pos.TOP_LEFT);
        popupContentPane.getStyleClass().add("popup-pane");

        popup.setAutoHide(true);
        popupNodeProperty.addListener((l, o, n)->{
            popup.getContent().clear();
            popupContentPane.getChildren().add(n);
            popup.getContent().add(popupContentPane);
        });
        popupContentPane.heightProperty().addListener(l->show());
        this.setOnAction(e->{
            show();
        });
    }

    private void show() {
        sizePopup();
        Point2D p = getPrefPopupPosition();
        double anchorX = clacAnchorX();
        double anchorY = clacAnchorY();
//        popup.show(this.getScene().getWindow(), snapPosition(p.getX()), snapPosition(p.getY()));
            popup.show(this.getScene().getWindow(), anchorX, anchorY);
        popup.requestFocus();
//        sizePopup();
    }

    private double clacAnchorX() {
        double anchorX = this.getScene().getWindow().getX() + this.localToScene(0, 0).getX() + this.getScene().getX() - 1;
        if (popupPos == PopupPos.down_east) {
            anchorX = anchorX + this.getHeight();
        } else if (popupPos == PopupPos.down_west) {
            anchorX = anchorX + this.getHeight();
        } else if (popupPos == PopupPos.up_east) {
            anchorX = anchorX - this.popupContentWidth();
        } else if (popupPos == PopupPos.up_west) {
            // not nothing
        }
        return anchorX;
    }

    private double clacAnchorY() {
        double anchorY = this.getScene().getWindow().getY() + this.localToScene(0, 0).getY() + this.getScene().getY();
        if (popupPos == PopupPos.down_east) {
            anchorY = anchorY + this.getHeight();
        } else if (popupPos == PopupPos.down_west) {
            anchorY = anchorY + this.getHeight();
        } else if (popupPos == PopupPos.up_east) {
            anchorY = anchorY - this.popupContentHeight();
        } else if (popupPos == PopupPos.up_west) {
            anchorY = anchorY - this.popupContentHeight();
        }
        return anchorY;
    }

    private double popupContentHeight() {
        Node popupContent = popupContentPane;
        if (popupContent instanceof Region) {
            Region r = (Region) popupContent;
            return r.getHeight();
        }
        return popupContent.prefHeight(0);
    }

    private double popupContentWidth() {
        Node popupContent = popupContentPane;
        if (popupContent instanceof Region) {
            Region r = (Region) popupContent;
            return r.getWidth();
        }
        return popupContent.prefWidth(0);
    }

    private void sizePopup() {
        final Node popupContent = popupContentPane;

        if (popupContent instanceof Region) {
            // snap to pixel
            final Region r = (Region) popupContent;

            // 0 is used here for the width due to RT-46097
            double prefHeight = snapSize(r.prefHeight(0));
            double minHeight = snapSize(r.minHeight(0));
            double maxHeight = snapSize(r.maxHeight(0));
            double h = snapSize(Math.min(Math.max(prefHeight, minHeight), Math.max(minHeight, maxHeight)));

            System.out.println("height:" + h);
            double prefWidth = snapSize(r.prefWidth(h));
            double minWidth = snapSize(r.minWidth(h));
            double maxWidth = snapSize(r.maxWidth(h));
            double w = snapSize(Math.min(Math.max(prefWidth, minWidth), Math.max(minWidth, maxWidth)));

            popupContent.resize(w, h);
        } else {
            popupContent.autosize();
        }
    }

    private Point2D getPrefPopupPosition() {
        return com.sun.javafx.util.Utils.pointRelativeTo(this, popupContentPane, HPos.CENTER, VPos.BOTTOM, 0, 0, true);
    }

    protected double snapPosition(double value) {
        return this.isSnapToPixel() ? Math.round(value) : value;
    }

    public static enum PopupPos {
        down_west,
        down_east,
        up_west,
        up_east,
        ;
    }

}
