package ldh.fx.form;

import com.sun.javafx.css.converters.EnumConverter;
import com.sun.javafx.scene.control.MultiplePropertyChangeListenerHandler;
import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.WritableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.WeakListChangeListener;
import javafx.css.*;
import javafx.event.EventHandler;
import javafx.scene.AccessibleAction;
import javafx.scene.AccessibleAttribute;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ldh on 2018/1/22.
 */
public class FormPaneSkin extends SkinBase<FormPane> {
    private static enum FormAnimation {
        NONE,
        GROW
    }

    private enum FormAnimationState {
        SHOWING, HIDING, NONE;
    }

    private ObjectProperty<FormAnimation> openFormAnimation = new StyleableObjectProperty<FormAnimation>(FormAnimation.GROW) {
        @Override public CssMetaData<TabPane, FormAnimation> getCssMetaData() {
            return FormPaneSkin.StyleableProperties.OPEN_TAB_ANIMATION;
        }

        @Override public Object getBean() {
            return FormPaneSkin.this;
        }

        @Override public String getName() {
            return "openTabAnimation";
        }
    };

    private static Node clone(Node n) {
        if (n == null) {
            return null;
        }
        if (n instanceof ImageView) {
            ImageView iv = (ImageView) n;
            ImageView imageview = new ImageView();
            imageview.setImage(iv.getImage());
            return imageview;
        }
        if (n instanceof Label) {
            Label l = (Label)n;
            Label label = new Label(l.getText(), l.getGraphic());
            return label;
        }
        return null;
    }
    private static final double ANIMATION_SPEED = 150;
    private static final int SPACER = 10;

    private Rectangle clipRect;

    public FormPaneSkin(FormPane formPane) {
        super(formPane);

        clipRect = new Rectangle(formPane.getWidth(), formPane.getHeight());
        getSkinnable().setClip(clipRect);


        for (Row row : getSkinnable().getRows()) {
            addRow(row);
        }

//        initializeSwipeHandlers();
    }

//    @Override protected void handleControlPropertyChanged(String property) {
//        super.handleControlPropertyChanged(property);
//        if ("SELECTED_TAB".equals(property)) {
//            isSelectingTab = true;
//            selectedTab = getSkinnable().getSelectionModel().getSelectedItem();
//            getSkinnable().requestLayout();
//        } else if ("SIDE".equals(property)) {
//            updateTabPosition();
//        } else if ("WIDTH".equals(property)) {
//            clipRect.setWidth(getSkinnable().getWidth());
//        } else if ("HEIGHT".equals(property)) {
//            clipRect.setHeight(getSkinnable().getHeight());
//        }
//    }

//    private void removeTabs(List<? extends Tab> removedList) {
//        for (final Tab tab : removedList) {
////            stopCurrentAnimation(tab);
//            // Animate the tab removal
//            final com.sun.javafx.scene.control.skin.TabPaneSkin.TabHeaderSkin tabRegion = tabHeaderArea.getTabHeaderSkin(tab);
//            if (tabRegion != null) {
//                tabRegion.isClosing = true;
//
//                tabRegion.removeListeners(tab);
////                removeTabContent(tab);
//
//                // remove the menu item from the popup menu
//                ContextMenu popupMenu = tabHeaderArea.controlButtons.popup;
//                com.sun.javafx.scene.control.skin.TabPaneSkin.TabMenuItem tabItem = null;
//                if (popupMenu != null) {
//                    for (MenuItem item : popupMenu.getItems()) {
//                        tabItem = (com.sun.javafx.scene.control.skin.TabPaneSkin.TabMenuItem) item;
//                        if (tab == tabItem.getTab()) {
//                            break;
//                        }
//                        tabItem = null;
//                    }
//                }
//                if (tabItem != null) {
//                    tabItem.dispose();
//                    popupMenu.getItems().remove(tabItem);
//                }
//                // end of removing menu item
//
//                EventHandler<ActionEvent> cleanup = ae -> {
//                    tabRegion.animationState = com.sun.javafx.scene.control.skin.TabPaneSkin.TabAnimationState.NONE;
//
//                    tabHeaderArea.removeTab(tab);
//                    tabHeaderArea.requestLayout();
//                    if (getSkinnable().getTabs().isEmpty()) {
//                        tabHeaderArea.setVisible(false);
//                    }
//                };
//
//                if (closeTabAnimation.get() == com.sun.javafx.scene.control.skin.TabPaneSkin.TabAnimation.GROW) {
//                    tabRegion.animationState = com.sun.javafx.scene.control.skin.TabPaneSkin.TabAnimationState.HIDING;
//                    Timeline closedTabTimeline = tabRegion.currentAnimation =
//                            createTimeline(tabRegion, Duration.millis(ANIMATION_SPEED), 0.0F, cleanup);
//                    closedTabTimeline.play();
//                } else {
//                    cleanup.handle(null);
//                }
//            }
//        }
//    }

    private void addRows(List<? extends Row> addedList, int from) {
        int i = 0;

        for (final Row row : addedList) {
            addRow(row);
        }
    }

    private void addRow(Row row) {
//        getChildren().add(0, row);
    }

    private void removeRow(Row row) {

    }

    private void updateRowPosition() {
        getSkinnable().applyCss();
        getSkinnable().requestLayout();
    }

    private boolean isFloatingStyleClass() {
        return getSkinnable().getStyleClass().contains(TabPane.STYLE_CLASS_FLOATING);
    }

    private double maxw = 0.0d;
//    @Override protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
//        // The TabPane can only be as wide as it widest content width.
//        for (com.sun.javafx.scene.control.skin.TabPaneSkin.TabContentRegion contentRegion: tabContentRegions) {
//            maxw = Math.max(maxw, snapSize(contentRegion.prefWidth(-1)));
//        }
//
//        final boolean isHorizontal = isHorizontal();
//        final double tabHeaderAreaSize = snapSize(isHorizontal ?
//                tabHeaderArea.prefWidth(-1) : tabHeaderArea.prefHeight(-1));
//
//        double prefWidth = isHorizontal ?
//                Math.max(maxw, tabHeaderAreaSize) : maxw + tabHeaderAreaSize;
//        return snapSize(prefWidth) + rightInset + leftInset;
//    }

    private double maxh = 0.0d;
//    @Override protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
//        // The TabPane can only be as high as it highest content height.
////        for (com.sun.javafx.scene.control.skin.TabPaneSkin.TabContentRegion contentRegion: tabContentRegions) {
////            maxh = Math.max(maxh, snapSize(contentRegion.prefHeight(-1)));
////        }
////
////        final boolean isHorizontal = isHorizontal();
////        final double tabHeaderAreaSize = snapSize(isHorizontal ?
////                tabHeaderArea.prefHeight(-1) : tabHeaderArea.prefWidth(-1));
//
//        double prefHeight = isHorizontal ?
//                maxh + snapSize(tabHeaderAreaSize) : Math.max(maxh, tabHeaderAreaSize);
//        return snapSize(prefHeight) + topInset + bottomInset;
//    }

//    @Override public double computeBaselineOffset(double topInset, double rightInset, double bottomInset, double leftInset) {
//        Side tabPosition = getSkinnable().getSide();
//        if (tabPosition == Side.TOP) {
//            return tabHeaderArea.getBaselineOffset() + topInset;
//        }
//        return 0;
//    }

    @Override protected void layoutChildren(final double x, final double y,
                                            final double w, final double h) {
        FormPane formPane = getSkinnable();
    }

    private static class StyleableProperties {
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        private final static CssMetaData<TabPane, FormPaneSkin.FormAnimation> OPEN_TAB_ANIMATION =
                new CssMetaData<TabPane, FormAnimation>("-fx-open-tab-animation",
                        new EnumConverter<FormAnimation>(FormAnimation.class), FormAnimation.GROW) {

                    @Override public boolean isSettable(TabPane node) {
                        return true;
                    }

                    @Override public StyleableProperty<FormAnimation> getStyleableProperty(TabPane node) {
                        FormPaneSkin skin = (FormPaneSkin) node.getSkin();
                        return (StyleableProperty<FormAnimation>)(WritableValue<FormAnimation>)skin.openFormAnimation;
                    }
                };

        static {

            final List<CssMetaData<? extends Styleable, ?>> styleables =
                    new ArrayList<CssMetaData<? extends Styleable, ?>>(SkinBase.getClassCssMetaData());
            styleables.add(OPEN_TAB_ANIMATION);
            STYLEABLES = Collections.unmodifiableList(styleables);

        }
    }

    /**
     * @return The CssMetaData associated with this class, which may include the
     * CssMetaData of its super classes.
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return FormPaneSkin.StyleableProperties.STYLEABLES;
    }

    /**
     * {@inheritDoc}
     */
    @Override public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    static int CLOSE_BTN_SIZE = 16;

    /**************************************************************************
     *
     * TabHeaderSkin: skin for each tab
     *
     **************************************************************************/

    class TabHeaderSkin extends StackPane {
        private final Tab tab;
        public Tab getTab() {
            return tab;
        }
        private Label label;
        private StackPane closeBtn;
        private StackPane inner;
        private Tooltip oldTooltip;
        private Tooltip tooltip;
        private Rectangle clip;

        private boolean isClosing = false;

        private MultiplePropertyChangeListenerHandler listener =
                new MultiplePropertyChangeListenerHandler(param -> {
                    handlePropertyChanged(param);
                    return null;
                });

        private final ListChangeListener<String> styleClassListener = new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                getStyleClass().setAll(tab.getStyleClass());
            }
        };

        private final WeakListChangeListener<String> weakStyleClassListener =
                new WeakListChangeListener<>(styleClassListener);

        public TabHeaderSkin(final Tab tab) {
            getStyleClass().setAll(tab.getStyleClass());
            setId(tab.getId());
            setStyle(tab.getStyle());
            setAccessibleRole(AccessibleRole.TAB_ITEM);

            this.tab = tab;
            clip = new Rectangle();
            setClip(clip);

            label = new Label(tab.getText(), tab.getGraphic());
            label.getStyleClass().setAll("tab-label");

            closeBtn = new StackPane() {
                @Override protected double computePrefWidth(double h) {
                    return CLOSE_BTN_SIZE;
                }
                @Override protected double computePrefHeight(double w) {
                    return CLOSE_BTN_SIZE;
                }
                @Override
                public void executeAccessibleAction(AccessibleAction action, Object... parameters) {
                    switch (action) {
                        case FIRE: {
//                            Tab tab = getRow();
//                            TabPaneBehavior behavior = getBehavior();
//                            if (behavior.canCloseTab(tab)) {
//                                behavior.closeTab(tab);
//                                setOnMousePressed(null);
//                            }
                        }
                        default: super.executeAccessibleAction(action, parameters);
                    }
                }
            };
            closeBtn.setAccessibleRole(AccessibleRole.BUTTON);
//            closeBtn.setAccessibleText(getString("Accessibility.title.TabPane.CloseButton"));
            closeBtn.getStyleClass().setAll("tab-close-button");
            closeBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent me) {
//                    Tab tab = getTab();
//                    TabPaneBehavior behavior = getBehavior();
//                    if (behavior.canCloseTab(tab)) {
//                        behavior.closeTab(tab);
//                        setOnMousePressed(null);
//                    }
                }
            });

//            updateGraphicRotation();

            final Region focusIndicator = new Region();
            focusIndicator.setMouseTransparent(true);
            focusIndicator.getStyleClass().add("focus-indicator");

            inner.getStyleClass().add("tab-container");
//            inner.setRotate(getSkinnable().getSide().equals(Side.BOTTOM) ? 180.0F : 0.0F);
            inner.getChildren().addAll(label, closeBtn, focusIndicator);

            getChildren().addAll(inner);

            tooltip = tab.getTooltip();
            if (tooltip != null) {
                Tooltip.install(this, tooltip);
                oldTooltip = tooltip;
            }

            listener.registerChangeListener(tab.closableProperty(), "CLOSABLE");
            listener.registerChangeListener(tab.selectedProperty(), "SELECTED");
            listener.registerChangeListener(tab.textProperty(), "TEXT");
            listener.registerChangeListener(tab.graphicProperty(), "GRAPHIC");
            listener.registerChangeListener(tab.contextMenuProperty(), "CONTEXT_MENU");
            listener.registerChangeListener(tab.tooltipProperty(), "TOOLTIP");
            listener.registerChangeListener(tab.disableProperty(), "DISABLE");
            listener.registerChangeListener(tab.styleProperty(), "STYLE");

            tab.getStyleClass().addListener(weakStyleClassListener);

//            listener.registerChangeListener(getSkinnable().tabClosingPolicyProperty(), "TAB_CLOSING_POLICY");
//            listener.registerChangeListener(getSkinnable().sideProperty(), "SIDE");
//            listener.registerChangeListener(getSkinnable().rotateGraphicProperty(), "ROTATE_GRAPHIC");
//            listener.registerChangeListener(getSkinnable().tabMinWidthProperty(), "TAB_MIN_WIDTH");
//            listener.registerChangeListener(getSkinnable().tabMaxWidthProperty(), "TAB_MAX_WIDTH");
//            listener.registerChangeListener(getSkinnable().tabMinHeightProperty(), "TAB_MIN_HEIGHT");
//            listener.registerChangeListener(getSkinnable().tabMaxHeightProperty(), "TAB_MAX_HEIGHT");

            getProperties().put(Tab.class, tab);
            getProperties().put(ContextMenu.class, tab.getContextMenu());

            setOnContextMenuRequested((ContextMenuEvent me) -> {
                if (getTab().getContextMenu() != null) {
                    getTab().getContextMenu().show(inner, me.getScreenX(), me.getScreenY());
                    me.consume();
                }
            });
            setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent me) {
                    if (getTab().isDisable()) {
                        return;
                    }
//                    if (me.getButton().equals(MouseButton.MIDDLE)) {
//                        if (showCloseButton()) {
//                            Tab tab = getTab();
//                            TabPaneBehavior behavior = getBehavior();
//                            if (behavior.canCloseTab(tab)) {
//                                removeListeners(tab);
//                                behavior.closeTab(tab);
//                            }
//                        }
//                    } else if (me.getButton().equals(MouseButton.PRIMARY)) {
//                        getBehavior().selectTab(getTab());
//                    }
                }
            });

            // initialize pseudo-class state
            pseudoClassStateChanged(SELECTED_PSEUDOCLASS_STATE, tab.isSelected());
            pseudoClassStateChanged(DISABLED_PSEUDOCLASS_STATE, tab.isDisable());
//            final Side side = getSkinnable().getSide();
        }

        private void handlePropertyChanged(final String p) {
            // --- Tab properties
            if ("CLOSABLE".equals(p)) {
                inner.requestLayout();
                requestLayout();
            } else if ("SELECTED".equals(p)) {
                pseudoClassStateChanged(SELECTED_PSEUDOCLASS_STATE, tab.isSelected());
                // Need to request a layout pass for inner because if the width
                // and height didn't not change the label or close button may have
                // changed.
                inner.requestLayout();
                requestLayout();
            } else if ("TEXT".equals(p)) {
                label.setText(getTab().getText());
            } else if ("GRAPHIC".equals(p)) {
                label.setGraphic(getTab().getGraphic());
            } else if ("CONTEXT_MENU".equals(p)) {
                // todo
            } else if ("TOOLTIP".equals(p)) {
                // uninstall the old tooltip
                if (oldTooltip != null) {
                    Tooltip.uninstall(this, oldTooltip);
                }
                tooltip = tab.getTooltip();
                if (tooltip != null) {
                    // install new tooltip and save as old tooltip.
                    Tooltip.install(this, tooltip);
                    oldTooltip = tooltip;
                }
            } else if ("DISABLE".equals(p)) {
                pseudoClassStateChanged(DISABLED_PSEUDOCLASS_STATE, tab.isDisable());
                inner.requestLayout();
                requestLayout();
            } else if ("STYLE".equals(p)) {
                setStyle(tab.getStyle());
            }

            // --- Skinnable properties
            else if ("TAB_CLOSING_POLICY".equals(p)) {
                inner.requestLayout();
                requestLayout();
            } else if ("SIDE".equals(p)) {
//                final Side side = getSkinnable().getSide();
//                pseudoClassStateChanged(TOP_PSEUDOCLASS_STATE, (side == Side.TOP));
//                pseudoClassStateChanged(RIGHT_PSEUDOCLASS_STATE, (side == Side.RIGHT));
//                pseudoClassStateChanged(BOTTOM_PSEUDOCLASS_STATE, (side == Side.BOTTOM));
//                pseudoClassStateChanged(LEFT_PSEUDOCLASS_STATE, (side == Side.LEFT));
//                inner.setRotate(side == Side.BOTTOM ? 180.0F : 0.0F);
//                if (getSkinnable().isRotateGraphic()) {
//                    updateGraphicRotation();
//                }
            } else if ("ROTATE_GRAPHIC".equals(p)) {
//                updateGraphicRotation();
            } else if ("TAB_MIN_WIDTH".equals(p)) {
                requestLayout();
                getSkinnable().requestLayout();
            } else if ("TAB_MAX_WIDTH".equals(p)) {
                requestLayout();
                getSkinnable().requestLayout();
            } else if ("TAB_MIN_HEIGHT".equals(p)) {
                requestLayout();
                getSkinnable().requestLayout();
            } else if ("TAB_MAX_HEIGHT".equals(p)) {
                requestLayout();
                getSkinnable().requestLayout();
            }
        }

//        private void updateGraphicRotation() {
//            if (label.getGraphic() != null) {
//                label.getGraphic().setRotate(getSkinnable().isRotateGraphic() ? 0.0F :
//                        (getSkinnable().getSide().equals(Side.RIGHT) ? -90.0F :
//                                (getSkinnable().getSide().equals(Side.LEFT) ? 90.0F : 0.0F)));
//            }
//        }

        private final DoubleProperty animationTransition = new SimpleDoubleProperty(this, "animationTransition", 1.0) {
            @Override protected void invalidated() {
                requestLayout();
            }
        };

        private void removeListeners(Tab tab) {
            listener.dispose();
            inner.getChildren().clear();
            getChildren().clear();
        }

        private Timeline currentAnimation;

        @Override protected double computePrefWidth(double height) {
//            if (animating) {
//                return prefWidth.getValue();
//            }
            double minWidth = snapSize(getSkinnable().getMinWidth());
            double maxWidth = snapSize(getSkinnable().getMaxWidth());
            double paddingRight = snappedRightInset();
            double paddingLeft = snappedLeftInset();
            double tmpPrefWidth = snapSize(label.prefWidth(-1));

            // only include the close button width if it is relevant
//            if (showCloseButton()) {
//                tmpPrefWidth += snapSize(closeBtn.prefWidth(-1));
//            }

            if (tmpPrefWidth > maxWidth) {
                tmpPrefWidth = maxWidth;
            } else if (tmpPrefWidth < minWidth) {
                tmpPrefWidth = minWidth;
            }
            tmpPrefWidth += paddingRight + paddingLeft;
//            prefWidth.setValue(tmpPrefWidth);
            return tmpPrefWidth;
        }

        @Override protected double computePrefHeight(double width) {
            double minHeight = snapSize(getSkinnable().getMinHeight());
            double maxHeight = snapSize(getSkinnable().getMaxHeight());
            double paddingTop = snappedTopInset();
            double paddingBottom = snappedBottomInset();
            double tmpPrefHeight = snapSize(label.prefHeight(width));

            if (tmpPrefHeight > maxHeight) {
                tmpPrefHeight = maxHeight;
            } else if (tmpPrefHeight < minHeight) {
                tmpPrefHeight = minHeight;
            }
            tmpPrefHeight += paddingTop + paddingBottom;
            return tmpPrefHeight;
        }

        @Override protected void layoutChildren() {
            double w = (snapSize(getWidth()) - snappedRightInset() - snappedLeftInset()) * animationTransition.getValue();
            inner.resize(w, snapSize(getHeight()) - snappedTopInset() - snappedBottomInset());
            inner.relocate(snappedLeftInset(), snappedTopInset());
        }

        @Override protected void setWidth(double value) {
            super.setWidth(value);
            clip.setWidth(value);
        }

        @Override protected void setHeight(double value) {
            super.setHeight(value);
            clip.setHeight(value);
        }

        @Override
        public Object queryAccessibleAttribute(AccessibleAttribute attribute, Object... parameters) {
            switch (attribute) {
                case TEXT: return getTab().getText();
//                case SELECTED: return selectedTab == getTab();
                default: return super.queryAccessibleAttribute(attribute, parameters);
            }
        }

        @Override
        public void executeAccessibleAction(AccessibleAction action, Object... parameters) {
            switch (action) {
                case REQUEST_FOCUS:
//                    getSkinnable().getSelectionModel().select(getTab());
                    break;
                default: super.executeAccessibleAction(action, parameters);
            }
        }

    } /* End TabHeaderSkin */

    private static final PseudoClass SELECTED_PSEUDOCLASS_STATE =
            PseudoClass.getPseudoClass("selected");
    private static final PseudoClass TOP_PSEUDOCLASS_STATE =
            PseudoClass.getPseudoClass("top");
    private static final PseudoClass BOTTOM_PSEUDOCLASS_STATE =
            PseudoClass.getPseudoClass("bottom");
    private static final PseudoClass LEFT_PSEUDOCLASS_STATE =
            PseudoClass.getPseudoClass("left");
    private static final PseudoClass RIGHT_PSEUDOCLASS_STATE =
            PseudoClass.getPseudoClass("right");
    private static final PseudoClass DISABLED_PSEUDOCLASS_STATE =
            PseudoClass.getPseudoClass("disabled");

    @Override
    public Object queryAccessibleAttribute(AccessibleAttribute attribute, Object... parameters) {
        switch (attribute) {
//            case FOCUS_ITEM: return tabHeaderArea.getTabHeaderSkin(selectedTab);
//            case ITEM_COUNT: return tabHeaderArea.headersRegion.getChildren().size();
//            case ITEM_AT_INDEX: {
//                Integer index = (Integer)parameters[0];
//                if (index == null) return null;
//                return tabHeaderArea.headersRegion.getChildren().get(index);
//            }
            default: return super.queryAccessibleAttribute(attribute, parameters);
        }
    }
}
