package ldh.form.model.structure;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ldh.form.view.control.SimpleTextControl;

public class StringField extends DataField<StringProperty, String, StringField> {

    private final BooleanProperty multiline = new SimpleBooleanProperty(false);

    StringField(SimpleStringProperty valueProperty, SimpleStringProperty persistentValueProperty) {
        super(valueProperty, persistentValueProperty);

        valueTransformer = String::valueOf;
        renderer = new SimpleTextControl();

        userInput.set(String.valueOf(value.getValue()));
    }

    public StringField multiline(boolean newValue) {
        multiline.setValue(newValue);
        return this;
    }

    public boolean isMultiline() {
        return multiline.get();
    }

    public BooleanProperty multilineProperty() {
        return multiline;
    }

}
