package ldh.fx.component;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Window;

import java.io.IOException;
import java.util.logging.Logger;

public class LxWindowBase extends AnchorPane {

    private Logger logger = Logger.getLogger(LxWindowBase.class.getName());

//    @FXML private BorderPane contentContainer;

    private double startMoveX = -1;
    private double startMoveY = -1;
    private double stageX = -1;
    private double stageY = -1;
    protected Boolean dragging = false;
    protected boolean isMoved = false;

    protected static int RESIZE_PADDING = 5;
    protected static int SHADOW_WIDTH = 0;

    protected Double layoutX = 0d, layoutY = 0d;

    public LxWindowBase() {
        loadFx("/component/LxWindow.fxml");
    }

//    protected BorderPane getContentContainer() {
//        return contentContainer;
//    }

    public void startMoveWindow(MouseEvent evt) {
        startMoveX = evt.getScreenX();
        startMoveY = evt.getScreenY();
        Window w = this.getScene().getWindow();
        stageX = w.getX();
        stageY = w.getY();
        dragging = true;
    }

    public void moveWindow(MouseEvent evt) {
        double x = evt.getX(), y = evt.getY();
        Bounds boundsInParent = this.getBoundsInParent();
        if (isRightEdge(x, y, boundsInParent) || isLeftEdge(x, y, boundsInParent)
                || isTopEdge(x, y, boundsInParent) || isBottomEdge(x, y, boundsInParent)) return;
        if (getCursor() != null && getCursor() != Cursor.DEFAULT) return;
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
//        node.setOnDragDetected(e->startMoveWindow(e));
//        node.setOnMouseDragged(e->moveWindow(e));
//        node.setOnMouseReleased(e->endMoveWindow(e));
        node.addEventFilter(MouseEvent.MOUSE_PRESSED, e->startMoveWindow(e));
        node.addEventFilter(MouseEvent.MOUSE_DRAGGED, e->moveWindow(e));
        node.addEventFilter(MouseEvent.MOUSE_RELEASED, e->endMoveWindow(e));
    }

    protected void loadFx(String fxmlPath) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    protected boolean isRightEdge(double x, double y, Bounds boundsInParent) {
        if (x < boundsInParent.getWidth() && x > boundsInParent.getWidth() - RESIZE_PADDING - SHADOW_WIDTH &&  y > this.getPadding().getLeft()) {
            return true;
        }
        return false;
    }

    protected boolean isTopEdge(double x, double y, Bounds boundsInParent) {
        if (y >= 0 && y < RESIZE_PADDING + SHADOW_WIDTH && x < boundsInParent.getWidth() - 100 && x > 50) {
            return true;
        }
        return false;
    }

    protected boolean isBottomEdge(double x, double y, Bounds boundsInParent) {
        if (y < boundsInParent.getHeight() && y > boundsInParent.getHeight() - RESIZE_PADDING - SHADOW_WIDTH) {
            return true;
        }
        return false;
    }

    protected boolean isLeftEdge(double x, double y, Bounds boundsInParent) {
        if (x >= 0 && x < RESIZE_PADDING + SHADOW_WIDTH && y > this.getPadding().getLeft()) {
            return true;
        }
        return false;
    }
}
