package ldh.fx.ui.util;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Window;
import sun.misc.Resource;

/**
 * Created by ldh on 2018/1/30.
 */
public class StageResizable extends Resizable{

    public StageResizable(Region eastEdgePane, Region seBuglePane, Region southEdgePane, Region swBuglePane, Region westEdgePane, Region northEdgePane) {
        super(eastEdgePane, seBuglePane, southEdgePane, swBuglePane, westEdgePane, northEdgePane);
    }

    public void eastEdgeChangeSize(MouseEvent evt) {
        eastEdgePane.setCursor(Cursor.H_RESIZE);
    }

    public void startChangeEastEdgeSize(MouseEvent evt) {
        this.startChangeSize(evt, eastEdgePane);
    }

    public void changeEastEdgeSize(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();

            Window w = eastEdgePane.getScene().getWindow();
            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;

            w.setX(lastX + changeW);
            w.setWidth(lastWidth - changeW);
        }
    }

    public void endChangeEastEdageSize(MouseEvent evt) {
        this.endChangeSize(evt);
    }

    public void westEdgeChangeSize(MouseEvent evt) {
        westEdgePane.setCursor(Cursor.H_RESIZE);
    }

    public void startChangeWestEdgeSize(MouseEvent evt) {
        this.startChangeSize(evt, westEdgePane);
    }

    public void changeWestEdgeSize(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            Window w = westEdgePane.getScene().getWindow();
            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;
            w.setWidth(lastWidth + changeW);
        }
    }

    public void endChangeWestEdgeSize(MouseEvent evt) {
        this.endChangeSize(evt);
    }

    public void southEdgeChangeSize(MouseEvent evt) {
        southEdgePane.setCursor(Cursor.V_RESIZE);
    }

    public void startChangeSouthEdgeSize(MouseEvent evt) {
        this.startChangeSize(evt, southEdgePane);
    }

    public void changeSouthEdgeSize(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            Window w = southEdgePane.getScene().getWindow();
            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;
            w.setHeight(lastHeight + changeH);
        }
    }

    public void endChangeSouthEdgeSize(MouseEvent evt) {
        endChangeSize(evt);
    }

    public void seBugleChangeSize(MouseEvent evt) {
        seBuglePane.setCursor(Cursor.NE_RESIZE);
    }

    public void startChangeSeBugleSize(MouseEvent evt) {
        this.startChangeSize(evt, seBuglePane);
    }

    public void changeSeBugleSize(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();

            Window w = seBuglePane.getScene().getWindow();
            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;
            w.setX(lastX + changeW);
            w.setWidth(lastWidth - changeW);
            w.setHeight(lastHeight + changeH);
        }
    }

    public void endChangeSeBugleSize(MouseEvent evt) {
        endChangeSize(evt);
    }

    public void swBugleChangeSize(MouseEvent evt) {
        swBuglePane.setCursor(Cursor.NW_RESIZE);
    }

    public void startChangeSwBugleSize(MouseEvent evt) {
        startChangeSize(evt, swBuglePane);
    }

    public void changeSwBugleSize(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();

            Window w = swBuglePane.getScene().getWindow();
            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;
            w.setHeight(lastHeight + changeH);
            w.setWidth(lastWidth + changeW);
        }
    }

    public void endChangeSwBugleSize(MouseEvent evt) {
        if (dragging) {
            dragging = false;
        }
    }

    public void northEdgeChangeSize(MouseEvent evt) {
        northEdgePane.setCursor(Cursor.V_RESIZE);
    }

    public void startChangeNorthEdgeSize(MouseEvent evt) {
        this.startChangeSize(evt, northEdgePane);
    }

    public void changeNorthEdgeSize(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            Window w = northEdgePane.getScene().getWindow();
            double changeH = endMoveY - this.startMoveY;
            w.setY(lastY + changeH);
            w.setHeight(lastHeight - changeH);
        }
    }

    public void endChangeNorthEdgeSize(MouseEvent evt) {
        endChangeSize(evt);
    }


    public void startChangeSize(MouseEvent evt, Region region) {
        startMoveX = evt.getScreenX();
        startMoveY = evt.getScreenY();
        dragging = true;
        Window w = region.getScene().getWindow();
        lastHeight = w.getHeight();
        lastWidth = w.getWidth();
        lastX = w.getX();
        lastY = w.getY();
    }

    public void endChangeSize(MouseEvent evt) {
        if (dragging) {
            dragging = false;
        }
    }
}
