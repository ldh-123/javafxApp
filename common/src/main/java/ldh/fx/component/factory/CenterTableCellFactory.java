package ldh.fx.component.factory;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import ldh.fx.util.MethodUtil;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ldh on 2017/4/16.
 */
public class CenterTableCellFactory<S, T> implements Callback<TableColumn<S,T>, TableCell<S,T>> {

    private String property;

    public CenterTableCellFactory() {}

    public CenterTableCellFactory(String property) {
        this.property = property;
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        TableCell<S, T> tc = new TableCell<S, T>() {
            @Override
            public void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    Label label;
                    if (item instanceof Date) {
                        Date date = (Date) item;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        label = new Label(sdf.format(date));
                    } else {
                        if (property != null) {
                            Object obj = invoke(item, property);
                            label = new Label(obj.toString());
                        } else {
                            label = new Label(item.toString());
                        }
                    }
                    this.setGraphic(label);
                }
            }
        };
        tc.setAlignment(Pos.CENTER);
        return tc;
    }

    private Object invoke(T item, String property) {
        String methodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
        Method method = MethodUtil.getMethod(item.getClass(), methodName, new Class<?>[]{});
        if (method == null) {
            return "error property";
        }
        try {
            return method.invoke(item);
        } catch (Exception e) {
            return "error property";
        }
    }
}
