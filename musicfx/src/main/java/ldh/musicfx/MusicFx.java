package ldh.musicfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ldh.musicfx.ui.MusicWindow;
import ldh.musicfx.ui.NavPane;

import java.io.IOException;

/**
 * Created by Puhui on 2016/9/24.
 */
public class MusicFx extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MusicWindow musicWindow = new MusicWindow(primaryStage, "demo");
        musicWindow.addStylesheet(MusicFx.class.getResource("/css/MusicFx.css").toExternalForm());
        musicWindow.setContent(loadCenterPane());
//        musicWindow.setNavPane(new NavPane());
        musicWindow.showFadeIn();
    }

    private Node loadCenterPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MusicCenter.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return fxmlLoader.getRoot();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
