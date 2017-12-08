package ldh.common.ui.page;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

/**
 * Created by xiongfei.lei on 2017/12/8.
 */
public class LdhWindow extends AnchorPane {

    private double startMoveX = -1;
    private double startMoveY = -1;
    private double stageX = -1;
    private double stageY = -1;
    private Boolean dragging = false;

    public LdhWindow() {
        this.setOnDragDetected(e->startMoveWindow(e));
        this.setOnMouseDragged(e->moveWindow(e));
        this.setOnMouseReleased(e->endMoveWindow(e));
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

}
