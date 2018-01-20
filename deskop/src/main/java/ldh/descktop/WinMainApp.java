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
import ldh.descktop.util.PageUtil;
import ldh.descktop.util.RegionUtil;
import ldh.descktop.util.ThreadToolUtil;
import ldh.fx.StageUtil;

/**
 * Created by ldh on 2018/1/16.
 */
public class WinMainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageUtil.STAGE = primaryStage;
        Image image = new Image(WinMainApp.class.getResource("/images/win10.png").toExternalForm());
        DesktopNodeFactory nodeFactory = () -> {WebView webView = new WebView(); webView.getEngine().load("http://www.baidu.com"); return webView;};
        DesktopToolbar toolbar = new WinDesktopToolbar();

        DesktopPane desktopPane = new DesktopPane();
//        desktop.setPadding(new Insets(20));
        desktopPane.getChildren().add(new DesktopItem(image, "Win10-UI官网", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(RegionUtil.createLabel("CVS浏览器", new FontAwesomeIconView(), "cvs-graphic"), ()-> PageUtil.load("/fxml/Cvs.fxml")));
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

        WinDesktop desktop = new WinDesktop(desktopPane, toolbar);

        Scene scene = new Scene(desktop, 1200, 700);
        scene.getStylesheets().add(this.getClass().getResource("/css/win10.css").toExternalForm());
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
