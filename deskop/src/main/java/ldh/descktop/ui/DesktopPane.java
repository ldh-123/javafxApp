package ldh.descktop.ui;

import javafx.collections.ListChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

/**
 * Created by ldh on 2018/1/11.
 */
public class DesktopPane extends FlowPane {

    public DesktopPane() {
        this.getStyleClass().add("desktop-pane");
        setOrientation(Orientation.VERTICAL);
    }

}
