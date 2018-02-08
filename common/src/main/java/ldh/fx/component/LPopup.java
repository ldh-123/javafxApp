package ldh.fx.component;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.util.Duration;
import ldh.fx.ui.util.RegionUtil;

/**
 * Created by ldh on 2018/2/8.
 */
public class LPopup {

    protected static final Interpolator WEB_EASE = Interpolator.SPLINE(0.25, 0.1, 0.25, 1);

    private ObjectProperty<Node> popupNodeProperty = new SimpleObjectProperty<>();
    private Popup popup = new Popup();
    private PopupPos popupPos;
    private StackPane popupContentPane = new StackPane();
    private Region region;


    public LPopup(Region region, PopupPos popupPos) {
        this.region = region;
        region.getStylesheets().add(LPopupButton.class.getResource("/ldh.fx.css/LPopupButton.css").toExternalForm());
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
    }

    public Popup getPopup() {
        return popup;
    }

    public void show() {
        sizePopup();
        Point2D p = getPrefPopupPosition();
        double anchorX = RegionUtil.calcAnchorX(region, popupPos, popupContentPane);
        double anchorY = RegionUtil.calcAnchorY(region, popupPos, popupContentPane);
        popup.show(region.getScene().getWindow(), anchorX, anchorY);
        animation();
        popup.requestFocus();
    }

    public boolean isShowing() {
        return popup.isShowing();
    }

    public void hide() {
        popup.hide();
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

    private void sizePopup() {
        final Node popupContent = popupContentPane;

        if (popupContent instanceof Region) {
            final Region r = (Region) popupContent;

            double prefHeight = RegionUtil.snapSize(region, r.prefHeight(0));
            double minHeight = RegionUtil.snapSize(region, r.minHeight(0));
            double maxHeight = RegionUtil.snapSize(region, r.maxHeight(0));
            double h = RegionUtil.snapSize(region, Math.min(Math.max(prefHeight, minHeight), Math.max(minHeight, maxHeight)));

            double prefWidth = RegionUtil.snapSize(region, r.prefWidth(h));
            double minWidth = RegionUtil.snapSize(region, r.minWidth(h));
            double maxWidth = RegionUtil.snapSize(region, r.maxWidth(h));
            double w = RegionUtil.snapSize(region, Math.min(Math.max(prefWidth, minWidth), Math.max(minWidth, maxWidth)));

            popupContent.resize(w, h);
        } else {
            popupContent.autosize();
        }
    }

    private Point2D getPrefPopupPosition() {
        return com.sun.javafx.util.Utils.pointRelativeTo(region, popupContentPane, HPos.CENTER, VPos.BOTTOM, 0, 0, true);
    }

}
