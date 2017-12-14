package ldh.form.model.structure;


import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;

import java.util.List;

public abstract class SelectionField<V, F extends SelectionField<V, F>> extends Field<F> {

    final ListProperty<V> items;

    SelectionField(ListProperty<V> items) {
        this.items = items;
    }

    abstract boolean validateRequired();

    boolean validate(List<String> errorMessages) {
        if (!validateRequired()) {
            if (isI18N() && requiredErrorKey.get() != null) {
                this.errorMessageKeys.setAll(requiredErrorKey.get());
            } else if (requiredError.get() != null) {
                this.errorMessages.setAll(requiredError.get());
            }

            valid.set(false);
            return false;
        }

        if (isI18N()) {
            errorMessageKeys.setAll(errorMessages);
        } else {
            this.errorMessages.setAll(errorMessages);
        }

        if (errorMessages.size() > 0) {
            valid.set(false);
            return false;
        }

        valid.set(true);
        return true;
    }

    public ObservableList getItems() {
        return items.get();
    }

    public ListProperty<V> itemsProperty() {
        return items;
    }

}
