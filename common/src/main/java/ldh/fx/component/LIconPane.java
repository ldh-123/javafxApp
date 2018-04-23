package ldh.fx.component;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphIcons;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.octicons.OctIcon;
import de.jensd.fx.glyphs.weathericons.WeatherIcon;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import ldh.fx.ui.util.RegionUtil;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ldh on 2018/4/18.
 */
public class LIconPane extends BorderPane {

    private Class[] glyphIconClasses;
    private Map<String, IconNode> glyphIconNodeMap = new HashMap<>();
    private Map<String, List<Label>> labelMap = new HashMap<>();
    private String currentClass = null;
    private StackPane contentPane = new StackPane();
    private Label iconLabel = new Label();
    private Slider slider = new Slider(10, 100, 30);
    private ColorPicker iconColor = new ColorPicker(Color.LIGHTBLUE);
    private TextArea codeTextArea;
    private ObjectProperty<Node> selectedNodeProperty = new SimpleObjectProperty<>();

    public LIconPane() {
        this(FontAwesomeIcon.class, MaterialDesignIcon.class, OctIcon.class, WeatherIcon.class);
    }

    public LIconPane(Class<? extends GlyphIcons> ...glyphIconClasses) {
        this.glyphIconClasses = glyphIconClasses;
        initLeftPane();
        initCenterPane();
        this.getStylesheets().add(LIconPane.class.getResource("/component/LIconPane.css").toExternalForm());
        this.getStyleClass().add("icon-pane");

        iconColor.setValue(Color.BLUEVIOLET);
    }

    public ObjectProperty<Node> getSelectedNodeProperty() {
        return selectedNodeProperty;
    }

    private void initCenterPane() {
        VBox centerPane = new VBox();
        centerPane.getStyleClass().add("icon-container");
        HBox searchPane = buildSearchPane();
        HBox iconPane = buildIconPane();

        int i = 0;
        for (Class clazz : glyphIconClasses) {
            if (clazz.isEnum()) {
                IconNode iconNode = new IconNode(clazz, i==0);
                iconNode.setUserData(i);
                glyphIconNodeMap.put(clazz.getSimpleName(), iconNode);
                contentPane.getChildren().add(iconNode);
                i++;
                if (i==1) {
                    currentClass = clazz.getSimpleName();
                } else {
                    iconNode.setVisible(false);
                }
            }
        }
        VBox.setVgrow(contentPane, Priority.ALWAYS);

        codeTextArea = new TextArea();
        codeTextArea.setEditable(false);
        codeTextArea.getStyleClass().add("code-text-area");
        centerPane.getChildren().addAll(searchPane, iconPane, contentPane, codeTextArea);

        this.setCenter(centerPane);
    }

    private HBox buildSearchPane() {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("search-pane");
        Label label = new Label("查询名称:");
        TextField textField = new TextField();
        textField.textProperty().addListener((b, o, newValue)->{
            IconNode iconNode = glyphIconNodeMap.get(currentClass);
            if (iconNode != null) {
                iconNode.filter(newValue);
            }
        });
        HBox.setHgrow(textField, Priority.ALWAYS);
        Button button = new Button("查询");
        hbox.getChildren().addAll(label, textField, button);
        return hbox;
    }

    private HBox buildIconPane() {
        HBox iconPane = new HBox();
        iconPane.getStyleClass().add("icon-box");
        Label label = new Label("Graphic");
        slider.valueProperty().addListener((b, o, n)->{
            Node node = iconLabel.getGraphic();
            if (node != null && node instanceof GlyphIcon) {
                ((GlyphIcon) node).setGlyphSize(n);
            }
        });
        iconColor = new ColorPicker();
        iconColor.valueProperty().addListener((b, o, n)->{
            Node node = iconLabel.getGraphic();
            if (node != null && node instanceof GlyphIcon) {
                ((GlyphIcon) node).setFill(n);
            }
        });
        iconPane.getChildren().addAll(label, iconLabel, slider, iconColor);
        return iconPane;
    }

    private void initLeftPane() {
        VBox box = new VBox();
        box.getStyleClass().add("icon-nav");
        for (Class clazz : glyphIconClasses) {
            if (clazz.isEnum()) {
                Button fontawareBtn = new Button(clazz.getSimpleName());
                fontawareBtn.setOnAction(e->{
                    IconNode currentIconNode = glyphIconNodeMap.get(currentClass);
                    currentIconNode.showData();
                    currentClass = fontawareBtn.getText();
                    IconNode iconNode = glyphIconNodeMap.get(currentClass);
                    iconNode.showData();
                    translate(currentIconNode, iconNode);
                    cleanActived(box);
                    fontawareBtn.getStyleClass().add("actived");
                });
                box.getChildren().addAll(fontawareBtn);
            }
        }
        this.setLeft(box);
    }

    private void cleanActived(VBox box) {
        for (Node node : box.getChildren()) {
            if (node instanceof Button) {
                node.getStyleClass().remove("actived");
            }
        }
    }

    private void translate(IconNode currentIconNode, IconNode iconNode) {
        int idx1 = (int) currentIconNode.getUserData();
        int idx2 = (int) iconNode.getUserData();
        if (idx1 == idx2) return;

        Rectangle clip = new Rectangle();
        clip.setWidth(contentPane.getWidth());
        clip.setHeight(contentPane.getHeight());
        contentPane.setClip(clip);

        TranslateTransition from = null;
        TranslateTransition to = null;
        if (idx1 > idx2) {
            from = new TranslateTransition(Duration.millis(400), currentIconNode);
            from.setFromY(0);
            from.setToY(-contentPane.getHeight());
            from.setOnFinished(e->currentIconNode.setVisible(false));

            to = new TranslateTransition(Duration.millis(400), iconNode);
            iconNode.setTranslateY(contentPane.getHeight());
            iconNode.setVisible(true);
            to.setFromY(contentPane.getHeight());
            to.setToY(0);
        } else {
            from = new TranslateTransition(Duration.millis(400), currentIconNode);
            from.setFromY(0);
            from.setToY(contentPane.getHeight());
            from.setOnFinished(e->currentIconNode.setVisible(false));

            to = new TranslateTransition(Duration.millis(400), iconNode);
            iconNode.setTranslateY(-contentPane.getHeight());
            iconNode.setVisible(true);
            to.setFromY(-contentPane.getHeight());
            to.setToY(0);
        }

        SequentialTransition sequentialTransition = new SequentialTransition(from, to);
        sequentialTransition.playFromStart();
    }

    private class IconNode extends ScrollPane {

        private Class glyphIconClass;
        private ObservableList<Label> labelObservableList = FXCollections.observableArrayList();
        private FilteredList<Label> filteredData = new FilteredList<>(labelObservableList, p -> true);
        private Constructor constructor = null;
        private FlowPane flowPane = new FlowPane();
        private boolean isShowing = false;

        public IconNode(Class glyphIconClass, boolean isShowing) {
            this.glyphIconClass = glyphIconClass;
            this.isShowing = isShowing;
            this.setFitToWidth(true);
            this.setFitToHeight(true);

            flowPane.getStyleClass().add("icon-flow-pane");
            this.setContent(flowPane);
            new Thread(new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    initUi();
                    return null;
                }
            }).start();
        }

        public void showData() {
            if (labelObservableList.size() > 0) return;
            List<Label> iconLables = labelMap.get(glyphIconClass.getSimpleName());
            labelObservableList.addAll(iconLables);
            flowPane.getChildren().addAll(filteredData);
            flowPane.requestLayout();
        }

        private void initUi() throws InterruptedException {
            if (glyphIconClass.isEnum()) {
                GlyphIcons[] tts = (GlyphIcons[]) glyphIconClass.getEnumConstants();
                List<Label> iconLables = new ArrayList<>();
                for (GlyphIcons glyphIcons : tts) {
                    Label iconLabel = new Label();
//                    GlyphIcon icon = new MaterialIconView((MaterialIcon) glyphIcons);
                    GlyphIcon icon = build(glyphIcons);
                    icon.setGlyphSize(26);
                    iconLabel.setGraphic(icon);
                    iconLabel.setOnMouseClicked(e -> clickLabel(icon));
                    iconLables.add(iconLabel);
                }
                labelMap.put(glyphIconClass.getSimpleName(), iconLables);
            }
            if (isShowing) {
                Platform.runLater(()->this.showData());
            }
        }

        private void clickLabel(GlyphIcon icon) {
            String name = icon.getClass().getSimpleName();
            String code = name + " " + icon.getGlyphName().toLowerCase() + "Icon = new " + name + "(" + glyphIconClass.getSimpleName() + "." + icon.getGlyphName() + ")";
            codeTextArea.setText(code);

            GlyphIcon newIcon = (GlyphIcon) RegionUtil.copyGraphic(icon);
            newIcon.setGlyphSize(slider.getValue());
            newIcon.setFill(iconColor.getValue());
            iconLabel.setGraphic(newIcon);

            selectedNodeProperty.set(RegionUtil.copyGraphic(newIcon));
        }

        private GlyphIcon copyGlyphIcon(GlyphIcon icon) {
            Enum enumIcon = Enum.valueOf(glyphIconClass, icon.getGlyphName());
            try {
                if (constructor == null) {
                    Class clazz = icon.getClass();
                    constructor = clazz.getDeclaredConstructor(enumIcon.getClass());
                }
                return (GlyphIcon) constructor.newInstance(enumIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private GlyphIcon build(GlyphIcons glyphIcons) {
            String className = glyphIcons.getClass().getName() + "View";
            try {
                if (constructor == null) {
                    Class clazz = Class.forName(className);
                    constructor = clazz.getDeclaredConstructor(glyphIcons.getClass());
                }
                return (GlyphIcon) constructor.newInstance(glyphIcons);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private void filter(String newValue) {
            filteredData.setPredicate(labelt -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                Node graphic = labelt.getGraphic();
                if (graphic instanceof GlyphIcon) {
                    if (((GlyphIcon) graphic).getGlyphName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false; // Does not match.
            });
            flowPane.getChildren().clear();
            flowPane.getChildren().addAll(filteredData);
        }
    }
}
