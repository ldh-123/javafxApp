package ldh.descktop.ui;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.animation.*;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import ldh.fx.StageUtil;
import ldh.fx.util.DialogUtil;
import ldh.fxanimations.CachedTimelineTransition;

/**
 * Created by ldh on 2018/1/11.
 */
public class DesktopPane extends FlowPane {

    public DesktopPane() {
        this.getStyleClass().add("desktop-pane");
        setOrientation(Orientation.VERTICAL);

        initPopupMenu();
    }

    private void initPopupMenu() {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setAutoHide(true);

        MenuItem fullScreen = new MenuItem("进入全屏");
        setGraphic(fullScreen, new MaterialDesignIconView(), "screen-full-graphic");
        MenuItem notFullScreen = new MenuItem("退出全屏");
        setGraphic(notFullScreen, new MaterialDesignIconView(), "screen-restore-graphic");
        MenuItem about = new MenuItem("关于");
        setGraphic(about, new MaterialIconView(), "screen-about-graphic");
        about.setOnAction(e->{
            DialogUtil.info("javafx demo", "javafx desktop demo", 300d, 150d);
        });
        contextMenu.getItems().addAll(fullScreen, notFullScreen, about);
        contextMenu.getStyleClass().add("desktop-context-menu");

        fullScreen.setOnAction(e->{
            if (StageUtil.STAGE != null) {
                StageUtil.STAGE.setFullScreen(true);
            }
        });
        notFullScreen.setOnAction(e->{
            if (StageUtil.STAGE != null) {
                StageUtil.STAGE.setFullScreen(false);
            }
        });
        this.setOnMousePressed(e->{
            if (e.getButton() == MouseButton.SECONDARY)
                contextMenu.show(DesktopPane.this.getScene().getWindow(), e.getScreenX(), e.getScreenY());
        });
    }

    private void setGraphic(MenuItem fullScreen, GlyphIcon glyphIcon, String graphicName) {
        glyphIcon.getStyleClass().add(graphicName);
        fullScreen.setGraphic(glyphIcon);
    }

    public void animation() {
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().clear();
        for (Node node : this.getManagedChildren()) {
//            FadeTransition fadeTransition=new FadeTransition(Duration.millis(300), node);
//            fadeTransition.setFromValue(1.0f);
//            fadeTransition.setToValue(0f);
//            fadeTransition.setCycleCount(1);
            FlipOutTransition fout = new FlipOutTransition(node);
            parallelTransition.getChildren().add(fout);
        }
        parallelTransition.play();
    }

    public void animationRestore() {
        ParallelTransition parallelTransition = new ParallelTransition();
        for (Node node : this.getManagedChildren()) {
//            FadeTransition fadeTransition=new FadeTransition(Duration.millis(300), node);
//            fadeTransition.setFromValue(0f);
//            fadeTransition.setToValue(1f);
//            fadeTransition.setCycleCount(1);
            FlipInTransition fin = new FlipInTransition(node);
            parallelTransition.getChildren().add(fin);
        }
        parallelTransition.play();
    }

    public class FlipInTransition extends CachedTimelineTransition {
        private Camera oldCamera;

        /**
         * Create new FlipInXTransition
         *
         * @param node The node to affect
         */
        public FlipInTransition(final Node node) {
            super(
                    node,
                    new Timeline(
                            new KeyFrame(Duration.millis(0),
                                    new KeyValue(node.rotateProperty(), -90, WEB_EASE),
                                    new KeyValue(node.opacityProperty(), 0, WEB_EASE)
                            ),
                            new KeyFrame(Duration.millis(400),
                                    new KeyValue(node.rotateProperty(), 10, WEB_EASE)
                            ),
                            new KeyFrame(Duration.millis(500),
                                    new KeyValue(node.rotateProperty(), -10, WEB_EASE)
                            ),
                            new KeyFrame(Duration.millis(600),
                                    new KeyValue(node.rotateProperty(), 0, WEB_EASE),
                                    new KeyValue(node.opacityProperty(), 1, WEB_EASE)
                            )
                    )
            );
            setCycleDuration(Duration.millis(600));
            setDelay(Duration.seconds(0.1));
        }

        @Override protected void starting() {
            super.starting();
            node.setRotationAxis(Rotate.X_AXIS);
            oldCamera = node.getScene().getCamera();
            node.getScene().setCamera(new PerspectiveCamera());
        }

        @Override protected void stopping() {
            super.stopping();
            node.setRotate(0);
            node.setRotationAxis(Rotate.Z_AXIS);
            node.getScene().setCamera(oldCamera);
        }
    }

    public class FlipOutTransition extends CachedTimelineTransition {
        private Camera oldCamera;

        /**
         * Create new FlipOutXTransition
         *
         * @param node The node to affect
         */
        public FlipOutTransition(final Node node) {
            super(
                    node,
                    new Timeline(
                            new KeyFrame(Duration.millis(0),
                                    new KeyValue(node.rotateProperty(), 0, WEB_EASE),
                                    new KeyValue(node.opacityProperty(), 1, WEB_EASE)
                            ),
                            new KeyFrame(Duration.millis(600),
                                    new KeyValue(node.rotateProperty(), -90, WEB_EASE),
                                    new KeyValue(node.opacityProperty(), 0, WEB_EASE)
                            )
                    )
            );
            setCycleDuration(Duration.seconds(0.6));
            setDelay(Duration.seconds(0.1));
        }

        @Override protected void starting() {
            super.starting();
            node.setRotationAxis(Rotate.X_AXIS);
            oldCamera = node.getScene().getCamera();
            node.getScene().setCamera(new PerspectiveCamera());
        }

        @Override protected void stopping() {
            super.stopping();
            node.setRotate(0);
            node.setRotationAxis(Rotate.Z_AXIS);
            node.getScene().setCamera(oldCamera);
        }
    }
}
