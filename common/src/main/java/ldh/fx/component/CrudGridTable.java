package ldh.fx.component;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ldh.common.Pagination;
import ldh.common.json.JsonViewFactory;
import ldh.fx.StageUtil;
import ldh.fx.component.function.CrudForm;
import ldh.fx.component.model.GridTableModel;
import ldh.fx.component.model.PathModel;
import ldh.fx.util.DialogUtil;
import ldh.fx.util.JsonHttpUtil;
import ldh.fx.util.MethodUtil;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by ldh on 2017/4/14.
 */
public class CrudGridTable<D> extends GridTable<D> {

    public CrudGridTable() {
        super();
    }

    public CrudGridTable(GridTableModel gridTableModel) {
        super(gridTableModel);
    }

    @Override
    protected void createFunctionBtn(ToolBar toolBar) {
        String url = gridTableModel.getPath("addFxmlPath").getUrl();
        Glyph addGraphic = Glyph.create( "FontAwesome|" + FontAwesome.Glyph.PLUS_SQUARE.name()).size(18).color(Color.CORNFLOWERBLUE);
        Button addBtn = new Button("", addGraphic);
        addBtn.setOnAction(e->addDialog(null, url, "增加数据"));
        Glyph editGraphic = Glyph.create( "FontAwesome|" + FontAwesome.Glyph.EDIT.name()).size(18).color(Color.CORNFLOWERBLUE);
        Button editBtn = new Button("", editGraphic);
        String editFxmlUrl = gridTableModel.getPath("editFxmlPath").getUrl();
        editBtn.setOnAction(e->editDialog(editFxmlUrl, "修改数据"));
        Glyph removeGraphic = Glyph.create( "FontAwesome|" + FontAwesome.Glyph.MINUS_SQUARE.name()).size(18).color(Color.CORNFLOWERBLUE);
        Button removeBtn = new Button("", removeGraphic);
        removeBtn.setOnAction(e->deleteDialog(url, "删除数据"));
        toolBar.getItems().addAll(addBtn, editBtn, removeBtn);
    }

    public void editDialog(String fxml, String title) {
        D data = getSelectData();
        if (data == null) return;
        FXMLLoader loader = openDialog(fxml, title);
        if (loader == null) return;
        Object t = loader.getController();
        if (t instanceof CrudForm) {
            CrudForm editForm = (CrudForm) t;
            editForm.setInitData(data);
            editForm.setLoadData(this);
            editForm.setPath(this.gridTableModel);
        }
    }

    public <T> void addDialog(T data, String fxml, String title) {
        FXMLLoader loader = openDialog(fxml, title);
        if (loader == null) return;
        Object t = loader.getController();
        if (t instanceof CrudForm) {
            CrudForm editForm = (CrudForm) t;
            editForm.setInitData(data);
            editForm.setLoadData(this);
            editForm.setPath(this.gridTableModel);
        }
    }

    public void deleteDialog(String fxml, String title) {
        D data = getSelectData();
        if (data == null) return;
        try {
            PathModel pathModel = this.gridTableModel.getPath("deleteDataUrl");
            String url = buildDeleteUrl(pathModel, data);
            JsonHttpUtil.get(JsonViewFactory.create(), url, Void.class);
        } catch (Exception e) {
            DialogUtil.show(Alert.AlertType.ERROR, "错误", e.getMessage());
            return;
        }
        load(new Pagination(1, 10));
    }

    protected String buildDeleteUrl(PathModel pathModel, D data) {
        if (pathModel.getProperty() != null && !pathModel.getProperty().equals("")) {
            String deleteUrl = pathModel.getUrl();
            Method method = MethodUtil.getMethod(data.getClass(), "get" + firstUpper(pathModel.getProperty()));
            try {
                Object value = method.invoke(data, new Object[]{});
                return String.format(deleteUrl, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pathModel.getUrl();
    }

    private String firstUpper(String property) {
        return property.substring(0, 1).toUpperCase() + property.substring(1);
    }

    public FXMLLoader openDialog(String fxml, String title) {
        GridPane root = null;
        try {
            Stage stage = new Stage();
            stage.initOwner(StageUtil.STAGE);
            stage.initModality(Modality.WINDOW_MODAL);

            FXMLLoader loader = new FXMLLoader(CrudGridTable.class.getResource(fxml));
            root = loader.load();
            Scene scene = new Scene(root);
            root.prefHeightProperty().bind(stage.heightProperty());
            root.prefWidthProperty().bind(stage.widthProperty());
            stage.setFullScreen(false);
            stage.setResizable(true);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
            return  loader;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public D getSelectData() {
        D data = tableView.getSelectionModel().getSelectedItem();
        if (data == null) {
            DialogUtil.show(Alert.AlertType.ERROR, "错误提示", "请选择一行");
            return null;
        }
        return data;
    }

    protected void deleteData(String url) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                this.updateMessage("正在删除数据");
                try {
                    JsonHttpUtil.get(JsonViewFactory.create(), url, Void.class);
                } catch (Exception e) {
                    DialogUtil.show(Alert.AlertType.ERROR, "删除数据失败", e.getMessage());
                    return null;
                }

                load(null);
                this.updateMessage("删除数据成功");
                return null;
            }
        };
        new Thread(task).start();
    }
}
