package ldh.fx.component.factory;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ldh on 2017/4/17.
 */
public class CellFactoryRegister {

    private static CellFactoryRegister instance = null;
    private Map<String, Callback<TableColumn, TableCell>> tableCellFactoryMap = new HashMap<>();


    private  CellFactoryRegister() {}

    public static CellFactoryRegister getInstance() {
        if (instance == null) {
            synchronized (CellFactoryRegister.class) {
                instance = new CellFactoryRegister();
            }
        }
        return instance;
    }

    public void register(String key, Callback<TableColumn, TableCell> callback) {
        if (tableCellFactoryMap.containsKey(key)) {
            throw new RuntimeException("已经包含了");
        }
        tableCellFactoryMap.put(key, callback);
    }

    public Callback<TableColumn, TableCell> get(String key) {
        if (!tableCellFactoryMap.containsKey(key)) {
            throw new IllegalArgumentException(key);
        }
        return tableCellFactoryMap.get(key);
    }
}
