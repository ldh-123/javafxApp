package ldh.musicfx.ui;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by Puhui on 2016/9/27.
 */
public class NavPane extends VBox {

    @FXML
    StackPane headPane;

    private BooleanProperty isToggleBooleanProperty;

    public NavPane(BooleanProperty isToggleBooleanProperty) {
        this.isToggleBooleanProperty = isToggleBooleanProperty;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NavPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        String imgFile = getClass().getClassLoader().getResource("img/music.jpg").toExternalForm();
        TitlePane titlePane = new TitlePane(imgFile, "Toggle", isToggleBooleanProperty);
        headPane.getChildren().add(titlePane);

        PopupButtonPane popupButtonPane = new PopupButtonPane(isToggleBooleanProperty);
        this.getChildren().add(popupButtonPane);
    }
}
