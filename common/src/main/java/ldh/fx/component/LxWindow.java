package ldh.fx.component;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LxWindow extends LxWindowBase {

    private DialogModel dialogModel;

    private static int RESIZE_PADDING = 5;
    private static int SHADOW_WIDTH = 0;

    private double initX = -1;
    private double initY = -1;
    private double initStageX = 0;
    private double initStageY = 0;
    private double initWidth =0;
    private double initHeight = 0;
    private boolean isDragable = false;

    private static Double headHeight = 35d;

    public void setDialogModel(DialogModel dialogModel) {
        this.dialogModel = dialogModel;
    }

    public void buildResizable(Region node) {
        node.setOnMouseMoved(e->nodeMove(e));
        node.setOnMousePressed(e->nodeClick(e));
        node.setOnMouseDragged(e->nodeDrage(e));
        node.setOnMouseReleased(e->nodeRelease(e));
    }

    protected boolean isModel() {
        return dialogModel != DialogModel.Normal;
    }

    private void nodeRelease(MouseEvent e) {
        isDragable = false;
    }

    private void nodeClick(MouseEvent mouseEvent) {
        initX = mouseEvent.getScreenX();
        initY = mouseEvent.getScreenY();
        if (isModel()) {
            initWidth = this.getScene().getWindow().getWidth();
            initHeight = this.getScene().getWindow().getHeight();
            initStageX = this.getScene().getX();
            initStageY = this.getScene().getY();
        } else {
            initWidth = this.getWidth();
            initHeight = this.getHeight();
            initStageX = this.getLayoutX();
            initStageY = this.getLayoutY();
        }
        isDragable = true;
        mouseEvent.consume();
    }

    private void nodeDrage(MouseEvent mouseEvent) {
        if (!isDragable) {
            return;
        }
        if (mouseEvent.isStillSincePress()) {
            return;
        }
        double deltax = mouseEvent.getScreenX() - initX;
        double deltay = mouseEvent.getScreenY() - initY;

        Cursor cursor = this.getCursor();
        if (Cursor.E_RESIZE.equals(cursor)) {
            setStageWidth(initWidth + deltax);
            mouseEvent.consume();
        } else if (Cursor.NE_RESIZE.equals(cursor)) {
            if (setStageHeight(initHeight - deltay)) {
                setStageY(initStageX + deltay);
            }
            setStageWidth(initWidth + deltax);
            mouseEvent.consume();
        } else if (Cursor.SE_RESIZE.equals(cursor)) {
            setStageWidth(initWidth + deltax);
            setStageHeight(initHeight + deltay);
            mouseEvent.consume();
        } else if (Cursor.S_RESIZE.equals(cursor)) {
            setStageHeight(initHeight + deltay);
            mouseEvent.consume();
        } else if (Cursor.W_RESIZE.equals(cursor)) {
            if (setStageWidth(initWidth - deltax)) {
                setStageX(initStageX + deltax);
            }
            mouseEvent.consume();
        } else if (Cursor.SW_RESIZE.equals(cursor)) {
            if (setStageWidth(initWidth - deltax)) {
                setStageX(initStageX + deltax);
            }
            setStageHeight(initHeight + deltay);
            mouseEvent.consume();
        } else if (Cursor.NW_RESIZE.equals(cursor)) {
            if (setStageWidth(initWidth - deltax)) {
                setStageX(initStageX + deltax);
            }
            if (setStageHeight(initHeight - deltay)) {
                setStageY(initStageY + deltay);
            }
            mouseEvent.consume();
        } else if (Cursor.N_RESIZE.equals(cursor)) {
            if (setStageHeight(initHeight - deltay)) {
                setStageY(initStageY + deltay);
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
        } else if (isBottomEdge(x, y, boundsInParent)) {
            setCursor(Cursor.S_RESIZE);
        } else {
            setCursor(Cursor.DEFAULT);
        }
        if (isModel()) {
             if (isLeftEdge(x, y, boundsInParent)) {
                if (y < RESIZE_PADDING + SHADOW_WIDTH + 10) {
                    setCursor(Cursor.NW_RESIZE);
                } else if (y > boundsInParent.getHeight() - (double) (RESIZE_PADDING + SHADOW_WIDTH)) {
                    setCursor(Cursor.SW_RESIZE);
                } else {
                    setCursor(Cursor.W_RESIZE);
                }
            } else if (isTopEdge(x, y, boundsInParent)) {
                setCursor(Cursor.N_RESIZE);
            }
        }
    }

    boolean setStageWidth(double width) {
        if (isModel()) {
            this.getScene().getWindow().setWidth(width);
            this.setPrefWidth(width);
            return true;
        } else {
            this.setWidth(width);
            this.setPrefWidth(width);
            return true;
        }
    }

    boolean setStageHeight(double height) {
        if (isModel()) {
            this.getScene().getWindow().setHeight(height);
            this.setPrefHeight(height);
            return false;
        } else {
            this.setHeight(height);
            setPrefHeight(height);
            return true;
        }
    }

    void setStageY(double y) {
        if (isModel()) {
            ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(getScene().getWindow().getX(), getScene().getWindow().getY(), getScene().getWindow().getWidth(), getScene().getWindow().getHeight());
            if (screensForRectangle.size() > 0) {
                Screen screen = screensForRectangle.get(0);
                Rectangle2D visualBounds = screen.getVisualBounds();
                if (y < visualBounds.getHeight() - 30 && y + SHADOW_WIDTH >= visualBounds.getMinY()) {
                    this.getScene().getWindow().setY(y);
                }
            }
            return;
        }
        this.setLayoutY(y);
    }

    void setStageX(double x) {
        if (isModel()) {
            getScene().getWindow().setX(x);
            return;
        }
        this.setLayoutX(x);
    }

    private boolean isRightEdge(double x, double y, Bounds boundsInParent) {
        if (x < boundsInParent.getWidth() && x > boundsInParent.getWidth() - RESIZE_PADDING - SHADOW_WIDTH &&  y > headHeight + this.getPadding().getLeft()) {
            return true;
        }
        return false;
    }

    private boolean isTopEdge(double x, double y, Bounds boundsInParent) {
        if (y >= 0 && y < RESIZE_PADDING + SHADOW_WIDTH && x < boundsInParent.getWidth() - 100 && x > 50) {
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
