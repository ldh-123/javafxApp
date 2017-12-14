package ldh.form.model.structure;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ldh.form.model.util.BindingMode;
import ldh.form.model.util.TranslationService;
import ldh.form.view.control.SimpleControl;
import ldh.form.view.util.ColSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class Field<F extends Field> {


    private final StringProperty label = new SimpleStringProperty("");
    private final StringProperty labelKey = new SimpleStringProperty("");

    private final StringProperty tooltip = new SimpleStringProperty("");
    private final StringProperty tooltipKey = new SimpleStringProperty("");

    private final StringProperty placeholder = new SimpleStringProperty("");
    private final StringProperty placeholderKey = new SimpleStringProperty("");

    final StringProperty requiredErrorKey = new SimpleStringProperty("");
    final StringProperty requiredError = new SimpleStringProperty("");
    private final BooleanProperty required = new SimpleBooleanProperty(false);
    private final BooleanProperty editable = new SimpleBooleanProperty(true);

    final BooleanProperty valid = new SimpleBooleanProperty(true);
    final BooleanProperty changed = new SimpleBooleanProperty(false);

    private final StringProperty id = new SimpleStringProperty(UUID.randomUUID().toString());
    private final ListProperty<String> styleClass = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final IntegerProperty span = new SimpleIntegerProperty(12);

    final ListProperty<String> errorMessages = new SimpleListProperty<>(FXCollections.observableArrayList());
    final ListProperty<String> errorMessageKeys = new SimpleListProperty<>(FXCollections.observableArrayList());

    TranslationService translationService;

    SimpleControl<F> renderer;

    final InvalidationListener bindingModeListener = (observable) -> {
        if (validate()) {
            persist();
        }
    };

    Field() {
        labelKey.addListener((observable, oldValue, newValue) -> label.setValue(translationService.translate(newValue)));

        tooltipKey.addListener((observable, oldValue, newValue) -> tooltip.setValue(translationService.translate(newValue)));

        placeholderKey.addListener((observable, oldValue, newValue) -> placeholder.setValue(translationService.translate(newValue)));

        requiredErrorKey.addListener((observable, oldValue, newValue) -> validate());

        errorMessageKeys.addListener((observable, oldValue, newValue) -> errorMessages.setAll(errorMessageKeys.stream()
                .map(s -> translationService.translate(s))
                .collect(Collectors.toList())));
    }

    public static StringField ofStringType(String defaultValue) {
        return new StringField(new SimpleStringProperty(defaultValue), new SimpleStringProperty(defaultValue));
    }

    public static StringField ofStringType(StringProperty binding) {
        return new StringField(new SimpleStringProperty(binding.getValue()), new SimpleStringProperty(binding.getValue())).bind(binding);
    }

    public static DoubleField ofDoubleType(double defaultValue) {
        return new DoubleField(new SimpleDoubleProperty(defaultValue), new SimpleDoubleProperty(defaultValue));
    }

    public static DoubleField ofDoubleType(DoubleProperty binding) {
        return new DoubleField(new SimpleDoubleProperty(binding.getValue()), new SimpleDoubleProperty(binding.getValue())).bind(binding);
    }

    public static IntegerField ofIntegerType(int defaultValue) {
        return new IntegerField(new SimpleIntegerProperty(defaultValue), new SimpleIntegerProperty(defaultValue));
    }

    public static IntegerField ofIntegerType(IntegerProperty binding) {
        return new IntegerField(new SimpleIntegerProperty(binding.getValue()), new SimpleIntegerProperty(binding.getValue())).bind(binding);
    }

    public static BooleanField ofBooleanType(boolean defaultValue) {
        return new BooleanField(new SimpleBooleanProperty(defaultValue), new SimpleBooleanProperty(defaultValue));
    }

    public static BooleanField ofBooleanType(BooleanProperty binding) {
        return new BooleanField(new SimpleBooleanProperty(binding.getValue()), new SimpleBooleanProperty(binding.getValue())).bind(binding);
    }

    public static <T> MultiSelectionField<T> ofMultiSelectionType(List<T> items, List<Integer> selection) {
        return new MultiSelectionField<>(new SimpleListProperty<>(FXCollections.observableArrayList(items)), selection);
    }

    public static <T> MultiSelectionField<T> ofMultiSelectionType(List<T> items) {
        return new MultiSelectionField<>(new SimpleListProperty<>(FXCollections.observableArrayList(items)), new ArrayList<>());
    }

    public static <T> MultiSelectionField ofMultiSelectionType(ListProperty<T> itemsBinding, ListProperty<T> selectionBinding) {
        return new MultiSelectionField<>(new SimpleListProperty<>(itemsBinding.getValue()), new ArrayList<>(selectionBinding.getValue().stream().map(t -> itemsBinding.getValue().indexOf(t)).collect(Collectors.toList()))).bind(itemsBinding, selectionBinding);
    }

    public static <T> SingleSelectionField<T> ofSingleSelectionType(List<T> items, int selection) {
        return new SingleSelectionField<>(new SimpleListProperty<>(FXCollections.observableArrayList(items)), selection);
    }

    public static <T> SingleSelectionField<T> ofSingleSelectionType(List<T> items) {
        return new SingleSelectionField<>(new SimpleListProperty<>(FXCollections.observableArrayList(items)), -1);
    }

    public static <T> SingleSelectionField<T> ofSingleSelectionType(ListProperty<T> itemsBinding, ObjectProperty<T> selectionBinding) {
        return new SingleSelectionField<>(new SimpleListProperty<>(itemsBinding.getValue()), itemsBinding.indexOf(selectionBinding.getValue())).bind(itemsBinding, selectionBinding);
    }

    public F required(boolean newValue) {
        required.set(newValue);
        validate();

        return (F) this;
    }

    public F required(String errorMessage) {
        required.set(true);

        if (isI18N()) {
            requiredErrorKey.set(errorMessage);
        } else {
            requiredError.set(errorMessage);
        }

        validate();

        return (F) this;
    }

    public F editable(boolean newValue) {
        editable.set(newValue);
        return (F) this;
    }

    public F label(String newValue) {
        if (isI18N()) {
            labelKey.set(newValue);
        } else {
            label.set(newValue);
        }

        return (F) this;
    }

    public F tooltip(String newValue) {
        if (isI18N()) {
            tooltipKey.set(newValue);
        } else {
            tooltip.set(newValue);
        }

        return (F) this;
    }

    public F placeholder(String newValue) {
        if (isI18N()) {
            placeholderKey.set(newValue);
        } else {
            placeholder.set(newValue);
        }

        return (F) this;
    }

    public F id(String newValue) {
        id.set(newValue);
        return (F) this;
    }

    public F styleClass(String... newValue) {
        styleClass.setAll(newValue);
        return (F) this;
    }

    public F render(SimpleControl<F> newValue) {
        renderer = newValue;
        return (F) this;
    }

    public F span(int newValue) {
        span.setValue(newValue);
        return (F) this;
    }

    public F span(ColSpan newValue) {
        span.setValue(newValue.valueOf());
        return (F) this;
    }

    abstract void setBindingMode(BindingMode newValue);

    abstract void persist();

    abstract void reset();

    /**
     * This internal method is called by the containing section when a new
     * translation has been added to the form.
     *
     * @param newValue
     *              The new service to use for translating translatable values.
     */
    void translate(TranslationService newValue) {
        translationService = newValue;

        if (!isI18N()) {
            return;
        }

        updateElement(label, labelKey);
        updateElement(tooltip, tooltipKey);
        updateElement(placeholder, placeholderKey);
        updateElement(requiredError, requiredErrorKey);

        // Validation results are handled separately as they use a somewhat
        // more complex structure.

        validate();
    }

    void updateElement(StringProperty displayProperty, StringProperty keyProperty) {

        if ((keyProperty.get() == null || keyProperty.get().isEmpty()) && !displayProperty.get().isEmpty()) {
            keyProperty.setValue(displayProperty.get());
        } else if (!keyProperty.get().isEmpty()) {
            displayProperty.setValue(translationService.translate(keyProperty.get()));
        }
    }

    abstract boolean validate();

    public String getPlaceholder() {
        return placeholder.get();
    }

    public StringProperty placeholderProperty() {
        return placeholder;
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public String getTooltip() {
        return tooltip.get();
    }

    public StringProperty tooltipProperty() {
        return tooltip;
    }

    public boolean isValid() {
        return valid.get();
    }

    public BooleanProperty validProperty() {
        return valid;
    }

    public boolean hasChanged() {
        return changed.get();
    }

    public BooleanProperty changedProperty() {
        return changed;
    }

    public boolean isRequired() {
        return required.get();
    }

    public BooleanProperty requiredProperty() {
        return required;
    }

    public boolean isEditable() {
        return editable.get();
    }

    public BooleanProperty editableProperty() {
        return editable;
    }

    public boolean isI18N() {
        return translationService != null;
    }

    public int getSpan() {
        return span.get();
    }

    public IntegerProperty spanProperty() {
        return span;
    }

    public String getID() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public ObservableList<String> getStyleClass() {
        return styleClass.get();
    }

    public ListProperty<String> styleClassProperty() {
        return styleClass;
    }

    public SimpleControl<F> getRenderer() {
        return renderer;
    }

    public List<String> getErrorMessages() {
        return errorMessages.get();
    }

    public ListProperty<String> errorMessagesProperty() {
        return errorMessages;
    }

}

