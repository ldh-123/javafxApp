package ldh.form.model.structure;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import ldh.form.view.control.SimpleDoubleControl;

public class DoubleField extends DataField<DoubleProperty, Double, DoubleField> {


    DoubleField(SimpleDoubleProperty valueProperty, SimpleDoubleProperty persistentValueProperty) {
        super(valueProperty, persistentValueProperty);

        valueTransformer = Double::parseDouble;
        renderer = new SimpleDoubleControl();

        userInput.set(String.valueOf(value.getValue()));
    }

}
