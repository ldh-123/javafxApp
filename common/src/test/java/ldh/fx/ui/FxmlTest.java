package ldh.fx.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ldh.fx.component.function.CrudForm;
import ldh.fx.component.function.Searchable;

import java.io.IOException;

/**
 * Created by ldh on 2018/5/11.
 */
public class FxmlTest extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/form/EditPojoForm.fxml"));
        try {
            GridPane parent = fxmlLoader.load();
            System.out.println(parent.getPrefHeight());
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println(parent.getWidth());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
