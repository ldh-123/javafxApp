package ldh.fx.component;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import ldh.fx.ui.util.StageResizable;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LdhWindow extends LWindowBody {

    protected Stage newStage;
    protected Popup popup;
    protected Stage parentStage;

    private BooleanProperty modelProperty = new SimpleBooleanProperty(false);

    public LdhWindow() {
        super();
        buildMovable();
    }

    public void initModel(Stage stage, boolean isModel) {
        parentStage = stage;
        modelProperty.set(isModel);
        if (isModel()) {
            newStage = new Stage();
            newStage.initOwner(stage);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(this, 800, 600);
            scene.setFill(null);
            newStage.setScene(scene);
        } else {
            popup = new Popup();
            popup.setAutoHide(false);
            popup.getContent().clear();
            popup.getContent().add(this);
        }
        buildResizable();
    }

    public void buildResizable() {
        if (isModel()) {
            buildResizable(newStage);
        } else {
            buildResizable(this);
        }
    }

    public void buildMovable() {
        buildMovable(this);
    }

    public boolean isModel() {
        return modelProperty.get();
    }

    public void show() {
        if (modelProperty.get()) {
            newStage.show();
        } else {
            popup.show(parentStage);
        }
    }
}
