/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.fx.ui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ldh.fx.ui.LWindow;

/**
 *
 * @author Puhui
 */
public class LWindowTest extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        LWindow window = new LWindow(stage, "sadadfa", new Label("sadfafdasfasdfasfasdfsadfasfsafdas"));
//        stage.initStyle(StageStyle.TRANSPARENT);
    }

    /**
     * The fxml() method is ignored in correctly deployed JavaFX application.
     * fxml() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores fxml().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
