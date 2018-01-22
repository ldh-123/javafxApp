package ldh.fx.ui.util;

import de.jensd.fx.glyphs.GlyphIcon;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Created by ldh on 2018/1/19.
 */
public class RegionUtil {

    public static Button createButton(String text, GlyphIcon icon, String styleClass) {
        Button button = new Button(text);
        icon.getStyleClass().add(styleClass);
        button.setGraphic(icon);
        return button;
    }

    public static Label createLabel(String text, GlyphIcon icon, String styleClass) {
        Label button = new Label(text);
        icon.getStyleClass().add(styleClass);
        button.setGraphic(icon);
        return button;
    }
}
