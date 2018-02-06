package ldh.fx.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ldh.common.PageResult;
import ldh.fx.component.CrudGridTable;
import ldh.fx.component.PageablePane;
import ldh.fx.component.model.GridTableModel;
import ldh.fx.util.JsonParse;

import java.util.ArrayList;
import java.util.List;

public class TableTest  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridTableModel gridTableModel = JsonParse.parseTableModel(TableTest.class.getResourceAsStream("/data/StudentTable.json"));
        CrudGridTable crudGridTable = new CrudGridTable(gridTableModel);

        List<Student> studentList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Student s1 = new Student();
            s1.setName("student_" + i);
            studentList.add(s1);
        }
        crudGridTable.setData(new PageResult(20, studentList));

        VBox vbox = new VBox(crudGridTable);
        vbox.setPadding(new Insets(20));
        Scene scene = new Scene(vbox, 1200, 600);
        scene.getStylesheets().add("/component/GridTable.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
