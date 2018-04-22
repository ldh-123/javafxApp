package ldh.fx.component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import ldh.fx.ui.util.RegionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ldh123 on 2018/4/22.
 */
public class LNavPane extends ScrollPane {

    private VBox smallPane = new VBox();
    private StackPane stackPane = new StackPane();

    public LNavPane() {
        this.getStyleClass().add("leftPane");
        this.setFitToWidth(true);
        this.setFitToHeight(true);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        smallPane.setVisible(false);
        smallPane.getStyleClass().add("nav-small-pane");
        stackPane.getChildren().add(smallPane);
        this.contentProperty().addListener((b, o, n)->{
            addNode(n);
        });
    }

    private void addNode(Node node) {
        if (!(node instanceof VBox)) return;
        this.getChildren().remove(node);
        stackPane.getChildren().add(node);
        ObservableList<Node> nodeList = ((VBox)node).getChildren();
        for (Node nodet : nodeList) {
            if (nodet instanceof Button) {
                Button child = (Button) nodet;
                Button newBtn = new Button(child.getText());
                newBtn.setTooltip(new Tooltip(child.getText()));
                newBtn.setOnAction(child.getOnAction());
                Node graphic = RegionUtil.copyGraphic(child.getGraphic());
                newBtn.setGraphic(graphic);
                smallPane.getChildren().add(newBtn);
            } else if (nodet instanceof TitledPane) {
                TitledPane titledPane = (TitledPane) nodet;
                Button newBtn = new Button(titledPane.getText());
                Node graphic = RegionUtil.copyGraphic(titledPane.getGraphic());
                newBtn.setGraphic(graphic);
                newBtn.setTooltip(new Tooltip(titledPane.getText()));
                smallPane.getChildren().add(newBtn);

                buildPopup(newBtn, titledPane);
            }
        }
        this.setContent(stackPane);
    }

    public void tongleLeftPane() {
        Region bigLeftPane = (Region) stackPane.getChildren().get(1);
        if (!smallPane.isVisible()) {
            bigLeftPane.setVisible(false);
            smallPane.setVisible(true);
            this.setPrefWidth(50);
            this.setMinWidth(50);
        } else {
            bigLeftPane.setVisible(true);
            smallPane.setVisible(false);
            this.setPrefWidth(220);
            this.setMinWidth(220);
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

}
