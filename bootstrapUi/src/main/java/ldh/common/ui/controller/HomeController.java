package ldh.common.ui.controller;

import com.sun.org.apache.bcel.internal.generic.POP;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Popup;
import javafx.util.Duration;
import ldh.common.ui.node.ChartContent;
import ldh.common.ui.node.FormContent;
import ldh.common.ui.node.FormContent2;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.swing.*;
import javax.xml.bind.annotation.XmlAnyAttribute;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by xiongfei.lei on 2017/11/15.
 */
public class HomeController implements Initializable {

    @FXML private HomeHeadController headController;
    @FXML private VBox bigLeftPane;
    @FXML private VBox smallLeftPane;
    @FXML private ScrollPane leftPane;
    @FXML private StackPane contentPane;
    @FXML private WebView webView;

    private Map<Button, Popup> popupMap = new HashMap<>();

    public void tongleLeftPane() {
        if (bigLeftPane.isVisible()) {
            bigLeftPane.setVisible(false);
            smallLeftPane.setVisible(true);
            leftPane.setPrefWidth(50);
        } else {
            bigLeftPane.setVisible(true);
            smallLeftPane.setVisible(false);
            leftPane.setPrefWidth(220);
        }

    }

    @FXML public void navTitleBtn(ActionEvent e) {
        e.consume();
    }

    @FXML public void showDashboard() {
        System.out.println("show dashboard!!!!!!!!");
    }

    @FXML public void leftNaveClick(ActionEvent e) {
        Node parent = (Node) e.getSource();
        if (parent instanceof Button) {
            Object userData = ((Button) parent).getUserData();
            if (userData == null) return;
            if (userData.toString().equals("chart1")) {
                changeContent(new ChartContent());
            } else if (userData.toString().equals("form1")) {
                changeContent(new FormContent());
            } else if (userData.toString().equals("form2")) {
                changeContent(new FormContent());
            }
        }
    }

    @FXML public void bigLeftPaneClick(MouseEvent e) {
        System.out.println("bigLeftPaneClick!!!!!!!!");
        smallLeftPane.setVisible(false);
        bigLeftPane.setVisible(true);
        e.consume();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        headController.setHomeController(this);
        leftPane.setPrefWidth(220);
        webView.getEngine().load("http://www.baidu.com");

        buildPopup();

        bigLeftPane.setOnMousePressed(e->e.consume());
        smallLeftPane.setOnMousePressed(e->e.consume());
        expandTitlePanes();

        for (Node node : smallLeftPane.getChildren()){
            if (node instanceof Button) {
                Button child = (Button) node;
                child.setTooltip(new Tooltip(child.getText()));
            }
        }
        for (Node node : bigLeftPane.getChildren()) {
            node.setOnMouseClicked(e->{
                expandedOther(node);
            });
        }
    }

    private void expandedOther(Node titledPane) {
        for (Node node : bigLeftPane.getChildren()) {
            if (node instanceof TitledPane) {
                TitledPane tmp = (TitledPane) node;
                if (tmp != titledPane && tmp.isExpanded()) {
                    tmp.setExpanded(false);
                }
            }
        }
    }


    private void expandTitlePanes() {
        List<Node> nodes = bigLeftPane.getChildren();
        for (Node node : nodes) {
            if (node instanceof TitledPane) {
                TitledPane titledPane = (TitledPane) node;
                titledPane.setExpanded(false);
            }
        }
    }

    private void buildPopup() {
        List<Node> nodes = smallLeftPane.getChildren();
        Map<String, TitledPane> titledPaneMap = getTitlePane();
        for (Node node : nodes) {
            if (node instanceof Button) {
                Button b = (Button) node;
                if (titledPaneMap.containsKey(b.getText())) {
                    Popup popup = popupMap.get(b);
                    if (popup == null) {
                        popup = buildPopup(b, titledPaneMap.get(b.getText()));
                        popupMap.put(b, popup);
                    }
                }
            }
        }
    }

    private Popup buildPopup(Button b, TitledPane titledPane) {
        Popup popup = new Popup();
        popup.setAutoHide(true);

        VBox box = new VBox();
        box.getStyleClass().add("popup-nav");
        Button first = new Button(titledPane.getText());
        first.getStyleClass().add("first-nav");
        box.getChildren().add(first);
        Node node = titledPane.getContent();
        if (node instanceof VBox) {
            List<Node> children = ((VBox) node).getChildren();
            for (Node n : children) {
                if (n instanceof Button) {
                    Button l = (Button) n;
                    Button label = new Button(l.getText());
                    label.getStyleClass().add("child-nav");
                    FontAwesomeIconView fontIcon = new FontAwesomeIconView();
                    fontIcon.getStyleClass().add("nav-circle");
                    label.setGraphic(fontIcon);
                    label.setUserData(l.getUserData());
                    label.setOnAction(l.getOnAction());
                    box.getChildren().add(label);
                }
            }
        }
        popup.getContent().add(box);

        box.setOnMouseEntered(e1->{
            popup.show(b.getScene().getWindow(),
                    b.getScene().getWindow().getX() + b.localToScene(0, 0).getX() + b.getScene().getX() + b.getWidth()-1,
                    b.getScene().getWindow().getY() + b.localToScene(0, 0).getY() + b.getScene().getY());
        });
        box.setOnMouseExited(e2->popup.hide());

        b.setOnMouseEntered(e->{
            popup.show(b.getScene().getWindow(),
                    b.getScene().getWindow().getX() + b.localToScene(0, 0).getX() + b.getScene().getX() + b.getWidth()-1,
                    b.getScene().getWindow().getY() + b.localToScene(0, 0).getY() + b.getScene().getY());
        });
        b.setOnMousePressed(e->{
            popup.show(b.getScene().getWindow(),
                    b.getScene().getWindow().getX() + b.localToScene(0, 0).getX() + b.getScene().getX() + b.getWidth()-1,
                    b.getScene().getWindow().getY() + b.localToScene(0, 0).getY() + b.getScene().getY());
        });
        b.setOnMouseExited(e->{
            double w = b.getScene().getWindow().getX() + b.localToScene(0, 0).getX() + b.getScene().getX() + b.getWidth();
            double h = b.getScene().getWindow().getY() + b.localToScene(0, 0).getY() + b.getScene().getY();
            if (!(e.getScreenX() >= w-5 && e.getScreenX() <= w+5 && e.getScreenY() >= h && e.getScreenY() <= h + b.getHeight() + 10 )) {
                popup.hide();
            }
        });

        popup.showingProperty().addListener((ob, o, n) -> {
            if (n) {
                b.setStyle("-fx-background-color: #212529");
            } else {
                b.getStyleClass().remove("nav-show");
            }
        });
        return popup;
    }

    private Map<String, TitledPane> getTitlePane() {
        Map<String, TitledPane> resultMap = new HashMap<>();
        List<Node> nodes = bigLeftPane.getChildren();
        for (Node node : nodes) {
            if (node instanceof TitledPane) {
                TitledPane titledPane = (TitledPane) node;
                if (titledPane.getText().trim().equals("")) continue;
                resultMap.put(titledPane.getText(), titledPane);
            }
        }
        return resultMap;
    }

    private void changeContent(Node node) {
        if (contentPane.getChildren().size() < 1) return;
        Node oldNode = contentPane.getChildren().get(0);
        FadeTransition ft = new FadeTransition(Duration.millis(500), oldNode);
        ft.setFromValue(1.0);
        ft.setToValue(0.01);
        ft.setOnFinished(e->{
            contentPane.getChildren().clear();
            contentPane.getChildren().add(node);
        });

        FadeTransition ft2 = new FadeTransition(Duration.millis(800), node);
        ft2.setFromValue(0.01);
        ft2.setToValue(1.0);
        SequentialTransition sequentialTransition=new SequentialTransition(ft,ft2);
        sequentialTransition.setCycleCount(1);
        sequentialTransition.play();
    }
}
