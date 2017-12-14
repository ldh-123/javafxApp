package ldh.form.model.structure;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ldh.form.model.util.BindingMode;
import ldh.form.model.validator.ValidationResult;
import ldh.form.model.validator.Validator;
import ldh.form.view.control.SimpleComboBoxControl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class SingleSelectionField<V> extends SelectionField<V, SingleSelectionField<V>> {

    private final ObjectProperty<V> persistentSelection = new SimpleObjectProperty<>();
    private final ObjectProperty<V> selection = new SimpleObjectProperty<>();

    private final List<Validator<V>> validators = new ArrayList<>();

    SingleSelectionField(ListProperty<V> items, int selection) {
        super(items);

        if (selection < items.size() && selection >= 0) {
            this.selection.set(this.items.get(selection));
            persistentSelection.setValue(this.selection.getValue());
        }

        changed.bind(Bindings.createBooleanBinding(() -> persistentSelection.get() == null ? this.selection.get() != null : !persistentSelection.get().equals(this.selection.get()), persistentSelection, this.selection));

        this.selection.addListener((observable, oldValue, newValue) -> validate());

        items.addListener((observable, oldValue, newValue) -> {
            this.selection.setValue(null);
            persistentSelection.setValue(null);
        });

        renderer = new SimpleComboBoxControl<>();
    }

    public SingleSelectionField<V> items(List<V> newValue, int newSelection) {
        items.setAll(newValue);

        if (newSelection != -1) {
            this.selection.setValue(items.get(newSelection));
            this.persistentSelection.setValue(this.selection.getValue());
        }

        return this;
    }

    public SingleSelectionField<V> items(List<V> newValue) {
        return this.items(newValue, -1);
    }

    @SafeVarargs
    public final SingleSelectionField<V> validate(Validator<V>... newValue) {
        validators.clear();
        Collections.addAll(validators, newValue);
        validate();

        return this;
    }

    public SingleSelectionField<V> select(int index) {
        if (index == -1) {
            selection.setValue(null);
        } else if (index < items.size() && index > -1 && (selection.get() == null || (selection.get() != null && !selection.get().equals(items.get(index))))) {
            selection.setValue(items.get(index));
        }

        return this;
    }

    public SingleSelectionField<V> deselect() {
        if (selection.get() != null) {
            selection.setValue(null);
        }

        return this;
    }

    public SingleSelectionField<V> bind(ListProperty<V> itemsBinding, ObjectProperty<V> selectionBinding) {
        items.bindBidirectional(itemsBinding);
        persistentSelection.unbindBidirectional(selectionBinding);

        return this;
    }

    public SingleSelectionField<V> unbind(ListProperty<V> itemsBinding, ObjectProperty<V> selectionBinding) {
        items.unbindBidirectional(itemsBinding);
        persistentSelection.unbindBidirectional(selectionBinding);

        return this;
    }

    public void setBindingMode(BindingMode newValue) {
        if (BindingMode.CONTINUOUS.equals(newValue)) {
            selection.addListener(bindingModeListener);
        } else {
            selection.removeListener(bindingModeListener);
        }
    }

    void persist() {
        if (!isValid()) {
            return;
        }

        persistentSelection.setValue(selection.getValue());
    }

    void reset() {
        if (!hasChanged()) {
            return;
        }

        selection.setValue(persistentSelection.getValue());
    }

    boolean validateRequired() {
        return !isRequired() || selection.get() != null;
    }

    boolean validate() {

        // Check all validation rules and collect any error messages.

        List<String> errorMessages = validators.stream()
                .map(v -> v.validate(selection.getValue()))
                .filter(r -> !r.getResult())
                .map(ValidationResult::getErrorMessage)
                .collect(Collectors.toList());

        return super.validate(errorMessages);
    }

    public V getSelection() {
        return selection.get();
    }

    public ObjectProperty<V> selectionProperty() {
        return selection;
    }

}
