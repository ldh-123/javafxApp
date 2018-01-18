package ldh.descktop.ui;

import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import ldh.descktop.util.NodeUtil;
import ldh.fx.component.LPopupButton;

/**
 * Created by ldh on 2018/1/18.
 */
public class DesktopNav extends HBox {

    public DesktopNav() {
        init();
    }

    public DesktopNav(double spacing) {
        super(spacing);
        init();
    }

    public DesktopNav(Node... children) {
        super(children);
        init();
    }

    public DesktopNav(double spacing, Node... children) {
        super(spacing, children);
        init();
    }

    private void init() {
        this.getStyleClass().add("desktop-nav");
        this.setAlignment(Pos.CENTER);

        this.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                while (c.next()) {
                    if (c.wasPermutated()) {
                        for (int i = c.getFrom(); i < c.getTo(); ++i) {
                            //permutate
                        }
                    } else if (c.wasUpdated()) {
                        //update item
                    } else {
                        for (Node additem : c.getAddedSubList()) {
                            DesktopNavItem stackPane = (DesktopNavItem) additem;
                            Button button = (Button) stackPane.getChildren().get(0);
                            Popup popup = new Popup();
                            Label label = new Label(button.getText());
                            popup.getContent().add(label);
                            stackPane.setOnMouseExited(e->{
                                button.setScaleX(1.0);
                                button.setScaleY(1.0);
                                button.setTranslateY(0);
                                popup.hide();
                            });
                            stackPane.setOnMouseEntered(e->{
                                button.setScaleX(1.3);
                                button.setScaleY(1.3);
                                button.setTranslateY(-30);
                                double anchorX = NodeUtil.anchorX(button) + button.getWidth()/2 - label.getWidth()/2 -1;
                                double anchorY = NodeUtil.anchorY(button) - 5;
                                popup.show(button.getScene().getWindow(), anchorX, anchorY);
                            });
                        }
                    }
                }
            }
        });
    }
}
