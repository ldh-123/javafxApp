package ldh.descktop;

import de.jensd.fx.glyphs.GlyphIcon;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ldh.descktop.ui.DesktopNav;
import ldh.descktop.ui.DesktopNavItem;
import ldh.descktop.util.ThreadToolUtil;
import ldh.fx.StageUtil;
import ldh.fx.util.DialogUtil;

/**
 * Created by ldh on 2018/1/25.
 */
public abstract class AbstractMainApp extends Application {

    protected Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageUtil.STAGE = primaryStage;
        this.stage = primaryStage;
        start();
        stage.setOnCloseRequest(e->{
            StageUtil.closeAllNewStages();
            ThreadToolUtil.close();
        });
    }

    public abstract void start() throws Exception;

    protected Button createButton(DesktopNav desktopNav, String text, GlyphIcon glyphIcon, String style) {
        Button button = new Button(text);
        glyphIcon.getStyleClass().add(style);
        button.setGraphic(glyphIcon);
        desktopNav.getChildren().add(new DesktopNavItem(button));
        return button;
    }

    protected Button createButton(DesktopNav desktopNav, String text, String style) {
        Button button = new Button(text);
        button.getStyleClass().add(style);
        desktopNav.getChildren().add(new DesktopNavItem(button));
        button.setOnAction(e-> DialogUtil.modelInfo("demo", text, 200, 100));
        return button;
    }
}
