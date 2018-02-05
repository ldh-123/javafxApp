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
import ldh.fx.ui.util.PageUtil;
import ldh.fx.ui.util.RegionUtil;
import ldh.descktop.util.ThreadToolUtil;
import ldh.fx.StageUtil;

/**
 * Created by ldh on 2018/1/16.
 */
public class WinMainApp extends AbstractMainApp {

    @Override
    public void start() throws Exception {
        Image image = new Image(WinMainApp.class.getResource("/images/win10.png").toExternalForm());
        DesktopNodeFactory nodeFactory = () -> {WebView webView = new WebView(); webView.getEngine().load("http://www.baidu.com"); return webView;};

        DesktopPane desktopPane = new DesktopPane();
//        desktop.setPadding(new Insets(20));
        desktopPane.getChildren().add(new DesktopItem(RegionUtil.createLabel("Home后台", new FontAwesomeIconView(), "home-graphic"), ()-> PageUtil.load("/fxml/Home.fxml")));
        desktopPane.getChildren().add(new DesktopItem(RegionUtil.createLabel("CVS浏览器", new FontAwesomeIconView(), "cvs-graphic"), ()-> PageUtil.load("/fxml/Cvs.fxml")));
        desktopPane.getChildren().add(new DesktopItem(image, "百度搜索", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(RegionUtil.createLabel("Form表单样式", new FontAwesomeIconView(), "form-graphic"), ()->new FormContent()));
        desktopPane.getChildren().add(new DesktopItem(RegionUtil.createLabel("动画面板", new FontAwesomeIconView(), "plan-pane-graphic"), ()->new AnimationPane()));

        DesktopToolbar toolbar = new WinDesktopToolbar(desktopPane);
        WinDesktop desktop = new WinDesktop(desktopPane, toolbar);

        Scene scene = new Scene(desktop, 1200, 700);
        scene.getStylesheets().add(this.getClass().getResource("/css/win10.css").toExternalForm());
        scene.getStylesheets().add(this.getClass().getResource("/css/Common.css").toExternalForm());
        scene.getStylesheets().add("component/LDialog.css");
        scene.getStylesheets().add("/component/LxDialog.css");
        stage.setScene(scene);
        stage.setTitle("Win10桌面");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
