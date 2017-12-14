package ldh.form.model.structure;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ldh.form.model.util.BindingMode;
import ldh.form.model.validator.ValidationResult;
import ldh.form.model.validator.Validator;
import ldh.form.view.control.SimpleListViewControl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MultiSelectionField<V> extends SelectionField<V, MultiSelectionField<V>> {

    private final ListProperty<V> persistentSelection = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ListProperty<V> selection = new SimpleListProperty<>(FXCollections.observableArrayList());

    private final List<Validator<ObservableList<V>>> validators = new ArrayList<>();

    MultiSelectionField(ListProperty<V> items, List<Integer> selection) {
        super(items);

        selection.forEach(i -> {
            if (i < this.items.size() && i >= 0) {
                this.selection.add(this.items.get(i));
            }
        });

        persistentSelection.addAll(this.selection.getValue());

        changed.bind(Bindings.createBooleanBinding(() -> !persistentSelection.equals(this.selection), persistentSelection, this.selection));

        this.selection.addListener((observable, oldValue, newValue) -> validate());

        items.addListener((observable, oldValue, newValue) -> {
            this.selection.clear();
            persistentSelection.clear();
        });

        renderer = new SimpleListViewControl<>();
    }

    public MultiSelectionField<V> items(List<V> newValue, List<Integer> newSelection) {
        items.setAll(newValue);

        newSelection.forEach(i -> selection.add(items.get(i)));
        persistentSelection.setAll(selection.getValue());

        return this;
    }

    public MultiSelectionField<V> items(List<V> newValue) {
        return this.items(newValue, new ArrayList<>());
    }

    @SafeVarargs
    public final MultiSelectionField<V> validate(Validator<ObservableList<V>>... newValue) {
        validators.clear();
        Collections.addAll(validators, newValue);
        validate();

        return this;
    }

    public MultiSelectionField<V> select(int index) {
        if (index < items.size() && index > -1 && !selection.contains(items.get(index))) {
            selection.add(items.get(index));
        }

        return this;
    }

    public MultiSelectionField<V> deselect(int index) {
        if (index < items.size() && selection.contains(items.get(index))) {
            selection.remove(items.get(index));
        }

        return this;
    }

    public MultiSelectionField<V> bind(ListProperty<V> itemsBinding, ListProperty<V> selectionBinding) {
        items.bindBidirectional(itemsBinding);
        persistentSelection.unbindBidirectional(selectionBinding);

        return this;
    }

    public MultiSelectionField<V> unbind(ListProperty<V> itemsBinding, ListProperty<V> selectionBinding) {
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

        persistentSelection.setAll(selection.getValue());
    }

    void reset() {
        if (!hasChanged()) {
            return;
        }

        selection.setAll(persistentSelection.getValue());
    }

    boolean validateRequired() {
        return !isRequired() || selection.size() > 0;
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

    public ObservableList<V> getSelection() {
        return selection.get();
    }

    public ListProperty<V> selectionProperty() {
        return selection;
    }

}
