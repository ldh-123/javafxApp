package ldh.descktop.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ldh.descktop.util.ThreadToolUtil;
import ldh.fx.StageUtil;

/**
 * Created by ldh on 2018/1/11.
 */
public class DesktopItemTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageUtil.STAGE = primaryStage;
        Image image = new Image(DesktopItemTest.class.getResource("/img/win10.png").toExternalForm());
//        DesktopToolbar toolbar = new DesktopToolbar();
        DesktopPane desktop = new DesktopPane();
        desktop.getStyleClass().add("desktop");
//        desktop.setPadding(new Insets(20));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
//        desktop.getChildren().addNewStage(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));

        Scene scene = new Scene(desktop, 1200, 700);
        scene.getStylesheets().add(this.getClass().getResource("/css/desktop.css").toExternalForm());
        scene.getStylesheets().add(this.getClass().getResource("/css/win10.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("demo");
        System.out.println("show=======================");
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
