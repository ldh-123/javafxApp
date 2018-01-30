package ldh.fx.ui.util;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Window;

/**
 * Created by ldh on 2018/1/30.
 */
public class StageMovable {

    private Region movablePane;

    private Rectangle moveTrackingRect;
    private Popup moveTrackingPopup;

    private double startMoveX = -1;
    private double startMoveY = -1;
    private Boolean dragging = false;

    public StageMovable(Region movablePane) {
        this.movablePane = movablePane;
    }

    private void buildMovable() {
        if (movablePane == null) return;
        movablePane.setOnMouseDragged(e->moveWindow(e));
        movablePane.setOnMouseReleased(e->endMoveWindow(e));
        movablePane.setOnDragDetected(e->startMoveWindow(e));
    }
    public void startMoveWindow(MouseEvent evt) {
        startMoveX = evt.getScreenX();
        startMoveY = evt.getScreenY();
        dragging = true;

        moveTrackingRect = new Rectangle();
        moveTrackingRect.setWidth(movablePane.getScene().getWindow().getWidth());
        moveTrackingRect.setHeight(movablePane.getScene().getWindow().getHeight());
        moveTrackingRect.getStyleClass().add( "tracking-rect" );

        moveTrackingPopup = new Popup();
        moveTrackingPopup.getContent().add(moveTrackingRect);
        moveTrackingPopup.show(movablePane.getScene().getWindow());
        moveTrackingPopup.setOnHidden( (e) -> resetMoveOperation());
    }

    public void moveWindow(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            Window w = movablePane.getScene().getWindow();
            double stageX = w.getX();
            double stageY = w.getY();
            moveTrackingPopup.setX(stageX + (endMoveX - startMoveX));
            moveTrackingPopup.setY(stageY + (endMoveY - startMoveY));
        }
    }

    public void endMoveWindow(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            Window w = movablePane.getScene().getWindow();
            double stageX = w.getX();
            double stageY = w.getY();
            w.setX(stageX + (endMoveX - startMoveX));
            w.setY(stageY + (endMoveY - startMoveY));
            if (moveTrackingPopup != null) {
                moveTrackingPopup.hide();
                moveTrackingPopup = null;
            }
        }
        resetMoveOperation();
    }

    private void resetMoveOperation() {
        startMoveX = 0;
        startMoveY = 0;
        dragging = false;
        moveTrackingRect = null;
    }
}
