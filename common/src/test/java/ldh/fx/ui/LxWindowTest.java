/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.fx.ui;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ldh.fx.ui.window.LxWindow;

/**
 *
 * @author Puhui
 */
public class LxWindowTest extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        LxWindow window = new LxWindow(stage, "sadadfa", new Label("sadfafdasfasdfasfasdfsadfasfsafdas"));
        window.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
