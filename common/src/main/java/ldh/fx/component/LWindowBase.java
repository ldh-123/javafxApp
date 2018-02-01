package ldh.fx.component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
public class LWindowBase extends BorderPane {

    private Logger logger = Logger.getLogger(LWindowBase.class.getName());

    @FXML
    protected VBox eastEdgePane;
    @FXML protected VBox westEdgePane;
    @FXML protected Button seBuglePane;
    @FXML protected HBox southEdgePane;
    @FXML protected Button swBuglePane;

    private Resizable resizable;
    private StageMovable stageMovable;
    private ScrollPane scrollPane = new ScrollPane();

    private ObjectProperty<Region> contentPaneProperty = new SimpleObjectProperty<>();

    public LWindowBase() {
        loadFx("/component/LWindow.fxml");
        this.setCenter(scrollPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("content-container");
        contentPaneProperty.addListener((l, o, n)->{
            scrollPane.setContent(n);
        });
    }

    public void buildResizable(Object obj) {
        if (obj instanceof Stage) {
            resizable = new StageResizable(eastEdgePane, seBuglePane, southEdgePane, swBuglePane, westEdgePane);

            buildEastEdgeResizable();
            buildSouthEdgeResizable();
            buildWestEdgeResizable();
            buildSeBugleResizable();
            buildSwBugleResizable();
        } else if (obj instanceof Region) {
            resizable = new RegionResizable((Region)obj, eastEdgePane, seBuglePane, southEdgePane, swBuglePane, westEdgePane);

//            buildEastEdgeResizable();
            buildSouthEdgeResizable();
            buildWestEdgeResizable();
//            buildSeBugleResizable();
            buildSwBugleResizable();
//            eastEdgePane.setVisible(false);
            seBuglePane.setVisible(false);
        }
    }

    public void buildMovable(Region region) {
        stageMovable = new StageMovable(region);
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

    public ObjectProperty<Region> contentPaneProperty() {
        return contentPaneProperty;
    }

    public void setContentPane(Region node) {
        contentPaneProperty.set(node);
    }

    public Node getContentPane() {
        return contentPaneProperty.get();
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
