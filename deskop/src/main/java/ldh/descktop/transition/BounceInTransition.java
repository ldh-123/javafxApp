package ldh.descktop.transition;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class BounceInTransition extends CachedTimelineTransition {

    public BounceInTransition(final Region node) {
        super(
                node,
                new Timeline(
                        new KeyFrame(Duration.millis(0), (e)->{},
                                new KeyValue(node.opacityProperty(), 0, WEB_EASE),
                                new KeyValue(node.scaleXProperty(), 0.6, WEB_EASE),
                                new KeyValue(node.scaleYProperty(), 0.6, WEB_EASE)
                        ),
                        new KeyFrame(Duration.millis(800),(e)->{System.out.println("xxx:" + node.getScene().getWindow().getX());},
                                new KeyValue(node.scaleXProperty(), 1, WEB_EASE),
                                new KeyValue(node.scaleYProperty(), 1, WEB_EASE)

                        )
                ),
                false
        );
        setCycleDuration(Duration.seconds(1));
//        setDelay(Duration.seconds(0.2));
    }
}
