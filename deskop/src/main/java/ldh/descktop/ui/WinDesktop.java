package ldh.descktop.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import sun.security.krb5.internal.crypto.Des;

import java.util.List;

/**
 * Created by ldh on 2018/1/11.
 */
public class WinDesktop extends BorderPane {

    private DesktopPane desktopPane;
    private DesktopToolbar toolbar;

    public WinDesktop() {

    }

    public WinDesktop(DesktopPane desktopPane, DesktopToolbar toolbar) {
        this.desktopPane = desktopPane;
        this.toolbar = toolbar;
        this.getStyleClass().add("desktop");
        this.setCenter(desktopPane);
        this.setBottom(toolbar);
        event();
    }

    public final void setDesktopPane(DesktopPane desktopPane) {
        this.setCenter(desktopPane);
    }

    public final DesktopPane getDesktopPane() {
        if (desktopPane == null) {
            desktopPane = new DesktopPane();
        }
        return desktopPane;
    }

    public final void setToolbar(DesktopToolbar value) {
        this.toolbar = value;
        this.setBottom(value);
        event();
    }

    public void event() {
        for (Node node : getDesktopPane().getChildren()) {
            if (node instanceof DesktopItem) {
                DesktopItem desktopItem = (DesktopItem) node;
                desktopItem.setDesktopToolbar(toolbar);
            }
        }
        getDesktopPane().getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                while (c.next()) {
                    for (Node node : c.getAddedSubList()) {
                        if (node instanceof DesktopItem) {
                            DesktopItem desktopItem = (DesktopItem) node;
                            desktopItem.setDesktopToolbar(toolbar);
                        }
                    }
                }
            }
        });
    }

    public final DesktopToolbar getToolbar() { return toolbar == null ? null : toolbar; }
}
