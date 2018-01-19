package ldh.common.ui.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import ldh.common.ui.util.CvsPropertiesUtil;
import ldh.fx.StageUtil;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.StatusBar;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class CvsController implements Initializable {

    FileChooser fileChooser = new FileChooser();

    @FXML private ListView<String> listView;
    @FXML private TabPane tabPane;
    @FXML private StatusBar statusBar;

    @FXML public void open() {
        fileChooser.setTitle("打开CVS文件");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("打开CVS文件", "*.csv"));
        File file = fileChooser.showOpenDialog(StageUtil.STAGE);
        openFile(file);
    }

    private void openFile(File file) {
        try {
            if (file == null) return;
            TableView<Object[]> tableView = new TableView();
            tableView.getStyleClass().add("data-table");
            MaskerPane masker = new MaskerPane();
            Tab tab = new Tab(file.getName());
            tab.setContent(new StackPane(tableView, masker));
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            tableView.sortPolicyProperty().set(t -> {
                Comparator<Object[]> comparator = (r1, r2)
                        -> t.getComparator() == null ? 0 //no column sorted: don't change order
                        : t.getComparator().compare(r1, r2); //columns are sorted: sort accordingly
                FXCollections.sort(tableView.getItems(), comparator);
                return true;
            });
            loadData(tableView, file, masker);

            for (String str : listView.getItems()) {
                if (str.equals(file.getPath())) {
                    return;
                }
            }
            listView.getItems().add(file.getPath());
            CvsPropertiesUtil.appendAndSave("files", file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadData(TableView tableView, File file, MaskerPane masker) {
        statusBar.getRightItems().clear();
        Task<Void> task = new Task<Void>() {
            @Override protected Void call() throws Exception {
                updateMessage("开始加载文件 ....");

                List<String> lines = Files.readAllLines(Paths.get(file.toURI()), Charset.forName("utf-8"));
                if (lines.size() <= 0) {
                    Platform.runLater(()->{
                        masker.setVisible(false);
                    });
                    return null;
                }
                updateMessage("开始渲染数据 ....");
                Platform.runLater(()->{
                    statusBar.getRightItems().add(new Label("加载数据：" + lines.size() + "条"));
                    setTableHeader(tableView, lines.get(0));
                    setTableData(tableView, lines);
                    masker.setVisible(false);
                });
                updateMessage("完成");
                updateProgress(0, 0);
                done();
                return null;
            }
        };

        statusBar.textProperty().bind(task.messageProperty());
        statusBar.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(event -> {
            statusBar.textProperty().unbind();
            statusBar.progressProperty().unbind();
        });

        new Thread(task).start();
    }

    public void setTableHeader(TableView<Object[]> tableView, String headers) {
        headers = "num," + headers;
        String[] strs = headers.split(",");
        int i = -1;
        for (String str : strs) {
            i++;
            final int idx = i;
            TableColumn tc = new TableColumn(str);
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object[],Object>, ObservableValue<Object>>() {

                @Override
                public ObservableValue<Object> call(TableColumn.CellDataFeatures<Object[], Object> param) {
                    return new SimpleObjectProperty(param.getValue()[idx]);
                }
            });
            tableView.getColumns().add(tc);
        }
    }

    public void setTableData(TableView<Object[]> tableView, List<String> tableData) {
        boolean isfirst = true;
        int i = 0;
        for (String line : tableData) {
            if (isfirst) {
                isfirst = false;
                continue;
            }
            i++;
            String[] strs = line.split(",");
            Object[] values = new Object[strs.length + 1];
            values[0] = i;
            System.arraycopy(strs, 0, values, 1, strs.length);
            tableView.getItems().add(values);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String files = CvsPropertiesUtil.getValue("files");
        if (files != null) {
            String[] fileArray = files.split(",");
            for (String file : fileArray) {
                listView.getItems().add(file);
            }
        }
        listView.setOnMouseClicked(e->{
            if (e.getClickCount() != 2) return;
            String selected = listView.getSelectionModel().getSelectedItem();
            if (selected == null) return;
            String fileName = getFileName(selected);
            Tab tab = getTabPaneByName(fileName);
            if (tab != null) {
                tabPane.getSelectionModel().select(tab);
                return;
            }
            openFile(new File(selected));
        });

        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            String value = getFileName(item);
                            this.setText(value);
                        } else {
                            this.setText("");
                        }
                    }
                };
            }
        });

        listView.setContextMenu(buildListViewContextMenu());
    }

    private ContextMenu buildListViewContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem mi = new MenuItem("删除");
        mi.setOnAction(e->{
            String item = listView.getSelectionModel().getSelectedItem();
            if (item == null) return;
            Platform.runLater(()->{
                listView.getItems().remove(item);
                listView.refresh();
            });
            new Thread(()->CvsPropertiesUtil.removeAndSave("files", item)).start();
        });
        contextMenu.getItems().add(mi);
        return contextMenu;
    }

    private Tab getTabPaneByName(String selected) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(selected)) {
                return tab;
            }
        }
        return null;
    }

    private String getFileName(String filePath) {
        String value = filePath;
        if (filePath.contains("/")) {
            value = filePath.substring(filePath.lastIndexOf("/") + 1);
        } else if (filePath.contains("\\")) {
            value = filePath.substring(filePath.lastIndexOf("\\") + 1);
        }
        return value;
    }
}
