package control;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by ldh on 2018/5/4.
 */
public class MaskedTextTieldTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        MaskedTextField text = new MaskedTextField("(010)#UL###-AAA"); // Only Number

        text.setPlaceholder("-");

        text.setMask("((AAA))");

        Scene scene = new Scene(text);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
