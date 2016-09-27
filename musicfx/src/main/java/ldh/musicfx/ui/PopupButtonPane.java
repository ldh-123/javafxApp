package ldh.musicfx.ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;

/**
 * Created by Puhui on 2016/9/27.
 */
public class PopupButtonPane extends HBox {

    private Button button;
    private SettingPane settingPane;
    private Popup popUp;
    private DoubleProperty popPosDoubleProperty = new SimpleDoubleProperty();
    private Timeline timeLine;
    private BooleanProperty isToggleBooleanProperty;

    public PopupButtonPane(BooleanProperty isToggleBooleanProperty) {
        button = new Button("user");
        settingPane = new SettingPane(this);
        this.getChildren().add(button);
        button.setOnAction(e->showPop(e));
        button.setOnMouseExited(e->hidePopup2(e));
        popUp = new Popup();
//        popUp.setAutoHide(true);
        settingPane.setOnDragEntered(e->showPopup());
        settingPane.setOnMouseExited(e->hidePopup());
        popUp.getContent().add(settingPane);
        initTimeline();

        popPosDoubleProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                final Window window = button.getScene().getWindow();
                double height = settingPane.getHeight();
                popUp.show(
                        window,
                        window.getX() + button.localToScene(0, 0).getX() + button.getScene().getX() + newValue.doubleValue(),
                        window.getY() + button.localToScene(0, 0).getY() + button.getScene().getY()  - button.getPrefHeight() - settingPane.getHeight() - 5);

            }
        });

        initUI(true);
        bindIsToggle(isToggleBooleanProperty);
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
        if (!newValue) {
            HBox hbox = new HBox();
            hbox.setPadding(new Insets(15));
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER_LEFT);
            Label label = new Label("user");
            hbox.getChildren().addAll(button, label);
            this.getChildren().add(hbox);
        } else {
            HBox hbox = new HBox();
            hbox.setPadding(new Insets(15));
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.getChildren().addAll(button);
            this.getChildren().add(hbox);
        }
    }

    private void hidePopup2(MouseEvent e) {
        double x = e.getSceneX();
        double y = e.getSceneY();
        if (x >  button.localToScene(0, 0).getX() && x < button.localToScene(0, 0).getX() + settingPane.getWidth()) {
            if (y <= button.localToScene(0, 0).getY() && y > button.localToScene(0, 0).getY() - 10) {
                return;
            }
        }
        hidePopup();
    }

    public void hidePopup() {
        popUp.hide();
    }

    private void showPopup() {
        final Window window = button.getScene().getWindow();
        popUp.show(
                window,
                window.getX() + button.localToScene(0, 0).getX() + button.getScene().getX(),
                window.getY() + button.localToScene(0, 0).getY() + button.getScene().getY()  - 300 - 5);

    }

    private void showPop(ActionEvent e) {
        if (popUp.isShowing()) return;
        timeLine.playFromStart();
    }

    private void initTimeline() {
        timeLine = new Timeline();
        final KeyValue kv = new KeyValue(popPosDoubleProperty, 100);
        final KeyFrame kf = new KeyFrame(Duration.millis(0), kv);
        final KeyValue kv1 = new KeyValue(popPosDoubleProperty, 0);
        final KeyFrame kf1 = new KeyFrame(Duration.millis(100), kv1);
        timeLine.getKeyFrames().addAll(kf, kf1);
    }
}
