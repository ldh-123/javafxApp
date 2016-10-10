package ldh.musicfx.ui.nav;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ldh.musicfx.model.NavData;
import ldh.musicfx.model.NavGroup;

/**
 * Created by Puhui on 2016/10/8.
 */
public class NavDataGroupPane extends VBox {

    private NavGroup navGroup;
    private BooleanProperty isToggleBooleanProperty;

    public NavDataGroupPane(NavGroup navGroup, BooleanProperty isToggleBooleanProperty) {
        this.navGroup = navGroup;
        this.isToggleBooleanProperty = isToggleBooleanProperty;
        initUI();
    }

    private void initUI() {
        this.getChildren().clear();
//        VBox vbox = new VBox();
//        vbox.setPadding(new Insets(15));
//        vbox.setSpacing(10);
//        vbox.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label(navGroup.getTitle());
        label.getStyleClass().add("navPane-title");
        this.getChildren().add(label);
        for (NavData navData : navGroup.getNavDataList()) {
            NavDataPane navDataPane = new NavDataPane(navData, isToggleBooleanProperty);
            this.getChildren().add(navDataPane);
        }
    }
}
