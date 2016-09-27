package ldh.fx.ui.window;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.*;
import ldh.fx.ui.Undecorator;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by Puhui on 2016/9/26.
 */
public class LxWindow extends AnchorPane {

    public static Stage owner;
    public static Stage newStage;
    private static int RESIZE_PADDING = 5;
    private static int SHADOW_WIDTH = 0;
    private double initX = -1;
    private double initY = -1;
    private double initStageX = 0;
    private double initStageY = 0;
    private double initWidth =0;
    private double initHeight = 0;
    private boolean isDragable = false;

    private BoundingBox savedBounds;

    @FXML private StackPane contentPane;
    @FXML private HBox titlePane;
    @FXML private Button maximizeBtn;

    public LxWindow(Stage stage, String title, Node node) {
        this.owner = stage;
        newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ldh/fx/ui/window/LxWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setContent(node);

        event();

        Scene scene = new Scene(this, 800, 600);
        String cssFile = getClass().getResource("/ldh/fx/ui/window/LxWindow.css").toExternalForm();
        scene.getStylesheets().add(cssFile);
        newStage.setScene(scene);

        newStage.setOnCloseRequest(e-> Platform.exit());
    }

    public void setContent(Node node) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(node);
    }

    public void show() {
        newStage.show();
    }

    @FXML
    public void closeWindow(ActionEvent evt) {
        LxWindow.newStage.close();
    }

    @FXML
    public void minimizeWindow(ActionEvent evt) {
        LxWindow.newStage.setIconified(true);
    }

    private void event() {
        this.setOnMouseMoved(e->nodeMove(e));
        this.setOnMousePressed(e->nodeClick(e));
        this.setOnMouseDragged(e->nodeDrage(e));
        this.setOnMouseReleased(e->nodeRelease(e));
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
//        if (maximized) {
//            // Remove maximized state
//            undecorator.maximizeProperty().set(false);
//            return;
//        } else if (savedBounds != null) {
//            undecorator.setShadow(true);
//        }
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

    @FXML
    public void startMoveWindow(MouseEvent evt) {
        initX = evt.getScreenX();
        initY = evt.getScreenY();
        Window w = this.getScene().getWindow();
        initStageX = w.getX();
        initStageY = w.getY();
        isDragable = true;
    }

    @FXML
    public void moveWindow(MouseEvent evt) {
        if (isDragable) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            newStage.setX(initStageX + (endMoveX - initX));
            newStage.setY(initStageY + (endMoveY - initY));
        }
    }

    @FXML
    public void titleDoubleClick(MouseEvent evt) {
        if (evt.getClickCount() != 2) return;
        Node n = (Node)evt.getSource();
        Window w = n.getScene().getWindow();
        double currentX = w.getX();
        double currentY = w.getY();
        double currentWidth = w.getWidth();
        double currentHeight = w.getHeight();

        ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(newStage.getX(), newStage.getY(), newStage.getWidth(), newStage.getHeight());
        Screen screen = screensForRectangle.get(0);
        Rectangle2D bounds = screen.getVisualBounds();

        if(currentX != bounds.getMinX() &&
                currentY != bounds.getMinY() &&
                currentWidth != bounds.getWidth() &&
                currentHeight != bounds.getHeight() ) {  // if not maximized
            w.setX(bounds.getMinX());
            w.setY(bounds.getMinY());
            w.setWidth(bounds.getWidth());
            w.setHeight(bounds.getHeight());

            savedBounds = new BoundingBox(currentX, currentY, currentWidth, currentHeight);
        } else {
            w.setX(savedBounds.getMinX());
            w.setY(savedBounds.getMinY());
            w.setWidth(savedBounds.getWidth());
            w.setHeight(savedBounds.getHeight());
        }
        evt.consume();  // don't bubble up to title bar
    }

    @FXML
    public void endMoveWindow(MouseEvent evt) {
        if (isDragable) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            newStage.setX(initStageX + (endMoveX - initX));
            newStage.setY(initStageY + (endMoveY - initY));
        }
    }

    @FXML
    public void maximizeWindow(ActionEvent evt) {
        if (newStage.isFullScreen()) {
            maximizeBtn.getStyleClass().remove("maximizeBtnUnFull");
            maximizeBtn.getStyleClass().add("maximizeBtnFull");
        } else {
            maximizeBtn.getStyleClass().remove("maximizeBtnFull");
            maximizeBtn.getStyleClass().add("maximizeBtnUnFull");
        }
        newStage.setFullScreen(!newStage.isFullScreen());
        evt.consume();  // don't bubble up to title bar
    }

    private boolean isRightEdge(double x, double y, Bounds boundsInParent) {
        if (x < boundsInParent.getWidth() && x > boundsInParent.getWidth() - RESIZE_PADDING - SHADOW_WIDTH &&  y > titlePane.getHeight() + this.getPadding().getLeft()) {
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
        if (x >= 0 && x < RESIZE_PADDING + SHADOW_WIDTH && y > titlePane.getHeight() + this.getPadding().getLeft()) {
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
            Undecorator.LOGGER.log(Level.SEVERE, "setStageY issue", e);
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
}
