package ldh.common.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Created by xiongfei.lei on 2017/11/15.
 */
public class MainController {

    @FXML private HeadController headController;
    @FXML
    public void addActiveEvent(MouseEvent e) {
        VBox container = (VBox)((Label) e.getSource()).getParent();
        List<Node> nodes = container.getChildren();
        for (Node node : nodes) {
            if (node instanceof Label) {
                Label l = (Label) node;
                l.getStyleClass().remove("active");
            }
        }
        ((Label)e.getSource()).getStyleClass().add("active");
    }

    @FXML
    public void searchBtn(ActionEvent e) {
        System.out.println("search!!!!!!!!!!!!!");
    }
}
