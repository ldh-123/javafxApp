package ldh.fx.form;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;

/**
 * Created by ldh on 2018/1/22.
 */
public class FormPane extends Control {

    private ObservableList<Row> rows = FXCollections.observableArrayList();

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
    }

    public final ObservableList<Row> getRows() {
        return rows;
    }
}
