package ldh.descktop;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import ldh.descktop.page.AnimationPane;
import ldh.descktop.page.FormContent;
import ldh.descktop.ui.*;
import ldh.descktop.util.ThreadToolUtil;
import ldh.fx.StageUtil;

/**
 * Created by ldh on 2018/1/16.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageUtil.STAGE = primaryStage;
        Image image = new Image(MainApp.class.getResource("/images/win10.png").toExternalForm());
        DesktopNodeFactory nodeFactory = () -> {WebView webView = new WebView(); webView.getEngine().load("http://www.baidu.com"); return webView;};
        DesktopToolbar toolbar = new DesktopToolbar();

        DesktopPane desktopPane = new DesktopPane();
        desktopPane.getStyleClass().add("desktop");
//        desktop.setPadding(new Insets(20));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(image, "Form表单样式", ()->new FormContent()));

        Label label = new Label("动画面板");
        FontAwesomeIconView icon = new FontAwesomeIconView();
        icon.getStyleClass().add("plan-pane-graphic");
        label.setGraphic(icon);
        desktopPane.getChildren().add(new DesktopItem(label, ()->new AnimationPane()));

        Desktop desktop = new Desktop(desktopPane, toolbar);

        Scene scene = new Scene(desktop, 1200, 700);
        scene.getStylesheets().add(this.getClass().getResource("/css/desktop.css").toExternalForm());
        scene.getStylesheets().add(this.getClass().getResource("/css/demo.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("demo");
        primaryStage.setOnCloseRequest(e->{
            ThreadToolUtil.close();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}