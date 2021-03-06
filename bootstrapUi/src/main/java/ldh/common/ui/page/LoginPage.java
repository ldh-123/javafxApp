package ldh.common.ui.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import ldh.fx.component.LWindowBase;
import ldh.fx.component.LxWindowBase;

import java.io.IOException;

public class LoginPage extends LWindowBase {

    @FXML private Region topRectangle;
    @FXML private Region buttomRectangle;
    @FXML private GridPane loginPane;

    private Stage STAGE;

    public LoginPage(double width, double height) {
        super();
        buildMovable(this);
        this.setPrefHeight(height);this.setMinHeight(height);
        this.setPrefWidth(width);this.setMinWidth(width);

        loadFxl();
//        topRectangle.setWidth(width-3);
//        topRectangle.setHeight(height/2-1);
//        buttomRectangle.setWidth(width-3);
//        buttomRectangle.setHeight(height/2-2);

        AnchorPane.setBottomAnchor(topRectangle, height/2);
        AnchorPane.setTopAnchor(buttomRectangle, height/2);

        AnchorPane.setTopAnchor(loginPane, (height-loginPane.getPrefHeight())/2);
        AnchorPane.setLeftAnchor(loginPane, (width-loginPane.getPrefWidth())/2);
    }

    private void loadFxl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/page/LoginPage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setStage(Stage stage) {
        this.STAGE = stage;
    }

    @FXML
    public void closeBtn() {
        STAGE.close();
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
//        AnchorPane.setTopAnchor(loginPane, (this.getHeight()-loginPane.getBoundsInLocal().getHeight())/2);
//        AnchorPane.setLeftAnchor(loginPane, (this.getWidth()-loginPane.getBoundsInLocal().getWidth())/2);
    }

}
