package ldh.descktop.ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.WindowEvent;

import java.util.List;

/**
 * Created by ldh on 2018/1/12.
 */
public class ToolbarButton extends StackPane {

    private Button textButton;
    private Button exitButton = new Button();

    public ToolbarButton(Button textButton) {
        this.textButton = textButton;
        this.getStyleClass().add("toolbar-button-container");
        this.textButton.getStyleClass().add("toolbar-button");
        this.exitButton.getStyleClass().add("toolbar-button-close");
        FontAwesomeIconView icon = new FontAwesomeIconView();
        icon.getStyleClass().add("toolbar-button-close-graphic");
        exitButton.setGraphic(icon);
        this.getChildren().addAll(textButton, exitButton);
        StackPane.setAlignment(exitButton, Pos.TOP_RIGHT);
        exitButton.setVisible(false);
        exitButton.setOnAction(e->textButton.fireEvent(new WindowEvent(this.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST)));
        this.setOnMouseExited(e->exitButton.setVisible(false));
        this.setOnMouseEntered(e->exitButton.setVisible(true));
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
    }

    public Button getButton() {
        return textButton;
    }
}
