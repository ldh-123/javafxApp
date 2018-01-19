package ldh.fx.transition;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class BounceInTransition extends CachedTimelineTransition {

    public BounceInTransition(final Region node) {
        super(
                node,
                new Timeline(
                        new KeyFrame(Duration.millis(0),e->node.setVisible(true),
                                new KeyValue(node.scaleXProperty(), 0.6, WEB_EASE),
                                new KeyValue(node.scaleYProperty(), 0.6, WEB_EASE)
                        ),
                        new KeyFrame(Duration.millis(500),
                                new KeyValue(node.opacityProperty(), 1, WEB_EASE),
                                new KeyValue(node.scaleXProperty(), 1, WEB_EASE),
                                new KeyValue(node.scaleYProperty(), 1, WEB_EASE)

                        )
                ),
                false
        );
        setCycleDuration(Duration.seconds(0.5));
//        setDelay(Duration.seconds(0.1));
    }
}
