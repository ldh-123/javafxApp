package ldh.musicfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ldh.musicfx.ui.MusicWindow;
import ldh.musicfx.ui.NavPane;

/**
 * Created by Puhui on 2016/9/24.
 */
public class MusicFx extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MusicWindow musicWindow = new MusicWindow(primaryStage, "demo");
        musicWindow.addStylesheet(MusicFx.class.getResource("/css/MusicFx.css").toExternalForm());
        musicWindow.setContent(new Label("ssdaa"));
//        musicWindow.setNavPane(new NavPane());
        musicWindow.showFadeIn();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
