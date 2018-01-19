package ldh.fx.component;

import javafx.animation.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.util.Duration;
import ldh.fxanimations.BounceInLeftTransition;
import ldh.fxanimations.BounceInRightTransition;
import ldh.fxanimations.BounceInTransition;

/**
 * Created by ldh on 2018/1/11.
 */
public class LPopupButton extends Button {

    protected static final Interpolator WEB_EASE = Interpolator.SPLINE(0.25, 0.1, 0.25, 1);

    public static double popupHeight = 200;
    public static double popupWidth = 300;

    private ObjectProperty<Node> popupNodeProperty = new SimpleObjectProperty<>();
    private Popup popup = new Popup();
    private PopupPos popupPos;
    private StackPane popupContentPane = new StackPane();

    public LPopupButton(PopupPos popupPos) {
        this("", popupPos);
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
        popupContentPane.widthProperty().addListener(l->show());
        this.setOnAction(e->{
            show();
        });
    }

    private void show() {
        sizePopup();
        Point2D p = getPrefPopupPosition();
        double anchorX = clacAnchorX();
        double anchorY = clacAnchorY();
        popup.show(this.getScene().getWindow(), anchorX, anchorY);
        animation();
        popup.requestFocus();
    }

    private void animation() {
        Node node = popupContentPane;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(node.visibleProperty(), true),
                        new KeyValue(node.opacityProperty(), 0, WEB_EASE),
                        new KeyValue(node.scaleXProperty(), 0.6, WEB_EASE),
                        new KeyValue(node.scaleYProperty(), 0.6, WEB_EASE)
                ),
                new KeyFrame(Duration.millis(100),
                        new KeyValue(node.scaleXProperty(), 1, WEB_EASE),
                        new KeyValue(node.scaleYProperty(), 1, WEB_EASE)

                )
        );
        timeline.play();
    }

    private double clacAnchorX() {
        double anchorX = this.getScene().getWindow().getX() + this.localToScene(0, 0).getX() + this.getScene().getX() - 1;
        if (popupPos == PopupPos.down_east) {
            anchorX = anchorX  - this.popupContentWidth();
        } else if (popupPos == PopupPos.down_west) {
            // nothing
        } else if (popupPos == PopupPos.up_east) {
            anchorX = anchorX - this.popupContentWidth();
        } else if (popupPos == PopupPos.up_west) {
//            anchorX = anchorX - this.popupContentWidth();
        }
        return anchorX;
    }

    private double clacAnchorY() {
        double anchorY = this.getScene().getWindow().getY() + this.localToScene(0, 0).getY() + this.getScene().getY();
        if (popupPos == PopupPos.down_east) {
            anchorY = anchorY + this.getHeight() + 1;
        } else if (popupPos == PopupPos.down_west) {
            anchorY = anchorY + this.getHeight() + 1;
        } else if (popupPos == PopupPos.up_east) {
            anchorY = anchorY - this.popupContentHeight() - 1;
        } else if (popupPos == PopupPos.up_west) {
            anchorY = anchorY - this.popupContentHeight() - 1;
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
            final Region r = (Region) popupContent;

            double prefHeight = snapSize(r.prefHeight(0));
            double minHeight = snapSize(r.minHeight(0));
            double maxHeight = snapSize(r.maxHeight(0));
            double h = snapSize(Math.min(Math.max(prefHeight, minHeight), Math.max(minHeight, maxHeight)));

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
