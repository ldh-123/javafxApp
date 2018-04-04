package control;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.Data;

/**
 * Created by ldh on 2018/4/4.
 */
@Data
public class StageSizer {
    private static int MINIMUM_VISIBLE_WIDTH = 100;
    private static int MINIMUM_VISIBLE_HEIGHT = 50;
    private static int MARGIN = 50;

    private Boolean maximized;
    private Number x;
    private Number y;
    private Number width;
    private Number height;

    public void setStage(Stage stage) {
        // First, restore the size and position of the stage.
        resizeAndPosition(stage);
        // If the stage is not visible in any of the current screens, relocate it the primary screen.
        if (isWindowIsOutOfBounds(stage)) {
            moveToPrimaryScreen(stage);
        }
        // And now watch the stage to keep the properties updated.
        watchStage(stage);
    }

    private void resizeAndPosition(Stage stage) {
        if (getX() != null) {
            stage.setX(getX().doubleValue());
        }
        if (getY() != null) {
            stage.setY((Double) getY().doubleValue());
        }
        if (getWidth() != null) {
            stage.setWidth((Double) getWidth().doubleValue());
        }
        if (getHeight() != null) {
            stage.setHeight((Double) getHeight().doubleValue());
        }
        if (getMaximized() != null) {
            stage.setMaximized(getMaximized());
        }
    }

    private boolean isWindowIsOutOfBounds(Stage stage) {
        boolean windowIsOutOfBounds = true;
        for (Screen screen : Screen.getScreens()) {
            Rectangle2D bounds = screen.getVisualBounds();
            if (bounds.getMinX() < stage.getX() && stage.getX() + MINIMUM_VISIBLE_WIDTH < bounds.getMaxX() &&
                    bounds.getMinY() < stage.getY() && stage.getY() + MINIMUM_VISIBLE_HEIGHT < bounds.getMaxY()) {
                windowIsOutOfBounds = false;
                break;
            }
        }
        return windowIsOutOfBounds;
    }

    private void moveToPrimaryScreen(Stage stage) {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        stage.setX(bounds.getMinX() + MARGIN);
        stage.setY(bounds.getMinY() + MARGIN);
        stage.setWidth(bounds.getWidth() - MARGIN * 2);
        stage.setHeight(bounds.getHeight() - MARGIN * 2);
    }

    private void watchStage(Stage stage) {
        // Get the current values.
        setX(stage.getX());
        setY(stage.getY());
        setWidth(stage.getWidth());
        setHeight(stage.getHeight());
        setMaximized(stage.isMaximized());
        // Watch for future changes.
        stage.xProperty().addListener((observable, old, x) -> setX(x));
        stage.yProperty().addListener((observable, old, y) -> setY(y));
        stage.widthProperty().addListener((observable, old, width) -> setWidth(width));
        stage.heightProperty().addListener((observable, old, height) -> setHeight(height));
        stage.maximizedProperty().addListener((observable, old, maximized) -> setMaximized(maximized));
    }


}
