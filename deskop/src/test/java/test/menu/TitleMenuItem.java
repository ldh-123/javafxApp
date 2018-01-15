package test.menu;

import javafx.scene.control.MenuItem;

/**
 * Created by ldh on 2018/1/15.
 */
public class TitleMenuItem extends MenuItem {

    /**
     * Constructor
     *
     * @param text
     *            The Text of The Menu Item
     *
     */
    public TitleMenuItem(String text) {
        setText(text);
        setDisable(true);
        getStyleClass().clear();
        getStyleClass().add("title-menu-item");
    }

}
