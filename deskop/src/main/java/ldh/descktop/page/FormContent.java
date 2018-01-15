package ldh.descktop.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class FormContent extends VBox {

    public FormContent() {
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/FormContent.fxml"));
            fxmlloader.setRoot(this);
            fxmlloader.setController(this);
            fxmlloader.load();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML public void chartContentClick() {

    }
}
