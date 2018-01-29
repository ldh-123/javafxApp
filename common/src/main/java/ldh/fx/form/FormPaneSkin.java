package ldh.fx.form;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ldh on 2018/1/22.
 */
public class FormPaneSkin extends SkinBase<FormPane> {

    private static final Logger logger = Logger.getLogger(FormPaneSkin.class.getName());

    private StackPane stackPane = new StackPane();
    private GridPane verticalPane = new GridPane();
    private GridPane hThirdPane = new GridPane();
    private GridPane hSecondPane = new GridPane();
    private GridPane hSecondVPane = new GridPane();

    public FormPaneSkin(FormPane formPane) {
        super(formPane);
        stackPane.getChildren().addAll(verticalPane, hThirdPane, hSecondPane, hSecondVPane);
        this.getChildren().add(stackPane);
        stackPane.setStyle("-fx-background-color: grey");
        stackPane.widthProperty().addListener((l,o,n)->{
            verticalPane.setPrefWidth(n.doubleValue());
            hThirdPane.setPrefWidth(n.doubleValue());
            hSecondPane.setPrefWidth(n.doubleValue());
            hSecondVPane.setPrefWidth(n.doubleValue());
        });
        verticalPane.widthProperty().addListener((l, o, n)->{
            System.out.println("width:" + n.doubleValue());
        });
    }

    @Override protected void layoutChildren(final double x, final double y, final double w, final double h) {
        FormPane formPane = getSkinnable();
        FormType formType = formPane.getFormType();
        formType = formType == null ? FormType.Vertical : formType;
        if (formType == FormType.Vertical) {
            buildVerticalPane();
        } else if (formType == FormType.Horizontal_Third) {
            display(hThirdPane, verticalPane, hSecondPane, hSecondVPane);
        } else if (formType == FormType.Horizontal_second) {
            display(hThirdPane, hThirdPane, verticalPane, hSecondVPane);
        } else if (formType == FormType.Horizontal_second_v) {
            display(hSecondVPane, hThirdPane, hSecondPane, verticalPane);
        }
        this.layoutInArea(stackPane, x, y, w, h, 0, HPos.CENTER, VPos.CENTER);
    }

    private void buildVerticalPane() {
        display(verticalPane, hThirdPane, hSecondPane, hSecondVPane);
        FormPane formPane = getSkinnable();
        ObservableList<Row> rows = formPane.getRows();
        int rowIndex = 0;
        int idx = 0;
        verticalPane.getChildren().clear();
        ColumnConstraints column1 = new ColumnConstraints(250);
        column1.setHgrow(Priority.ALWAYS);
        verticalPane.getColumnConstraints().add(column1);
        for (Row row : rows) {
            for (Node node : row.getNodes()) {
                GridPane.setRowIndex(node, rowIndex + idx++);
                GridPane.setColumnIndex(node, 0);
                verticalPane.getChildren().add(node);
            }
            Separator separator = new Separator();
            separator.setOrientation(Orientation.HORIZONTAL);
            verticalPane.getChildren().add(separator);
            GridPane.setRowIndex(separator, rowIndex + idx++);
            GridPane.setColumnIndex(separator, 0);
            rowIndex++;
        }
    }

    private void display(Pane showPane, Pane... hiddenPane) {
        showPane.setVisible(true);
        for (Pane pane : hiddenPane) {
            pane.setVisible(false);
        }
    }

}
