package ldh.fx.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ldh.fx.component.CrudGridTable;
import ldh.fx.component.PageablePane;
import ldh.fx.component.model.GridTableModel;
import ldh.fx.util.JsonParse;

public class TableTest  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridTableModel gridTableModel = JsonParse.parseTableModel(TableTest.class.getResourceAsStream("/data/StudentTable.json"));
        CrudGridTable crudGridTable = new CrudGridTable(gridTableModel);

        VBox vbox = new VBox(crudGridTable);
        vbox.setPadding(new Insets(20));
        Scene scene = new Scene(vbox, 1200, 600);
        scene.getStylesheets().add("/component/GridTable.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
