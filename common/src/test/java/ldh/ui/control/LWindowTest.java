/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.ui.control;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Puhui
 */
public class LWindowTest extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        LWindow window = new LWindow("sadadfa", new Label("sadfafdasfasdfasfasdfsadfasfsafdas"));
        
        Scene scene = null;
//        String osName = System.getProperty("os.name");
//        if( osName != null && osName.startsWith("Windows") ) {
//
//            //
//            // Windows hack b/c unlike Mac and Linux, UNDECORATED doesn't include a shadow
//            //
//            scene = (new WindowsHack()).getShadowScene(window);
//            stage.initStyle(StageStyle.TRANSPARENT);
//
//        } else {
//            scene = new Scene(window);
//            stage.initStyle(StageStyle.UNDECORATED);
//        }

        scene = new Scene(window);
        stage.initStyle(StageStyle.UNDECORATED);

        scene.getStylesheets().add("/ldh/ui/control/LWindow.css");
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
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
