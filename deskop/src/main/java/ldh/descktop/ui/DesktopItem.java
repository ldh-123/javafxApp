package ldh.descktop.ui;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import ldh.fx.component.LdhDialog;

/**
 * Created by ldh on 2018/1/11.
 */
public class DesktopItem extends StackPane {

    private Image image;
    private String text;
    private String desc;
    private String url;

    public DesktopItem(Image image, String text, String url) {
        this(image, text, url, text);
    }

    public DesktopItem(Image image, String text, String url, String desc) {
        this.getStyleClass().add("desktop-item");
        this.image = image;
        this.text = text;
        this.url = url;
        this.desc = desc;

        initUi();
    }

    private void initUi() {
        ImageView iv = new ImageView(image);
        iv.setFitWidth(50);
        iv.setFitHeight(50);
        Rectangle clip = new Rectangle(50, 50);
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        iv.setClip(clip);
        Label label = new Label(text, iv);
        label.getStyleClass().add("item-content");
        label.setTooltip(new Tooltip(desc));
        label.setOnMouseClicked(e->openDialog(e));
        this.getChildren().add(label);
    }

    private void openDialog(MouseEvent event) {
        if (event.getClickCount() != 2) return;
        if (url.startsWith("http")) {
            LdhDialog ldhDialog = new LdhDialog(desc, 1000d, 600d);
            WebView webView = new WebView();
            ldhDialog.setContentPane(webView);
            ldhDialog.show();
            Platform.runLater(()->webView.getEngine().load(url));
        } else {

        }
    }

}
