package ldh.descktop.ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ldh.fx.StageUtil;
import ldh.fx.component.LDialog;
import ldh.fx.component.LDialogBase;
import ldh.fx.component.DialogModel;
import ldh.fx.component.LdhDialog;
import ldh.fx.component.LdhPopupDialog;
import ldh.fx.component.LxDialog;
import ldh.fx.util.DialogUtil;
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
        toolbarButton.getButton().getGraphic().getStyleClass().add("desktop-button-min-graphic");
        desktopToolbar.getContentPane().getChildren().add(toolbarButton);

        Object obj = desktopNodeFactory.create();
        if (desktopNodeFactory.isNode(obj)) {

            LDialog ldhDialog = new LDialog(StageUtil.STAGE, getLabel().getTooltip().getText(), 800d, 500d);
//            LdhDialog ldhDialog = new LdhDialog(getLabel().getTooltip().getText(), 800d, 500d);
//            ldhDialog.setIsHide(true);
            ldhDialog.show();

            ldhDialog.setOnCloseRequestHandler(e->desktopToolbar.getContentPane().getChildren().remove(toolbarButton));
            toolbarButton.getButton().setOnAction(e->{
                ldhDialog.show();
            });

            toolbarButton.getButton().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, e->{
                desktopToolbar.getContentPane().getChildren().remove(toolbarButton);
                ldhDialog.close();e.consume();
            });

            ldhDialog.setContentPane((Node) obj);
        } else if (desktopNodeFactory.isStage(obj)) {
            Stage stage = (Stage) obj;
            stage.show();
            stage.requestFocus();
            StageUtil.putNewStage(stage, (Void)->{desktopToolbar.getContentPane().getChildren().remove(toolbarButton); return null;});
            toolbarButton.getButton().setOnAction(e->{
                if (stage.isShowing()) {
                    stage.setIconified(false);
                } else {
                    stage.setIconified(true);
                }
            });

            toolbarButton.getButton().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, e->{
                stage.close();
                StageUtil.closeNewStage(stage);
                desktopToolbar.getContentPane().getChildren().remove(toolbarButton);
                e.consume();
            });
        } else {
            DialogUtil.modelInfo("error", "desktopNodeFactory create oldy node and stage", 300, 200);
        }
    }

    private Node buildNewGraphic(Node graphic) {
        if (graphic instanceof ImageView) {
            ImageView imageView = new ImageView();
            imageView.setImage(((ImageView) graphic).getImage());
            double width = 20d, height = 20d;
            imageView.setFitHeight(width); imageView.setFitWidth(width);
//            clipImageView(imageView, width, height);
            return imageView;
        } else if (graphic instanceof FontAwesomeIconView) {
            FontAwesomeIconView glyphIcon = (FontAwesomeIconView) graphic;
            FontAwesomeIconView newGlyphIcon = new FontAwesomeIconView();
            newGlyphIcon.setGlyphName(glyphIcon.getGlyphName());
            newGlyphIcon.setGlyphSize(28);
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
