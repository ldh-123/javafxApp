package ldh.form.view.control;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import ldh.form.model.structure.SingleSelectionField;

public class SimpleComboBoxControl<V> extends SimpleControl<SingleSelectionField<V>> {

    private Label fieldLabel;
    private ComboBox<V> comboBox;
    private Label readOnlyLabel;
    private StackPane stack;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeParts() {
        super.initializeParts();

        getStyleClass().add("simple-select-control");

        fieldLabel = new Label(field.labelProperty().getValue());
        readOnlyLabel = new Label();
        stack = new StackPane();

        comboBox = new ComboBox<>(field.getItems());

        comboBox.getSelectionModel().select(field.getItems().indexOf(field.getSelection()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void layoutParts() {
        super.layoutParts();

        int columns = field.getSpan();
        readOnlyLabel.getStyleClass().add("read-only-label");

        comboBox.setMaxWidth(Double.MAX_VALUE);
        comboBox.setVisibleRowCount(4);

        stack.setAlignment(Pos.CENTER_LEFT);
        stack.getChildren().addAll(comboBox, readOnlyLabel);

        add(fieldLabel, 0,0,2,1);
        add(stack, 2, 0, columns - 2, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupBindings() {
        super.setupBindings();

        fieldLabel.textProperty().bind(field.labelProperty());
        comboBox.visibleProperty().bind(field.editableProperty());
        readOnlyLabel.visibleProperty().bind(field.editableProperty().not());
        readOnlyLabel.textProperty().bind(comboBox.valueProperty().asString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupValueChangedListeners() {
        super.setupValueChangedListeners();

        field.itemsProperty().addListener((observable, oldValue, newValue) -> comboBox.setItems(field.getItems()));

        field.selectionProperty().addListener((observable, oldValue, newValue) -> {
            if (field.getSelection() != null) {
                comboBox.getSelectionModel().select(field.getItems().indexOf(field.getSelection()));
            } else {
                comboBox.getSelectionModel().clearSelection();
            }
        });

        field.errorMessagesProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(comboBox));
        field.tooltipProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(comboBox));
        comboBox.focusedProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(comboBox));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupEventHandlers() {
        comboBox.setOnMouseEntered(event -> toggleTooltip(comboBox));
        comboBox.setOnMouseExited(event -> toggleTooltip(comboBox));

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> field.select(comboBox.getSelectionModel().getSelectedIndex()));
    }

}
