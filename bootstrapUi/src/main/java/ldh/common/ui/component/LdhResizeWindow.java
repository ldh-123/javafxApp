package ldh.common.ui.component;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LdhResizeWindow extends LdhWindow {

    public Stage newStage;
    private static int RESIZE_PADDING = 5;
    private static int SHADOW_WIDTH = 0;

    private double initX = -1;
    private double initY = -1;
    private double initStageX = 0;
    private double initStageY = 0;
    private double initWidth =0;
    private double initHeight = 0;
    private boolean isDragable = false;

//    private Region dragNode = null;

    private static Double headHeight = 35d;

    public Stage getStage() {
        return newStage;
    }

    public void setStage(Stage stage) {
        this.newStage = stage;
    }

    protected void buildResizable(Region node) {
        node.setOnMouseMoved(e->nodeMove(e));
        node.setOnMousePressed(e->nodeClick(e));
        node.setOnMouseDragged(e->nodeDrage(e));
        node.setOnMouseReleased(e->nodeRelease(e));
    }

    private void nodeRelease(MouseEvent e) {
        isDragable = false;
    }

    private void nodeClick(MouseEvent mouseEvent) {
        initX = mouseEvent.getScreenX();
        initY = mouseEvent.getScreenY();
        initWidth = newStage.getScene().getWindow().getWidth();
        initHeight = newStage.getScene().getWindow().getHeight();
        initStageX = newStage.getX();
        initStageY = newStage.getY();
        isDragable = true;
        mouseEvent.consume();
    }

    private void nodeDrage(MouseEvent mouseEvent) {
        if (!isDragable) {
            return;
        }
        if (newStage.isFullScreen()) {
            return;
        }
        if (mouseEvent.isStillSincePress()) {
            return;
        }
        double deltax = mouseEvent.getScreenX() - initX;
        double deltay = mouseEvent.getScreenY() - initY;

        Cursor cursor = this.getCursor();
        if (Cursor.E_RESIZE.equals(cursor)) {
            setStageWidth(newStage, initWidth + deltax);
            mouseEvent.consume();
        } else if (Cursor.NE_RESIZE.equals(cursor)) {
            if (setStageHeight(newStage, initHeight - deltay)) {
                setStageY(newStage, initStageX + deltay);
            }
            setStageWidth(newStage, initWidth + deltax);
            mouseEvent.consume();
        } else if (Cursor.SE_RESIZE.equals(cursor)) {
            setStageWidth(newStage, initWidth + deltax);
            setStageHeight(newStage, initHeight + deltay);
            mouseEvent.consume();
        } else if (Cursor.S_RESIZE.equals(cursor)) {
            setStageHeight(newStage, initHeight + deltay);
            mouseEvent.consume();
        } else if (Cursor.W_RESIZE.equals(cursor)) {
            if (setStageWidth(newStage, initWidth - deltax)) {
                newStage.setX(initStageX + deltax);
            }
            mouseEvent.consume();
        } else if (Cursor.SW_RESIZE.equals(cursor)) {
            if (setStageWidth(newStage, initWidth - deltax)) {
                newStage.setX(initStageX + deltax);
            }
            setStageHeight(newStage, initHeight + deltay);
            mouseEvent.consume();
        } else if (Cursor.NW_RESIZE.equals(cursor)) {
            if (setStageWidth(newStage, initWidth - deltax)) {
                newStage.setX(initStageX + deltax);
            }
            if (setStageHeight(newStage, initHeight - deltay)) {
                setStageY(newStage, initStageY + deltay);
            }
            mouseEvent.consume();
        } else if (Cursor.N_RESIZE.equals(cursor)) {
            if (setStageHeight(newStage, initHeight - deltay)) {
                setStageY(newStage, initStageY + deltay);
            }
            mouseEvent.consume();
        }
    }

    private void nodeMove(MouseEvent mouseEvent) {
//        if (maximized) {
//            setCursor(node, Cursor.DEFAULT);
//            return; // maximized mode does not support resize
//        }
        if (newStage.isFullScreen()) {
            return;
        }
        if (!newStage.isResizable()) {
            return;
        }
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        Bounds boundsInParent = this.getBoundsInParent();
        if (isRightEdge(x, y, boundsInParent)) {
            if (y < RESIZE_PADDING + SHADOW_WIDTH) {
                setCursor(Cursor.NE_RESIZE);
            } else if (y > boundsInParent.getHeight() - (double) (RESIZE_PADDING + SHADOW_WIDTH)) {
                setCursor(Cursor.SE_RESIZE);
            } else {
                setCursor(Cursor.E_RESIZE);
            }
        } else if (isLeftEdge(x, y, boundsInParent)) {
            if (y < RESIZE_PADDING + SHADOW_WIDTH) {
                setCursor(Cursor.NW_RESIZE);
            } else if (y > boundsInParent.getHeight() - (double) (RESIZE_PADDING + SHADOW_WIDTH)) {
                setCursor(Cursor.SW_RESIZE);
            } else {
                setCursor(Cursor.W_RESIZE);
            }
        } else if (isTopEdge(x, y, boundsInParent)) {
            setCursor(Cursor.N_RESIZE);
        } else if (isBottomEdge(x, y, boundsInParent)) {
            setCursor(Cursor.S_RESIZE);
        } else {
            setCursor(Cursor.DEFAULT);
        }
    }

    boolean setStageWidth(Stage stage, double width) {
        if (width >= stage.getMinWidth()) {
            stage.getScene().getWindow().setWidth(width);
            return true;
        }
        return false;
    }

    boolean setStageHeight(Stage stage, double height) {
        if (height >= stage.getMinHeight()) {
            stage.getScene().getWindow().setHeight(height);
            return true;
        }
        return false;
    }

    void setStageY(Stage stage, double y) {
        try {
            ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
            if (screensForRectangle.size() > 0) {
                Screen screen = screensForRectangle.get(0);
                Rectangle2D visualBounds = screen.getVisualBounds();
                if (y < visualBounds.getHeight() - 30 && y + SHADOW_WIDTH >= visualBounds.getMinY()) {
                    stage.setY(y);
                }
            }
        } catch (Exception e) {
        }
    }

    private boolean isRightEdge(double x, double y, Bounds boundsInParent) {
        if (x < boundsInParent.getWidth() && x > boundsInParent.getWidth() - RESIZE_PADDING - SHADOW_WIDTH &&  y > headHeight + this.getPadding().getLeft()) {
            return true;
        }
        return false;
    }

    private boolean isTopEdge(double x, double y, Bounds boundsInParent) {
        if (y >= 0 && y < RESIZE_PADDING + SHADOW_WIDTH) {
            return true;
        }
        return false;
    }

    private boolean isBottomEdge(double x, double y, Bounds boundsInParent) {
        if (y < boundsInParent.getHeight() && y > boundsInParent.getHeight() - RESIZE_PADDING - SHADOW_WIDTH) {
            return true;
        }
        return false;
    }

    private boolean isLeftEdge(double x, double y, Bounds boundsInParent) {
        if (x >= 0 && x < RESIZE_PADDING + SHADOW_WIDTH && y > headHeight + this.getPadding().getLeft()) {
            return true;
        }
        return false;
    }

}
