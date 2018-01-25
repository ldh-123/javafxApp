package ldh.descktop.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ldh.descktop.util.ThreadToolUtil;
import ldh.fx.StageUtil;

/**
 * Created by ldh on 2018/1/11.
 */
public class DesktopXmlTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageUtil.STAGE = primaryStage;
        Parent node = FXMLLoader.load(DesktopXmlTest.class.getResource("/fxml/DesktopTest.fxml"));

        Scene scene = new Scene(node, 1200, 700);
        scene.getStylesheets().add(this.getClass().getResource("/css/desktop.css").toExternalForm());
//        scene.getStylesheets().addNewStage(this.getClass().getResource("/css/win10.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("demo");
        primaryStage.setOnCloseRequest(e->{
            System.out.println("exit=======================");
            ThreadToolUtil.close();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
