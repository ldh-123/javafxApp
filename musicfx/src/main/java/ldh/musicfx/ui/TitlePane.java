package ldh.musicfx.ui;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * Created by Puhui on 2016/9/24.
 */
public class TitlePane extends StackPane {

    private StringProperty imgURLStringProperty = new SimpleStringProperty();
    private StringProperty titleStringProperty = new SimpleStringProperty();
    private ObjectProperty<EventHandler> eventHandlerObjectProperty = new SimpleObjectProperty<>();

    public TitlePane(String imgUrl, String title, BooleanProperty isToggleProperty) {
        imgURLStringProperty.set(imgUrl);
        titleStringProperty.set(title);
        initUI(isToggleProperty.getValue());
        bindIsToggle(isToggleProperty);
    }

    private void bindIsToggle(BooleanProperty isToggleProperty) {
        isToggleProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                initUI(newValue);
            }
        });
    }

    private void initUI(Boolean newValue) {
        this.getChildren().clear();
        if (!newValue) {
            HBox hbox = new HBox();
            hbox.setPadding(new Insets(10));
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER_LEFT);
            Image img = new Image(imgURLStringProperty.get());
            ImageView imageView = new ImageView(img);
            imageView.setFitHeight(35);
            imageView.setFitWidth(35);
            Label label = new Label(titleStringProperty.get());
            hbox.getChildren().addAll(imageView, label);
            this.getChildren().add(hbox);
        } else {
            HBox hbox = new HBox();
            hbox.setPadding(new Insets(10));
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER_LEFT);
            Image img = new Image(imgURLStringProperty.get());
            ImageView imageView = new ImageView(img);
            imageView.setFitHeight(35);
            imageView.setFitWidth(35);
            Label label = new Label(titleStringProperty.get());
            hbox.getChildren().addAll(imageView);
            this.getChildren().add(hbox);
        }

    }

    public String getImgURLStringProperty() {
        return imgURLStringProperty.get();
    }

    public StringProperty imgURLStringPropertyProperty() {
        return imgURLStringProperty;
    }

    public void setImgURLStringProperty(String imgURLStringProperty) {
        this.imgURLStringProperty.set(imgURLStringProperty);
    }

    public String getTitleStringProperty() {
        return titleStringProperty.get();
    }

    public StringProperty titleStringPropertyProperty() {
        return titleStringProperty;
    }

    public void setTitleStringProperty(String titleStringProperty) {
        this.titleStringProperty.set(titleStringProperty);
    }

    public EventHandler getEventHandlerObjectProperty() {
        return eventHandlerObjectProperty.get();
    }

    public ObjectProperty<EventHandler> eventHandlerObjectPropertyProperty() {
        return eventHandlerObjectProperty;
    }

    public void setEventHandlerObjectProperty(EventHandler eventHandlerObjectProperty) {
        this.eventHandlerObjectProperty.set(eventHandlerObjectProperty);
    }
}
