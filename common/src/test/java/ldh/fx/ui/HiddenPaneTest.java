package ldh.fx.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ldh.fx.component.LHiddenPane;

/**
 * Created by ldh on 2018/1/16.
 */
public class HiddenPaneTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new Label("top"));
        borderPane.setBottom(new Label("bottom"));
        borderPane.setCenter(new Label("center"));
        borderPane.setLeft(new Label("left"));

        BorderPane borderPane2 = new BorderPane();
        borderPane2.setTop(new Label("top2"));
        borderPane2.setBottom(new Label("bottom2"));
        borderPane2.setCenter(new Label("center2"));
        borderPane2.setLeft(new Label("left2"));

        LHiddenPane hiddenPane = new LHiddenPane(borderPane, borderPane2);
//        LHiddenPane hiddenPane = new LHiddenPane();
//        hiddenPane.getChildren().addAll(borderPane, borderPane2);
//        hiddenPane.getChildren().add(borderPane);
//        hiddenPane.getChildren().add(borderPane2);
        Scene scene = new Scene(hiddenPane, 1200, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
