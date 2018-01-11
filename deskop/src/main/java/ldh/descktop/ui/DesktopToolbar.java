package ldh.descktop.ui;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.icons525.Icons525View;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Platform;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import ldh.descktop.util.ThreadToolUtil;
import ldh.fx.ui.LPopupButton;
import org.apache.commons.lang3.time.DateUtils;
import org.controlsfx.control.GridView;
import org.controlsfx.control.spreadsheet.Grid;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by ldh on 2018/1/11.
 */
public class DesktopToolbar extends GridPane {

    private LPopupButton windowButton = new LPopupButton(LPopupButton.PopupPos.east_down);
    private HBox leftPane = new HBox();
    private HBox contentPane = new HBox();
    private HBox rightPane = new HBox();

    public DesktopToolbar() {
        this.getStyleClass().add("desktop-toolbar");
        FontAwesomeIconView windowGraphic = new FontAwesomeIconView();
        windowGraphic.getStyleClass().add("window-graphic");
        windowButton.setGraphic(windowGraphic);
        windowButton.getStyleClass().add("toolbar-item");
        windowButton.setPopupNode(new Label("asdfaffdadfasdfasfasdfasfsa"));

        GridPane.setFillHeight(leftPane, true);
        GridPane.setFillHeight(rightPane, true);
        GridPane.setFillHeight(windowButton, true);
        GridPane.setFillHeight(contentPane, true);

        GridPane.setVgrow(leftPane, Priority.ALWAYS);
        GridPane.setVgrow(rightPane, Priority.ALWAYS);
        GridPane.setVgrow(windowButton, Priority.ALWAYS);
        GridPane.setVgrow(contentPane, Priority.ALWAYS);

        GridPane.setHgrow(contentPane, Priority.ALWAYS);

        add(windowButton, 0, 0);
        add(leftPane, 1, 0);
        add(contentPane, 2, 0);
        add(rightPane, 3, 0);

        rightPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        rightPane.setAlignment(Pos.CENTER);
        leftPane.setAlignment(Pos.CENTER);
        initItem();
    }

    private void initItem() {
//        addButton(new Icons525View(), "browser-graphic", leftPane);
        Button browser = addButton(null, "", leftPane);
        browser.setText("ie");
        addButton(new MaterialDesignIconView(), "message-graphic", rightPane);
        Label dayLabel = new Label();
        Label timeLabel = new Label();
        dateText(dayLabel, timeLabel);
        VBox dateBox = new VBox();
        dateBox.setAlignment(Pos.CENTER);
        dateBox.getChildren().addAll(timeLabel, dayLabel);
        dateBox.setSpacing(2);
        dateBox.getStyleClass().add("toolbar-item");
        rightPane.getChildren().add(dateBox);
    }

    private void dateText(Label dayLabel, Label timeLabel) {
        Runnable runnable = () -> {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String day = dateFormat.format(date);
            dateFormat = new SimpleDateFormat("HH:mm");
            String time = dateFormat.format(date);
            Platform.runLater(() -> {
                dayLabel.setText(day);
                timeLabel.setText(time);
            });
        };
        ThreadToolUtil.scheduleAtFixedRate(runnable, 40, TimeUnit.SECONDS);
    }

    private Button addButton(GlyphIcon glyphIcon, String icon, HBox box) {
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
