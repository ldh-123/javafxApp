package ldh.descktop.ui;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.WindowEvent;
import ldh.descktop.util.ThreadToolUtil;
import ldh.fx.StageUtil;
import ldh.fx.component.DialogModel;
import ldh.fx.component.LDialog;
import ldh.fx.component.LPopupButton;
import ldh.fx.component.PopupPos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by ldh on 2018/1/19.
 */
public class WinDesktopToolbar extends DesktopToolbar {

    private LPopupButton windowButton = null;

    public WinDesktopToolbar(DesktopPane desktopPane) {
        super(desktopPane);
    }

    protected void initItem() {
        windowButton = new LPopupButton(PopupPos.up_east);
        FontAwesomeIconView windowGraphic = new FontAwesomeIconView();
        windowGraphic.getStyleClass().add("window-graphic");
        windowButton.setGraphic(windowGraphic);
        windowButton.getStyleClass().add("toolbar-item");
        windowButton.setPopupContentPane(new Label("asdfaffdadfasdfasfasdfasfsa"));
        windowButton.getPopup().showingProperty().addListener((l, o, n)->{
            animation(n);
        });

        this.getLeftPane().getChildren().add(0, windowButton);

//        addButton(new Icons525View(), "browser-graphic", leftPane);
        Button browser = addButton(new MaterialDesignIconView(), "browser-graphic", getLeftPane());
        browser.setOnAction(e->addBrowserDialog());
        addMessageButton(new MaterialDesignIconView(), "message-graphic", getRightPane());
//        addButton(new MaterialDesignIconView(), "message-graphic", rightPane);
        Label dayLabel = new Label();
        Label timeLabel = new Label();
        dateText(dayLabel, timeLabel);
        VBox dateBox = new VBox();
        dateBox.setAlignment(Pos.CENTER);
        dateBox.getChildren().addAll(timeLabel, dayLabel);
        dateBox.setSpacing(2);
        dateBox.getStyleClass().addAll("toolbar-item", "win-date");
        getRightPane().getChildren().add(dateBox);
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

    private Button addMessageButton(GlyphIcon glyphIcon, String icon, Pane box) {
        LPopupButton messageButton = new LPopupButton(PopupPos.up_west);
        messageButton.getStyleClass().add("toolbar-item");
        if (glyphIcon != null) {
            glyphIcon.getStyleClass().add(icon);
            messageButton.setGraphic(glyphIcon);
        }
        box.getChildren().add(messageButton);
        messageButton.setPopupContentPane(new Label("asdfasfasfdasfa"));
        messageButton.getPopup().showingProperty().addListener((l, o, n)->{
            animation(n);
        });
        return messageButton;
    }

    private void addBrowserDialog() {
        ToolbarButton toolbarButton = new ToolbarButton(new Button("百度搜索"));
        LDialog ldhDialog = new LDialog(StageUtil.STAGE, "百度搜索", 1000d, 600d, DialogModel.Stand_alone);
//        ldhDialog.setModel(false);
//        ldhDialog.setIsHide(true);
        ldhDialog.getScene().getStylesheets().add("/component/LxDialog.css");
        ldhDialog.getScene().getStylesheets().add("/component/LDialog.css");
        WebView webView = new WebView();
        ldhDialog.setContentPane(webView);
        ldhDialog.show();
        getContentPane().getChildren().add(toolbarButton);
        ldhDialog.setOnCloseRequestHandler(e->getContentPane().getChildren().remove(toolbarButton));
        toolbarButton.getButton().setOnAction(e->{
            if (ldhDialog.isShowing()) {
//                ldhDialog.min();
            } else {
                ldhDialog.show();
            }
        });
        toolbarButton.getButton().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, e->{ldhDialog.close();e.consume();});
        Platform.runLater(()->webView.getEngine().load("http://www.baidu.com"));
    }
}
