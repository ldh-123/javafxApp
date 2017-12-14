package ldh.form.model.structure;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import ldh.form.view.control.SimpleIntegerControl;

public class IntegerField extends DataField<IntegerProperty, Integer, IntegerField> {

    IntegerField(SimpleIntegerProperty valueProperty, SimpleIntegerProperty persistentValueProperty) {
        super(valueProperty, persistentValueProperty);

        valueTransformer = Integer::parseInt;
        renderer = new SimpleIntegerControl();

        userInput.set(String.valueOf(value.getValue()));
    }

}
