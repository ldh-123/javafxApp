package ldh.fx.component.function;

/**
 * Created by ldh on 2017/4/14.
 */
public interface CrudForm<T> {

    void setInitData(T data);

    void setLoadData(LoadData loadData);

    void setPath(IPath path);
}
