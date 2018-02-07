package com.modulefx.test.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class App extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        BorderPane borderPane = new BorderPane();

        String fxmlUri = "/fxml/Main.fxml";
        
        //load the Main.fxml layout
        Parent root = loadFxml(fxmlUri);

        //load the styleSheet of the project
        Scene scene = new Scene(root);
        scene.getStylesheets().add(App.class.getResource("/css/test.css").toString());

        primaryStage.setScene(scene);
        //TODO change to a properties value
        primaryStage.setTitle("ModuleFX");
        primaryStage.show();
    }

    private Parent loadFxml(String fxmlUri) {
        try {
            
            //set the osgi ClassLoader to the FXMLLoader.
            FXMLLoader.setDefaultClassLoader(Activator.class.getClassLoader());
//            return FXMLLoader.load(getClass().getClassLoader().getResource(fxmlUri));
            return FXMLLoader.load(App.class.getResource(fxmlUri));
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
