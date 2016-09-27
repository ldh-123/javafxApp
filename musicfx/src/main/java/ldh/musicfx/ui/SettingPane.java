package ldh.musicfx.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialogs;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by Puhui on 2016/9/27.
 */
public class SettingPane extends VBox {

    private PopupButtonPane popupButtonPane;

    public SettingPane(PopupButtonPane pane) {
        popupButtonPane = pane;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SettingPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void settingItemClick(MouseEvent evt) {
        popupButtonPane.hidePopup();
        Dialogs.showInformationDialog(MusicWindow.newStage, "I have a great message for you!", "Information Dialog", "title");
    }
}
