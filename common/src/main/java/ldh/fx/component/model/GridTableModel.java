package ldh.fx.component.model;

import ldh.fx.component.function.IPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by ldh on 2017/4/14.
 */
public class GridTableModel implements IPath {

    private String clazz;
    private List<ColumnModel> columnModels;
    private List<Integer> pageSizes = new ArrayList<>(Arrays.asList(5, 10, 20, 30, 50, 100));
    private Integer defaultPageSize = 10;
    private Map<String, PathModel> pathModels;

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public List<ColumnModel> getColumnModels() {
        return columnModels;
    }

    public void setColumnModels(List<ColumnModel> columnModels) {
        this.columnModels = columnModels;
    }

    public List<Integer> getPageSizes() {
        return pageSizes;
    }

    public void setPageSizes(List<Integer> pageSizes) {
        this.pageSizes = pageSizes;
    }

    public Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(Integer defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public Map<String, PathModel> getPathModels() {
        return pathModels;
    }

    public void setPathModels(Map<String, PathModel> pathModels) {
        this.pathModels = pathModels;
    }

    @Override
    public PathModel getPath(String key) {
        if (pathModels == null) {
            throw new RuntimeException("请初始化路径");
        }
        if (!pathModels.containsKey(key)) {
            throw new IllegalArgumentException(key + "不存在");
        }
        return pathModels.get(key);
    }
}