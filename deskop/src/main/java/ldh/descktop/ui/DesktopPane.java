package ldh.descktop.ui;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import ldh.fx.StageUtil;
import ldh.fx.util.DialogUtil;

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
//        contextMenu.setAutoFix(true);

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

}
