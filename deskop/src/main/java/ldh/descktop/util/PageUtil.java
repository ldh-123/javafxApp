package ldh.descktop.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Created by ldh on 2018/1/19.
 */
public class PageUtil {

    public static Parent load(String url) {
        try {
            return FXMLLoader.load(PageUtil.class.getResource(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Label("erorr");
    }
}
