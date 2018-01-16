package ldh.descktop.ui;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.scene.Node;
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
import ldh.descktop.transition.BounceInTransition2;
import ldh.fx.component.LdhDialog;
import ldh.fx.component.LdhPopupDialog;
import ldh.descktop.transition.BounceInTransition;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by ldh on 2018/1/11.
 */
public class DesktopItem extends StackPane {

    private DesktopToolbar desktopToolbar;
    private Label label;
    private DesktopNodeFactory desktopNodeFactory;

    public DesktopItem() {
        this.getStyleClass().add("desktop-item");
        initUi();
    }

    public DesktopItem(Label label, DesktopNodeFactory desktopNodeFactory) {
        this.getStyleClass().add("desktop-item");
        this.label = label;
        this.desktopNodeFactory = desktopNodeFactory;

//        clipImageView(label.getGraphic(), 50, 50);
        initUi();
    }

    public DesktopItem(String text, DesktopNodeFactory desktopNodeFactory) {
        this(null, text, desktopNodeFactory, text);
    }

    public DesktopItem(Image image, String text, DesktopNodeFactory desktopNodeFactory) {
        this(image, text, desktopNodeFactory, text);
    }

    public DesktopItem(Image image, String text, DesktopNodeFactory desktopNodeFactory, String desc) {
        this.getStyleClass().add("desktop-item");
        ImageView iv = new ImageView(image);
        clipImageView(iv, 50, 50);
        label = new Label(text,iv);
        label.setTooltip(new Tooltip(desc));
        this.desktopNodeFactory = desktopNodeFactory;

        initUi();
    }

    public void setText(String text) {
        getLabel().setText(text);
    }

    public String getText() {
        return StringUtils.isEmpty(getLabel().getText()) ? "" : label.getText();
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
        label.getStyleClass().add("item-content");
        label.setTooltip(new Tooltip(getText()));
        label.setOnMouseClicked(e->openDialog(e));
        this.getChildren().add(label);
    }

    private void openDialog(MouseEvent event) {
        if (event.getClickCount() != 2) return;
        ToolbarButton toolbarButton = new ToolbarButton(new Button(getText()));
        Node newNode = buildNewGraphic(this.getLabel().getGraphic());
        toolbarButton.getButton().setGraphic(newNode);
        toolbarButton.getButton().getGraphic().setStyle("-glyph-size: 15px;");

//        LdhPopupDialog ldhDialog = new LdhPopupDialog(getLabel().getTooltip().getText(), 1000d, 600d);
        LdhDialog ldhDialog = new LdhDialog(getLabel().getTooltip().getText(), 1000d, 600d);
        ldhDialog.setModel(false);
        ldhDialog.setIsHide(true);
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

        Platform.runLater(()->ldhDialog.setContentPane(desktopNodeFactory.create()));
    }

    private Node buildNewGraphic(Node graphic) {
        if (graphic instanceof ImageView) {
            ImageView imageView = new ImageView();
            imageView.setImage(((ImageView) graphic).getImage());
            clipImageView(imageView, 30, 30);
            return imageView;
        } else if (graphic instanceof FontAwesomeIconView) {
            FontAwesomeIconView glyphIcon = (FontAwesomeIconView) graphic;
            FontAwesomeIconView newGlyphIcon = new FontAwesomeIconView();
            newGlyphIcon.setGlyphName(glyphIcon.getGlyphName());
            newGlyphIcon.setGlyphSize(25);
            return newGlyphIcon;
        }
        return graphic;
    }

    private void clipImageView(ImageView imageView, double width, double height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        Rectangle clip = new Rectangle(width, height);
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        imageView.setClip(clip);
    }

}
