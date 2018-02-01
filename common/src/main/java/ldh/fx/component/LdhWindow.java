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
import ldh.fx.transition.BounceInTransition;
import ldh.fx.ui.util.StageResizable;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LWindow extends LWindowBody {

    protected Stage newStage;
    protected Popup popup;
    protected Stage parentStage;

    private ObjectProperty<DialogModel> modelProperty = new SimpleObjectProperty(DialogModel.Normal);
    protected BooleanProperty isMinProperty = new SimpleBooleanProperty(false);

    public LWindow() {
        super();
        buildMovable();
    }

    public void initDialogModel(Stage stage) {
        initDialogModel(stage, DialogModel.Normal);
    }

    public void initDialogModel(Stage stage, DialogModel dialogModel) {
        parentStage = stage;
        modelProperty.set(dialogModel);
        if (dialogModel != DialogModel.Normal) {
            newStage = new Stage();
            if (dialogModel == DialogModel.Application_model) {
                newStage.initOwner(stage);
                newStage.initModality(Modality.APPLICATION_MODAL);
            }
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
        return modelProperty.get() != DialogModel.Normal;
    }

    public void show() {
        if (isModel()) {
            newStage.show();
            isMinProperty.set(false);
        } else {
            if (popup.isShowing()) {
                popup.hide();
                return;
            }
            new BounceInTransition(this).play();
            popup.show(parentStage);
        }
    }
}
