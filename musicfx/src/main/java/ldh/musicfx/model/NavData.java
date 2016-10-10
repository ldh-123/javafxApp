package ldh.musicfx.model;

import java.util.List;

/**
 * Created by Puhui on 2016/10/8.
 */
public class NavData {

    private String imageSrc;
    private String text;
    private List<NavData> children;

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<NavData> getChildren() {
        return children;
    }

    public void setChildren(List<NavData> children) {
        this.children = children;
    }
}
