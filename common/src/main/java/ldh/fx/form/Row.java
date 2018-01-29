package ldh.fx.form;

import com.sun.javafx.event.EventHandlerManager;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.event.EventDispatchChain;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ldh on 2018/1/22.
 */
public class Row implements EventTarget, Styleable {

    private static final String DEFAULT_STYLE_CLASS = "row";

    private String text;
    private ObservableList<Node> nodes = FXCollections.observableArrayList();
    private StringProperty style;
    private final ObservableList<String> styleClass = FXCollections.observableArrayList();
    private ReadOnlyObjectWrapper<FormPane> formPane;
    private BooleanProperty disable;
    private ReadOnlyBooleanWrapper disabled;
    private static final Object USER_DATA_KEY = new Object();
    private StringProperty id;

    // A map containing a set of properties for this row
    private ObservableMap<Object, Object> properties;

    public Row() {
        this(null);
    }

    public Row(String text) {
        this(text, null);
    }


    public Row(String text, Node... nodes) {
        setText(text);
        setNodes(nodes);
        styleClass.addAll(DEFAULT_STYLE_CLASS);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setNodes(Node ...nodess) {
        if (nodess == null) return;
//        nodes.clear();
        nodes.addAll(nodess);
    }

    public final ObservableList<Node> getNodes() {
        return nodes;
    }

    public final ObservableList<Node> nodesProperty() {
        if (nodes == null) {
            nodes = new SimpleListProperty<Node>(this, "nodes");
        }
        return nodes;
    }

    public final void setStyle(String value) { styleProperty().set(value); }


    @Override
    public final String getStyle() { return style == null ? null : style.get(); }


    public final StringProperty styleProperty() {
        if (style == null) {
            style = new SimpleStringProperty(this, "style");
        }
        return style;
    }

    final void setFormPane(FormPane value) {
        formPanePropertyImpl().set(value);
    }

    public final FormPane getFormPane() {
        return formPane == null ? null : formPane.get();
    }

    public final ReadOnlyObjectProperty<FormPane> tabPaneProperty() {
        return formPanePropertyImpl().getReadOnlyProperty();
    }

    private ReadOnlyObjectWrapper<FormPane> formPanePropertyImpl() {
        if (formPane == null) {
            formPane = new ReadOnlyObjectWrapper<FormPane>(this, "formPane") {
                private WeakReference<FormPane> oldParent;

                @Override protected void invalidated() {
                    if(oldParent != null && oldParent.get() != null) {
                        oldParent.get().disabledProperty().removeListener(parentDisabledChangedListener);
                    }
                    updateDisabled();
                    FormPane newParent = get();
                    if (newParent != null) {
                        newParent.disabledProperty().addListener(parentDisabledChangedListener);
                    }
                    oldParent = new WeakReference<FormPane>(newParent);
                    super.invalidated();
                }
            };
        }
        return formPane;
    }

    private final InvalidationListener parentDisabledChangedListener = valueModel -> {
        updateDisabled();
    };

    private void updateDisabled() {
        boolean disabled = isDisable() || (getFormPane() != null && getFormPane().isDisabled());
        setDisabled(disabled);

        // Fix for RT-24658 - content should be disabled if the tab is disabled
        List<Node> nodes = this.getNodes();
        if (nodes != null) {
            for (Node node : nodes) {
                node.setDisable(disabled);
            }
        }
    }

    public final BooleanProperty disableProperty() {
        if (disable == null) {
            disable = new BooleanPropertyBase(false) {
                @Override
                protected void invalidated() {
                    updateDisabled();
                }

                @Override
                public Object getBean() {
                    return Row.this;
                }

                @Override
                public String getName() {
                    return "disable";
                }
            };
        }
        return disable;
    }

    public final void setDisable(boolean value) {
        disableProperty().set(value);
    }

    public final boolean isDisable() { return disable == null ? false : disable.get(); }

    private final void setDisabled(boolean value) {
        disabledPropertyImpl().set(value);
    }

    public final boolean isDisabled() {
        return disabled == null ? false : disabled.get();
    }

    public final ReadOnlyBooleanProperty disabledProperty() {
        return disabledPropertyImpl().getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper disabledPropertyImpl() {
        if (disabled == null) {
            disabled = new ReadOnlyBooleanWrapper() {
                @Override
                public Object getBean() {
                    return Row.this;
                }

                @Override
                public String getName() {
                    return "disabled";
                }
            };
        }
        return disabled;
    }

    public final ObservableMap<Object, Object> getProperties() {
        if (properties == null) {
            properties = FXCollections.observableMap(new HashMap<Object, Object>());
        }
        return properties;
    }

    public boolean hasProperties() {
        return properties != null && !properties.isEmpty();
    }

    public void setUserData(Object value) {
        getProperties().put(USER_DATA_KEY, value);
    }

    public Object getUserData() {
        return getProperties().get(USER_DATA_KEY);
    }

    @Override
    public String getTypeSelector() {
        return "row";
    }

    public final void setId(String value) { idProperty().set(value); }

    @Override
    public final String getId() { return id == null ? null : id.get(); }

    public final StringProperty idProperty() {
        if (id == null) {
            id = new SimpleStringProperty(this, "id");
        }
        return id;
    }

    @Override
    public ObservableList<String> getStyleClass() {
        return styleClass;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    @Override
    public Styleable getStyleableParent() {
        return getFormPane();
    }

    @Override
    public ObservableSet<PseudoClass> getPseudoClassStates() {
        return FXCollections.emptyObservableSet();
    }

    private final EventHandlerManager eventHandlerManager = new EventHandlerManager(this);

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return tail.prepend(eventHandlerManager);
    }


    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return Collections.emptyList();
    }
}
