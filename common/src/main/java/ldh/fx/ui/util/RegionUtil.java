package ldh.fx.ui.util;

import de.jensd.fx.glyphs.GlyphIcon;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

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

    public static void sizeRegion(Region region, Scene scene) {
        region.setPrefWidth(scene.getWidth());
        region.setPrefHeight(scene.getHeight());
//        region.resize(scene.getWidth(), scene.getHeight());
        region.requestLayout();
    }

    public static void sizeRegionWhenSceneChange(Region region, Scene scene) {
        scene.widthProperty().addListener((l, o, n)-> RegionUtil.sizeRegion(region, scene));
        scene.heightProperty().addListener((l, o, n)->RegionUtil.sizeRegion(region, scene));
    }
}
