package ldh.musicfx.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;
import ldh.musicfx.ui.TitlePane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Puhui on 2016/9/24.
 */
public class MusicFxController implements Initializable {

    private BooleanProperty isToggleBooleanProperty = new SimpleBooleanProperty(true);
    private DoubleProperty leftPaneWidth = new SimpleDoubleProperty(300);

    @FXML
    VBox musicLeftPane;
    @FXML
    StackPane titlePane;

    private double startMoveX = -1;
    private double startMoveY = -1;
    private double stageX = -1;
    private double stageY = -1;
    private Boolean dragging = false;

    @FXML
    public void toggleLefPaneAction(MouseEvent ae) {
        double width1 = 200;
        double width2 = 60;
        if (isToggleBooleanProperty.get()) {
            width1 = 60;
            width2 = 200;
        }
        isToggleBooleanProperty.set(!isToggleBooleanProperty.get());
        Timeline timeline = new Timeline();
        final KeyValue kv = new KeyValue(leftPaneWidth, width1);
        final KeyFrame kf = new KeyFrame(Duration.millis(0), kv);
        final KeyValue kv2 = new KeyValue(leftPaneWidth, width2);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(500), kv2);
        timeline.getKeyFrames().addAll(kf, kf2);
        timeline.play();
    }

    @FXML
    public void startMoveWindow(MouseEvent evt) {
        startMoveX = evt.getScreenX();
        startMoveY = evt.getScreenY();
        Window w = titlePane.getScene().getWindow();
        stageX = w.getX();
        stageY = w.getY();
        dragging = true;
    }

    @FXML
    public void moveWindow(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            Window w = titlePane.getScene().getWindow();
            w.setX(stageX + (endMoveX - startMoveX));
            w.setY(stageY + (endMoveY - startMoveY));
        }
    }

    @FXML
    public void endMoveWindow(MouseEvent evt) {
        if (dragging) {
            startMoveX = 0;
            startMoveY = 0;
            stageX = 0;
            stageY = 0;
            dragging = false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leftPaneWidth.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                musicLeftPane.setPrefWidth(newValue.doubleValue());
            }
        });

        String imgFile = getClass().getClassLoader().getResource("img/music.jpg").toExternalForm();
        TitlePane tp = new TitlePane(imgFile, "test", isToggleBooleanProperty);
        titlePane.getChildren().add(tp);
    }


}
