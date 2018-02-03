package ldh.fx.component;

import javafx.stage.Stage;

/**
 * Created by ldh on 2018/2/3.
 */
public class LxDialog extends LxDialogBase {

    public LxDialog(Stage stage, String title, DialogModel dialogModel, double width, double height) {
        super();
        this.setTitle(title);
        initDialogModel(stage, dialogModel);
        this.setPrefSize(width, height);

        this.setMovable();
    }

    public LxDialog(Stage stage, String title, double width, double height) {
        this(stage, title, DialogModel.Normal, width, height);
    }


}
