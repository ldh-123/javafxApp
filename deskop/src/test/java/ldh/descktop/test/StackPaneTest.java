package ldh.descktop.test;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ldh.descktop.page.AnimationPane;

/**
 * Created by ldh on 2018/1/19.
 */
public class StackPaneTest extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane stackPane = buildStackPane();
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(20));
        hbox.getChildren().add(stackPane);
        Scene scene = new Scene(hbox, 800, 500);
        scene.getStylesheets().add(StackPaneTest.class.getResource("/css/StackPaneTest.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("demo");
        primaryStage.show();
    }

    private StackPane buildStackPane() {
        StackPane stackPane = new StackPane();
        Button textButton = new Button("adsfadfas");
        Button exitButton = new Button();
        stackPane.getStyleClass().add("toolbar-button-container");
        textButton.getStyleClass().add("toolbar-button");
        exitButton.getStyleClass().add("toolbar-button-close");
        FontAwesomeIconView icon = new FontAwesomeIconView();
        icon.getStyleClass().add("toolbar-button-close-graphic");
        exitButton.setGraphic(icon);
        stackPane.getChildren().addAll(textButton, exitButton);
        StackPane.setAlignment(exitButton, Pos.TOP_RIGHT);
        exitButton.setVisible(false);
        exitButton.setOnAction(e->textButton.fireEvent(new WindowEvent(stackPane.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST)));
        stackPane.setOnMouseExited(e->exitButton.setVisible(false));
        stackPane.setOnMouseEntered(e->exitButton.setVisible(true));
        exitButton.setOnMouseEntered(e->{
            exitButton.setVisible(true);
            textButton.fireEvent(new MouseEvent(MouseEvent.MOUSE_ENTERED, 0,
                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null));
        });
        exitButton.setOnMouseExited(e->{
            exitButton.setVisible(false);
            textButton.fireEvent(new MouseEvent(MouseEvent.MOUSE_EXITED, 0,
                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null));
        });
        return stackPane;
    }
}
