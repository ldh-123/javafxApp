package searchbox;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class SearchBox extends Region {

    private TextField textBox;
    private Button clearButton;

    public SearchBox() {
        setId("SearchBox");
        getStyleClass().add("search-box");
        setMinHeight(24);
        setPrefSize(200, 24);
        setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        textBox = new TextField();
        textBox.setPromptText("Search");
        clearButton = new Button();
        clearButton.setVisible(false);
        getChildren().addAll(textBox, clearButton);
        clearButton.setOnAction((ActionEvent actionEvent) -> {
            textBox.setText("");
            textBox.requestFocus();
        });
        textBox.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            clearButton.setVisible(textBox.getText().length() != 0);
        });
    }

    @Override
    protected void layoutChildren() {
        textBox.resize(getWidth(), getHeight());
        clearButton.resizeRelocate(getWidth() - 18, 6, 12, 13);
    }

}
