package ldh.common.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ldh.fx.StageUtil;
import ldh.fx.component.LPopupButton;
import ldh.fx.component.LPopup;
import ldh.fx.component.LxDialog;
import ldh.fx.component.PopupPos;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeHeadController implements Initializable {

    @FXML private VBox userContent;
    @FXML private Button userBtn;
    @FXML private HBox head;

    private HomeController homeController;
//    private Popup userPopup;
    private LPopup userPopupPane;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    @FXML
    public void tongleLeftPane(ActionEvent e) {
        homeController.tongleLeftPane();
    }

    @FXML public void showUserPopup(ActionEvent e) {
        double width = userContent.getPrefWidth();
        if (width < 1) {
            width = userContent.prefWidth(-1);
            width = width < 1 ? 200 : width;
        }
        userPopupPane.show();
//        userPopup.show(head.getScene().getWindow(),
//                head.getScene().getWindow().getX() + userBtn.localToScene(0, 0).getX() + userBtn.getScene().getX() + userBtn.getWidth() - width -1,
//                head.getScene().getWindow().getY() + userBtn.localToScene(0, 0).getY() + userBtn.getScene().getY() + userBtn.getHeight()+2);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPopup();
        userPopupPane = new LPopup(userBtn, PopupPos.down_west);
        userPopupPane.setPopupContentPane(userContent);
    }

    private void initPopup() {
        head.getChildren().remove(userContent);
        userContent.getStyleClass().add("nav-user-popup");
    }

    @FXML public void settingBtn(ActionEvent e) {
        LxDialog ldhDialog = new LxDialog(StageUtil.STAGE, "test", 500d, 300d);
        VBox box = new VBox();
        for (int i=0; i<10; i++) {
            box.getChildren().add(new Label("asdfasfd"));
        }
        ldhDialog.setContentPane(box);
        box.setStyle("-fx-padding: 5");
        ldhDialog.show();

        if (userPopupPane.isShowing()) {
            userPopupPane.hide();
        }
    }

    @FXML public void profileBtn(ActionEvent actionEvent) {
        LxDialog ldhDialog = new LxDialog(StageUtil.STAGE, "test", 500d, 300d);
        VBox box = new VBox();
        box.setStyle("-fx-padding: 5");
        for (int i=0; i<10; i++) {
            box.getChildren().add(new Label("asdfasfd"));
        }
        ldhDialog.setContentPane(box);
        ldhDialog.show();
        if (userPopupPane.isShowing()) {
            userPopupPane.hide();
        }
    }

    @FXML public void logoutBtn(ActionEvent actionEvent) {
        StageUtil.exit();
    }
}
