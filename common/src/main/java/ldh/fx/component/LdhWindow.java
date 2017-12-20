package ldh.fx.component;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Window;

/**
 * Created by xiongfei.lei on 2017/12/8.
 */
public class LdhWindow extends AnchorPane {

    private double startMoveX = -1;
    private double startMoveY = -1;
    private double stageX = -1;
    private double stageY = -1;
    protected Boolean dragging = false;

    public LdhWindow() {
//        buildMovable(this);
    }

    public void setContentPane(Node node) {
        this.getChildren().clear();
        this.getChildren().add(node);
    }

    @FXML
    public void startMoveWindow(MouseEvent evt) {
        startMoveX = evt.getScreenX();
        startMoveY = evt.getScreenY();
        Window w = this.getScene().getWindow();
        stageX = w.getX();
        stageY = w.getY();
        dragging = true;
    }

    @FXML
    public void moveWindow(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            Window w = this.getScene().getWindow();
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

    protected void buildMovable(Region node) {
        node.setOnDragDetected(e->startMoveWindow(e));
        node.setOnMouseDragged(e->moveWindow(e));
        node.setOnMouseReleased(e->endMoveWindow(e));
    }
}
