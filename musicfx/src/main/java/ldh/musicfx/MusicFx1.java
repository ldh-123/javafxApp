package ldh.musicfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Puhui on 2016/9/24.
 */
public class MusicFx1 extends Application {

    private static Stage primaryStageObj;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(primaryStage);
        primaryStageObj = stage;

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MusicFx.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Socket Chat : Client version 0.3");
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResource("img/music.jpg").toString()));
        Scene mainScene = new Scene(root, 850, 620);
        mainScene.getStylesheets().add(getClass().getResource("/css/MusicFx.css").toExternalForm());
        mainScene.setRoot(root);
        stage.setResizable(true);
        stage.setScene(mainScene);
        stage.show();
        stage.setOnCloseRequest(e -> Platform.exit());
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStageObj;
    }
}
