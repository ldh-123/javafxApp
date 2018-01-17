package ldh.descktop.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PopupControl;
import javafx.scene.layout.FlowPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import ldh.fx.component.LdhDialog;

import java.util.concurrent.atomic.DoubleAdder;

/**
 * Created by ldh on 2018/1/16.
 */
public class DialogTransitionTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        LdhDialog dialog = new LdhDialog("dialog", 600d, 300d);
        Button button = new Button("demo");
        button.setOnAction(e->{
            dialog.show();
            Timeline timeline = new Timeline();
            DoubleAdder y = new DoubleAdder();
            int count = (int)dialog.getNewStage().getY()/10;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    y.add(10);
                    dialog.getNewStage().setY(y.doubleValue());
                }
            });
            timeline.getKeyFrames().add(keyFrame);
            timeline.setCycleCount(count);
            timeline.play();
        });
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().add(button);

        Scene scene = new Scene(flowPane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("demo");
        primaryStage.show();

        Popup popup = new Popup();
        PopupControl popupControl = new PopupControl();

    }
}
