package ldh.fx.util;

import com.google.gson.Gson;
import ldh.fx.component.model.GridTableModel;

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
}
