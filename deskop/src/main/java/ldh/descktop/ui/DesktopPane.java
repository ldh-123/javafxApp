package ldh.descktop.ui;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import ldh.fx.StageUtil;

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
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem fullScreen = new MenuItem("进入全屏");
        MenuItem notFullScreen = new MenuItem("退出全屏");
        MenuItem about = new MenuItem("关于");
        contextMenu.getItems().addAll(fullScreen, notFullScreen, about);
        contextMenu.getStyleClass().add("desktop-context-menu");
        fullScreen.getStyleClass().add("desktop-menu-item");

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
            contextMenu.show(DesktopPane.this, e.getScreenX(), e.getScreenY());
        });
    }

}
