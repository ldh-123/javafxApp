package ldh.fx.ui.util;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Window;

/**
 * Created by ldh on 2018/1/30.
 */
public abstract class Resizable {

    protected Region eastEdgePane;
    protected Region westEdgePane;
    protected Region southEdgePane;
    protected Region northEdgePane;
    protected Region seBuglePane;
    protected Region swBuglePane;

    protected double startMoveX = -1;
    protected double startMoveY = -1;
    protected Boolean dragging = false;
    protected double lastX = 0.0d;
    protected double lastY = 0.0d;
    protected double lastWidth = 0.0d;
    protected double lastHeight = 0.0d;

    public Resizable(Region eastEdgePane, Region seBuglePane, Region southEdgePane, Region swBuglePane, Region westEdgePane, Region northEdgePane) {
        this.eastEdgePane = eastEdgePane;
        this.seBuglePane = seBuglePane;
        this.southEdgePane = southEdgePane;
        this.swBuglePane = swBuglePane;
        this.westEdgePane = westEdgePane;
        this.northEdgePane = northEdgePane;
    }

    public void buildEastEdgeResizable() {
        if (eastEdgePane == null) return;
        eastEdgePane.setOnMouseDragged(e->changeEastEdgeSize(e));
        eastEdgePane.setOnMouseReleased(e->endChangeEastEdageSize(e));
        eastEdgePane.setOnMousePressed(e->startChangeEastEdgeSize(e));
        eastEdgePane.setOnMouseEntered(e->eastEdgeChangeSize(e));
    }

    public void buildWestEdgeResizable() {
        if (westEdgePane == null) return;
        westEdgePane.setOnMouseDragged(e->changeWestEdgeSize(e));
        westEdgePane.setOnMouseReleased(e->endChangeWestEdgeSize(e));
        westEdgePane.setOnDragDetected(e->startChangeWestEdgeSize(e));
        westEdgePane.setOnMouseEntered(e->westEdgeChangeSize(e));
    }

    public void buildSouthEdgeResizable() {
        if (southEdgePane == null) return;
        southEdgePane.setOnMouseDragged(e->changeSouthEdgeSize(e));
        southEdgePane.setOnMouseReleased(e->endChangeSouthEdgeSize(e));
        southEdgePane.setOnDragDetected(e->startChangeSouthEdgeSize(e));
        southEdgePane.setOnMouseEntered(e->southEdgeChangeSize(e));
    }

    public void buildSeBugleResizable() {
        if (seBuglePane == null) return;
        seBuglePane.setOnMouseDragged(e->changeSeBugleSize(e));
        seBuglePane.setOnMouseReleased(e->endChangeSeBugleSize(e));
        seBuglePane.setOnDragDetected(e->startChangeSeBugleSize(e));
        seBuglePane.setOnMouseEntered(e->seBugleChangeSize(e));
    }

    public void buildSwBugleResizable() {
        if (swBuglePane == null) return;
        swBuglePane.setOnMouseDragged(e->changeSwBugleSize(e));
        swBuglePane.setOnMouseReleased(e->endChangeSwBugleSize(e));
        swBuglePane.setOnDragDetected(e->startChangeSwBugleSize(e));
        swBuglePane.setOnMouseEntered(e->swBugleChangeSize(e));
    }

    public void buildNorthEdgeResizable() {
        if (northEdgePane == null) return;
        northEdgePane.setOnMouseDragged(e->changeNorthEdgeSize(e));
        northEdgePane.setOnMouseReleased(e->endChangeNorthEdgeSize(e));
        northEdgePane.setOnDragDetected(e->startChangeNorthEdgeSize(e));
        northEdgePane.setOnMouseEntered(e->northEdgeChangeSize(e));
    }

    public abstract void eastEdgeChangeSize(MouseEvent evt);

    public abstract void startChangeEastEdgeSize(MouseEvent evt);

    public abstract void changeEastEdgeSize(MouseEvent evt);

    public abstract void endChangeEastEdageSize(MouseEvent evt);

    public abstract void westEdgeChangeSize(MouseEvent evt);

    public abstract void startChangeWestEdgeSize(MouseEvent evt);
    public abstract void changeWestEdgeSize(MouseEvent evt);

    public abstract void endChangeWestEdgeSize(MouseEvent evt);

    public abstract void southEdgeChangeSize(MouseEvent evt);

    public abstract void startChangeSouthEdgeSize(MouseEvent evt);

    public abstract void changeSouthEdgeSize(MouseEvent evt);

    public abstract void endChangeSouthEdgeSize(MouseEvent evt);

    public abstract void seBugleChangeSize(MouseEvent evt);

    public abstract void startChangeSeBugleSize(MouseEvent evt);

    public abstract void changeSeBugleSize(MouseEvent evt);

    public abstract void endChangeSeBugleSize(MouseEvent evt);

    public abstract void swBugleChangeSize(MouseEvent evt);

    public abstract void startChangeSwBugleSize(MouseEvent evt);

    public abstract void changeSwBugleSize(MouseEvent evt);

    public abstract void endChangeSwBugleSize(MouseEvent evt);

    public abstract void northEdgeChangeSize(MouseEvent evt);

    public abstract void startChangeNorthEdgeSize(MouseEvent evt);

    public abstract void changeNorthEdgeSize(MouseEvent evt);

    public abstract void endChangeNorthEdgeSize(MouseEvent evt);

    public abstract void startChangeSize(MouseEvent evt, Region region);

    public abstract void endChangeSize(MouseEvent evt);
}
