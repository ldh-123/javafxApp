package ldh.fx.ui.util;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Window;

/**
 * Created by ldh on 2018/1/30.
 */
public class RegionResizable extends Resizable{

    private Region region;

    public RegionResizable(Region region, Region eastEdgePane, Region seBuglePane, Region southEdgePane, Region swBuglePane, Region westEdgePane, Region northEdgePane) {
        super(eastEdgePane, seBuglePane, southEdgePane, swBuglePane, westEdgePane, northEdgePane);
        this.region = region;
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

            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;

            region.getScene().getWindow().setX(lastX + changeW);
            region.setPrefWidth(lastWidth - changeW);
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
            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;
            region.setPrefWidth(lastWidth + changeW);
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
            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;
            region.setPrefHeight(lastHeight + changeH);
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

            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;
            region.getScene().getWindow().setX(lastX + changeW);
            region.setPrefWidth(lastWidth - changeW);
            region.setPrefHeight(lastHeight + changeH);
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

            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;
            region.setPrefHeight(lastHeight + changeH);
            region.setPrefWidth(lastWidth + changeW);
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
            double changeH = endMoveY - this.startMoveY;
            region.getScene().getWindow().setY(lastY + changeH);
            region.setPrefHeight(lastHeight - changeH);
        }
    }

    public void endChangeNorthEdgeSize(MouseEvent evt) {
        this.endChangeSize(evt);
    }


    public void startChangeSize(MouseEvent evt, Region region2) {
        startMoveX = evt.getScreenX();
        startMoveY = evt.getScreenY();
        dragging = true;
        lastHeight = region.getHeight();
        lastWidth = region.getPrefWidth();
        lastX = region.getScene().getWindow().getX();
        lastY = region.getScene().getWindow().getY();
        evt.consume();
    }

    public void endChangeSize(MouseEvent evt) {
        if (dragging) {
            dragging = false;
        }
    }
}
