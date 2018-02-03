package ldh.fx.component;

import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public class LdhResizePopupWindow extends LxWindowBase {

    private static int RESIZE_PADDING = 3;
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
        initWidth = this.getWidth();
        initHeight = this.getHeight();
        initStageX = this.getLayoutX();
        initStageY = this.getLayoutY();
        isDragable = true;
        mouseEvent.consume();
    }

    private void nodeDrage(MouseEvent mouseEvent) {
        if (!isDragable) {
            return;
        }
        double deltax = mouseEvent.getScreenX() - initX;
        double deltay = mouseEvent.getScreenY() - initY;

        Cursor cursor = this.getCursor();
        if (Cursor.E_RESIZE.equals(cursor)) {
            setStageWidth(mouseEvent, initWidth + deltax);
            mouseEvent.consume();
        } else if (Cursor.NE_RESIZE.equals(cursor)) {
            if (setStageHeight(mouseEvent,initHeight - deltay)) {
                setStageY(mouseEvent, initStageX + deltay);
            }
            setStageWidth(mouseEvent,initWidth + deltax);
            mouseEvent.consume();
        } else if (Cursor.SE_RESIZE.equals(cursor)) {
            setStageWidth(mouseEvent,initWidth + deltax);
            setStageHeight(mouseEvent, initHeight + deltay);
            mouseEvent.consume();
        } else if (Cursor.S_RESIZE.equals(cursor)) {
            setStageHeight(mouseEvent, initHeight + deltay);
            mouseEvent.consume();
        } else if (Cursor.W_RESIZE.equals(cursor)) {
            if (setStageWidth(mouseEvent, initWidth - deltax)) {
//                newStage.setX(initStageX + deltax);
                this.setLayoutX(initStageX + deltax);
            }
            mouseEvent.consume();
        } else if (Cursor.SW_RESIZE.equals(cursor)) {
            if (setStageWidth(mouseEvent,initWidth - deltax)) {
//                newStage.setX(initStageX + deltax);
                this.setLayoutX(initStageX + deltax);
            }
            setStageHeight(mouseEvent,initHeight + deltay);
            mouseEvent.consume();
        } else if (Cursor.NW_RESIZE.equals(cursor)) {
            if (setStageWidth(mouseEvent, initWidth - deltax)) {
//                newStage.setX(initStageX + deltax);
                this.setLayoutX(initStageX + deltax);
            }
            if (setStageHeight(mouseEvent, initHeight - deltay)) {
                setStageY(mouseEvent, initStageY + deltay);
            }
            mouseEvent.consume();
        } else if (Cursor.N_RESIZE.equals(cursor)) {
            if (setStageHeight(mouseEvent, initHeight - deltay)) {
                setStageY(mouseEvent, initStageY - deltay);
            }
            mouseEvent.consume();
        }
    }

    private void nodeMove(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        Bounds boundsInParent = this.getBoundsInParent();
        if (isRightEdge(x, y, boundsInParent)) {
            if (y < RESIZE_PADDING + SHADOW_WIDTH + 10) {
                setCursor(Cursor.NE_RESIZE);
            } else if (y > boundsInParent.getHeight() - (double) (RESIZE_PADDING + SHADOW_WIDTH)) {
                setCursor(Cursor.SE_RESIZE);
            } else {
                setCursor(Cursor.E_RESIZE);
            }
        }
//        else if (isLeftEdge(x, y, boundsInParent)) {
//            if (y < RESIZE_PADDING + SHADOW_WIDTH + 10) {
//                setCursor(Cursor.NW_RESIZE);
//            } else if (y > boundsInParent.getHeight() - (double) (RESIZE_PADDING + SHADOW_WIDTH)) {
//                setCursor(Cursor.SW_RESIZE);
//            } else {
//                setCursor(Cursor.W_RESIZE);
//            }
//        }
//        else if (isTopEdge(x, y, boundsInParent)) {
//            setCursor(Cursor.N_RESIZE);
//        }
        else if (isBottomEdge(x, y, boundsInParent)) {
            setCursor(Cursor.S_RESIZE);
        } else {
            setCursor(Cursor.DEFAULT);
        }
    }

    boolean setStageWidth(MouseEvent mouseEvent, double width) {
//        if (width >= stage.getMinWidth()) {
//            this.setWidth(width);
//            return true;
//        }
        this.setWidth(width);
        this.setPrefWidth(width);
        return true;
    }

    boolean setStageHeight(MouseEvent mouseEvent, double height) {
//        if (height >= stage.getMinHeight()) {
//            stage.getScene().getWindow().setHeight(height);
//            return true;
//        }
        this.setHeight(height);
        setPrefHeight(height);
        return true;
    }

    void setStageY(MouseEvent mouseEvent, double y) {
//        try {
//            ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(mouseEvent.getX(), mouseEvent.getY(), this.getWidth(), this.getHeight());
//            if (screensForRectangle.size() > 0) {
//                Screen screen = screensForRectangle.get(0);
//                Rectangle2D visualBounds = screen.getVisualBounds();
//                if (y < visualBounds.getHeight() - 30 && y + SHADOW_WIDTH >= visualBounds.getMinY()) {
//                    this.setLayoutY(y);
//                }
//            }
//        } catch (Exception e) {
//        }
        this.setLayoutY(y);
    }

    protected boolean isRightEdge(double x, double y, Bounds boundsInParent) {
        if (x < boundsInParent.getWidth() && x > boundsInParent.getWidth() - RESIZE_PADDING - SHADOW_WIDTH &&  y > headHeight + this.getPadding().getLeft()) {
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
        if (x >= 0 && x < RESIZE_PADDING + SHADOW_WIDTH && y > headHeight + this.getPadding().getTop()) {
            return true;
        }
        return false;
    }

}
