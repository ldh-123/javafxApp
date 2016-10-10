package ldh.musicfx.ui.nav;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;
import ldh.musicfx.model.NavData;
import ldh.musicfx.ui.MusicWindow;
import ldh.musicfx.ui.TitlePane;

/**
 * Created by Puhui on 2016/10/8.
 */
public class NavDataPane extends HBox {

    private NavData navData;
    private VBox popupNode;
    private Popup popup = new Popup();
    private HBox minPane = null;
    private TitledPane titlePane = null;
    private HBox titleHBox = null;

    public NavDataPane(NavData navData, BooleanProperty isToggleBooleanProperty) {
        this.navData = navData;
        bindIsToggle(isToggleBooleanProperty);
        initUI(true);
    }

    private void bindIsToggle(BooleanProperty isToggleProperty) {
        isToggleProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                initUI(newValue);
            }
        });
    }

    private void initUI(Boolean newValue) {
        this.getChildren().clear();
        if (newValue) {
            createMinPane();
            this.getChildren().add(minPane);
            initTitlePane();
        } else {
            maxNavPane();
        }
    }

    private void initTitlePane() {
        if (titleHBox == null) {
            titleHBox = createDataPane(navData);
        }
    }

    private void maxNavPane() {
        if (navData.getChildren() != null && navData.getChildren().size() > 0) {
            createTitledPane();
            titlePane.setPrefWidth(MusicWindow.LEFT_PANE_MAX_WIDTH);
            this.getChildren().add(titlePane);
        } else {
            if (titleHBox == null) {
                titleHBox = createDataPane(navData);
            }
            this.getChildren().add(titleHBox);
        }
        this.setPrefWidth(MusicWindow.LEFT_PANE_MAX_WIDTH);
    }

    private HBox createDataPane(NavData navData) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        Image image = new Image(navData.getImageSrc());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        Label label = new Label(navData.getText());
        hbox.getChildren().addAll(imageView, label);
        hbox.getStyleClass().add("navPane");
        return hbox;
    }

    private void createPopup(NavData navData) {
        if (popupNode != null) return;
        popupNode = new VBox();
        popupNode.setPrefWidth(150);
        popupNode.setPadding(new Insets(5, 0, 5,0));
        popupNode.setSpacing(10);
        popupNode.setAlignment(Pos.CENTER_LEFT);
        for (NavData nd : navData.getChildren()) {
            HBox hbox2 = createDataPane(nd);
            popupNode.getChildren().add(hbox2);
        }
        popupNode.getStyleClass().add("navPane-popup");
        popup.getContent().add(popupNode);
    }

    private void showPopup(HBox hbox) {
        if(popup.isShowing()) {
            return;
        }
        final Window window = hbox.getScene().getWindow();
        popup.show(
                window,
                window.getX() + hbox.localToScene(0, 0).getX() + hbox.getScene().getX() + hbox.getPrefWidth() - 1,
                window.getY() + hbox.localToScene(0, 0).getY() + hbox.getScene().getY());
    }

    private void hidePopup(HBox hbox, MouseEvent e) {
        double x = e.getSceneX();
        double y = e.getSceneY();
        if (x >  hbox.localToScene(0, 0).getX() && x < hbox.localToScene(0, 0).getX() + hbox.getWidth() + 5) {
            if (y >= hbox.localToScene(0, 0).getY() && y < hbox.localToScene(0, 0).getY() + hbox.getHeight()) {
                return;
            }
        }
        popup.hide();
    }

    private void createMinPane() {
        if (minPane != null) return;
        minPane = new HBox();
        minPane.setPadding(new Insets(5, 5, 5, 5));
        minPane.setSpacing(10);
        minPane.setAlignment(Pos.CENTER_LEFT);

        StackPane stackPane = new StackPane();
        Image image = new Image(navData.getImageSrc());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        Label label = new Label(navData.getText());
        stackPane.getChildren().addAll(label, imageView);

        Rectangle clip = new Rectangle(40, 30);
        stackPane.setClip(clip);

        minPane.getChildren().addAll(stackPane);
        minPane.getStyleClass().add("navPane");
        imageView.setTranslateY(30);

        if (navData.getChildren() != null && navData.getChildren().size() > 0) {
            Region region = new Region();
            minPane.getChildren().add(region);
            HBox.setHgrow(region, Priority.ALWAYS);
            Label label2 = new Label(">");
            minPane.getChildren().add(label2);

            createPopup(navData);
            minPane.setOnMouseEntered(e->{showPopup(minPane); moveImg(label, imageView, true);});
            minPane.setOnMouseExited(e->{hidePopup(minPane, e); moveImg(label, imageView, false);});
            popupNode.setOnMouseExited(e->hidePopup(minPane, e));
        } else {
            minPane.setOnMouseEntered(e->moveImg(label, imageView, true));
            minPane.setOnMouseExited(e->moveImg(label, imageView, false));
        }
        minPane.setPrefWidth(MusicWindow.LEFT_PANE_MIN_WIDTH);
    }

    private void createTitledPane() {
        if (titlePane != null) return;
        titlePane = new TitledPane();
        titlePane.setText(navData.getText());

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(5, 0, 5,0));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER_LEFT);
        for (NavData nd : navData.getChildren()) {
            HBox hbox = createDataPane(nd);
            vbox.getChildren().add(hbox);
        }
        titlePane.setContent(vbox);
        titlePane.setExpanded(false);
    }


    private void moveImg(Label label, ImageView imageView, boolean b) {
        if (b) {
            TranslateTransition labelT = new TranslateTransition();
            labelT.setToY(-30);
            labelT.setDuration(Duration.millis(100));
            labelT.setNode(label);

            TranslateTransition imageViewT = new TranslateTransition();
            imageViewT.setToY(0);
            imageViewT.setDuration(Duration.millis(100));
            imageViewT.setNode(imageView);

            ParallelTransition st = new ParallelTransition(labelT, imageViewT);
            st.playFromStart();
        } else {
            TranslateTransition labelT = new TranslateTransition();
            labelT.setToY(0);
            labelT.setDuration(Duration.millis(100));
            labelT.setNode(label);

            TranslateTransition imageViewT = new TranslateTransition();
            imageViewT.setToY(30);
            imageViewT.setDuration(Duration.millis(100));
            imageViewT.setNode(imageView);

            ParallelTransition pt = new ParallelTransition(labelT, imageViewT);
            pt.playFromStart();
        }

    }
}
