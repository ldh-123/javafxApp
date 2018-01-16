package ldh.fx.component;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Window;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LdhWindow extends AnchorPane {

    private Logger logger = Logger.getLogger(LdhWindow.class.getName());

    private double startMoveX = -1;
    private double startMoveY = -1;
    private double stageX = -1;
    private double stageY = -1;
    protected Boolean dragging = false;
    protected boolean isMoved = false;

    protected Double layoutX = 0d, layoutY = 0d;

    public LdhWindow() {
//        buildMovable(this);
    }

    public void setContentPane(Node node) {
        this.getChildren().clear();
        this.getChildren().add(node);
    }

    public void startMoveWindow(MouseEvent evt) {
        startMoveX = evt.getScreenX();
        startMoveY = evt.getScreenY();
        Window w = this.getScene().getWindow();
        stageX = w.getX();
        stageY = w.getY();
        dragging = true;
    }

    public void moveWindow(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            Window w = this.getScene().getWindow();
            w.setX(stageX + (endMoveX - startMoveX));
            w.setY(stageY + (endMoveY - startMoveY));
            isMoved = true;
        }
        layoutX = this.getScene().getWindow().getX();
        layoutY = this.getScene().getWindow().getY();
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
        layoutX = this.getScene().getWindow().getX();
        layoutY = this.getScene().getWindow().getY();
    }

    protected void buildMovable(Region node) {
        node.setOnDragDetected(e->startMoveWindow(e));
        node.setOnMouseDragged(e->moveWindow(e));
        node.setOnMouseReleased(e->endMoveWindow(e));
    }
}
