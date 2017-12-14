package ldh.form.view.control;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.StackPane;
import ldh.form.model.structure.DataField;

public abstract class SimpleNumberControl<F extends DataField, D extends Number> extends SimpleControl<F> {

    private StackPane stack;

    private Label fieldLabel;
    Spinner<D> editableSpinner;
    private Label readOnlyLabel;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeParts() {
        super.initializeParts();

        stack = new StackPane();

        fieldLabel = new Label();
        readOnlyLabel = new Label();
        editableSpinner = new Spinner<>();
        editableSpinner.setEditable(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void layoutParts() {
        super.layoutParts();

        readOnlyLabel.getStyleClass().add("read-only-label");
        stack.getChildren().addAll(editableSpinner, readOnlyLabel);
        stack.setAlignment(Pos.CENTER_LEFT);

        editableSpinner.setMaxWidth(Double.MAX_VALUE);

        int columns = field.getSpan();

        if (columns < 3) {
            add(fieldLabel, 0, 0, columns, 0);
            add(stack, 0, 1, columns, 1);
        } else {
            add(fieldLabel, 0, 0, 2, 1);
            add(stack, 2, 0, columns - 2, 1);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupBindings() {
        super.setupBindings();

        editableSpinner.visibleProperty().bind(field.editableProperty());
        readOnlyLabel.visibleProperty().bind(field.editableProperty().not());

        editableSpinner.getEditor().textProperty().bindBidirectional(field.userInputProperty());
        readOnlyLabel.textProperty().bind(field.userInputProperty());
        fieldLabel.textProperty().bind(field.labelProperty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupEventHandlers() {
        editableSpinner.getEditor().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    editableSpinner.increment(1);
                    break;
                case DOWN:
                    editableSpinner.decrement(1);
                    break;
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupValueChangedListeners() {
        super.setupValueChangedListeners();
        editableSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(editableSpinner));
    }
}
