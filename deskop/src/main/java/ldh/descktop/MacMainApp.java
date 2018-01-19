package ldh.descktop;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
 * Created by ldh on 2018/1/18.
 */
public class MacMainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageUtil.STAGE = primaryStage;
        Image image = new Image(WinMainApp.class.getResource("/images/win10.png").toExternalForm());
        DesktopNodeFactory nodeFactory = () -> {WebView webView = new WebView(); webView.getEngine().load("http://www.baidu.com"); return webView;};
        DesktopToolbar toolbar = new MacDesktopToolbar();

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

        DesktopNav desktopNav = buildDesktopDav();

        MacDesktop desktop = new MacDesktop(desktopPane, toolbar, desktopNav);

        Scene scene = new Scene(desktop, 1300, 650);
        scene.setFill(null);
        scene.getStylesheets().add(this.getClass().getResource("/css/mac.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("demo");
        primaryStage.setOnCloseRequest(e->{
            ThreadToolUtil.close();
        });
        primaryStage.show();
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

    private Button createButton(DesktopNav desktopNav, String text, GlyphIcon glyphIcon, String style) {
        Button button = new Button(text);
        glyphIcon.getStyleClass().add(style);
        button.setGraphic(glyphIcon);
        desktopNav.getChildren().add(new DesktopNavItem(button));
        return button;
    }

    private Button createButton(DesktopNav desktopNav, String text, String style) {
        Button button = new Button(text);
        button.getStyleClass().add(style);
        desktopNav.getChildren().add(new DesktopNavItem(button));
        return button;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
