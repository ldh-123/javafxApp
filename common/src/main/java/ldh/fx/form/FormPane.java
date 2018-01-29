package ldh.fx.form;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

/**
 * Created by ldh on 2018/1/22.
 */
public class FormPane extends Control {

    private ObservableList<Row> rows = FXCollections.observableArrayList();
    private ObjectProperty<FormType> formTypeProperty = new SimpleObjectProperty<>();

    public FormPane() {
        getStyleClass().setAll("form-pane");
    }

    public FormPane(Row... rows) {
        getStyleClass().setAll("form-pane");

        this.rows.addListener((ListChangeListener<Row>) c -> {
            while (c.next()) {
                for (Row row : c.getRemoved()) {
                    if (row != null && !getRows().contains(row)) {
                        row.setFormPane(null);
                    }
                }

                for (Row row : c.getAddedSubList()) {
                    if (row != null) {
                        row.setFormPane(FormPane.this);
                    }
                }
            }
        });

        if (rows != null) {
            getRows().addAll(rows);
        }

        listen();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new FormPaneSkin(this);
    }

    public final ObservableList<Row> getRows() {
        return rows;
    }

    public final ObjectProperty<FormType> formTypeProperty() {
        return formTypeProperty;
    }

    public FormType getFormType() {
        return formTypeProperty.get();
    }

    public void setFormTypeProperty(FormType formType) {
        formTypeProperty.set(formType);
    }

    private void listen() {
        formTypeProperty.addListener((l, o, n)->{
            this.layoutChildren();
        });
    }
}
