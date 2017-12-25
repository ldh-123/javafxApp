package ldh.fx.component.model;

/**
 * Created by ldh on 2017/4/14.
 */
public class ColumnModel {

    private String property;
    private String text;
    private Double width;
    private String subProperty;
    private String cellFactory;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public String getSubProperty() {
        return subProperty;
    }

    public void setSubProperty(String subProperty) {
        this.subProperty = subProperty;
    }

    public String getCellFactory() {
        return cellFactory;
    }

    public void setCellFactory(String cellFactory) {
        this.cellFactory = cellFactory;
    }
}
