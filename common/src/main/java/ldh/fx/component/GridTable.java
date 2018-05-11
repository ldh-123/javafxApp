package ldh.fx.component;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import ldh.common.PageResult;
import ldh.common.Pageable;
import ldh.common.Pagination;
import ldh.common.json.JsonViewFactory;
import ldh.common.util.ParameterizedTypeUtil;
import ldh.fx.component.factory.CellFactoryRegister;
import ldh.fx.component.factory.CenterTableCellFactory;
import ldh.fx.component.function.CrudForm;
import ldh.fx.component.function.LoadData;
import ldh.fx.component.function.Searchable;
import ldh.fx.component.model.ColumnModel;
import ldh.fx.component.model.GridTableModel;
import ldh.fx.util.DialogUtil;
import ldh.fx.util.JsonHttpUtil;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.spreadsheet.Grid;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ldh on 2017/4/10.
 */
public class GridTable<D> extends StackPane implements LoadData {

    protected GridTableModel gridTableModel;
    protected TableView<D> tableView = new TableView();

    private MaskerPane maskerPane = new MaskerPane();
    private PageablePane pageablePane = new PageablePane();
    private VBox container = new VBox();
    private Region searchContainer;
    private MasterDetailPane masterDetailPane = new MasterDetailPane(Side.TOP);
    private Pageable pageable;
    private Searchable searchable;

    public GridTable() {
        this(new GridTableModel());
    }

    public GridTable(GridTableModel gridTableModel) {
        this.gridTableModel = gridTableModel;
        ToolBar toolBar = createToolBar();
        maskerPane.setVisible(false);
        buildTableView();
        createBottom();
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(tableView, pageablePane);
        searchContainer = buildSearchContainer(masterDetailPane);
        masterDetailPane.setShowDetailNode(false);
        masterDetailPane.setDetailNode(searchContainer);
        masterDetailPane.setMasterNode(vbox);

        container.getChildren().addAll(toolBar, masterDetailPane);
        VBox.setVgrow(masterDetailPane, Priority.ALWAYS);

        this.getChildren().addAll(container, maskerPane);
        this.load(new Pagination(1, 10));

        tableView.getStyleClass().add("table");
        this.getStyleClass().add("grid-table");
        this.getStylesheets().add(GridTable.class.getResource("/component/GridTable.css").toExternalForm());
    }

    private void buildTableView() {
        List<ColumnModel> columnModels = gridTableModel.getColumnModels();
        if (columnModels == null) return;
        for (ColumnModel columnModel : columnModels) {
            TableColumn tableColumn = new TableColumn(columnModel.getText());
            tableColumn.setCellValueFactory(new PropertyValueFactory<D, Object>(columnModel.getProperty()));
            if (columnModel.getWidth() != null) {
                tableColumn.setPrefWidth(columnModel.getWidth());
            }
            if (columnModel.getCellFactory() != null) {
                Callback<TableColumn, TableCell> callback = CellFactoryRegister.getInstance().get(columnModel.getCellFactory());
                tableColumn.setCellFactory(callback);
            } else if (columnModel.getSubProperty() != null) {
                tableColumn.setCellFactory(new CenterTableCellFactory<D, Object>(columnModel.getSubProperty()));
            } else {
                tableColumn.setCellFactory(new CenterTableCellFactory<D, Object>());
            }

            tableView.getColumns().add(tableColumn);
        }
    }

    private void createBottom() {
        pageablePane.setPageSizes(gridTableModel.getPageSizes());
        pageablePane.setPageSize(gridTableModel.getDefaultPageSize());
        pageablePane.setLoadData(this);
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();
        toolBar.getStyleClass().add("table-toolbar");
        createFunctionBtn(toolBar);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        Glyph searchGraphic = Glyph.create( "FontAwesome|" + FontAwesome.Glyph.SEARCH.name()).size(18).color(Color.CORNFLOWERBLUE);
        Button searchBtn = new Button("", searchGraphic);
        searchBtn.setOnAction(e->expandPane());
        toolBar.getItems().addAll(region, searchBtn);
        return toolBar;
    }

    private void expandPane() {
        masterDetailPane.setShowDetailNode(!masterDetailPane.isShowDetailNode());
    }

    protected void createFunctionBtn(ToolBar toolBar) {
    }

    @Override
    public void load(Pageable pageable) {
        if (pageable == null) {
            pageable = this.pageable;
        } else {
            this.pageable = pageable;
        }
        Pageable searchPagination = pageable;
        maskerPane.setVisible(true);

        Task<Void> task = new Task() {
            @Override
            protected Void call() throws Exception {
                Map<String, Object> paramMap = null;
                if (searchable != null) paramMap = searchable.buildParams();
                if (paramMap == null) paramMap = new HashMap();
                initPageable(paramMap, searchPagination);
                Class clazz = Class.forName(gridTableModel.getClazz());
                Type type = ParameterizedTypeUtil.type(PageResult.class, clazz);
                PageResult pageResult = JsonHttpUtil.get(JsonViewFactory.create(), gridTableModel.getPath("loadDataUrl").getUrl(), paramMap, type);
                done();
                Platform.runLater(()->setData(pageResult));
                return null;
            }
        };
        task.setOnSucceeded(event -> {loadingEnd(task);});
        task.setOnFailed(event->{loadingEnd(task);});
        new Thread(task).start();
    }

    private void initPageable(Map<String, Object> paramMap, Pageable searchPagination) {
        if (searchPagination == null) return;
        paramMap.put("pageSize", searchPagination.getPageSize());
        paramMap.put("pageNo", searchPagination.getPageNo());
    }

    private void loadingEnd(Task task) {
        maskerPane.textProperty().unbind();
        maskerPane.progressProperty().unbind();
        maskerPane.setVisible(false);
        if (task.getException() != null) {
            task.getException().printStackTrace();
            DialogUtil.show(Alert.AlertType.CONFIRMATION, "错误", task.getException().getMessage());
        }
    }

    public void setData(PageResult pageResult) {
        if (pageResult != null) {
            tableView.getItems().clear();
            tableView.getItems().addAll(pageResult.getBeans());
            tableView.refresh();
            pageablePane.setPageResult(pageResult);
        }
    }

    private Region buildSearchContainer(MasterDetailPane masterDetailPane) {
        HBox hbox = new HBox();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.gridTableModel.getPath("searchEditPath").getUrl()));
        try {
            GridPane parent = fxmlLoader.load();
            Object controller = fxmlLoader.getController();
            if (controller instanceof Searchable) {
                this.searchable = (Searchable) controller;
            }
            if (controller instanceof CrudForm) {
                CrudForm crudForm = (CrudForm) controller;
                crudForm.setLoadData(this);
            }

            parent.setPadding(new Insets(5));
            hbox.getChildren().add(parent);
            parent.prefWidthProperty().bind(hbox.widthProperty());
            hbox.setPrefHeight(parent.getPrefHeight() <= 0 ? 130 : parent.prefHeight(-1));
            hbox.widthProperty().addListener(b->{
                System.out.println(tableView.getPrefWidth() + ":" + tableView.getWidth());
                masterDetailPane.setDividerPosition(parent.getPrefHeight()/tableView.getHeight());
            });
            masterDetailPane.setDividerPosition(0.4);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return hbox;
    }
}
