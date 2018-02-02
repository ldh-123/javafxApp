/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.fx.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ldh.fx.component.DialogModel;
import ldh.fx.component.LxWindow;

/**
 *
 * @author Puhui
 */
public class LxWindowTest extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        LxWindow window = new LxWindow();
        window.buildResizable(window);
//        window.buildMovable(window);
        window.setStyle("-fx-background-color:grey;");
        window.setDialogModel(DialogModel.Normal);
        Scene scene = new Scene(window, 300, 400);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
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
