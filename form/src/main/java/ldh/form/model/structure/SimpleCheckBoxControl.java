package ldh.form.model.structure;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ldh.form.view.control.SimpleControl;

import java.util.ArrayList;
import java.util.List;

public class SimpleCheckBoxControl<V> extends SimpleControl<MultiSelectionField<V>> {

    private Label fieldLabel;
    private final List<CheckBox> checkboxes = new ArrayList<>();
    private VBox box;

    @Override
    public void initializeParts() {
        super.initializeParts();

        getStyleClass().add("simple-checkbox-control");

        fieldLabel = new Label(field.labelProperty().getValue());
        box = new VBox();

        createCheckboxes();
    }

    @Override
    public void layoutParts() {
        super.layoutParts();

        int columns = field.getSpan();

        box.setSpacing(5);

        add(fieldLabel, 0,0,2,1);
        add(box, 2, 0, columns - 2,1);
    }

    @Override
    public void setupBindings() {
        super.setupBindings();

        fieldLabel.textProperty().bind(field.labelProperty());
        setupCheckboxBindings();
    }

    @Override
    public void setupValueChangedListeners() {
        super.setupValueChangedListeners();

        field.itemsProperty().addListener((observable, oldValue, newValue) -> {
            createCheckboxes();
            setupCheckboxBindings();
            setupCheckboxEventHandlers();
        });

        field.selectionProperty().addListener((observable, oldValue, newValue) -> {
            for (int i = 0; i < checkboxes.size(); i++) {
                checkboxes.get(i).setSelected(field.getSelection().contains(field.getItems().get(i)));
            }
        });

        field.errorMessagesProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(box, checkboxes.get(checkboxes.size() - 1)));
        field.tooltipProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(box, checkboxes.get(checkboxes.size() - 1)));

        for (int i = 0; i < checkboxes.size(); i++) {
            checkboxes.get(i).focusedProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(box, checkboxes.get(checkboxes.size() - 1)));
        }
    }

    @Override
    public void setupEventHandlers() {
        box.setOnMouseEntered(event -> toggleTooltip(box, checkboxes.get(checkboxes.size() - 1)));
        box.setOnMouseExited(event -> toggleTooltip(box, checkboxes.get(checkboxes.size() - 1)));
        setupCheckboxEventHandlers();
    }

    private void createCheckboxes() {
        box.getChildren().clear();
        checkboxes.clear();

        for (int i = 0; i < field.getItems().size(); i++) {
            CheckBox cb = new CheckBox();

            cb.setText(field.getItems().get(i).toString());
            cb.setSelected(field.getSelection().contains(field.getItems().get(i)));

            checkboxes.add(cb);
        }

        box.getChildren().addAll(checkboxes);
    }

    private void setupCheckboxBindings() {
        for (CheckBox checkbox : checkboxes) {
            checkbox.disableProperty().bind(field.editableProperty().not());
        }
    }

    private void setupCheckboxEventHandlers() {
        for (int i = 0; i < checkboxes.size(); i++) {
            final int j = i;

            checkboxes.get(i).setOnAction(event -> {
                if (checkboxes.get(j).isSelected()) {
                    field.select(j);
                } else {
                    field.deselect(j);
                }
            });
        }
    }

}
