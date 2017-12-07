package ldh.common.ui.node;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.decoration.GraphicDecoration;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.controlsfx.control.decoration.Decorator.addDecoration;

/**
 * Created by xiongfei.lei on 2017/11/22.
 */
public class FormContent2 extends VBox implements Initializable{

    ValidationSupport validationSupport = null;

    @FXML private TextField emailField2;
    @FXML private TextField emailField;

    public FormContent2() {
        validationSupport = new ValidationSupport();
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/node/FormContent2.fxml"));
            fxmlloader.setRoot(this);
            fxmlloader.setController(this);
            fxmlloader.load();
        }catch(IOException ex){
            ex.printStackTrace();
        }

    }

    @FXML public void chartContentClick() {
        addDecoration(emailField2, new GraphicDecoration(createImageNode(), Pos.BOTTOM_LEFT));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validationSupport.registerValidator(emailField2, Validator.createEmptyValidator("Text is required"));
        validationSupport.registerValidator(emailField, Validator.createEmptyValidator("Text is required"));


    }

    private Node createImageNode() {
        Image image = new Image("/img/security-low.png");
        ImageView imageView = new ImageView(image);
        return imageView;
    }
}
