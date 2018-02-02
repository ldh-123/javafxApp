package ldh.fx.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Window;

import java.io.IOException;
import java.util.logging.Logger;

public class LxWindowBase extends AnchorPane {

    private Logger logger = Logger.getLogger(LxWindowBase.class.getName());

    @FXML private BorderPane contentContainer;

    private double startMoveX = -1;
    private double startMoveY = -1;
    private double stageX = -1;
    private double stageY = -1;
    protected Boolean dragging = false;
    protected boolean isMoved = false;

    protected Double layoutX = 0d, layoutY = 0d;

    public LxWindowBase() {
        loadFx("/component/LxWindow.fxml");
    }

    protected BorderPane getContentContainer() {
        return contentContainer;
    }

    public void startMoveWindow(MouseEvent evt) {
        startMoveX = evt.getScreenX();
        startMoveY = evt.getScreenY();
        Window w = this.getScene().getWindow();
        stageX = w.getX();
        stageY = w.getY();
        dragging = true;
    }

    public void moveWindow(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            Window w = this.getScene().getWindow();
            w.setX(stageX + (endMoveX - startMoveX));
            w.setY(stageY + (endMoveY - startMoveY));
            isMoved = true;
        }
        layoutX = this.getScene().getWindow().getX();
        layoutY = this.getScene().getWindow().getY();
    }

    @FXML
    public void endMoveWindow(MouseEvent evt) {
        if (dragging) {
            startMoveX = 0;
            startMoveY = 0;
            stageX = 0;
            stageY = 0;
            dragging = false;
        }
        layoutX = this.getScene().getWindow().getX();
        layoutY = this.getScene().getWindow().getY();
    }

    public void buildMovable(Region node) {
        node.setOnDragDetected(e->startMoveWindow(e));
        node.setOnMouseDragged(e->moveWindow(e));
        node.setOnMouseReleased(e->endMoveWindow(e));
    }

    protected void loadFx(String fxmlPath) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
