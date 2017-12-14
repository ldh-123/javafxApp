package ldh.form.model.structure;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import ldh.form.view.control.SimpleBooleanControl;

public class BooleanField extends DataField<BooleanProperty, Boolean, BooleanField> {

    BooleanField(SimpleBooleanProperty valueProperty, SimpleBooleanProperty persistentValueProperty) {
        super(valueProperty, persistentValueProperty);

        valueTransformer = Boolean::parseBoolean;
        renderer = new SimpleBooleanControl();

        userInput.set(String.valueOf(value.getValue()));
    }

    @Override
    protected boolean validateRequired(String newValue) {
        return !isRequired() || (isRequired() && newValue.equals("true"));
    }

}
