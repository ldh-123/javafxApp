package ldh.descktop.ui;

import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.*;

/**
 * Created by ldh on 2018/1/18.
 */
public class MacDesktop extends BorderPane {

    private DesktopPane desktopPane;
    private DesktopToolbar toolbar;
    private DesktopNav desktopNav;
    private HBox navPane = new HBox();

    public MacDesktop() {

    }

    public MacDesktop(DesktopPane desktopPane, DesktopToolbar toolbar, DesktopNav desktopNav) {
        this.desktopPane = desktopPane;
        this.toolbar = toolbar;
        this.desktopNav = desktopNav;
        this.getStyleClass().add("desktop");
        this.setCenter(desktopPane);
        this.setTop(toolbar);
        this.setBottom(navPane);

        navPane.getStyleClass().add("desktop-nav-container");
        navPane.setAlignment(Pos.BOTTOM_CENTER);
        buildNav();
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
        this.setTop(value);
        event();
    }

    public final void setDesktopNav(DesktopNav desktopNav) {
        this.desktopNav = desktopNav;
        buildNav();
        event();
    }

    private void buildNav() {
        Region leftPane = new Region();
        leftPane.setStyle("-fx-background-color: transparent");
        Region rightPane = new Region();
        rightPane.setStyle("-fx-background-color: transparent");
        navPane.getChildren().addAll(leftPane, desktopNav, rightPane);
        HBox.setHgrow(leftPane, Priority.ALWAYS);
        HBox.setHgrow(rightPane, Priority.ALWAYS);
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
