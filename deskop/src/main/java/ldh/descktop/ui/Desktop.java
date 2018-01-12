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
public class Desktop extends BorderPane {

//    private ObjectProperty<DesktopToolbar> toolbar;
    private DesktopPane desktopPane;
    private DesktopToolbar toolbar;

    public Desktop() {

    }

    public Desktop(DesktopPane desktopPane, DesktopToolbar toolbar) {
        this.desktopPane = desktopPane;
        this.toolbar = toolbar;
        this.setCenter(desktopPane);
        this.setBottom(toolbar);
        event();
    }

//    public final ObjectProperty<DesktopPane> desktopPaneProperty() {
//        if (desktopPane == null) {
//            desktopPane = new SimpleObjectProperty<>();
//        }
//        return desktopPane;
//    }

    public final void setDesktopPane(DesktopPane desktopPane) {
//        desktopPaneProperty().set(desktopPane);
        this.setCenter(desktopPane);
    }

    public final DesktopPane getDesktopPane() {
        if (desktopPane == null) {
            desktopPane = new DesktopPane();
        }
        return desktopPane;
    }

//    public final ObjectProperty<DesktopToolbar> toolbarProperty() {
//        if (toolbar == null) {
//            toolbar = new ToolbarProperty("toolbar");
//        }
//        return toolbar;
//    }

    public final void setToolbar(DesktopToolbar value) {
//        toolbarProperty().set(value);
//        toolbarProperty().addListener((l, o, n)->{
//
//        });
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

    private final class ToolbarProperty extends ObjectPropertyBase<DesktopToolbar> {
        private Node oldValue = null;
        private final String propertyName;
        private boolean isBeingInvalidated;

        ToolbarProperty(String propertyName) {
            this.propertyName = propertyName;
            getChildren().addListener(new ListChangeListener<Node>() {

                @Override
                public void onChanged(ListChangeListener.Change<? extends Node> c) {
                    if (oldValue == null || isBeingInvalidated) {
                        return;
                    }
                    while (c.next()) {
                        if (c.wasRemoved()) {
                            List<? extends Node> removed = c.getRemoved();
                            for (int i = 0, sz = removed.size(); i < sz; ++i) {
                                if (removed.get(i) == oldValue) {
                                    oldValue = null; // Do not remove again in invalidated
                                    set(null);
                                }
                            }
                        }
                    }
                }
            });
        }

        @Override
        protected void invalidated() {
            final List<Node> children = getChildren();

            isBeingInvalidated = true;
            try {
                if (oldValue != null) {
                    children.remove(oldValue);
                }

                final Node _value = get();
                this.oldValue = _value;

                if (_value != null) {
                    children.add(_value);
                }
            } finally {
                isBeingInvalidated = false;
            }
        }

        @Override
        public Object getBean() {
            return Desktop.this;
        }

        @Override
        public String getName() {
            return propertyName;
        }
    }
}
