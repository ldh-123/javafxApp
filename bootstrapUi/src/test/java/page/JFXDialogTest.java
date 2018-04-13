package page;

import com.jfoenix.controls.JFXDialog;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ldh.common.ui.node.FormContent;

public class JFXDialogTest extends Application {

    public static Stage STAGE = null;

    public void start(Stage primaryStage) throws Exception {
        StackPane stackPane = new StackPane();
        Button button = new Button("show dialog");

        VBox hBox = new VBox();
        hBox.getChildren().addAll(button, stackPane);

        JFXDialog jfxDialog = new JFXDialog();
        stackPane.getChildren().addAll(button, jfxDialog);
        button.setOnAction(e->{
            jfxDialog.setDialogContainer(stackPane);
            jfxDialog.setTransitionType(JFXDialog.DialogTransition.TOP);
            FormContent loginPage = new FormContent();
            jfxDialog.setContent(loginPage);
            jfxDialog.show();
        });
        Scene scene = new Scene(hBox);
//        scene.getStylesheets().addAll("bootstrapfx.css");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
        STAGE = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
