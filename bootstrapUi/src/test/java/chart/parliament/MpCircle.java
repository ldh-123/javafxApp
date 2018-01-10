package chart.parliament;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class MpCircle {
    Mp mp=null;
    Circle circle;

    MpCircle(double centerX, double centerY, double radius, Color color) {
        circle = new Circle(centerX,centerY,radius,color);
    }

    Circle getCircle() {
        return circle;
    }

    void setCircle(Circle circle) {
        this.circle = circle;
    }

    Mp getMp() {
        return mp;
    }

    void setMp(Mp mp) {
        this.mp = mp;
    }
}
