package ldh.musicfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Window;
import ldh.musicfx.MusicFx;

/**
 * Created by Puhui on 2016/9/25.
 */
public class WindowController {

    private double lastX = 0.0d;
    private double lastY = 0.0d;
    private double lastWidth = 0.0d;
    private double lastHeight = 0.0d;

    @FXML
    public void minMusicFx(MouseEvent evt) {
        MusicFx.getPrimaryStage().setIconified(true);
    }

    @FXML
    public void closeMusicFx(MouseEvent evt) {
        MusicFx.getPrimaryStage().hide();
    }

    @FXML
    public void maxMusicFx(MouseEvent evt) {
        Node n = (Node)evt.getSource();
        Window w = n.getScene().getWindow();
        double currentX = w.getX();
        double currentY = w.getY();
        double currentWidth = w.getWidth();
        double currentHeight = w.getHeight();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        if( currentX != bounds.getMinX() &&
                currentY != bounds.getMinY() &&
                currentWidth != bounds.getWidth() &&
                currentHeight != bounds.getHeight() ) {  // if not maximized

            w.setX(bounds.getMinX());
            w.setY(bounds.getMinY());
            w.setWidth(bounds.getWidth());
            w.setHeight(bounds.getHeight());

            lastX = currentX;  // save old dimensions
            lastY = currentY;
            lastWidth = currentWidth;
            lastHeight = currentHeight;
        } else {
            w.setX(lastX);
            w.setY(lastY);
            w.setWidth(lastWidth);
            w.setHeight(lastHeight);

        }
        evt.consume();  // don't bubble up to title bar
    }
}
