package ldh.form.view.control;


import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ldh.form.model.structure.BooleanField;


public class SimpleBooleanControl extends SimpleControl<BooleanField> {

    private Label fieldLabel;
    private CheckBox checkBox;
    private VBox container;

    @Override
    public void initializeParts() {
        super.initializeParts();

        getStyleClass().add("simple-boolean-control");

        fieldLabel = new Label(field.labelProperty().getValue());
        checkBox = new CheckBox();
        container = new VBox();
        checkBox.setSelected(field.getValue());
    }

    @Override
    public void layoutParts() {
        super.layoutParts();

        container.getChildren().add(checkBox);

        add(fieldLabel, 0,0,2,1);
        add(container, 2, 0, field.getSpan() - 2,1);
    }

    @Override
    public void setupBindings() {
        super.setupBindings();

        checkBox.disableProperty().bind(field.editableProperty().not());
        fieldLabel.textProperty().bind(field.labelProperty());
    }

    @Override
    public void setupValueChangedListeners() {
        super.setupValueChangedListeners();
        field.userInputProperty().addListener((observable, oldValue, newValue) -> checkBox.setSelected(Boolean.parseBoolean(field.getUserInput())));

        field.errorMessagesProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(checkBox));
        field.tooltipProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(checkBox));

        checkBox.focusedProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(checkBox));
    }

    @Override
    public void setupEventHandlers() {
        setOnMouseEntered(event -> toggleTooltip(checkBox));
        setOnMouseExited(event -> toggleTooltip(checkBox));

        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> field.userInputProperty().setValue(String.valueOf(newValue)));
    }

}
