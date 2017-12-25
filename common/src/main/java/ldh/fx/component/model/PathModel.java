package ldh.fx.component.model;

/**
 * Created by ldh on 2017/4/22.
 */
public class PathModel {

    private String url;
    private String property;

    public PathModel(String url, String property) {
        this.url = url;
        this.property = property;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
