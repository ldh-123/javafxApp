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
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.WindowEvent;
import ldh.descktop.util.ThreadToolUtil;
import ldh.fx.component.LdhDialog;
import ldh.fx.ui.LPopupButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by ldh on 2018/1/11.
 */
public class DesktopToolbar extends GridPane {

    private LPopupButton windowButton = new LPopupButton(LPopupButton.PopupPos.up_west);
    private HBox leftPane = new HBox();
    private HBox contentPane = new HBox();
    private HBox rightPane = new HBox();

    public DesktopToolbar() {
        this.getStyleClass().add("desktop-toolbar");
        FontAwesomeIconView windowGraphic = new FontAwesomeIconView();
        windowGraphic.getStyleClass().add("window-graphic");
        windowButton.setGraphic(windowGraphic);
        windowButton.getStyleClass().add("toolbar-item");
        windowButton.setPopupContentPane(new Label("asdfaffdadfasdfasfasdfasfsa"));

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

        leftPane.getStyleClass().add("left-pane");
        rightPane.getStyleClass().add("right-pane");
        contentPane.getStyleClass().add("content-pane");
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

    private void initItem() {
//        addButton(new Icons525View(), "browser-graphic", leftPane);
        Button browser = addButton(new MaterialDesignIconView(), "browser-graphic", leftPane);
        browser.setOnAction(e->addBrowserDialog());
        addMessageButton(new MaterialDesignIconView(), "message-graphic", rightPane);
//        addButton(new MaterialDesignIconView(), "message-graphic", rightPane);
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

    private Button addMessageButton(GlyphIcon glyphIcon, String icon, HBox box) {
        LPopupButton messageButton = new LPopupButton(LPopupButton.PopupPos.up_east);
        messageButton.getStyleClass().add("toolbar-item");
        if (glyphIcon != null) {
            glyphIcon.getStyleClass().add(icon);
            messageButton.setGraphic(glyphIcon);
        }
        box.getChildren().add(messageButton);
        messageButton.setPopupContentPane(new Label("asdfasfasfdasfa"));
        return messageButton;
    }

    private void addBrowserDialog() {
        ToolbarButton toolbarButton = new ToolbarButton(new Button("百度搜索"));
        LdhDialog ldhDialog = new LdhDialog("百度搜索", 1000d, 600d);
        ldhDialog.setModel(false);
        ldhDialog.setIsHide(true);
        WebView webView = new WebView();
        ldhDialog.setContentPane(webView);
        ldhDialog.show();
        getContentPane().getChildren().add(toolbarButton);
        ldhDialog.setOnCloseRequestHandler(e->getContentPane().getChildren().remove(toolbarButton));
        toolbarButton.getButton().setOnAction(e->{
            if (ldhDialog.isShowing()) {
                ldhDialog.min();
            } else {
                ldhDialog.show();
            }
        });
        toolbarButton.getButton().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, e->{ldhDialog.close();e.consume();});
        Platform.runLater(()->webView.getEngine().load("http://www.baidu.com"));
    }
}
