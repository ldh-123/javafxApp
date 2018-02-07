/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.fx.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        window.setStyle("-fx-background-color:grey;");
        window.initDialogModel(stage, DialogModel.Normal);
        window.setPrefSize(300, 400);
        window.setMovable();
        window.setResizable();
        Button b = new Button("open");
        b.setOnAction(e->window.show());
        Scene scene = new Scene(b, 300, 400);
        stage.setScene(scene);
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
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
