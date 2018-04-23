package ldh.fx.component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import ldh.fx.ui.util.RegionResizable;
import ldh.fx.ui.util.Resizable;
import ldh.fx.ui.util.StageMovable;
import ldh.fx.ui.util.StageResizable;

import java.io.IOException;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Created by ldh on 2018/1/30.
 */
public class LWindowBase extends AnchorPane {

    private Logger logger = Logger.getLogger(LWindowBase.class.getName());

    @FXML protected Region eastEdgePane;
    @FXML protected Region westEdgePane;
    @FXML protected Region seBuglePane;
    @FXML protected Region southEdgePane;
    @FXML protected Region northEdgePane;
    @FXML protected Region swBuglePane;
    @FXML protected BorderPane dialogContainer;
    @FXML protected ScrollPane contentContainer;

    private Resizable resizable;
    private StageMovable stageMovable;

    private ObjectProperty<Node> centerProperty = new SimpleObjectProperty<>();
    private ObjectProperty<Node> topProperty = new SimpleObjectProperty<>();

    public LWindowBase() {
        loadFx("/component/LWindow.fxml");
        centerProperty.addListener((l, o, n)->{
            contentContainer.setContent(n);
        });
        topProperty.addListener((l, o, n)->{
            dialogContainer.setTop(n);
        });
    }

    public void addDropShadow() {
        Effect effect = this.getEffect();
        if (effect != null && effect instanceof DropShadow) return;
        DropShadow dropShadow = new DropShadow();
        this.setEffect(dropShadow);
    }

    public void removeDropShadow() {
        Effect effect = this.getEffect();
        if (effect instanceof DropShadow) {
            this.setEffect(null);
        }
    }

    public Node getCenter() {
        return centerProperty.get();
    }

    public ObjectProperty<Node> getCenterProperty() {
        return centerProperty;
    }

    public void setCenter(Node node) {
        centerProperty.set(node);
    }

    public void setContentPane(Node node) {
        centerProperty.set(node);
    }

    public Node getTop() {
        return topProperty.get();
    }

    public ObjectProperty<Node> getTopProperty() {
        return topProperty;
    }

    public void setTop(Node node) {
        topProperty.set(node);
    }


    protected void buildResizable(Object obj) {
        if (obj instanceof Stage) {
            resizable = new StageResizable(eastEdgePane, seBuglePane, southEdgePane, swBuglePane, westEdgePane, northEdgePane);

            buildEastEdgeResizable();
            buildSouthEdgeResizable();
            buildWestEdgeResizable();
            buildNorthEdgeResizable();
            buildSeBugleResizable();
            buildSwBugleResizable();
        } else if (obj instanceof Region) {
            resizable = new RegionResizable((Region)obj, eastEdgePane, seBuglePane, southEdgePane, swBuglePane, westEdgePane, northEdgePane);

//            buildEastEdgeResizable();
            buildSouthEdgeResizable();
            buildWestEdgeResizable();
//            buildNorthEdgeResizable();
//            buildSeBugleResizable();
            buildSwBugleResizable();
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
    public void buildNorthEdgeResizable() {
        resizable.buildNorthEdgeResizable();
    }
    public void buildSeBugleResizable() {
        resizable.buildSeBugleResizable();
    }
    public void buildSwBugleResizable() {
        resizable.buildSwBugleResizable();
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
