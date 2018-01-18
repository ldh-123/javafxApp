package ldh.descktop.ui;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Created by ldh on 2018/1/18.
 */
public class DesktopNavItem extends StackPane {

    public DesktopNavItem() {
        init();
    }

    public DesktopNavItem(Node... children) {
        super(children);
        init();
    }

    private void init() {
        this.getStyleClass().add("desktop-nav-item");
    }
}
