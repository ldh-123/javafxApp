package ldh.descktop.ui;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Platform;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.WindowEvent;
import ldh.descktop.util.ThreadToolUtil;
import ldh.fx.component.LdhDialog;
import ldh.fx.component.LPopupButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by ldh on 2018/1/11.
 */
public class DesktopToolbar extends HBox {

    private HBox leftPane = new HBox();
    private HBox contentPane = new HBox();
    private HBox rightPane = new HBox();

    public DesktopToolbar() {
        this.getStyleClass().add("desktop-toolbar");

        rightPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        rightPane.setAlignment(Pos.CENTER);
        leftPane.setAlignment(Pos.CENTER);

        leftPane.getStyleClass().add("left-pane");
        rightPane.getStyleClass().add("right-pane");
        contentPane.getStyleClass().add("content-pane");

        HBox.setHgrow(contentPane, Priority.ALWAYS);
        this.getChildren().addAll(leftPane, contentPane, rightPane);
        initItem();
    }

    public Pane getLeftPane() {
        return leftPane;
    }

    public Pane getRightPane() {
        return rightPane;
    }

    public Pane getContentPane() {
        return contentPane;
    }

    protected void initItem() {}

    protected Button addButton(GlyphIcon glyphIcon, String icon, Pane box) {
        Button button = new Button();
        button.getStyleClass().add("toolbar-item");
        if (glyphIcon != null) {
            glyphIcon.getStyleClass().add(icon);
            button.setGraphic(glyphIcon);
        }
        box.getChildren().add(button);
        return button;
    }
}
