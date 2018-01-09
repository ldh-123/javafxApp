package ldh.fx.util;

import com.google.gson.Gson;
import ldh.fx.component.model.GridTableModel;

import java.io.InputStream;

/**
 * Created by ldh on 2017/4/14.
 */
public class JsonParse {

    public static GridTableModel parseTableModel(String file) throws Exception {
        String json = FileUtil.loadJarFile(file);
        Gson gson = new Gson();
        GridTableModel tableModel = gson.fromJson(json, GridTableModel.class);
        return tableModel;
    }

    public static GridTableModel parseTableModel(InputStream inputStream) throws Exception {
        String json = FileUtil.loadFile(inputStream);
        Gson gson = new Gson();
        GridTableModel tableModel = gson.fromJson(json, GridTableModel.class);
        return tableModel;
    }
}
