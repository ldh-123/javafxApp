package ldh.descktop.test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by ldh on 2018/1/25.
 */
public class TwoStageJavaFXExample extends Application {

    Stage secondaryStage = null;
    Stage primaryStage = null;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Button btn = new Button();
        btn.setText("去第二舞台");

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                newStage();
                //primaryStage.hide();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("第一舞台");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void newStage() {
        if (secondaryStage != null) {
            if (secondaryStage.isShowing()) {
                secondaryStage.setIconified(false);
//                secondaryStage.hide();
            } else {
                secondaryStage.setIconified(true);
            }
        } else {
            secondaryStage = createNewStage();

            secondaryStage.show();

        }
        System.out.println("转向第二舞台");
    }

    //显示第二个舞台
    public Stage createNewStage(){
        Stage secondaryStage=new Stage();
//        secondaryStage.initOwner(primaryStage);
        Button btn1=new Button();
        StackPane root1=new StackPane();
        btn1.setText("欢迎来到第二舞台");

        btn1.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                System.out.println("欢迎来到第二舞台");
            }
        });

        root1.getChildren().add(btn1);
        Scene secondaryScene=new Scene(root1,500,250);
        secondaryStage.setScene(secondaryScene);
        secondaryStage.setTitle("第二舞台");
        secondaryStage.show();
        return secondaryStage;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
