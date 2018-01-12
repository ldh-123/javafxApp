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
import javafx.stage.WindowEvent;
import ldh.descktop.page.FormContent;
import ldh.fx.component.LdhDialog;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by ldh on 2018/1/11.
 */
public class DesktopItem extends StackPane {

    private Image image;
    private String text;
    private String desc;
    private String url;
    private DesktopToolbar desktopToolbar;
    private Label label;

    public DesktopItem() {
        this.getStyleClass().add("desktop-item");
        initUi();
    }

    public DesktopItem(String text, String url) {
        this(null, text, url, text);
    }

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

    public void setText(String text) {
        getLabel().setText(text);
    }

    public String getText() {
        return StringUtils.isEmpty(getLabel().getText()) ? (text == null ? "" : text) : label.getText();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public Label getLabel() {
        if (label == null) {
            label = new Label();
        }
        return label;
    }

    public void setDesktopToolbar(DesktopToolbar desktopToolbar) {
        this.desktopToolbar = desktopToolbar;
    }

    private void initUi() {
        label = new Label(getText());
        if (image != null) {
            ImageView iv = new ImageView(image);
            iv.setFitWidth(50);
            iv.setFitHeight(50);
            Rectangle clip = new Rectangle(50, 50);
            clip.setArcWidth(15);
            clip.setArcHeight(15);
            iv.setClip(clip);
            label.setGraphic(iv);
        }

        label.getStyleClass().add("item-content");
        label.setTooltip(new Tooltip(getText()));
        label.setOnMouseClicked(e->openDialog(e));
        this.getChildren().add(label);
    }

    private void openDialog(MouseEvent event) {
        if (event.getClickCount() != 2) return;
        if (url.startsWith("http")) {
            ToolbarButton toolbarButton = new ToolbarButton(new Button(getText()));
            LdhDialog ldhDialog = new LdhDialog(desc, 1000d, 600d);
            ldhDialog.setModel(false);
            ldhDialog.setIsHide(true);
            WebView webView = new WebView();
            ldhDialog.setContentPane(webView);
            ldhDialog.show();
            desktopToolbar.getContentPane().getChildren().add(toolbarButton);
            ldhDialog.setOnCloseRequestHandler(e->desktopToolbar.getContentPane().getChildren().remove(toolbarButton));
            toolbarButton.getButton().setOnAction(e->{
                if (ldhDialog.isShowing()) {
                    ldhDialog.min();
                } else {
                    ldhDialog.show();
                }
            });
            toolbarButton.getButton().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, e->{ldhDialog.close();e.consume();});
            Platform.runLater(()->webView.getEngine().load(url));
        } else if (url.equals("form")) {
            ToolbarButton toolbarButton = new ToolbarButton(new Button(getText()));
            LdhDialog ldhDialog = new LdhDialog(desc, 1000d, 600d);
            ldhDialog.setModel(false);
            ldhDialog.setIsHide(true);
            ldhDialog.setContentPane(new FormContent());
            ldhDialog.show();
            desktopToolbar.getContentPane().getChildren().add(toolbarButton);
            ldhDialog.setOnCloseRequestHandler(e->desktopToolbar.getContentPane().getChildren().remove(toolbarButton));
            toolbarButton.getButton().setOnAction(e->{
                if (ldhDialog.isShowing()) {
                    ldhDialog.min();
                } else {
                    ldhDialog.show();
                }
            });
            toolbarButton.getButton().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, e->{ldhDialog.close();e.consume();});
        }
    }

}
