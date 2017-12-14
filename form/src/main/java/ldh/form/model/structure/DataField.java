package ldh.form.model.structure;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ldh.form.model.util.BindingMode;
import ldh.form.model.util.TranslationService;
import ldh.form.model.util.ValueTransformer;
import ldh.form.model.validator.ValidationResult;
import ldh.form.model.validator.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class DataField<P extends Property, V, F extends Field> extends Field<F> {

    final P value;
    private final P persistentValue;
    final StringProperty userInput = new SimpleStringProperty("");

    private final List<Validator<V>> validators = new ArrayList<>();

    ValueTransformer<V> valueTransformer;

    private final StringProperty formatErrorKey = new SimpleStringProperty("");
    private final StringProperty formatError = new SimpleStringProperty("");

    private final InvalidationListener externalBindingListener = (observable) -> userInput.setValue(((P) observable).getValue().toString());

    DataField(P valueProperty, P persistentValueProperty) {
        value = valueProperty;
        persistentValue = persistentValueProperty;

        changed.bind(Bindings.createBooleanBinding(() -> !String.valueOf(persistentValue.getValue()).equals(userInput.getValue()), userInput, persistentValue));

        formatErrorKey.addListener((observable, oldValue, newValue) -> formatError.setValue(translationService.translate(newValue)));

        userInput.addListener((observable, oldValue, newValue) -> {
            if (validate()) {
                value.setValue(valueTransformer.transform(newValue));
            }
        });
    }

    public F format(ValueTransformer<V> newValue) {
        valueTransformer = newValue;
        validate();
        return (F) this;
    }

    public F format(ValueTransformer<V> newValue, String errorMessage) {
        valueTransformer = newValue;

        if (isI18N()) {
            formatErrorKey.set(errorMessage);
        } else {
            formatError.set(errorMessage);
        }

        validate();
        return (F) this;
    }

    public F format(String errorMessage) {
        if (isI18N()) {
            formatErrorKey.set(errorMessage);
        } else {
            formatError.set(errorMessage);
        }

        validate();
        return (F) this;
    }

    @SafeVarargs
    public final F validate(Validator<V>... newValue) {
        validators.clear();
        Collections.addAll(validators, newValue);
        validate();

        return (F) this;
    }

    public F bind(P binding) {
        persistentValue.bindBidirectional(binding);
        binding.addListener(externalBindingListener);

        return (F) this;
    }

    public F unbind(P binding) {
        persistentValue.unbindBidirectional(binding);
        binding.removeListener(externalBindingListener);

        return (F) this;
    }

    public void setBindingMode(BindingMode newValue) {
        if (BindingMode.CONTINUOUS.equals(newValue)) {
            value.addListener(bindingModeListener);
        } else {
            value.removeListener(bindingModeListener);
        }
    }

    void persist() {
        if (!isValid()) {
            return;
        }

        persistentValue.setValue(value.getValue());
    }

    void reset() {
        if (!hasChanged()) {
            return;
        }

        userInput.setValue(String.valueOf(persistentValue.getValue()));
    }

    protected boolean validateRequired(String newValue) {
        return !isRequired() || (isRequired() && !newValue.isEmpty());
    }

    boolean validate() {
        String newValue = userInput.getValue();

        if (!validateRequired(newValue)) {
            if (isI18N() && requiredErrorKey.get() != null) {
                errorMessageKeys.setAll(requiredErrorKey.get());
            } else if (requiredError.get() != null) {
                errorMessages.setAll(requiredError.get());
            }

            valid.set(false);
            return false;
        }

        V transformedValue;

        // Attempt a transformation from String to the field's underlying type.

        try {
            transformedValue = valueTransformer.transform(newValue);
        } catch (Exception e) {
            if (isI18N() && !formatErrorKey.get().isEmpty()) {
                errorMessageKeys.setAll(formatErrorKey.get());
            } else if (!formatError.get().isEmpty()) {
                errorMessages.setAll(formatError.get());
            }

            valid.set(false);
            return false;
        }

        // Check all validation rules and collect any error messages.

        List<String> errorMessages = validators.stream()
                .map(v -> v.validate(transformedValue))
                .filter(r -> !r.getResult())
                .map(ValidationResult::getErrorMessage)
                .collect(Collectors.toList());
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

    void translate(TranslationService service) {
        super.translate(service);

        updateElement(formatError, formatErrorKey);
        validate();
    }

    public String getUserInput() {
        return userInput.get();
    }

    public StringProperty userInputProperty() {
        return userInput;
    }

    public V getValue() {
        return (V) value.getValue();
    }

    public P valueProperty() {
        return value;
    }

}
