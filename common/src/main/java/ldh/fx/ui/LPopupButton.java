package ldh.fx.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Popup;

/**
 * Created by ldh on 2018/1/11.
 */
public class LPopupButton extends Button {

    private ObjectProperty<Node> popupNodeProperty = new SimpleObjectProperty<>();
    private Popup popup = new Popup();
    private PopupPos popupPos;

    public LPopupButton(PopupPos popupPos) {
        this.popupPos = popupPos;
        initEvent();
    }

    public LPopupButton(String text, PopupPos popupPos) {
        super(text);
        this.popupPos = popupPos;
        initEvent();
    }

    public LPopupButton(String text, Node graphic, PopupPos popupPos) {
        super(text, graphic);
        this.popupPos = popupPos;
        initEvent();
    }

    public void setPopupNode(Node popupNode) {
        popupNodeProperty.set(popupNode);
    }

    public Node getPopupNode() {
        return popupNodeProperty.get();
    }

    public ObjectProperty<Node> popupNodeProperty() {
        return popupNodeProperty;
    }

    private void initEvent() {
        popup.setAutoHide(true);
        popupNodeProperty.addListener((l, o, n)->{
            popup.getContent().clear();
            popup.getContent().add(n);
        });
        this.setOnAction(e->{
            double anchorX = clacAnchorX();
            double anchorY = clacAnchorY();
            popup.show(this.getScene().getWindow(), anchorX, anchorY);
        });
    }

    private double clacAnchorY() {
        return 0;
    }

    private double clacAnchorX() {
        return 0;
    }

    public static enum PopupPos {
        west_down,
        east_down,
        west_up,
        east_up,
        ;
    }

}
