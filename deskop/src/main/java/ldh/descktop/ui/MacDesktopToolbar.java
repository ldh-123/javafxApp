package ldh.descktop.ui;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.WindowEvent;
import ldh.descktop.util.ThreadToolUtil;
import ldh.fx.component.LPopupButton;
import ldh.fx.component.LdhDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by ldh on 2018/1/19.
 */
public class MacDesktopToolbar extends DesktopToolbar {

    private LPopupButton macButton = null;

    public MacDesktopToolbar() {
        super();
    }

    protected void initItem() {
        macButton = new LPopupButton(LPopupButton.PopupPos.down_west);
        MaterialDesignIconView macGraphic = new MaterialDesignIconView();
        macGraphic.getStyleClass().add("mac-graphic");
        macButton.setGraphic(macGraphic);
        macButton.getStyleClass().add("toolbar-item");
        macButton.setPopupContentPane(new Label("asdfaffdadfasdfasfasdfasfsa"));

        this.getLeftPane().getChildren().add(0, macButton);

        Button browser = addButton(new MaterialDesignIconView(), "browser-graphic", getLeftPane());
        browser.setOnAction(e->addBrowserDialog());
        addMessageButton(new MaterialDesignIconView(), "message-graphic", getRightPane());
        Label dayLabel = new Label();
        Label timeLabel = new Label();
        dateText(dayLabel, timeLabel);
        VBox dateBox = new VBox();
        dateBox.setAlignment(Pos.CENTER);
        dateBox.getChildren().addAll(timeLabel, dayLabel);
        dateBox.setSpacing(2);
        dateBox.getStyleClass().add("toolbar-item");
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
        LPopupButton messageButton = new LPopupButton(LPopupButton.PopupPos.down_east);
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
