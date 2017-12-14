package ldh.form.view.control;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import ldh.form.model.structure.StringField;

public class SimpleTextControl extends SimpleControl<StringField> {

    private StackPane stack;

    private TextField editableField;
    private TextArea editableArea;
    private Label readOnlyLabel;
    private Label fieldLabel;

    @Override
    public void initializeParts() {
        super.initializeParts();

        getStyleClass().add("simple-text-control");

        stack = new StackPane();

        editableField = new TextField(field.getValue());
        editableArea = new TextArea(field.getValue());

        readOnlyLabel = new Label(field.getValue());
        fieldLabel = new Label(field.labelProperty().getValue());
        editableField.setPromptText(field.placeholderProperty().getValue());
    }

    @Override
    public void layoutParts() {
        super.layoutParts();

        readOnlyLabel.getStyleClass().add("read-only-label");

        readOnlyLabel.setPrefHeight(26);

        editableArea.getStyleClass().add("simple-textarea");
        editableArea.setPrefRowCount(5);
        editableArea.setPrefHeight(80);
        editableArea.setWrapText(true);

        if (field.isMultiline()) {
            stack.setPrefHeight(80);
            readOnlyLabel.setPrefHeight(80);
        }

        stack.getChildren().addAll(editableField, editableArea, readOnlyLabel);

        stack.setAlignment(Pos.CENTER_LEFT);

        int columns = field.getSpan();

        if (columns < 3) {
            add(fieldLabel, 0, 0, columns, 1);
            add(stack, 0, 1, columns, 1);
        } else {
            add(fieldLabel, 0, 0, 2, 1);
            add(stack, 2, 0, columns - 2, 1);
        }
    }

    @Override
    public void setupBindings() {
        super.setupBindings();

        editableArea.visibleProperty().bind(Bindings.and(field.editableProperty(),
                                                        field.multilineProperty()));
        editableField.visibleProperty().bind(Bindings.and(field.editableProperty(),
                                                        field.multilineProperty().not()));
        readOnlyLabel.visibleProperty().bind(field.editableProperty().not());

        editableField.textProperty().bindBidirectional(field.userInputProperty());
        editableArea.textProperty().bindBidirectional(field.userInputProperty());
        readOnlyLabel.textProperty().bind(field.userInputProperty());
        fieldLabel.textProperty().bind(field.labelProperty());
        editableField.promptTextProperty().bind(field.placeholderProperty());
        editableArea.promptTextProperty().bind(field.placeholderProperty());

        editableArea.managedProperty().bind(editableArea.visibleProperty());
        editableField.managedProperty().bind(editableField.visibleProperty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupValueChangedListeners() {
        super.setupValueChangedListeners();

        field.multilineProperty().addListener((observable, oldValue, newValue) -> {
            stack.setPrefHeight(newValue ? 80 : 0);
            readOnlyLabel.setPrefHeight(newValue ? 80 : 26);
        });

        field.errorMessagesProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(field.isMultiline() ? editableArea : editableField));

        editableField.focusedProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(editableField));
        editableArea.focusedProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(editableArea));
    }

}
