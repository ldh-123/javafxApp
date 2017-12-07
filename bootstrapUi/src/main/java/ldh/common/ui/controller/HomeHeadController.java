package ldh.common.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by xiongfei.lei on 2017/11/15.
 */
public class HomeHeadController implements Initializable {

    @FXML private VBox userContent;
    @FXML private Button userBtn;
    @FXML private HBox head;

    private HomeController homeController;
    private Popup userPopup;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    @FXML
    public void tongleLeftPane(ActionEvent e) {
        System.out.println("search2!!!!!!!!!!!!!");
        homeController.tongleLeftPane();
    }

    @FXML public void settingBtn(ActionEvent e) {
        System.out.println("setting==================");
    }

    @FXML public void showUserPopup(ActionEvent e) {
        userPopup.show(head.getScene().getWindow(),
                head.getScene().getWindow().getX() + userBtn.localToScene(0, 0).getX() + userBtn.getScene().getX() + userBtn.getWidth() - 150 -1,
                head.getScene().getWindow().getY() + userBtn.localToScene(0, 0).getY() + userBtn.getScene().getY() + userBtn.getHeight());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPopup();
    }

    private void initPopup() {
        head.getChildren().remove(userContent);
        userContent.getStyleClass().add("nav-user-popup");
        userPopup = new Popup();
        userPopup.setAutoHide(true);
        userPopup.getContent().add(userContent);
    }
}
