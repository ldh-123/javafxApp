package ldh.descktop;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import ldh.descktop.page.AnimationPane;
import ldh.descktop.page.FormContent;
import ldh.descktop.page.JsonPane;
import ldh.descktop.ui.*;
import ldh.fx.ui.util.NodeUtil;
import ldh.fx.ui.util.PageUtil;
import ldh.fx.ui.util.RegionUtil;
import ldh.descktop.util.ThreadToolUtil;
import ldh.fx.StageUtil;
import ldh.fx.util.DialogUtil;

/**
 * Created by ldh on 2018/1/18.
 */
public class MacMainApp extends AbstractMainApp {

    @Override
    public void start() throws Exception {
        Image image = new Image(WinMainApp.class.getResource("/images/win10.png").toExternalForm());
        DesktopNodeFactory nodeFactory = () -> {WebView webView = new WebView(); webView.getEngine().load("http://www.baidu.com"); return webView;};

        DesktopPane desktopPane = new DesktopPane();
        desktopPane.getStyleClass().add("desktop");
//        desktop.setPadding(new Insets(20));
        DesktopNodeFactory stageFactory = () ->{
            Region parent = PageUtil.load("/fxml/Home.fxml");
            Stage stage = new Stage();
//            stage.initOwner(StageUtil.STAGE);
            Scene scene = new Scene(parent, 1200, 700);
            RegionUtil.sizeRegionWhenSceneChange(parent, scene);
            scene.getStylesheets().add(this.getClass().getResource("/css/Common.css").toExternalForm());
            stage.setScene(scene);
            return stage;
        };
        desktopPane.getChildren().add(new DesktopItem(RegionUtil.createLabel("Home后台", new FontAwesomeIconView(), "home-graphic"), stageFactory));
        desktopPane.getChildren().add(new DesktopItem(RegionUtil.createLabel("CVS浏览器", new FontAwesomeIconView(), "cvs-graphic"), ()-> PageUtil.load("/fxml/Cvs.fxml")));
        desktopPane.getChildren().add(new DesktopItem(image, "百度搜索", nodeFactory));
        desktopPane.getChildren().add(new DesktopItem(RegionUtil.createLabel("Form表单样式", new FontAwesomeIconView(), "form-graphic"), ()->new FormContent()));
        desktopPane.getChildren().add(new DesktopItem(RegionUtil.createLabel("动画面板", new FontAwesomeIconView(), "plan-pane-graphic"), ()->new AnimationPane()));
        desktopPane.getChildren().add(new DesktopItem(RegionUtil.createLabel("Json格式化", new FontAwesomeIconView(), "json-pane-graphic"), ()->new JsonPane()));

        DesktopNav desktopNav = buildDesktopDav();

        DesktopToolbar toolbar = new MacDesktopToolbar(desktopPane);
        MacDesktop desktop = new MacDesktop(desktopPane, toolbar, desktopNav);

        Scene scene = new Scene(desktop, 1300, 650);
        scene.setFill(null);
        scene.getStylesheets().add(this.getClass().getResource("/css/mac.css").toExternalForm());
        scene.getStylesheets().add(this.getClass().getResource("/css/mac-dialog.css").toExternalForm());
        scene.getStylesheets().add("component/LDialog.css");
//        scene.getStylesheets().add("bootstrapfx.css");
        scene.getStylesheets().add("/component/LxDialog.css");
        stage.setScene(scene);
        stage.setTitle("Mac桌面");
        stage.show();
    }

    private DesktopNav buildDesktopDav() {
        DesktopNav desktopNav = new DesktopNav();
        createButton(desktopNav, "finder","finder");
        createButton(desktopNav, "computer","computer");
        createButton(desktopNav, "gamecenter", "gamecenter");
        createButton(desktopNav, "icloud", "icloud");
        createButton(desktopNav, "prefapp","prefapp");
        createButton(desktopNav, "trashicon","trashicon");
        createButton(desktopNav, "appstore", "appstore");
        createButton(desktopNav, "iTunes", "iTunes");
        createButton(desktopNav, "imusic","imusic");
        createButton(desktopNav, "preview","preview");
        createButton(desktopNav, "mail", "mail");
        createButton(desktopNav, "notes", "notes");
        createButton(desktopNav, "messages","messages");
        createButton(desktopNav, "maps","maps");
        createButton(desktopNav, "ibooks", "ibooks");
        createButton(desktopNav, "facetime", "facetime");
        createButton(desktopNav, "launchpad","launchpad");
        return desktopNav;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
