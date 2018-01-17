package ldh.fx.component;

import com.sun.javafx.geom.Vec2d;
import javafx.animation.TranslateTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;

/**
 * Created by ldh on 2018/1/16.
 */
public class LHiddenPane extends StackPane {

    private Node showingNode;
    private Node hiddenNode;
    private Rectangle clip = new Rectangle();
    private TranslateTransition translateTransition;
    private boolean isShowing = true;

    public LHiddenPane() {
        super();
        event();
    }

    public LHiddenPane(Node... children) {
        super(children);

        if (children.length > 0) {
            showingNode = children[0];
        }
        if (children.length > 1) {
            hiddenNode = children[1];
        }
        bindEvent();

        event();
    }

    private void event() {
        clip.widthProperty().bind(this.widthProperty());
        clip.heightProperty().bind(this.heightProperty());
        this.setClip(clip);

        this.getChildren().addListener((ListChangeListener<? super Node>)(l) ->{
            List<Node> managed = getManagedChildren();
            for (int i = 0, size = managed.size(); i < size; i++) {
                if (i == 0) {
                    showingNode = managed.get(i);
                } else {
                    hiddenNode = managed.get(i);
                    bindEvent();
                }
            }
        });

        this.setOnMouseEntered(e->{
            if (hiddenNode != null) {
                isShowing = false;
                hiddenNode.setVisible(true);
                translateTransition.stop();
                translateTransition.setFromX(this.getWidth());
                translateTransition.setToX(0);
                translateTransition.play();
            }
        });
        this.setOnMouseExited(e->{
            if (hiddenNode != null) {
                isShowing = true;
                showingNode.setVisible(true);
                translateTransition.setFromX(0);
                translateTransition.setToX(this.getWidth());
                translateTransition.play();
            }
        });
    }

    private void bindEvent() {
        hiddenNode.setVisible(false);
        hiddenNode.setLayoutX(this.getWidth());
        buildTranslateTransition();
    }

    private void buildTranslateTransition() {
        translateTransition = new TranslateTransition();
        translateTransition.setNode(hiddenNode);
        translateTransition.setFromX(-this.getWidth());
        translateTransition.setToX(0);
        translateTransition.setDuration(Duration.valueOf("300ms"));
        translateTransition.setOnFinished(e->{
            if (isShowing) {
                hiddenNode.setVisible(false);
            } else {
                showingNode.setVisible(false);
            }

        });
    }
}
