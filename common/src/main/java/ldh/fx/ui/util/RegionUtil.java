package ldh.fx.ui.util;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import ldh.fx.component.LPopupButton;
import ldh.fx.component.PopupPos;

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

    public static Double anchorY(Region node, PopupPos popupPos) {
        double anchorY = node.getScene().getWindow().getY() + node.localToScene(0, 0).getY() + node.getScene().getY();
        if (popupPos == PopupPos.down_east) {
            anchorY = anchorY + node.getHeight();
        } else if (popupPos == PopupPos.down_west) {
            anchorY = anchorY + node.getHeight();
        } else if (popupPos == PopupPos.up_east) {
            anchorY = anchorY - node.getHeight();
        } else if (popupPos ==PopupPos.up_west) {
            anchorY = anchorY - node.getHeight();
        }
        return anchorY;
    }

    public static Double anchorX(Region node, PopupPos popupPos) {
        double anchorX = node.getScene().getWindow().getX() + node.localToScene(0, 0).getX() + node.getScene().getX() - 1;
        if (popupPos == PopupPos.down_east) {
            anchorX = anchorX + node.getHeight();
        } else if (popupPos == PopupPos.down_west) {
            anchorX = anchorX + node.getHeight();
        } else if (popupPos == PopupPos.up_east) {
            anchorX = anchorX - node.getWidth();
        } else if (popupPos == PopupPos.up_west) {
            anchorX = anchorX - node.getWidth();
        }
        return anchorX;
    }

    public static Double anchorX(Region node) {
        double anchorX = node.getScene().getWindow().getX() + node.localToScene(0, 0).getX() + node.getScene().getX();
        return anchorX;
    }

    public static Double anchorY(Region node) {
        double anchorY = node.getScene().getWindow().getY() + node.localToScene(0, 0).getY() + node.getScene().getY();
        return anchorY;
    }

    public static double calcAnchorX(Region region, PopupPos popupPos, Node contentPane) {
        double anchorX = region.getScene().getWindow().getX() + region.localToScene(0, 0).getX() + region.getScene().getX();
        double width = popupContentWidth(contentPane);
        if (popupPos == PopupPos.down_east) {
//            anchorX = anchorX + region.getWidth() - popupContentWidth(contentPane);
        } else if (popupPos == PopupPos.down_west) {
            anchorX = anchorX + region.getWidth() - width;
        } else if (popupPos == PopupPos.up_east) {
//            anchorX = anchorX - popupContentWidth(contentPane);
        } else if (popupPos == PopupPos.up_west) {
            anchorX = anchorX - width;
        }
        return anchorX;
    }

    public static double calcAnchorY(Region region, PopupPos popupPos, Node contentPane) {
        double anchorY = region.getScene().getWindow().getY() + region.localToScene(0, 0).getY() + region.getScene().getY();
        if (popupPos == PopupPos.down_east) {
            anchorY = anchorY + region.getHeight() + 1;
        } else if (popupPos == PopupPos.down_west) {
            anchorY = anchorY + region.getHeight() + 1;
        } else if (popupPos == PopupPos.up_east) {
            anchorY = anchorY - popupContentHeight(contentPane) - 1;
        } else if (popupPos == PopupPos.up_west) {
            anchorY = anchorY - popupContentHeight(contentPane) - 1;
        }
        return anchorY;
    }

    public static double snapSize(Region region, double value) {
        return snapSize(value, region.isSnapToPixel());
    }

    public static Node copyGraphic(Node node) {
        if (node == null) return null;
        if (node instanceof FontAwesomeIconView) {
            FontAwesomeIconView fontAwesomeIconView = (FontAwesomeIconView) node;
            FontAwesomeIconView newNode = new FontAwesomeIconView();
            newNode.getStyleClass().addAll(fontAwesomeIconView.getStyleClass());
            copy(fontAwesomeIconView, newNode);
            return newNode;
        } else if (node instanceof MaterialDesignIconView) {
            MaterialDesignIconView materialDesignIconView = (MaterialDesignIconView) node;
            MaterialDesignIconView newNode = new MaterialDesignIconView();
            newNode.getStyleClass().addAll(materialDesignIconView.getStyleClass());
            copy(materialDesignIconView, newNode);
            return newNode;
        }
        return null;
    }

    private static void copy(GlyphIcon from, GlyphIcon to) {
        to.setGlyphSize(from.getGlyphSize());
        to.setGlyphName(from.getGlyphName());
        to.setFill(from.getFill());

    }

    private static double popupContentWidth(Node contentPane) {
        if (contentPane instanceof Region) {
            Region r = (Region) contentPane;
            return r.getWidth();
        }
        return contentPane.prefWidth(0);
    }

    private static double popupContentHeight(Node contentPane) {
        if (contentPane instanceof Region) {
            Region r = (Region) contentPane;
            return r.getHeight();
        }
        return contentPane.prefHeight(0);
    }

    private static double snapSize(double value, boolean snapToPixel) {
        return snapToPixel ? Math.ceil(value) : value;
    }
}
