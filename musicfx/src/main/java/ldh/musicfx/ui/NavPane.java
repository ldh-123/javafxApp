package ldh.musicfx.ui;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ldh.musicfx.model.NavData;
import ldh.musicfx.model.NavGroup;
import ldh.musicfx.ui.nav.NavDataGroupPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Puhui on 2016/9/27.
 */
public class NavPane extends VBox {

    @FXML
    StackPane headPane;
    @FXML
    ScrollPane navContentPane;

    private BooleanProperty isToggleBooleanProperty;

    public NavPane(BooleanProperty isToggleBooleanProperty) {
        this.isToggleBooleanProperty = isToggleBooleanProperty;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NavPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        this.getStyleClass().add("navPaneContain");

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        String imgFile = getClass().getClassLoader().getResource("img/music.jpg").toExternalForm();
        TitlePane titlePane = new TitlePane(imgFile, "Toggle", isToggleBooleanProperty);
        headPane.getChildren().add(titlePane);

        PopupButtonPane popupButtonPane = new PopupButtonPane(isToggleBooleanProperty);
        this.getChildren().add(popupButtonPane);

        List<NavGroup> navGroups = buildNavGroup();
        VBox vbox = new VBox();
        for (NavGroup navGroup : navGroups) {
            NavDataGroupPane navDataGroupPane = new NavDataGroupPane(navGroup, isToggleBooleanProperty);
            vbox.getChildren().add(navDataGroupPane);
        }
        this.navContentPane.setContent(vbox);
    }

    private List<NavGroup> buildNavGroup() {
        List<NavGroup> navGroups = new ArrayList<>();
        for (int i=0; i<2; i++) {
            List<NavData> navDatas = new ArrayList<>();
            for (int j=0; j<5; j++) {
                NavData navData = new NavData();
                String imgFile = getClass().getClassLoader().getResource("img/music.jpg").toExternalForm();
                navData.setImageSrc(imgFile);
                navData.setText("test_" + j);
                navDatas.add(navData);

                List<NavData> children = new ArrayList<>();
                for (int k=0; k<4; k++) {
                    NavData navData2 = new NavData();
                    String imgFile2 = getClass().getClassLoader().getResource("img/music.jpg").toExternalForm();
                    navData2.setImageSrc(imgFile2);
                    navData2.setText("test" + j);
                    children.add(navData2);
                }
                navData.setChildren(children);
            }
            NavGroup nb = new NavGroup("test" + i, navDatas);
            navGroups.add(nb);
        }

        return navGroups;
    }
}
