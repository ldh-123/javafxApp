package ldh.descktop.transition;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BounceInTransition2 extends CachedTimelineTransition2 {

    public BounceInTransition2(final Stage node) {
        super(
                node,
                new Timeline(
                        new KeyFrame(Duration.millis(0), (e)->{
                            node.setWidth(node.getWidth()/2);
                            System.out.println("wwww:" + node.getWidth());
                        }
                        ),
                        new KeyFrame(Duration.millis(1000), (e)->{node.setWidth(1000);}
                        )
                ), false
        );
        setCycleDuration(Duration.seconds(1));
//        setDelay(Duration.seconds(0.2));
    }
}
