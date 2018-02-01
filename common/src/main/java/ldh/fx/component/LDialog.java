package ldh.fx.component;

import javafx.stage.*;

import java.util.logging.Logger;

public class LDialog extends LDialogBase {

    private Logger logger = Logger.getLogger(LDialog.class.getName());

    public LDialog(Stage stage, String titleStr, Double width, double height) {
        this(stage, titleStr, width, height, DialogModel.Normal);
    }

    public LDialog(Stage stage, String titleStr, Double width, double height, DialogModel dialogModel) {
        super();
        this.initDialogModel(stage, dialogModel);
        setTitle(titleStr);
        this.setPrefSize(width, height);
    }
}
