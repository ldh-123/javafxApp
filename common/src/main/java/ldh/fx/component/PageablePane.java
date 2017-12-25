package ldh.fx.component;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import ldh.common.PageResult;
import ldh.common.Pagination;
import ldh.fx.component.function.LoadData;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.io.IOException;
import java.util.List;

/**
 * Created by ldh on 2017/4/13.
 */
public class PageablePane extends HBox {

    private final static String FORMAT = "Displaying %s to %s of %s items";

    private LoadData loadData;
    private PageResult pageResult;

    @FXML private Label totalPageLabel;
    @FXML private TextField currentPageText;
    @FXML private ComboBox pageSizeChoiceBox;
    @FXML private ProgressBar progressBar;
    @FXML private Button firstBtn;
    @FXML private Button preBtn;
    @FXML private Button nextBtn;
    @FXML private Button lastBtn;
    @FXML private Button refreshBtn;
    @FXML private Label paginationInfo;

    public PageablePane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/component/PageablePane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);
        this.setPadding(new Insets(5));
        progressBar.setProgress(0d);
        this.setMaxWidth(Integer.MAX_VALUE);
        progressBar.setVisible(false);

        event();
    }

    private void event() {
        pageSizeChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue observable, Integer oldValue, Integer newValue) {
                if (newValue != null && !newValue.equals(oldValue) && oldValue != null) {
                    Pagination pagination = new Pagination(1, newValue);
                    loadData.load(pagination);
                }
            }
        });
    }

    public void setPageSizes(List<Integer> pageSizes) {
        pageSizeChoiceBox.getItems().addAll(pageSizes);
    }

    public void setPageSize(Integer pageSize) {
        pageSizeChoiceBox.getSelectionModel().select(pageSize);
    }

    public void setPageResult(PageResult pageResult) {
        this.pageResult = pageResult;
        totalPageLabel.setText(pageResult.getPageTotal() + "");
        currentPageText.setText(pageResult.getPageNo() + "");
        if(pageResult.getPageNo() == 1) {
            firstBtn.setDisable(true);
            preBtn.setDisable(true);
        } else {
            firstBtn.setDisable(false);
            preBtn.setDisable(false);
        }
        if(pageResult.getPageNo() == pageResult.getPageTotal()) {
            lastBtn.setDisable(true);
            nextBtn.setDisable(true);
        } else {
            lastBtn.setDisable(false);
            nextBtn.setDisable(false);
        }
        if (pageResult.getPageNo() == pageResult.getPageTotal() && pageResult.getPageNo() == 1) {
            currentPageText.setDisable(true);
        } else {
            currentPageText.setDisable(false);
        }
        long idx = (pageResult.getPageNo() - 1) * pageResult.getPageSize();
        paginationInfo.setText(String.format(FORMAT,  idx, idx + pageResult.getBeans().size() - 1, pageResult.getTotal()));
    }

    public void setLoadData(LoadData loadData) {
        this.loadData = loadData;
    }

    @FXML
    private void firstPageAct() {
        Pagination pagination = new Pagination(1, pageResult.getPageSize());
        loadData.load(pagination);
    }

    @FXML
    private void prePageAct() {
        long pageNo = pageResult.getPageNo();
        if(pageNo < 2) return;
        pageNo += -1;
        Pagination pagination = new Pagination(pageNo, pageResult.getPageSize());
        loadData.load(pagination);
    }

    @FXML
    private void pageAct() {
        Long num = Long.parseLong(currentPageText.getText().trim());
        if (num != pageResult.getPageNo() && num < pageResult.getPageTotal()) {
            Pagination pagination = new Pagination(num, pageResult.getPageSize());
            loadData.load(pagination);
        }
    }

    @FXML
    private void nextPageAct() {
        long pageNo = pageResult.getPageNo();
        if(pageNo >= pageResult.getPageTotal()) return;
        pageNo += 1;
        Pagination pagination = new Pagination(pageNo, pageResult.getPageSize());
        loadData.load(pagination);
    }

    @FXML
    private void lastPageAct() {
        long pageNo = pageResult.getPageNo();
        if(pageNo >= pageResult.getPageTotal()) return;
        pageNo = pageResult.getPageTotal();
        Pagination pagination = new Pagination(pageNo, pageResult.getPageSize());
        loadData.load(pagination);
    }

    @FXML
    private void refreshPageAct() {
        if (pageResult == null) {
            pageResult = new PageResult(0, null);
        }
        Pagination pagination = new Pagination(pageResult.getPageNo(), pageResult.getPageSize());
        loadData.load(pagination);
    }


    public void setLoading(boolean isLoading) {
        pageSizeChoiceBox.setDisable(isLoading);
        currentPageText.setDisable(isLoading);
        firstBtn.setDisable(isLoading);
        preBtn.setDisable(isLoading);
        nextBtn.setDisable(isLoading);
        lastBtn.setDisable(isLoading);
    }

}
