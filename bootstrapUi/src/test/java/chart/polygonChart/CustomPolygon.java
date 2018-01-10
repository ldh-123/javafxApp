package chart.polygonChart;

import javafx.scene.paint.Color;

public class CustomPolygon {
    private int polygonIndex;
    private int polygonEdgeNum;
    private Color strokeColor;
    private Color fillColor;
    private double strokeWidth;

    public CustomPolygon(int polygonIndex, int polygonEdgeNum, Color strokeColor, Color fillColor, double strokeWidth) {
        this.polygonIndex = polygonIndex;
        this.polygonEdgeNum = polygonEdgeNum;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
        this.strokeWidth = strokeWidth;
    }

    public int getPolygonIndex() {
        return polygonIndex;
    }

    public void setPolygonIndex(int polygonIndex) {
        this.polygonIndex = polygonIndex;
    }

    public int getPolygonEdgeNum() {
        return polygonEdgeNum;
    }

    public void setPolygonEdgeNum(int polygonEdgeNum) {
        this.polygonEdgeNum = polygonEdgeNum;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
}
