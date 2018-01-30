package ldh.fx.component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ldh.fx.ui.util.RegionResizable;
import ldh.fx.ui.util.Resizable;
import ldh.fx.ui.util.StageMovable;
import ldh.fx.ui.util.StageResizable;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by ldh on 2018/1/30.
 */
public class LWindowBody extends BorderPane {

    private Logger logger = Logger.getLogger(LdhWindow.class.getName());

    @FXML
    protected VBox eastEdgePane;
    @FXML protected VBox westEdgePane;
    @FXML protected Button seBuglePane;
    @FXML protected HBox southEdgePane;
    @FXML protected Button swBuglePane;

    private Resizable resizable;
    private StageMovable stageMovable;

    private ObjectProperty<Node> contentPaneProperty = new SimpleObjectProperty<>();

    public LWindowBody() {
        loadFx();
        contentPaneProperty.addListener((l, o, n)->{
            this.setCenter(n);
        });
    }

    public void buildResizable(Object obj) {
        if (obj instanceof Stage) {
            resizable = new StageResizable(eastEdgePane, seBuglePane, southEdgePane, swBuglePane, westEdgePane);
            stageMovable = new StageMovable(this);
        } else if (obj instanceof Region) {
            resizable = new RegionResizable((Region)obj, eastEdgePane, seBuglePane, southEdgePane, swBuglePane, westEdgePane);
            stageMovable = new StageMovable(this);
        }

        buildEastEdgeResizable();
        buildSouthEdgeResizable();
        buildWestEdgeResizable();
        buildSeBugleResizable();
        buildSwBugleResizable();
    }

    public void buildEastEdgeResizable() {
        resizable.buildEastEdgeResizable();
    }
    public void buildSouthEdgeResizable() {
        resizable.buildSouthEdgeResizable();
    }
    public void buildWestEdgeResizable() {
        resizable.buildWestEdgeResizable();
    }
    public void buildSeBugleResizable() {
        resizable.buildSeBugleResizable();
    }
    public void buildSwBugleResizable() {
        resizable.buildSwBugleResizable();
    }

    public ObjectProperty<Node> contentPaneProperty() {
        return contentPaneProperty;
    }

    public void setContentPane(Node node) {
        contentPaneProperty.set(node);
    }

    public Node getContentPane() {
        return contentPaneProperty.get();
    }

    private void loadFx() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/component/LWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

//    @FXML
//    public void close(MouseEvent evt) {
//        ((Label)evt.getSource()).getScene().getWindow().hide();
//    }
//
//    public void maxDoubleClick(MouseEvent evt) {
//        if (evt.getClickCount() != 2) return;
//        maximize(evt);
//    }
//
//    @FXML
//    public void maximize(MouseEvent evt) {
//        Node n = (Node)evt.getSource();
//        Window w = n.getScene().getWindow();
//        double currentX = w.getX();
//        double currentY = w.getY();
//        double currentWidth = w.getWidth();
//        double currentHeight = w.getHeight();
//
//        Screen screen = Screen.getPrimary();
//        Rectangle2D bounds = screen.getVisualBounds();
//
//        if( currentX != bounds.getMinX() &&
//                currentY != bounds.getMinY() &&
//                currentWidth != bounds.getWidth() &&
//                currentHeight != bounds.getHeight() ) {  // if not maximized
//
//            w.setX(bounds.getMinX());
//            w.setY(bounds.getMinY());
//            w.setWidth(bounds.getWidth());
//            w.setHeight(bounds.getHeight());
//
//            lastX = currentX;  // save old dimensions
//            lastY = currentY;
//            lastWidth = currentWidth;
//            lastHeight = currentHeight;
//        } else {
//            w.setX(lastX);
//            w.setY(lastY);
//            w.setWidth(lastWidth);
//            w.setHeight(lastHeight);
//
//        }
//        evt.consume();  // don't bubble up to title bar
//    }
//
//    @FXML
//    public void minimize(MouseEvent evt) {
//        Stage stage = (Stage)((Label)evt.getSource()).getScene().getWindow();
//        stage.setIconified(true);
//    }
//
//    public void setContent(Node node) {
//        this.setCenter(node);
//    }
}
