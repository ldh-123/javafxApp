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
public class DesktopTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageUtil.STAGE = primaryStage;
        Image image = new Image(DesktopTest.class.getResource("/img/win10.png").toExternalForm());
        DesktopPane desktopPane = new DesktopPane();
        desktopPane.getStyleClass().add("desktop");
//        desktop.setPadding(new Insets(20));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", "http://www.baidu.com"));

        DesktopToolbar toolbar = new DesktopToolbar();
        Desktop desktop = new Desktop(toolbar, desktopPane);

        Scene scene = new Scene(desktop, 1200, 700);
        scene.getStylesheets().add(this.getClass().getResource("/css/desktop.css").toExternalForm());
        scene.getStylesheets().add(this.getClass().getResource("/css/demo.css").toExternalForm());
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
