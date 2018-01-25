package ldh.descktop.ui;

import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Created by ldh on 2018/1/15.
 */
public interface DesktopNodeFactory {

    Object create();

    default boolean isNode(Object node) {
        return node instanceof Node;
    }

    default boolean isStage(Object node) {
        return node instanceof Stage;
    }
}
